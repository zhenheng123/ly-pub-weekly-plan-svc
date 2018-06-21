package com.ly.cloud.weekplan.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ly.cloud.common.mybatisplus.plugins.PageInfo;
import com.ly.cloud.exception.CloudException;
import com.ly.cloud.weekplan.common.utils.DateUtils;
import com.ly.cloud.weekplan.common.validator.ValidatorUtils;
import com.ly.cloud.weekplan.common.validator.group.AddGroup;
import com.ly.cloud.weekplan.dto.WeekItemDto;
import com.ly.cloud.weekplan.entity.WeekEntity;
import com.ly.cloud.weekplan.entity.WeekItemEntity;
import com.ly.cloud.weekplan.mapper.WeekItemMapper;
import com.ly.cloud.weekplan.service.WeekItemServivce;
import com.ly.cloud.weekplan.service.WeekServivce;
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
	
	/**
	 * (non-Javadoc)
	 * Title: addWeekItem
	 * Description: 添加周程项
	 * Created On: 2018年6月13日
	 * @author Liyewang
	 * <p>
	 * @param weekItemDto
	 * @return
	 * @throws Exception  WeekItemServivceImpl
	 * @see com.ly.cloud.weekplan.service.WeekItemServivce#addWeekItem(com.ly.cloud.weekplan.dto.WeekItemDto)
	 */
	@Override
	public boolean addWeekItem(WeekItemDto weekItemDto) throws Exception {
		ValidatorUtils.validateEntity(weekItemDto,AddGroup.class);
		
		
		WeekItemEntity weekItemEntity = new WeekItemEntity();
		BeanUtils.copyProperties(weekItemDto, weekItemEntity);
		
		
		if(weekItemDto.getJssj()!=null && weekItemDto.getJssj().before(weekItemDto.getKssj())) {
			throw new CloudException("周程项的结束时间不能小于开始时间");
		}
		//判断是否使用了会议室
		if(StringUtils.isBlank(weekItemDto.getHysbh())) {
			//处理关联的会议室
		}
		//是否设置了提醒人员
		
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

	@Override
	public PageInfo<WeekItemVo> selectList(int pageNum, int pageSize, String wid, String nr, Integer zt)
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
							.like(StringUtils.isNotBlank(nr),"NR",nr)
							.eq(zt!=null, "ZT", zt)
							.orderBy("KSSJ", true)
							.orderBy("PXH",false)
						);
			}
		}else {
			page= this.selectPage(
					new Page<WeekItemEntity>(pageNum, pageSize), 
					new EntityWrapper<WeekItemEntity>()
						.like(StringUtils.isNotBlank(nr),"NR",nr)
						.eq(zt!=null, "ZT", zt)
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

	
	
	
}
