package com.ly.cloud.weekplan.client.dto;

/**
 * 
 * Class Name: LeaderDailyPlanVo  
 * Description: 局领导日程表
 * @author: zhenheng
 * @mail: zhenheng@ly-sky.com 
 * @date: 2018年6月20日
 * @version: 1.0
 *
 */
public class LeaderDailyPlanVo {


    /**
     * 主键
     */
	private String bh;
    /**
     * 领导编号
     */
	private String ldbh;
    /**
     * 领导姓名
     */
	private String ldxm;
    /**
     * 事项
     */
	private String sx;
    /**
     * 开始时间
     */
	private String kssj;
	 /**
     * 结束时间
     */
	private String jssj;
    /**
     * 地点
     */
	private String dd;
    /**
     * 备注
     */
	private String bz;
    /**
     * 机构编号
     */
	private String jgbh;
    /**
     * 局领导日程的发布状态：0未发布，1已发布
     */
	private Integer zt;
	
	 /**
     * 创建时间
     */
	private String cjsj;
	
	 /**
     * 最后更新时间
     */
	private String zhgxsj;
	
	 /**
     * 星期
     */
	private String xq;
	
	 /**
     * 会议编号
     */
	private String hybh;
	
	/**
     * 流程实例编号
     */
	private String docunid;
	
	/**
     * 来文标题
     */
	private String subject;
	
	/**
     * 周程条目id
     */
	private String weekItemId;

	public String getBh() {
		return bh;
	}

	public void setBh(String bh) {
		this.bh = bh;
	}

	public String getLdbh() {
		return ldbh;
	}

	public void setLdbh(String ldbh) {
		this.ldbh = ldbh;
	}

	public String getLdxm() {
		return ldxm;
	}

	public void setLdxm(String ldxm) {
		this.ldxm = ldxm;
	}

	public String getSx() {
		return sx;
	}

	public void setSx(String sx) {
		this.sx = sx;
	}

	public String getKssj() {
		return kssj;
	}

	public void setKssj(String kssj) {
		this.kssj = kssj;
	}

	public String getJssj() {
		return jssj;
	}

	public void setJssj(String jssj) {
		this.jssj = jssj;
	}

	public String getDd() {
		return dd;
	}

	public void setDd(String dd) {
		this.dd = dd;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getJgbh() {
		return jgbh;
	}

	public void setJgbh(String jgbh) {
		this.jgbh = jgbh;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getCjsj() {
		return cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	public String getZhgxsj() {
		return zhgxsj;
	}

	public void setZhgxsj(String zhgxsj) {
		this.zhgxsj = zhgxsj;
	}

	public String getXq() {
		return xq;
	}

	public void setXq(String xq) {
		this.xq = xq;
	}

	public String getHybh() {
		return hybh;
	}

	public void setHybh(String hybh) {
		this.hybh = hybh;
	}

	public String getDocunid() {
		return docunid;
	}

	public void setDocunid(String docunid) {
		this.docunid = docunid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getWeekItemId() {
		return weekItemId;
	}

	public void setWeekItemId(String weekItemId) {
		this.weekItemId = weekItemId;
	}

	
	
}
