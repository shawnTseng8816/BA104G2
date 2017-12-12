package com.report_store.model;

import java.util.Date;

public class ReportStoreVO implements java.io.Serializable{
	private String rpt_snum;
	private String sto_num;
	private String mem_num;
	private String com_num;
	private Date rpt_time;
	private String status;
	private String staff_num;
	private Integer score;
	private String way;
private String content;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	public String getRpt_snum() {
		return rpt_snum;
	}
	public void setRpt_snum(String rpt_snum) {
		this.rpt_snum = rpt_snum;
	}
	public String getSto_num() {
		return sto_num;
	}
	public void setSto_num(String sto_num) {
		this.sto_num = sto_num;
	}
	public String getMem_num() {
		return mem_num;
	}
	public void setMem_num(String mem_num) {
		this.mem_num = mem_num;
	}
	public String getCom_num() {
		return com_num;
	}
	public void setCom_num(String com_num) {
		this.com_num = com_num;
	}
	public Date getRpt_time() {
		return rpt_time;
	}
	public void setRpt_time(Date rpt_time) {
		this.rpt_time = rpt_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStaff_num() {
		return staff_num;
	}
	public void setStaff_num(String staff_num) {
		this.staff_num = staff_num;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getWay() {
		return way;
	}
	public void setWay(String way) {
		this.way = way;
	}	
}
