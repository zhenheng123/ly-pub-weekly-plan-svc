package com.ly.cloud.weekplan.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ly.cloud.web.utils.WebResponse;
import com.ly.cloud.weekplan.dto.MeetingInfoDto;
import com.ly.cloud.weekplan.vo.MeetingInfoVo;


/**
 * 
 * Class Name: MeetingClient  
 * Description: 会议远程调用接口
 * @author: zhenheng
 * @mail: zhenheng@ly-sky.com 
 * @date: 2018年11月7日
 * @version: 1.0
 *
 */
@FeignClient(name = "ly-oa-meeting-svc", url = "http://localhost:1550",fallback = MeetingClient.MeetingClientFallback.class)
public interface MeetingClient {

	@RequestMapping(value = "/meetingInfo/conflictCheck", method = RequestMethod.GET)
	public WebResponse<List<MeetingInfoVo>> conflictCheck(@RequestParam Map<String, Object> map);
   
    @Component
    class MeetingClientFallback implements MeetingClient {

		@Override
		public WebResponse<List<MeetingInfoVo>> conflictCheck(@RequestParam Map<String, Object> map) {
			return null;
		}
    }
}
