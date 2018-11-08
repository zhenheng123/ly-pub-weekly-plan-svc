package com.ly.cloud.weekplan.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.ly.cloud.common.mybatisplus.plugins.PageInfo;
import com.ly.cloud.exception.biz.BusinessException;
import com.ly.cloud.weekplan.dto.WeekItemDto;
import com.ly.cloud.weekplan.dto.WeekItemUpdateDto;
import com.ly.cloud.weekplan.entity.WeekItemEntity;
import com.ly.cloud.weekplan.vo.MeetingInfoVo;
import com.ly.cloud.weekplan.vo.WeekItemVo;

/**
 * 
 * Class Name: WeekServivce 
 * Description: 周程项的业务类
 * 
 * @author: Liyewang
 * @mail: Liyewang@ly-sky.com
 * @date: 2018年6月13日
 * @version: 1.0
 *
 */
public interface WeekItemServivce extends IService<WeekItemEntity> {

	/**
	 * 
	 * Method Name: addWeekItem Description: 添加周程项服务类
	 * 
	 * @param weekItemDto
	 * @param orgId
     * @return
	 * @throws Exception
	 * @return boolean
	 * @exception @author
	 *                Liyewang
	 * @mail Liyewang@ly-sky.com
	 * @date: 2018年6月13日
	 */
	public boolean addWeekItem(WeekItemDto weekItemDto, String orgId) throws Exception;
	
	
	/**
	 * 
	 * Method Name:  fmXXSJ
	 * Description:  根据周程开始时间和结束时间格式化显示字段
	 * @param kssj
	 * @param jssj
	 * @return
	 * @throws Exception 
	 * @return String
	 * @exception 	
	 * @author Liyewang
	 * @mail Liyewang@ly-sky.com
	 * @date: 2018年6月15日
	 */
	public String fmXXSJ(Date kssj,Date jssj) throws Exception;
	
	
	/**
	 * 
	 * Method Name:  fmXXRQ
	 * Description:  根据周程开始时间格式化周程
	 * @param kssj
	 * @return
	 * @throws Exception 
	 * @return String
	 * @exception 	
	 * @author Liyewang
	 * @mail Liyewang@ly-sky.com
	 * @date: 2018年6月15日
	 */
	public String fmXXRQ(Date kssj) throws Exception;


	/**
	 * 
	 * Method Name:  selectList
	 * Description:  分页获取周程项
	 * @param nr 周程项内容
	 * @param zt 周程项状态
	 * @param pageNum
	 * @param pageSize
	 * @param wid 周程ID
	 * @param orgId
	 * @return void
	 * @throws Exception 
	 * @exception 	
	 * @author Liyewang
	 * @mail Liyewang@ly-sky.com
	 * @date: 2018年6月15日
	 */
	public PageInfo<WeekItemVo>selectPage(int pageNum, int pageSize, String wid, Map<String, String> map, String orgId) throws Exception;

	/**
	 *
	 * Method Name: selectPage
	 * Description: 查询周程项列表
	 * @param pageNum
	 * @param pageSize
	 * @param weekPlanId
	 * @param map
	 * @param orgId
	 * @return com.ly.cloud.common.mybatisplus.plugins.PageInfo<com.ly.cloud.weekplan.vo.WeekItemVo>
	 * @exception
	 * @author liufuhong
	 * @mail liufuhong@ly-sky.com
	 * @date 2018年11月07日
	 */
	public List<WeekItemEntity> selectByWeekPlanId(String weekPlanId);

	/**
	 * 
	 * Method Name:  fmZCRQ
	 * Description:  格式化周程的日期
	 * @param kssj
	 * @param jssj
	 * @return
	 * @throws Exception 
	 * @return String
	 * @exception 	
	 * @author Liyewang
	 * @mail Liyewang@ly-sky.com
	 * @date: 2018年6月21日
	 */
	public String fmZCRQ(Date kssj,Date jssj) throws Exception;

	/**
	 * 导出广州市教育局周程安排数据
	 * Method Name:  selectList
	 * Description:  
	 * @param id
	 * @param izt
	 * @return 
	 * @return List<WeekItemVo>
	 * @throws Exception 
	 * @exception 	
	 * @author zhenheng
	 * @mail zhenheng@ly-sky.com
	 * @date: 2018年9月11日
	 */
	public List<WeekItemVo> selectList(String id, Integer izt) throws Exception;


	/**周程会议室检查冲突*/
	public List<MeetingInfoVo> conflict(WeekItemDto weekItemDto) throws BusinessException;
	
	/**
	 *
	 * Method Name: update
	 * Description: 更新周程项
	 * @param weekItemEntity
	 * @return boolean
	 * @exception
	 * @author liufuhong
	 * @mail liufuhong@ly-sky.com
	 * @date 2018年11月07日
	 */
    boolean update(WeekItemUpdateDto weekItemEntity);

	boolean deleteBatchIds(String[] ids);

}