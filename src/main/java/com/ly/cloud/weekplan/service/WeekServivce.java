package com.ly.cloud.weekplan.service;

import com.baomidou.mybatisplus.service.IService;
import com.ly.cloud.weekplan.dto.WeekItemDto;
import com.ly.cloud.weekplan.entity.WeekEntity;
import com.ly.cloud.weekplan.entity.WeekItemEntity;
import com.ly.cloud.weekplan.vo.WeekVo;

import java.util.List;

/**
 * 
 * Class Name: WeekServivce Description: 周程的业务类
 * 
 * @author: Liyewang
 * @mail: Liyewang@ly-sky.com
 * @date: 2018年6月13日
 * @version: 1.0
 *
 */
public interface WeekServivce extends IService<WeekEntity> {

	/**
	 * 
	 * Method Name: getById 
	 * Description: 根据周程ID获取周程和它关联的周程项
	 * 
	 * @param id 周程ID
	 * @param izt 周程项状态
	 * @return
	 * @return WeekVo
	 * @throws Exception 
	 * @exception @author
	 *                Liyewang
	 * @mail Liyewang@ly-sky.com
	 * @date: 2018年6月14日
	 */
	public WeekVo getById(String id, Integer izt) throws Exception;

	/**
	 *
	 * Method Name: update
	 * Description: 更新周程
	 * @param weekEntity
	 * @return boolean
	 * @exception
	 * @author liufuhong
	 * @mail liufuhong@ly-sky.com
	 * @date 2018年11月06日
	 */
    boolean update(WeekEntity weekEntity);

    /**
     *
     * Method Name: delete
     * Description: 删除周程
     * @param ids
     * @return boolean
     * @exception
     * @author liufuhong
     * @mail liufuhong@ly-sky.com
     * @date 2018年11月07日
     */
	boolean delete(String[] ids);

	/**
	 *
	 * Method Name: selectListByWeekItem
	 * Description: 通过周程项查找周程列表
	 * @param weekItemDto
	 * @return java.util.List<com.ly.cloud.weekplan.entity.WeekEntity>
	 * @exception
	 * @author liufuhong
	 * @mail liufuhong@ly-sky.com
	 * @date 2018年11月07日
	 */
    List<WeekEntity> selectListByWeekItem(WeekItemDto weekItemDto);

    void syncDailyPlan(boolean deleteOldDate, WeekEntity weekEntity);
}