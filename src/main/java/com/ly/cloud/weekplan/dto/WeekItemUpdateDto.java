package com.ly.cloud.weekplan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ly.cloud.weekplan.common.utils.excel.ExcelField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 
 * Class Name: WeekItemEntity Description: 周程项DTO
 * 
 * @author: Liyewang
 * @mail: Liyewang@ly-sky.com
 * @date: 2018年6月13日
 * @version: 1.0
 *
 */
@ApiModel(value = "周程项表单")
@Data
public class WeekItemUpdateDto {

	/**
	 * @Fields serialVersionUID :
	 */
//	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private String bh;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间",example="2018-6-13 12:00")
	@NotNull(message = "开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
	private Date kssj;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间" ,example="2018-6-13 14:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date jssj;

	/**
	 * 显示地点
	 */
	@ApiModelProperty(value = "显示地点")
	@NotBlank(message = "显示地点不能为空")
	@ExcelField(title="地点", align=2, sort=20)
	private String xsdd;

	/**
	 * 关联的会议室
	 */
	@ApiModelProperty(value = "关联的会议室")
	private String hysbh;
	
	
	/**
	 * 关联的会议室名称
	 */
	@ApiModelProperty(value = "关联的会议室")
	private String hysbhmc;

	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	@NotBlank(message = "内容不能为空")
	@ExcelField(title="内容", align=2, sort=20)
	private String nr;

	/**
	 * 主管领导编号
	 */
	@ApiModelProperty(value = "主管领导编号")
	private String zgldbh;

	/**
	 * 主管领导名称
	 */
	@ApiModelProperty(value = "主管领导名称")
	private String zgldmc;

	/**
	 * 主办部门编号
	 */
	@ApiModelProperty(value = "主办部门编号")
	private String zbbmbh;
	
	/**
	 * 主办部门名称
	 */
	@ApiModelProperty(value = "主办部门名称")
	@ExcelField(title="主管领导和部门", align=2, sort=20)
	private String zbbmmc;

	/**
	 * 出席领导编号
	 */
	@ApiModelProperty(value = "出席领导编号")
	private String cxldbh;
	
	/**
	 * 出席领导名称
	 */
	@ApiModelProperty(value = "出席领导名称")
	@ExcelField(title="出席领导", align=2, sort=20)
	private String cxldmc;

	/**
	 * 参加人员编号
	 */
	@ApiModelProperty(value = "参加人员编号")
	private String cjrybh;
	
	/**
	 * 参加人员名称
	 */
	@ApiModelProperty(value = "参加人员名称")
	@ExcelField(title="参加人员", align=2, sort=20)
	private String cjrymc;
	
	
	/**
	 * 提醒人员编号
	 */
	@ApiModelProperty(value = "提醒人员编号")
	private String txrybh;

	/**
	 * 提醒人员名称
	 */
	@ApiModelProperty(value = "提醒人员名称")
	private String txrymc;

	

	/**
	 * （周程条目状态（有效:1，无效:2）
	 */
	@ApiModelProperty(value = "周程条目状态（有效:1，无效:2）")
	private Integer zt;
	
	
	/**
	 *排序号
	 */
	@ApiModelProperty(value = "排序号")
	private Integer pxh;
	
	
	/**
	 * 是否使用会议室0否，1是
	 */
	@ApiModelProperty(value = "是否使用会议室0否，1是")
	private Integer sfhys;

	/**
	 * 审批状态: 1.待审批,2.审批中,3.审批完毕
	 */
	@ApiModelProperty(value = "审批状态: 1.待审批,2.审批中,3.审批完毕")
	private String approvalStatus;
	
	

}