package com.ly.cloud.weekplan.controller;

import com.ly.cloud.common.mybatisplus.plugins.PageInfo;
import com.ly.cloud.web.utils.WebResponse;
import com.ly.cloud.weekplan.common.validator.ValidatorUtils;
import com.ly.cloud.weekplan.common.validator.group.UpdateGroup;
import com.ly.cloud.weekplan.dto.WeekItemDto;
import com.ly.cloud.weekplan.dto.WeekItemUpdateDto;
import com.ly.cloud.weekplan.service.WeekItemServivce;
import com.ly.cloud.weekplan.service.WeekServivce;
import com.ly.cloud.weekplan.vo.MeetingInfoVo;
import com.ly.cloud.weekplan.vo.WeekItemPageVO;
import com.ly.cloud.weekplan.vo.WeekItemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags="周程项API")
@RestController
@RequestMapping("/weekitem")
public class WeekItemController {
	
	private static Logger logger = LoggerFactory.getLogger(WeekItemController.class);

	@Autowired
	WeekItemServivce weekItemServivce;

	@Autowired
	WeekServivce weekServivce;

	@ApiOperation("添加周程项")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public WebResponse<Boolean> add(@RequestBody WeekItemDto weekItemDto, @RequestHeader("loginUserOrgId") String orgId) throws Exception {
		weekItemServivce.addWeekItem(weekItemDto, orgId);
		return new WebResponse<Boolean>().success(true);
	}
	
	@ApiOperation("删除或批量删除周程项")
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public WebResponse<Boolean> delete(@RequestBody String[] ids){
		weekItemServivce.deleteBatchIds(ids);
		return new WebResponse<Boolean>().success(true);
	}
	
	
	@ApiOperation("修改周程项")
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public WebResponse<Boolean> update(@RequestBody WeekItemDto weekItemDto) throws Exception{
		ValidatorUtils.validateEntity(weekItemDto,UpdateGroup.class);
		weekItemServivce.update(weekItemDto);
		return new WebResponse<Boolean>().success(true);
	}
	
	
	@ApiOperation("根据ID查看一个周程项")
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    public WebResponse<WeekItemVo> info(@PathVariable("id") String id) throws Exception{
		WeekItemVo vo = new WeekItemVo();
		BeanUtils.copyProperties(weekItemServivce.selectById(id), vo);
		return new WebResponse<WeekItemVo>().success(vo);
    }
	
	
	@ApiOperation("分页周程项")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
	public WebResponse<WeekItemPageVO> queryPageList(
			@RequestParam 
			int pageNum, 
			
			@RequestParam 
			int pageSize,
			
			@RequestParam(required=false)
			@ApiParam(value="周程ID，不是必须参数") 
			String wid,
			
			@RequestParam(required=false)
			@ApiParam(value="map封装的参数") 
			Map<String,String> map,

			@RequestHeader("loginUserOrgId")
			@ApiParam(value = "用户机构ID")
			String orgId
			
		) throws Exception {
		String approvalStatus = weekServivce.selectById(wid).getApprovalStatus();
		PageInfo<WeekItemVo> weekItemVoPageInfo = weekItemServivce.selectPage(pageNum, pageSize, wid, map, orgId);
		return new WebResponse<WeekItemPageVO>().success(new WeekItemPageVO(approvalStatus, weekItemVoPageInfo));
	}
	
	@ApiOperation("周程会议室检查冲突")
    @RequestMapping(value="/conflict/check",method = RequestMethod.GET)
    public WebResponse<List<MeetingInfoVo>> conflict(@ModelAttribute WeekItemDto weekItemDto) throws Exception{
		try {
			List<MeetingInfoVo> result = weekItemServivce.conflict(weekItemDto);
			return new WebResponse<List<MeetingInfoVo>>().success(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new WebResponse<List<MeetingInfoVo>>().failure(e.getMessage());
		}
    }
}