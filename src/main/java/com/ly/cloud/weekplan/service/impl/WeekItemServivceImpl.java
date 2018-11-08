package com.ly.cloud.weekplan.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ly.cloud.weekplan.dto.WeekItemUpdateDto;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ly.cloud.common.mybatisplus.plugins.PageInfo;
import com.ly.cloud.exception.CloudException;
import com.ly.cloud.exception.biz.BusinessException;
import com.ly.cloud.web.utils.WebResponse;
import com.ly.cloud.weekplan.client.MeetingClient;
import com.ly.cloud.weekplan.common.utils.DateUtils;
import com.ly.cloud.weekplan.common.validator.ValidatorUtils;
import com.ly.cloud.weekplan.common.validator.group.AddGroup;
import com.ly.cloud.weekplan.dto.WeekItemDto;
import com.ly.cloud.weekplan.entity.WeekEntity;
import com.ly.cloud.weekplan.entity.WeekItemEntity;
import com.ly.cloud.weekplan.mapper.WeekItemMapper;
import com.ly.cloud.weekplan.service.WeekItemServivce;
import com.ly.cloud.weekplan.service.WeekServivce;
import com.ly.cloud.weekplan.vo.MeetingInfoVo;
import com.ly.cloud.weekplan.vo.WeekItemVo;

/**
 * 
 * Class Name: WeekServivceImpl  
 * Description: 周程项业务类的实现类
 * @author: Liyewang
 * @mail: Liyewang@ly-sky.com 
 * @date: 2018年6月13日
 * @version: 1.0
 *
 */
@Service
public class WeekItemServivceImpl extends ServiceImpl<WeekItemMapper, WeekItemEntity> implements WeekItemServivce {

	@Autowired
	WeekServivce weekServivce;
	
	@Autowired
	private WeekItemMapper weekItemMapper;
	
	@Autowired
	private MeetingClient meetingClient;
	/**
	 * (non-Javadoc)
	 * Title: addWeekItem
	 * Description: 添加周程项
	 * Created On: 2018年6月13日
	 * @author Liyewang
	 * <p>
	 * @param weekItemDto
	 * @param orgId
	 * @return
	 * @throws Exception  WeekItemServivceImpl
	 * @see WeekItemServivce#addWeekItem(WeekItemDto, String)
	 */
	@Override
	public boolean addWeekItem(WeekItemDto weekItemDto, String orgId) throws Exception {
		ValidatorUtils.validateEntity(weekItemDto,AddGroup.class);
		
		
		WeekItemEntity weekItemEntity = new WeekItemEntity();
		BeanUtils.copyProperties(weekItemDto, weekItemEntity);
		weekItemEntity.setOrgClass(orgId);
		
		
		//判断是否使用了会议室
		if(StringUtils.isNotBlank(weekItemDto.getHysbh())) {
			//处理关联的会议室
			weekItemEntity.setXsdd(weekItemDto.getHysbhmc());
			if(weekItemDto.getJssj()!=null && weekItemDto.getJssj().before(weekItemDto.getKssj())) {
				throw new CloudException("周程项的结束时间不能小于开始时间");
			}
		}
		//是否设置了提醒人员

		//同步日程
		syncDailyPlan(true, weekItemEntity);

		weekItemEntity.setBh(UUID.randomUUID().toString().replaceAll("-", ""));
		this.insert(weekItemEntity);
		return true;
	} 

	@Override
	public String fmXXSJ(Date kssj, Date jssj) throws Exception {
		String reStr="";
		Calendar calendar=Calendar.getInstance();
    	calendar.setTime(kssj);
		if(calendar.get(Calendar.HOUR_OF_DAY)<13) {
			reStr="上午";
		}else {
			reStr="下午";
		}
		reStr+=DateUtils.format(kssj,"hh:mm");
		if(jssj!=null) {
			calendar.setTime(jssj);
			if(calendar.get(Calendar.HOUR_OF_DAY)<13) {
				reStr+="至上午";
			}else {
				reStr+="至下午";
			}
			reStr+=DateUtils.format(jssj,"hh:mm");
		}
		return reStr;
	}

