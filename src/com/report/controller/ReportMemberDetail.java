package com.report.controller;

import java.util.Date;

public class ReportMemberDetail {
	
	private String mem_name;
	private String mem_num;
	private String sto_name;
	private String sto_num;
	private String com_title;
	private String com_comment;
	private Date rpt_time;
	private String status;
	private String way;
	private String content;
	
	
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getMem_num() {
		return mem_num;
	}
	public void setMem_num(String mem_num) {
		this.mem_num = mem_num;
	}
	public String getSto_name() {
		return sto_name;
	}
	public void setSto_name(String sto_name) {
		this.sto_name = sto_name;
	}
	public String getSto_num() {
		return sto_num;
	}
	public void setSto_num(String sto_num) {
		this.sto_num = sto_num;
	}
	public String getCom_title() {
		return com_title;
	}
	public void setCom_title(String com_title) {
		this.com_title = com_title;
	}
	public String getCom_comment() {
		return com_comment;
	}
	public void setCom_comment(String com_comment) {
		this.com_comment = com_comment;
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
	public String getWay() {
		return way;
	}
	public void setWay(String way) {
		this.way = way;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
