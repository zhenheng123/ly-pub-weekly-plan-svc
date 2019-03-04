package com.ly.cloud.weekplan.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ly.cloud.weekplan.dto.WeekItemDto;
import com.ly.cloud.weekplan.entity.WeekItemEntity;
import com.ly.cloud.weekplan.vo.WeekItemVo;

/**
 * 
 * Class Name: WeekMapper  
 * Description: 周程项的数据访问层
 * @author: Liyewang
 * @mail: Liyewang@ly-sky.com 
 * @date: 2018年6月13日
 * @version: 1.0
 *
 */
public interface WeekItemMapper extends BaseMapper<WeekItemEntity> {
	/**周程会议室检查冲突*/
	List<WeekItemVo> conflict(@Param("vo") WeekItemDto weekItemDto);
	/**局领导检查冲突*/
	List<Map<String, Object>> getConflict(Map<String, Object> map);
	/**局领导是否有会议冲突*/
	List<Map<String, Object>> getMeetingConflict(Map<String, Object> map);
	/**查询本周内的周程条目数据*/
	List<WeekItemVo> getWeekplanList(@Param("vo")WeekItemDto weekItemDto);
	
	

}
  