	@Override
	public String fmXXRQ(Date kssj) throws Exception {
		String reStr="";
		DateTime dateTime = new DateTime(kssj);
		reStr=dateTime.getMonthOfYear()+"月"+dateTime.getDayOfMonth()+"日";
		switch(dateTime.getDayOfWeek()) {
		  case 1:reStr+="星期一";break;
		  case 2:reStr+="星期二";break;
		  case 3:reStr+="星期三";break;
		  case 4:reStr+="星期四";break;
		  case 5:reStr+="星期五";break;
		  case 6:reStr+="星期六";break;
		  case 7:reStr+="星期日";break;
		}
		return reStr;
	}
	
	
	public String fmZCRQ(Date kssj,Date jssj) throws Exception {
		String reStr="";
		DateTime dateTime = new DateTime(kssj);
		reStr=dateTime.getMonthOfYear()+"月"+dateTime.getDayOfMonth()+"日";
		dateTime = new DateTime(jssj);
		reStr+="至"+dateTime.getMonthOfYear()+"月"+dateTime.getDayOfMonth()+"日";
		return reStr;
	}
	

	@Override
	public PageInfo<WeekItemVo> selectPage(int pageNum, int pageSize, String wid, Map<String,String> map, String orgId)
			throws Exception {
		WeekEntity weekEntity=null;
		Page<WeekItemEntity> page=new Page<WeekItemEntity>();
		if(StringUtils.isNotBlank(wid)) {
			weekEntity= weekServivce.selectById(wid);
			if(weekEntity!=null && weekEntity.getKsrq()!=null && weekEntity.getJsrq()!=null) {
				Collection<String> columns;
				page= this.selectPage(
						new Page<WeekItemEntity>(pageNum, pageSize), 
						new EntityWrapper<WeekItemEntity>()
							.between("KSSJ",DateUtils.setDayStar(weekEntity.getKsrq()) , DateUtils.setDayEnd(weekEntity.getJsrq()))
							.like(StringUtils.isNotBlank(map.get("nr")),"NR",map.get("nr"))
							.eq(map.get("zt")!=null, "ZT", map.get("zt"))
							.eq((orgId!=null)&&(!orgId.equals("-1")), "ORGCLASS", orgId)
							.orderBy("KSSJ", true)
							.orderBy("PXH",false)
						);
			}
		}else {
			page= this.selectPage(
					new Page<WeekItemEntity>(pageNum, pageSize), 
					new EntityWrapper<WeekItemEntity>()
						.le(null != map.get("jssj"), "JSSJ", DateUtils.stringToDate(map.get("jssj"), DateUtils.DATE_WITHOUTSEC_PATTERN24))
						.ge(null != map.get("kssj"), "KSSJ", DateUtils.stringToDate(map.get("kssj"), DateUtils.DATE_WITHOUTSEC_PATTERN24))
						.like(StringUtils.isNotBlank(map.get("nr")),"NR",map.get("nr"))
						.eq(map.get("hysbh")!=null, "HYSBH", map.get("hysbh"))
						.eq(map.get("zt")!=null, "ZT", map.get("zt"))
						.eq(map.get("sfhys")!=null, "SFHYS", map.get("sfhys"))
						.eq((orgId!=null)&&(!orgId.equals("-1")), "ORGCLASS", orgId)
						.orderBy("KSSJ", true)
						.orderBy("PXH",false)
					);
		}
		
		PageInfo<WeekItemVo> pageInfo=new PageInfo<WeekItemVo>();
		List<WeekItemVo> volist=new ArrayList<WeekItemVo>();
		if(page!=null && page.getRecords()!=null && page.getRecords().size()>0) {
			for(WeekItemEntity po:page.getRecords()) {
				WeekItemVo vo=new WeekItemVo();
				BeanUtils.copyProperties(po, vo);
				vo.setXxrq(this.fmXXRQ(vo.getKssj()));
				vo.setXxsj(this.fmXXSJ(vo.getKssj(), vo.getJssj()));
				volist.add(vo);
			}
		}
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageCount(page.getPages());
		pageInfo.setPageNum(pageNum);
		pageInfo.setPageSize(pageSize);
		pageInfo.setList(volist);
		return pageInfo;
	}

