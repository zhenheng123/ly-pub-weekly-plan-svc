package com.ly.cloud.weekplan.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ly.cloud.common.mybatisplus.plugins.PageInfo;
import com.ly.cloud.exception.CloudException;
import com.ly.cloud.exception.biz.BusinessException;
import com.ly.cloud.web.utils.WebResponse;
import com.ly.cloud.weekplan.client.DailyPlanClient;
import com.ly.cloud.weekplan.client.MeetingClient;
import com.ly.cloud.weekplan.client.dto.LeaderDailyPlanDto;
import com.ly.cloud.weekplan.client.dto.LeaderDailyPlanVo;
import com.ly.cloud.weekplan.common.utils.DateUtils;
import com.ly.cloud.weekplan.common.validator.ValidatorUtils;
import com.ly.cloud.weekplan.common.validator.group.AddGroup;
import com.ly.cloud.weekplan.dto.WeekItemDto;
import com.ly.cloud.weekplan.dto.WeekItemUpdateDto;
import com.ly.cloud.weekplan.entity.WeekEntity;
import com.ly.cloud.weekplan.entity.WeekItemEntity;
import com.ly.cloud.weekplan.mapper.WeekItemMapper;
import com.ly.cloud.weekplan.service.WeekItemServivce;
import com.ly.cloud.weekplan.service.WeekServivce;
import com.ly.cloud.weekplan.vo.MeetingInfoVo;
import com.ly.cloud.weekplan.vo.WeekItemVo;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class WeekItemServivceImpl extends ServiceImpl<WeekItemMapper, WeekItemEntity> implements WeekItemServivce {

	private final WeekServivce weekServivce;
	
	private final WeekItemMapper weekItemMapper;
	
	private final MeetingClient meetingClient;
	
	private final DailyPlanClient dailyPlanClient;

	@Autowired
	public WeekItemServivceImpl(WeekItemMapper weekItemMapper,MeetingClient meetingClient, WeekServivce weekServivce,DailyPlanClient dailyPlanClient) {
		this.weekItemMapper = weekItemMapper;
		this.meetingClient = meetingClient;
		this.weekServivce = weekServivce;
		this.dailyPlanClient = dailyPlanClient;
	}

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
	 * @see WeekItemServivce#addWeekItem(WeekItemUpdateDto, String)
	 */
	@Override
	public boolean addWeekItem(WeekItemUpdateDto weekItemDto, String orgId) throws Exception {
		ValidatorUtils.validateEntity(weekItemDto,AddGroup.class);
		
		
		WeekItemEntity weekItemEntity = new WeekItemEntity();
		BeanUtils.copyProperties(weekItemDto, weekItemEntity);
		weekItemEntity.setOrgClass(orgId);
		
		
		//判断是否使用了会议室
		if(StringUtils.isNotBlank(weekItemDto.getHysbh())) {
			//处理关联的会议室
			//weekItemEntity.setXsdd(weekItemDto.getHysbhmc());
			weekItemEntity.setXsdd(weekItemDto.getXsdd());
			if(weekItemDto.getJssj()!=null && weekItemDto.getJssj().before(weekItemDto.getKssj())) {
				throw new CloudException("周程项的结束时间不能小于开始时间");
			}
		}
		//是否设置了提醒人员

		weekItemEntity.setBh(UUID.randomUUID().toString().replaceAll("-", ""));
		this.insert(weekItemEntity);
		//同步日程
		syncDailyPlan(true, weekItemEntity);
		return true;
	} 

	@Override
	public String fmXXSJ(Date kssj, Date jssj) {
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
	public String fmXXRQ(Date kssj) {
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
	public PageInfo<WeekItemVo> selectPage(int pageNum, int pageSize, String wid, Map<String,String> map, String orgId) {
		WeekEntity weekEntity;
		Page<WeekItemEntity> page=new Page<>();
		if(StringUtils.isNotBlank(wid)) {
			weekEntity= weekServivce.selectById(wid);
			if(weekEntity!=null && weekEntity.getKsrq()!=null && weekEntity.getJsrq()!=null) {
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
	public List<WeekItemVo> selectList(String id, Integer izt) {
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
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	@Override
	public boolean update(WeekItemUpdateDto weekItemDto) {
		WeekItemEntity weekItemEntity = new WeekItemEntity();
		BeanUtils.copyProperties(weekItemDto, weekItemEntity);
        boolean result = updateById(weekItemEntity);
        //同步日程
        try {
        	syncDailyPlan(true, weekItemEntity);
		} catch (Exception e) {
			log.error("同步日程出现异常:"+e.getMessage());
		}
        //同步局领导日程
        syncLeaderDailyPlan(weekItemEntity);
        return result;
	}

	private void syncLeaderDailyPlan(WeekItemEntity weekItemEntity) {
		try {
			WebResponse<List<LeaderDailyPlanVo>> result = dailyPlanClient.queryByWeekId(weekItemEntity.getBh());
			//判断之前是否同步过该条记录
			if(null != result && null != result.getData() && result.getData().size()>0) {
				//先删掉这条周程记录对应的局领导日程再同步
			//	dailyPlanClient.deleteListByItemId(weekItemEntity.getBh());
				List<String> ids = new ArrayList<>();
				ids.add(weekItemEntity.getBh());
				sync(ids,weekItemEntity.getOrgClass());
			}
		} catch (Exception e) {
			log.error("同步局领导日程出现异常:"+e.getMessage());
		}
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	@Override
	public boolean deleteBatchIds(String[] ids) {
		HashSet<WeekEntity> set = new HashSet<>();
		for (String id : ids) {
			List<WeekEntity> weekEntities = weekServivce.selectListByWeekItem(selectById(id));
			set.addAll(weekEntities);
		}
		//先删除数据再做日程同步
		boolean result = deleteBatchIds(Arrays.asList(ids));
		try {
			for (WeekEntity weekEntity : set) {
				weekServivce.syncDailyPlan(true, weekEntity);
			}
		} catch (Exception e) {
			log.error("同步日程出现异常:"+e.getMessage());
		}
		//同步局领导日程数据
		dailyPlanClient.deleteListByItemId(Arrays.asList(ids));
		return result;
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
	
	@Override
	public List<Map<String,Object>> getConflict(WeekItemDto weekItemDto, String orgId) throws BusinessException {
		List<Map<String,Object>> list = new ArrayList<>();
		String leaders = weekItemDto.getCxldbh();
		// 检查出席领导是否存在局领导日程
		if(StringUtils.isNotBlank(leaders)) {
			for(String leaderId : leaders.split(",")) {
				Map<String,Object> map = new HashMap<>();
				map.put("leaderId", leaderId);
				map.put("startTime", DateUtils.format(weekItemDto.getKssj(), DateUtils.DATE_WITHOUTSEC_PATTERN24));
				map.put("endTime", DateUtils.format(weekItemDto.getJssj(), DateUtils.DATE_WITHOUTSEC_PATTERN24));
				map.put("orgId", orgId);
				List<Map<String,Object>> leaderList = weekItemMapper.getConflict(map);
				if(null == leaderList || leaderList.size() ==0) {
					//检查出席领导在这个时间段是否有待参加的会议
					leaderList = weekItemMapper.getMeetingConflict(map);
				}
				list.addAll(leaderList);
			}
		}
		return list;
	}

	@Override
	public Page<WeekItemVo> getWeekplanList(Page<WeekItemVo> page,WeekItemDto weekItemDto) throws BusinessException {
		return page.setRecords(weekItemMapper.getWeekplanList(page,weekItemDto));
	}

	@Override
	public int sync(List<String> ids,String orgId) throws BusinessException {
		for(String id:ids) {
			WeekItemEntity entity = weekItemMapper.selectById(id);
			if(null != entity) {
				//根据条目id判断是否已同步过数据
				WebResponse<List<LeaderDailyPlanVo>> vo = dailyPlanClient.queryByWeekId(id);
				if(null != vo && null != vo.getData() && vo.getData().size()>0) {
					//删掉原数据
					List<String> source = new ArrayList<>();
					source.add(id);
					dailyPlanClient.deleteListByItemId(source);
					//continue;
				}
				//执行周程数据同步到局领导日程
				if(StringUtils.isNotBlank(entity.getZgldbh())) {
					String[] leadIds_1 = entity.getZgldbh().split(",");
					String[] leadNames_1 = entity.getZgldmc().split(",");
					dataSync(leadIds_1,leadNames_1,entity,orgId);
				}
				if(StringUtils.isNotBlank(entity.getCxldbh())) {
					String[] leadIds_2 = entity.getCxldbh().split(",");
					String[] leadNames_2 = entity.getCxldmc().split(",");
					dataSync(leadIds_2,leadNames_2,entity,orgId);
				}
				return 1;
			}
		}
		return 0;
	}

	private void dataSync(String[] leadIds,String[] leadNames,WeekItemEntity entity,String orgId) {
		for(int i = 0;i<leadIds.length;i++) {
			//组装数据
			LeaderDailyPlanDto dto = new LeaderDailyPlanDto();
			//dto.setBh(entity.getBh());
			dto.setLdbh(leadIds[i]);
			dto.setLdxm(leadNames[i]);
			dto.setSx(entity.getNr());
			dto.setKssj(DateUtils.format(entity.getKssj(), DateUtils.DATE_WITHOUTSEC_PATTERN24));
			dto.setJssj(DateUtils.format(entity.getJssj(), DateUtils.DATE_WITHOUTSEC_PATTERN24));
			dto.setDd(entity.getXsdd());
			dto.setXq(DateUtils.getWeekOfDate(entity.getKssj()));
			dto.setWeekItemId(entity.getBh());
			//远程调用局领导日程新增接口
			dailyPlanClient.insert(dto, orgId);
		}
	}
}

