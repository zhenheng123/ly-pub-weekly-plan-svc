package com.ly.cloud.weekplan.vo;

import java.util.Date;

import com.ly.cloud.weekplan.dto.WeekItemDto;

import lombok.Data;

/**
 * 
 * Class Name: WeekItemEntity Description: 周程项VO
 * 
 * @author: Liyewang
 * @mail: Liyewang@ly-sky.com
 * @date: 2018年6月13日
 * @version: 1.0
 *
 */
@Data
public class WeekItemVo extends WeekItemDto{
	
	private static final long serialVersionUID = 1L;


	/**
	 * 显示日期
	 */
	private String  xxrq;
	
	
	/**
	 * 显示时间
	 */
	private String  xxsj;

}