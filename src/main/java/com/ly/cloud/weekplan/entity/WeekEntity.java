package com.ly.cloud.weekplan.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

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
@TableName("LY_BG_ZC_ZCB")
public class WeekEntity implements Serializable {
	

	
	/** 
	 * @Fields serialVersionUID : 
	 */ 
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value="BH")
    private String bh;

    /**
     * 周程标题
     */
	
	@TableField(value="ZCMC")
    private String zcmc;

    /**
     * 开始日期
     */
	@TableField(value="KSRQ")
    private Date ksrq;

    /**
     * 结束日期
     */
	@TableField(value="JSRQ")
    private Date jsrq;

    /**
     * 备注
     */
	@TableField(value="BZ")
    private String bz;
	
	/**
     * 周程的发布状态：0未发布，1已发布
     */
	@TableField(value="ZT")
    private int zt;

    /**
     * 所属机构
     */
	@TableField(value="ORGCLASS")
    private String orgclass;

}