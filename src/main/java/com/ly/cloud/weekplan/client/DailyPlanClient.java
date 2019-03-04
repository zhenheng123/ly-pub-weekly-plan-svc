package com.ly.cloud.weekplan.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ly.cloud.web.utils.WebResponse;
import com.ly.cloud.weekplan.client.dto.LeaderDailyPlanDto;
import com.ly.cloud.weekplan.client.dto.LeaderDailyPlanVo;
import com.ly.cloud.weekplan.dto.PushDto;

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
    
    
    
    @RequestMapping(value = "/leaderDailyPlan/insert", method = RequestMethod.POST)
    public  WebResponse<Integer> insert(@RequestBody LeaderDailyPlanDto leaderDailyPlanDto,@RequestHeader("loginUserOrgId")String orgId);
    
    
    @RequestMapping(value = "/leaderDailyPlan/{weekItemId}/list", method = RequestMethod.GET)
    public  WebResponse<List<LeaderDailyPlanVo>> queryByWeekId(@PathVariable("weekItemId") String weekItemId);
    
    
    @RequestMapping(value = "/leaderDailyPlan/itemId/deleteList", method = RequestMethod.POST)
    public WebResponse<Integer> deleteListByItemId(@RequestBody List<String> weekItemIds);
    
    

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

		@Override
		public WebResponse<Integer> insert(LeaderDailyPlanDto leaderDailyPlanDto,
				String orgId) {
			return null;
		}

		@Override
		public WebResponse<List<LeaderDailyPlanVo>> queryByWeekId(String weekItemId) {
			return null;
		}

		@Override
		public WebResponse<Integer> deleteListByItemId(List<String> weekItemIds) {
			return null;
		}
    }
	
}
