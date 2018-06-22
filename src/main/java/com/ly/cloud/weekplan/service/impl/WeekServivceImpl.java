package com.ly.cloud.weekplan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ly.cloud.weekplan.entity.WeekEntity;
import com.ly.cloud.weekplan.entity.WeekItemEntity;
import com.ly.cloud.weekplan.mapper.WeekMapper;
import com.ly.cloud.weekplan.service.WeekItemServivce;
import com.ly.cloud.weekplan.service.WeekServivce;
import com.ly.cloud.weekplan.vo.WeekItemVo;
import com.ly.cloud.weekplan.vo.WeekVo;

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
public class WeekServivceImpl extends ServiceImpl<WeekMapper, WeekEntity> implements WeekServivce {

	@Autowired
	WeekItemServivce weekItemServivce;

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

}