	@Override
	public List<WeekItemEntity> selectByWeekPlanId(String weekPlanId) {
		WeekEntity weekEntity = weekServivce.selectById(weekPlanId);
		if (weekEntity == null || weekEntity.getKsrq() == null || weekEntity.getJsrq() == null) return null;
		Wrapper<WeekItemEntity> wrapper = new EntityWrapper<>();
		wrapper.between("KSSJ", DateUtils.setDayStar(weekEntity.getKsrq()), DateUtils.setDayEnd(weekEntity.getJsrq()));
		return selectList(wrapper);
	}

	@Override
	public List<WeekItemVo> selectList(String id, Integer izt) throws Exception {
		WeekEntity weekEntity = weekServivce.selectById(id);
		if (weekEntity == null) {
			return null;
		}
		List<WeekItemEntity> weekItemList = this.selectList(
				new EntityWrapper<WeekItemEntity>()
						.between("KSSJ", weekEntity.getKsrq(), weekEntity.getJsrq())
						.eq(izt != -1, "ZT", izt)
						.orderBy("KSSJ", true)
				);
		List<WeekItemVo> WeekItemVoList = new ArrayList<WeekItemVo>();
		for (WeekItemEntity item : weekItemList) {
			WeekItemVo weekItemVo = new WeekItemVo();
			BeanUtils.copyProperties(item, weekItemVo);
			weekItemVo.setXxrq(this.fmXXRQ(weekItemVo.getKssj()));
			weekItemVo.setXxsj(this.fmXXSJ(weekItemVo.getKssj(), weekItemVo.getJssj()));
			WeekItemVoList.add(weekItemVo);
		}
		return WeekItemVoList;
	}

	@Override
	public List<MeetingInfoVo> conflict(WeekItemDto weekItemDto) throws BusinessException{
		List<MeetingInfoVo> list = new ArrayList<>();
		//查询周程会议室是否被占用
		List<WeekItemVo> weekItemVos = weekItemMapper.conflict(weekItemDto);
		if(null == weekItemVos || weekItemVos.size() == 0 ) {
			//远程调用会议检查冲突接口
			Map<String,Object> map = new HashMap<>();
			map.put("hysbh", weekItemDto.getHysbh());
			map.put("kssj", DateUtils.format(weekItemDto.getKssj(), DateUtils.DATE_WITHOUTSEC_PATTERN24));
			map.put("jssj", DateUtils.format(weekItemDto.getJssj(), DateUtils.DATE_WITHOUTSEC_PATTERN24));
			WebResponse<List<MeetingInfoVo>> result = meetingClient.conflictCheck(map);
			if(null != result && null != result.getData()) {
				return result.getData();
			}
		}
		for(WeekItemVo vo:weekItemVos) {
			MeetingInfoVo meetingInfoVo = new MeetingInfoVo();
			meetingInfoVo.setHymc(vo.getNr());
			meetingInfoVo.setKssj(DateUtils.format(weekItemDto.getKssj(), DateUtils.DATE_WITHOUTSEC_PATTERN24));
			meetingInfoVo.setJssj(DateUtils.format(weekItemDto.getJssj(), DateUtils.DATE_WITHOUTSEC_PATTERN24));
			list.add(meetingInfoVo);
		}
		return list;
	}
	
	
	public boolean update(WeekItemUpdateDto weekItemDto) {
		WeekItemEntity weekItemEntity = new WeekItemEntity();
		BeanUtils.copyProperties(weekItemDto, weekItemEntity);

		//同步日程
		syncDailyPlan(true, weekItemEntity);

		return updateById(weekItemEntity);
	}

	@Override
	public boolean deleteBatchIds(String[] ids) {
		for (String id : ids) {
			WeekItemEntity weekItemEntity = selectById(id);
			syncDailyPlan(true, weekItemEntity);
		}
		return false;
	}

	/**
	 * 同步日程数据
	 * @param deleteOldData 是否删除旧日程
	 * @param weekItemEntity 周程项实体类
	 */
	void syncDailyPlan(boolean deleteOldData, WeekItemEntity weekItemEntity) {
		List<WeekEntity> weekEntities = weekServivce.selectListByWeekItem(weekItemEntity);
		for (WeekEntity weekEntity : weekEntities) {
			weekServivce.syncDailyPlan(deleteOldData, weekEntity);
		}
	}
}

