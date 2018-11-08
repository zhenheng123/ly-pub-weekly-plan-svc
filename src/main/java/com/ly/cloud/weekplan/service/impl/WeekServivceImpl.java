package com.ly.cloud.weekplan.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ly.cloud.exception.biz.BusinessException;
import com.ly.cloud.web.utils.WebResponse;
import com.ly.cloud.weekplan.client.DailyPlanClient;
import com.ly.cloud.weekplan.dto.PushDto;
import com.ly.cloud.weekplan.entity.WeekEntity;
import com.ly.cloud.weekplan.entity.WeekItemEntity;
import com.ly.cloud.weekplan.mapper.WeekMapper;
import com.ly.cloud.weekplan.service.WeekItemServivce;
import com.ly.cloud.weekplan.service.WeekServivce;
import com.ly.cloud.weekplan.vo.WeekItemVo;
import com.ly.cloud.weekplan.vo.WeekVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * Class Name: WeekServivceImpl 
 * Description: 周程业务类的实现类
 * 
 * @author: Liyewang
 * @mail: Liyewang@ly-sky.com
 * @date: 2018年6月13日
 * @version: 1.0
 *
 */
@Service
@Slf4j
public class WeekServivceImpl extends ServiceImpl<WeekMapper, WeekEntity> implements WeekServivce {
	/**
	 * 未发布
	 */
	private final static int WEEK_PLAN_STATUS_UNPUBLISHED = 0;

	/**
	 * 已发布
	 */
	private final static int WEEK_PLAN_STATUS_PUBLISHED = 1;

	/**
	 * 有效周程项
	 */
	private final static int WEEK_PLAN_ITEM_STATUS_ACTIVE = 1;

	@Autowired
	WeekItemServivce weekItemServivce;

	@Autowired
	DailyPlanClient dailyPlanClient;

	@Override
	public WeekVo getById(String id, Integer izt) throws Exception {

		WeekEntity weekEntity = this.selectById(id);

		if (weekEntity == null) {
			return null;
		}

		List<WeekItemEntity> weekItemList = weekItemServivce.selectList(
				new EntityWrapper<WeekItemEntity>()
						.between("KSSJ", weekEntity.getKsrq(), weekEntity.getJsrq())
						.eq(izt != -1, "ZT", izt)
						.orderBy("KSSJ", true)
				);
		List<WeekItemVo> WeekItemVoList = new ArrayList<WeekItemVo>();
		for (WeekItemEntity item : weekItemList) {
			WeekItemVo weekItemVo = new WeekItemVo();
			BeanUtils.copyProperties(item, weekItemVo);
			weekItemVo.setXxrq(weekItemServivce.fmXXRQ(weekItemVo.getKssj()));
			weekItemVo.setXxsj(weekItemServivce.fmXXSJ(weekItemVo.getKssj(), weekItemVo.getJssj()));
			WeekItemVoList.add(weekItemVo);
		}
		WeekVo weekVo = new WeekVo();
		BeanUtils.copyProperties(weekEntity, weekVo);
		weekVo.setXxrq(weekItemServivce.fmZCRQ(weekVo.getKsrq(), weekVo.getJsrq()));
		weekVo.setWeekItemVoList(WeekItemVoList);
		return weekVo;

	}

    @Override
    public boolean update(WeekEntity newEntity) {
		boolean result = this.updateById(newEntity);
		if (newEntity.getZt() != null){
			if (newEntity.getZt() == WEEK_PLAN_STATUS_PUBLISHED) {
				WeekEntity oldEntity = this.selectById(newEntity.getBh());
				if (oldEntity.getZt() == WEEK_PLAN_STATUS_UNPUBLISHED) {//未发布转为发布
					syncDailyPlan(false, newEntity);
				} else if (oldEntity.getZt() == WEEK_PLAN_STATUS_PUBLISHED) {//原先就有发布，则清除旧日程数据
					syncDailyPlan(true, newEntity);
				} else {
					throw new BusinessException("周程发布状态错误");
				}
			} else if (newEntity.getZt() == WEEK_PLAN_STATUS_UNPUBLISHED){
				syncDailyPlan(true, newEntity);
			}
		}
		return result;
    }

	@Override
	public boolean delete(String[] ids) {
		List<WeekEntity> weekEntities = new ArrayList<>();
		for (String id : ids) {
			WeekEntity weekEntity = new WeekEntity();
			weekEntity.setBh(id);
			weekEntities.add(weekEntity);
		}
		boolean result = deleteBatchIds(Arrays.asList(ids));
		for (WeekEntity weekEntity : weekEntities) syncDailyPlan(true, weekEntity);
		return result;
	}

    @Override
    public List<WeekEntity> selectListByWeekItem(WeekItemEntity weekItemEntity) {
		List<WeekEntity> weekEntities = new ArrayList<>();
		if (weekItemEntity == null || weekItemEntity.getKssj() == null || weekItemEntity.getJssj() == null)
			return weekEntities;
		Wrapper<WeekEntity> wrapper = new EntityWrapper<>();
		wrapper.le("KSRQ", weekItemEntity.getKssj())
				.ge("JSRQ", weekItemEntity.getKssj())
				.eq("ZT", WEEK_PLAN_STATUS_PUBLISHED);
        return selectList(wrapper);
    }

    /**
	 * 同步日程
	 * @param deleteOldDate 是否删除旧数据
	 * @param weekEntity 周程实体类
	 */
	public void syncDailyPlan(boolean deleteOldDate, WeekEntity weekEntity) {
		//删除旧数据
		if (deleteOldDate) {
			WebResponse<Boolean> res = dailyPlanClient.deleteByTaskId(weekEntity.getBh());//用周程编号进行删除
            if (res == null) {
                log.error("远程调用日程服务失败！");
            } else if (!res.getMeta().isSuccess()) {
				log.error("删除旧日程数据失败: {}", res.getMeta().getMessage());
			}
		}

		//未发布只做删除旧数据操作
        if (weekEntity.getZt() == WEEK_PLAN_STATUS_UNPUBLISHED) return;

		//获取周程项列表
		List<WeekItemVo> weekItemVos = weekItemServivce.selectList(weekEntity.getBh(), WEEK_PLAN_ITEM_STATUS_ACTIVE);
		if (weekItemVos == null) return;

		//同步日程
		List<PushDto> pushDtoList = new ArrayList<>();
		for (WeekItemVo weekItemVo : weekItemVos) {
			List<String> userList = new ArrayList<>();
			if (weekItemVo.getTxrybh() != null) {
				userList = Arrays.asList(weekItemVo.getTxrybh().split(","));
			}
			//将周程编号作为任务编号
			PushDto pushDto = new PushDto(userList, weekItemVo.getKssj(), weekItemVo.getJssj(),
					weekItemVo.getNr(), weekEntity.getBh(), weekItemVo.getXsdd());
			pushDtoList.add(pushDto);
		}
		WebResponse<Boolean> batchPushRes = dailyPlanClient.batchPush(pushDtoList);
		if (batchPushRes == null) {
			log.error("远程调用日程服务失败！");
		} else if (!batchPushRes.getMeta().isSuccess()) {
			log.error("同步日程失败: {}", batchPushRes.getMeta().getMessage());
		}
	}

}
