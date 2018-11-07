package com.ly.cloud.weekplan.dto;

/**
 * Class Name: MeetingInfoPo  
 * Description: 会议信息DTO
 * @author: zhenheng
 * @mail: zhenheng@ly-sky.com 
 * @date: 2018年6月6日
 * @version: 1.0
 */
public class MeetingInfoDto{
	
	
	/** 
	 * 主键
	 */
	private String id;
	
	/**
     * 会议名称
     */
	private String hymc;
	/** 
	 * 会议内容
	 */
	private String hynr;
	/** 
	 * 会议类型
	 */
	private String hylx;
	
	/** 
	 * 会议地点
	 */
	private String hydd;
	
	/** 
	 * 开始时间
	 */
	private String kssj;
	
	/** 
	 * 结束时间
	 */
	private String jssj;
	
	/** 
	 * 主持人号
	 */
	private String zcrh;
	/** 
	 * 记录人号
	 */
	private String jlrh;
	
	/** 
	 * 参与者编号
	 */
	private String cyzbh;
	/** 
	 * 参与者姓名
	 */
	private String cyzxm;
	/** 
	 * 其他参与者
	 */
	private String qtcyz;
	
	/** 
	 * 主办部门编号
	 */
	private String zbbmbh;
	/** 
	 * 参会人数
	 */
	private Integer chrs;
	
	/** 
	 * 会议联系人
	 */
	private String hylxr;
	
	/** 
	 * 会议联系人电话
	 */
	private String lxrdh;
	
	/** 
	 * 会议室编号
	 */
	private String hysbh;
	
	/** 
	 * 备注
	 */
	private String bz;
	
	/** 
	 * 创建时间
	 */
	private String cjsj;
	
	/** 
	 * 更新时间
	 */
	private String gxsj;
	
	/** 
	 * 删除标示（0,存在，1 删除）
	 */
	private Integer scbs;
	
	/** 
	 * 0 未审核， 1审核通过 ，2 未通过，3未提交，4撤销，5 已归档
	 */
	private Integer zt;
	
	/** 
	 * 流程实例id
	 */
	private String processId;
	
	/** 
	 * 主办部门名称
	 */
	private String zbbmmc;
	
	/** 
	 * 会议申请人（会议负责人）
	 */
	private String hysqrbh;
	
	/** 
	 * 是否关联议题
	 */
	private Integer glyt;
	
	/** 
	 * 主持人姓名
	 */
	private String zcrxm;
	
	/** 
	 * 记录人姓名
	 */
	private String jlrxm;
	
	/** 
	 * 局领导编号
	 */
	private String ldbh;
	
	/** 
	 * 局领导姓名
	 */
	private String ldxm;
	/** 
	 * 星期
	 */
	private String xq;
	
	/** 
	 * 签到权限(0 未开放，1已开放)
	 */
	private String qdqx;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHymc() {
		return hymc;
	}

	public void setHymc(String hymc) {
		this.hymc = hymc;
	}

	public String getHynr() {
		return hynr;
	}

	public void setHynr(String hynr) {
		this.hynr = hynr;
	}

	public String getHylx() {
		return hylx;
	}

	public void setHylx(String hylx) {
		this.hylx = hylx;
	}

	public String getHydd() {
		return hydd;
	}

	public void setHydd(String hydd) {
		this.hydd = hydd;
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

	public String getZcrh() {
		return zcrh;
	}

	public void setZcrh(String zcrh) {
		this.zcrh = zcrh;
	}

	public String getJlrh() {
		return jlrh;
	}

	public void setJlrh(String jlrh) {
		this.jlrh = jlrh;
	}

	public String getCyzbh() {
		return cyzbh;
	}

	public void setCyzbh(String cyzbh) {
		this.cyzbh = cyzbh;
	}

	public String getCyzxm() {
		return cyzxm;
	}

	public void setCyzxm(String cyzxm) {
		this.cyzxm = cyzxm;
	}

	public String getQtcyz() {
		return qtcyz;
	}

	public void setQtcyz(String qtcyz) {
		this.qtcyz = qtcyz;
	}

	public String getZbbmbh() {
		return zbbmbh;
	}

	public void setZbbmbh(String zbbmbh) {
		this.zbbmbh = zbbmbh;
	}

	public Integer getChrs() {
		return chrs;
	}

	public void setChrs(Integer chrs) {
		this.chrs = chrs;
	}

	public String getHylxr() {
		return hylxr;
	}

	public void setHylxr(String hylxr) {
		this.hylxr = hylxr;
	}

	public String getLxrdh() {
		return lxrdh;
	}

	public void setLxrdh(String lxrdh) {
		this.lxrdh = lxrdh;
	}

	public String getHysbh() {
		return hysbh;
	}

	public void setHysbh(String hysbh) {
		this.hysbh = hysbh;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getCjsj() {
		return cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	public String getGxsj() {
		return gxsj;
	}

	public void setGxsj(String gxsj) {
		this.gxsj = gxsj;
	}

	public Integer getScbs() {
		return scbs;
	}

	public void setScbs(Integer scbs) {
		this.scbs = scbs;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getZbbmmc() {
		return zbbmmc;
	}

	public void setZbbmmc(String zbbmmc) {
		this.zbbmmc = zbbmmc;
	}

	public String getHysqrbh() {
		return hysqrbh;
	}

	public void setHysqrbh(String hysqrbh) {
		this.hysqrbh = hysqrbh;
	}

	

	public String getZcrxm() {
		return zcrxm;
	}

	public void setZcrxm(String zcrxm) {
		this.zcrxm = zcrxm;
	}

	public String getJlrxm() {
		return jlrxm;
	}

	public void setJlrxm(String jlrxm) {
		this.jlrxm = jlrxm;
	}

	public Integer getGlyt() {
		return glyt;
	}

	public void setGlyt(Integer glyt) {
		this.glyt = glyt;
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

	public String getXq() {
		return xq;
	}

	public void setXq(String xq) {
		this.xq = xq;
	}

	public String getQdqx() {
		return qdqx;
	}

	public void setQdqx(String qdqx) {
		this.qdqx = qdqx;
	}

	
	
	
}
