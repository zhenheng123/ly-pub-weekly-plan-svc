package com.ly.cloud.weekplan.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 * 
 * Class Name: WeekItemEntity Description: 周程项实体表
 * 
 * @author: Liyewang
 * @mail: Liyewang@ly-sky.com
 * @date: 2018年6月13日
 * @version: 1.0
 *
 */
@Data
@TableName("LY_BG_ZC_ZCTM")
public class WeekItemEntity implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "BH")
	private String bh;

//	/**
//	 * 显示日期
//	 */
//	@TableField(value = "XSRQ")
//	private String xsrq;
//
//	/**
//	 * 显示时间
//	 */
//	@TableField(value = "XSSJ")
//	private String xssj;

	/**
	 * 开始时间
	 */
	@TableField(value = "KSSJ")
	private Date kssj;
	/**
	 * 结束时间
	 */
	@TableField(value = "JSSJ")
	private Date jssj;

	/**
	 * 显示地点
	 */
	@TableField(value = "XSDD")
	private String xsdd;

	/**
	 * 关联的会议室
	 */
	@TableField(value = "HYSBH")
	private String hysbh;

	/**
	 * 内容
	 */
	@TableField(value = "NR")
	private String nr;

	/**
	 * 主办部门
	 */
	@TableField(value = "ZBBM")
	private String zbbm;

	/**
	 * 出席领导
	 */
	@TableField(value = "CXLD")
	private String cxld;

	/**
	 * 参加人员编号
	 */
	@TableField(value = "XSCJRY")
	private String xscjry;


	/**
	 * 参加人员编号
	 */
	@TableField(value = "CJRYBH")
	private String cjrybh;
	
	/**
	 * 周程状态（待定:0，审批中:1，通过:2，有效:3，无效:4）
	 */
	@TableField(value = "ZT")
	private int zt;

}