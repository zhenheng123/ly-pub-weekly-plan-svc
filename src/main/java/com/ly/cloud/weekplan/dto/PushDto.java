package com.ly.cloud.weekplan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Class Name: PushDto
 * Description: 日程推送数据传输类
 *
 * @author: liufuhong
 * @mail: liufuhong@ly-sky.com
 * @date: 2018年08月03日
 * @version: 1.0
 */

@Data
public class PushDto {

    public final static String TASK_TYPE_WEEK_PLAN = "4";

    public final static int DAILY_PLAN_COLOR_DEFAULT = 2;

    public final static int DAILY_PLAN_TYPE_PUBLIC = 1;

    public final static int DO_NOT_REMINDER= 0;

    public PushDto() {

    }

    public PushDto(List<String> yhbhList, Date kssj, Date jssj, String rcbt, String rwbh, String dd) {
        this.yhbhList = yhbhList;
        this.kssj = kssj;
        this.jssj = jssj;
        this.rcbt = rcbt;
        this.rwbh = rwbh;
        this.dd = dd;
        this.sftx = DO_NOT_REMINDER;
        this.rcys = DAILY_PLAN_COLOR_DEFAULT;
        this.rclx = DAILY_PLAN_TYPE_PUBLIC;
        this.rwlx = TASK_TYPE_WEEK_PLAN;
    }

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @ApiModelProperty("开始时间")
    private Date kssj;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @ApiModelProperty("结束时间")
    private Date jssj;
    /**
     * 日程标题
     */
    @ApiModelProperty("日程标题")
    private String rcbt;
    /**
     * 日程内容
     */
    @ApiModelProperty("日程内容")
    private String rcnr;

    /**
     * 日程颜色(0：紧急 1：重要 2：一般 3：其他)
     */
    @ApiModelProperty("(0：紧急 1：重要 2：一般 3：其他)")
    private Integer rcys;
    /**
     * 日程（事务）类型 (0：私人,1：公开)
     */
    @ApiModelProperty("日程（事务）类型 (0：私人,1：公开)")
    private Integer rclx;
    /**
     * 任务编号
     */
    @ApiModelProperty("任务编号")
    private String rwbh;
    /**
     * 是否全天  0：不是  1：是
     */
    @ApiModelProperty("是否全天  0：不是  1：是")
    private Integer sfqt;
    /**
     * 是否有权限  0：无  1：有
     */
    @ApiModelProperty("是否有权限  0：无  1：有")
    private Integer sfsq;

    /**
     * 用户编号
     */
    @ApiModelProperty("用户编号")
    private List<String> yhbhList;

    /**
     * 是否需要提醒 0 ：不提醒 1：当天提醒 2：指定时间提醒
     */
    @ApiModelProperty("是否需要提醒 0 ：不提醒 1：当天提醒 2：指定时间提醒")
    private Integer sftx;

    /**
     * 是否重复提醒 0 ：不重复 1：重复
     */
    @ApiModelProperty("否重复提醒 0 ：不重复 1：重复")
    private Integer cftx;

    /**
     * 提醒时间
     */
    @ApiModelProperty("提醒时间")
    private String txsj;

    /**
     * 是否已经发送提醒 0：未提醒 1：已提醒
     */
    @ApiModelProperty("是否已经发送提醒 0：未提醒 1：已提醒")
    private Integer sffstx;

    /**
     * 地点
     */
    @ApiModelProperty("地点")
    private String dd;

    /**
     * 任务类型（1会议，2督办，3领导日程，4周程）
     */
    @ApiModelProperty("任务类型（1会议，2督办，3领导日程，4周程）")
    private String rwlx;

}
