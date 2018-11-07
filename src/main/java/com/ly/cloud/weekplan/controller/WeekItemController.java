package com.ly.cloud.weekplan.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ly.cloud.common.mybatisplus.plugins.PageInfo;
import com.ly.cloud.web.utils.WebResponse;
import com.ly.cloud.weekplan.common.validator.ValidatorUtils;
import com.ly.cloud.weekplan.common.validator.group.UpdateGroup;
import com.ly.cloud.weekplan.dto.WeekItemDto;
import com.ly.cloud.weekplan.entity.WeekItemEntity;
import com.ly.cloud.weekplan.service.WeekItemServivce;
import com.ly.cloud.weekplan.vo.WeekItemVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags="周程项API")
@RestController
@RequestMapping("/weekitem")
public class WeekItemController {
	
	@Autowired
	WeekItemServivce weekItemServivce;
	
	
	
	@ApiOperation("添加周程项")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public WebResponse<Boolean> add(@RequestBody WeekItemDto weekItemDto, @RequestHeader("loginUserOrgId") String orgId) throws Exception {
		weekItemServivce.addWeekItem(weekItemDto, orgId);
		return new WebResponse<Boolean>().success(true);
	}
	
	@ApiOperation("删除或批量删除周程项")
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public WebResponse<Boolean> delete(@RequestBody String[] ids){
		weekItemServivce.deleteBatchIds(Arrays.asList(ids));
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
	public WebResponse<PageInfo<WeekItemVo>> queryPageList(
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
		return new WebResponse<PageInfo<WeekItemVo>>().success(weekItemServivce.selectPage( pageNum, pageSize, wid, map, orgId));
	}

	
}