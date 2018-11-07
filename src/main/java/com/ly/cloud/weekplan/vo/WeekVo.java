package com.ly.cloud.weekplan.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ly.cloud.weekplan.entity.WeekItemEntity;

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
public class WeekVo implements Serializable {
	

	
	/** 
	 * @Fields serialVersionUID : 
	 */ 
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
    private String bh;

    /**
     * 周程标题
     */
	
    private String zcmc;

    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date ksrq;

    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date jsrq;
    
    /**
     * 显示日期
     */
    private String xxrq;

    /**
     * 备注
     */
    private String bz;

    /**
     * 所属机构
     */
    private String orgclass;
    
    /**
	 * 周程状态（1有效,2无效）
	 */
	private Integer zt;
	
	/**
	 * 周程项集合
	 */
	private List<WeekItemVo> weekItemVoList;

}