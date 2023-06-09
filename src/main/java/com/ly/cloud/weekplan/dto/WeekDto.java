package com.ly.cloud.weekplan.dto;

import com.ly.cloud.weekplan.common.validator.group.AddGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Class Name: WeekEntity  
 * Description: 周程实体表
 * @author: Liyewang
 * @mail: Liyewang@ly-sky.com 
 * @date: 2018年6月13日
 * @version: 1.0
 *
 */
@Data
public class WeekDto implements Serializable {
	

	
	/** 
	 * @Fields serialVersionUID : 
	 */ 
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
    private String bh;

    /**
     * 周程标题
     */
	
    
    @NotBlank(message="周程标题不能为空", groups = AddGroup.class)
    private String zcmc;

    /**
     * 开始日期
     */
    @ApiModelProperty(value = "开始日期",example="2018-6-13")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message="开始日期不能为空", groups = AddGroup.class)
    private Date ksrq;

    /**
     * 结束日期
     */
    @ApiModelProperty(value = "结束日期",example="2018-6-13")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message="结束日期不能为空", groups = AddGroup.class)
    private Date jsrq;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;
    
    /**
     * 周程的发布状态：0未发布，1已发布
     */
    @ApiModelProperty(value = "周程的发布状态：0未发布，1已发布")
    private Integer zt;

    
    /**
     * 所属机构
     */
    @ApiModelProperty(value = "所属机构")
    private String orgclass;

    /**
     * 审批状态: 1.待审批,2.审批中,3.审批完毕
     */
    @ApiModelProperty(value = "审批状态: 1.待审批,2.审批中,3.审批完毕")
    private String approvalStatus;

}