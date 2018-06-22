package com.ly.cloud.weekplan.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
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

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "BH")
	private String bh;

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
	 * 主办部门编号
	 */
	@TableField(value = "ZBBMBH")
	private String zbbmbh;

	/**
	 * 主办部门名称
	 */
	@TableField(value = "ZBBMMC")
	private String zbbmmc;

	/**
	 * 出席领导编号
	 */
	@TableField(value = "CXLDBH")
	private String cxldbh;

	/**
	 * 出席领导名称
	 */
	@TableField(value = "CXLDMC")
	private String cxldmc;

	/**
	 * 参加人员编号
	 */
	@TableField(value = "CJRYBH")
	private String cjrybh;

	/**
	 * 参加人员名称
	 */
	@TableField(value = "CJRYMC")
	private String cjrymc;
	
	
	/**
	 * 提醒人员编号
	 */
	@TableField(value = "TXRYBH")
	private String txrybh;

	/**
	 * 提醒人员名称
	 */
	@TableField(value = "TXRYMC")
	private String txrymc;

	/**
	 * 周程条目状态（有效:1，无效:2）
	 */
	@TableField(value = "ZT")
	private Integer zt;
	
	/**
	 * 排序号
	 */
	@TableField(value = "PXH")
	private Integer pxh;
	
	
	/**
	 * 是否使用会议室0否，1是
	 */
	@TableField(value = "SFHYS")
	private Integer sfhys;
	
	

}