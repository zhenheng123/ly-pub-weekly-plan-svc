package com.ly.cloud.weekplan.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ly.cloud.common.mybatisplus.plugins.PageInfo;
import com.ly.cloud.web.utils.WebResponse;
import com.ly.cloud.weekplan.common.validator.ValidatorUtils;
import com.ly.cloud.weekplan.common.validator.group.UpdateGroup;
import com.ly.cloud.weekplan.dto.WeekDto;
import com.ly.cloud.weekplan.dto.WeekItemDto;
import com.ly.cloud.weekplan.entity.WeekEntity;
import com.ly.cloud.weekplan.entity.WeekItemEntity;
import com.ly.cloud.weekplan.service.WeekItemServivce;
import com.ly.cloud.weekplan.vo.WeekItemVo;
import com.ly.cloud.weekplan.vo.WeekVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
	public WebResponse<Boolean> add(@RequestBody WeekItemDto weekItemDto) throws Exception {
		weekItemServivce.addWeekItem(weekItemDto);
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
		WeekItemEntity weekItemEntity = new WeekItemEntity();
		BeanUtils.copyProperties(weekItemDto, weekItemEntity);
		weekItemServivce.updateById(weekItemEntity);
		return new WebResponse<Boolean>().success(true);
	}
	
	
	@ApiOperation("根据ID查看一个周程项")
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    public WebResponse<WeekItemVo> info(@PathVariable("id") String id) throws Exception{
		WeekItemVo vo = new WeekItemVo();
		BeanUtils.copyProperties(weekItemServivce.selectById(id), vo);
		return new WebResponse<WeekItemVo>().success(vo);
    }
	
	
//	@ApiOperation("分页周程项")
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//	public WebResponse<PageInfo<WeekVo>> queryPageList(@RequestParam int pageNum, @RequestParam int pageSize,@RequestParam(required=false)@ApiParam("周程项名称，不是必须参数") String zcmc,@RequestParam(required=false)@ApiParam("周程项状态，审批中:1，通过:2，有效:3，无效:4")Integer zt) {
//		Page<WeekEntity> page = weekServivce.selectPage(
//				new Page<WeekEntity>(pageNum, pageSize), 
//				new EntityWrapper<WeekEntity>()
//					.like(StringUtils.isNotBlank(zcmc),"ZCMC",zcmc)
//					.eq(zt!=null, "ZT", zt)
//				);
//		PageInfo<WeekVo> pageInfo=new PageInfo<WeekVo>();
//		List<WeekVo> volist=new ArrayList<WeekVo>();
//		for(WeekEntity po:page.getRecords()) {
//			WeekVo vo=new WeekVo();
//			BeanUtils.copyProperties(po, vo);
//			volist.add(vo);
//		}
//		pageInfo.setTotal(page.getTotal());
//		pageInfo.setPageCount(page.getPages());
//		pageInfo.setPageNum(pageNum);
//		pageInfo.setPageSize(pageSize);
//		pageInfo.setList(volist);
//		return new WebResponse<PageInfo<WeekVo>>().success(pageInfo);
//	}
	
	
	
	
	
	
}