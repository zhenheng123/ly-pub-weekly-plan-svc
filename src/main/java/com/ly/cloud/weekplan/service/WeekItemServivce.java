package com.ly.cloud.weekplan.service;

import com.baomidou.mybatisplus.service.IService;
import com.ly.cloud.weekplan.dto.WeekItemDto;
import com.ly.cloud.weekplan.entity.WeekItemEntity;



/**
 * 
 * Class Name: WeekServivce  
 * Description: 周程项的业务类
 * @author: Liyewang
 * @mail: Liyewang@ly-sky.com 
 * @date: 2018年6月13日
 * @version: 1.0
 *
 */
public interface WeekItemServivce extends IService<WeekItemEntity>{
	
	/**
	 * 
	 * Method Name:  addWeekItem
	 * Description:  添加周程项服务类
	 * @param weekItemDto
	 * @return
	 * @throws Exception 
	 * @return boolean
	 * @exception 	
	 * @author Liyewang
	 * @mail Liyewang@ly-sky.com
	 * @date: 2018年6月13日
	 */
	public boolean addWeekItem(WeekItemDto weekItemDto) throws Exception;



}