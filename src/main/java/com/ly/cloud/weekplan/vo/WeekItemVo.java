package com.ly.cloud.weekplan.vo;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ly.cloud.weekplan.common.utils.excel.ExcelField;
import com.ly.cloud.weekplan.dto.WeekItemDto;

import io.swagger.annotations.ApiModelProperty;
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
public class WeekItemVo{
	
	
	/**
	 * 显示日期
	 */
	@ExcelField(title="日期", align=3, sort=20)
	private String  xxrq;
	
	
	/**
	 * 显示时间
	 */
	@ExcelField(title="时间", align=3, sort=20)
	private String  xxsj;

	
	/**
	 * 主键
	 */
	private String bh;

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
	@ExcelField(title="地点", align=4, sort=20)
	private String xsdd;

	/**
	 * 关联的会议室
	 */
	private String hysbh;
	
	
	/**
	 * 关联的会议室名称
	 */
	private String hysbhmc;

	/**
	 * 内容
	 */
	@ExcelField(title="内容", align=8, sort=20)
	private String nr;

	/**
	 * 主办部门编号
	 */
	private String zbbmbh;
	
	/**
	 * 主办部门名称
	 */
	@ExcelField(title="主管领导和部门", align=2, sort=20)
	private String zbbmmc;

	/**
	 * 出席领导编号
	 */
	private String cxldbh;
	
	/**
	 * 出席领导名称
	 */
	@ExcelField(title="出席领导", align=2, sort=20)
	private String cxldmc;

	/**
	 * 参加人员编号
	 */
	private String cjrybh;
	
	/**
	 * 参加人员名称
	 */
	@ExcelField(title="参加人员", align=4, sort=20)
	private String cjrymc;
	
	
	/**
	 * 提醒人员编号
	 */
	private String txrybh;

	/**
	 * 提醒人员名称
	 */
	private String txrymc;

	

	/**
	 * （周程条目状态（有效:1，无效:2）
	 */
	private Integer zt;
	
	
	/**
	 *排序号
	 */
	private Integer pxh;
	
	
	/**
	 * 是否使用会议室0否，1是
	 */
	private Integer sfhys;
	
}