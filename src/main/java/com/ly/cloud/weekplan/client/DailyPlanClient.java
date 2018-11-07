package com.ly.cloud.weekplan.client;

import com.ly.cloud.weekplan.dto.PushDto;
import com.ly.cloud.web.utils.WebResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class Name: DailyPlanClient
 * Description: 日程远程调用接口
 *
 * @author: liufuhong
 * @mail: liufuhong@ly-sky.com
 * @date: 2018年08月06日
 * @version: 1.0
 */

//@FeignClient(name = "ly-pub-daily-plan-svc", url = "http://localhost:1560", fallback = DailyPlanClient.DailyPlanClientFallback.class)
@FeignClient(name = "ly-pub-daily-plan-svc", fallback = DailyPlanClient.DailyPlanClientFallback.class)
public interface DailyPlanClient {

    /**
     *
     * Method Name: batchPush
     * Description: 将会议推送至日程
     * @param dtoList
     * @return com.ly.cloud.web.utils.WebResponse<Boolean>
     * @exception
     * @author liufuhong
     * @mail liufuhong@ly-sky.com
     * @date 2018年08月06日
     */
    @RequestMapping(value = "/dailyPlan/batchPush", method = RequestMethod.POST)
    public WebResponse<Boolean> batchPush(@RequestBody List<PushDto> dtoList);

    /**
     *
     * Method Name: deleteByTaskIds
     * Description: 根据任务编号删除日程
     * @param taskId 任务编号
     * @return com.ly.cloud.web.utils.WebResponse<java.lang.Integer>
     * @exception
     * @author liufuhong
     * @mail liufuhong@ly-sky.com
     * @date 2018年11月06日
     */
    @RequestMapping(value = "/dailyPlan/deleteByTaskId", method = RequestMethod.GET)
    public  WebResponse<Boolean> deleteByTaskId(@RequestParam(value = "taskId") String taskId);

    @Component
    class DailyPlanClientFallback implements DailyPlanClient {

        @Override
        public WebResponse<Boolean> batchPush(@RequestBody List<PushDto> dtoList) {
            return null;
        }

        @Override
        public WebResponse<Boolean> deleteByTaskId(@RequestParam String taskId) {
            return null;
        }
    }

}
