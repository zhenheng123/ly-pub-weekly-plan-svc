package com.ly.cloud.weekplan.vo;

import java.io.Serializable;
import java.util.Date;


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
public class WeekItemVo implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String bh;

	/**
	 * 显示日期
	 */
	private String xsrq;

	/**
	 * 显示时间
	 */
	private String xssj;

	/**
	 * 开始时间
	 */
	private Date kssj;
	/**
	 * 结束时间
	 */
	private Date jssj;

	/**
	 * 显示地点
	 */
	private String xsdd;

	/**
	 * 关联的会议室
	 */
	private String hysbh;

	/**
	 * 内容
	 */
	private String nr;

	/**
	 * 主办部门
	 */
	private String zbbm;

	/**
	 * 出席领导
	 */
	private String cxld;

	/**
	 * 参加人员编号
	 */
	private String xscjry;


	/**
	 * 参加人员编号
	 */
	private String cjrybh;

}