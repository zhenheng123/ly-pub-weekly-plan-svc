package com.ly.cloud.weekplan.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
import com.ly.cloud.exception.CloudException;
import com.ly.cloud.web.utils.WebResponse;
import com.ly.cloud.weekplan.common.utils.DateUtils;
import com.ly.cloud.weekplan.common.validator.ValidatorUtils;
import com.ly.cloud.weekplan.common.validator.group.AddGroup;
import com.ly.cloud.weekplan.common.validator.group.UpdateGroup;
import com.ly.cloud.weekplan.dto.WeekDto;
import com.ly.cloud.weekplan.entity.WeekEntity;
import com.ly.cloud.weekplan.service.WeekServivce;
import com.ly.cloud.weekplan.vo.WeekVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags="周程API")
@RestController
@RequestMapping("/week")
public class WeekController {
	
	@Autowired
	WeekServivce weekServivce;
	
	@ApiOperation("添加周程")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public WebResponse<Boolean> add(@RequestBody WeekDto weekDto) throws Exception {
		ValidatorUtils.validateEntity(weekDto,AddGroup.class);
		WeekEntity weekEntity = new WeekEntity();
		BeanUtils.copyProperties(weekDto, weekEntity);
		if(weekEntity.getJsrq().before(weekEntity.getKsrq())) {
			throw new CloudException("周程的结束时间不能早于开始时间");
		}
		weekEntity.setBh(UUID.randomUUID().toString().replaceAll("-", ""));
		weekEntity.setKsrq(DateUtils.setDayStar(weekEntity.getKsrq()));
		weekEntity.setJsrq(DateUtils.setDayEnd(weekEntity.getJsrq()));
		weekServivce.insert(weekEntity);
		return new WebResponse<Boolean>().success(true);
	}
	
	
	@ApiOperation("删除或批量删除周程")
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public WebResponse<Boolean> delete(@RequestBody String[] ids){
		weekServivce.deleteBatchIds(Arrays.asList(ids));
		return new WebResponse<Boolean>().success(true);
	}
	
	
	
	@ApiOperation("修改周程")
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public WebResponse<Boolean> update(@RequestBody WeekDto weekDto) throws Exception{
		ValidatorUtils.validateEntity(weekDto,UpdateGroup.class);
		WeekEntity weekEntity = new WeekEntity();
		BeanUtils.copyProperties(weekDto, weekEntity);
		weekServivce.updateById(weekEntity);
		return new WebResponse<Boolean>().success(true);
	}
	
	
	@ApiOperation("根据ID查看一个周程")
    @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
    public WebResponse<WeekVo> info(@PathVariable("id") String tid) throws Exception{
		WeekEntity weekEntity = weekServivce.selectById(tid);
    	WeekVo weekVo=new WeekVo();
		BeanUtils.copyProperties(weekEntity, weekVo);
        return new WebResponse<WeekVo>().success(weekVo);
    }
	
	@ApiOperation("分页周程")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
	public WebResponse<PageInfo<WeekVo>> queryPageList(@RequestParam int pageNum, @RequestParam int pageSize,@RequestParam(required=false)@ApiParam("周程名称，不是必须参数") String zcmc) {
		Page<WeekEntity> page = weekServivce.selectPage(new Page<WeekEntity>(pageNum, pageSize), new EntityWrapper<WeekEntity>().like(StringUtils.isNotBlank(zcmc),"ZCMC",zcmc));
		PageInfo<WeekVo> pageInfo=new PageInfo<WeekVo>();
		List<WeekVo> volist=new ArrayList<WeekVo>();
		for(WeekEntity po:page.getRecords()) {
			WeekVo vo=new WeekVo();
			BeanUtils.copyProperties(po, vo);
			volist.add(vo);
		}
		pageInfo.setTotal(page.getTotal());
		pageInfo.setPageCount(page.getPages());
		pageInfo.setPageNum(pageNum);
		pageInfo.setPageSize(pageSize);
		pageInfo.setList(volist);
		return new WebResponse<PageInfo<WeekVo>>().success(pageInfo);
	}
	
	
	
}