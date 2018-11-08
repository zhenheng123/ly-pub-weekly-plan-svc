package com.ly.cloud.weekplan.vo;

import com.ly.cloud.common.mybatisplus.plugins.PageInfo;
import lombok.Data;

/**
 * Class Name: WeekItemPageVO
 * Description: 周程项分页VO
 *
 * @author: liufuhong
 * @mail: liufuhong@ly-sky.com
 * @date: 2018年11月08日
 * @version: 1.0
 */

@Data
public class WeekItemPageVO {
    private String approvalStatus;
    private PageInfo<WeekItemVo> weekItemPage;

    public WeekItemPageVO(String approvalStatus, PageInfo<WeekItemVo> weekItemPage) {
        this.approvalStatus = approvalStatus;
        this.weekItemPage = weekItemPage;
    }
}
