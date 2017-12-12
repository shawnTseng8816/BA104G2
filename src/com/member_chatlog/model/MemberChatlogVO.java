package com.member_chatlog.model;

import java.sql.Date;

public class MemberChatlogVO implements java.io.Serializable{
	private String mc_num;
	private String rpt_mnum;
	private String clog_name;
	private String content;
	private Date clog_time;
	
	public String getMc_num() {
		return mc_num;
	}
	public void setMc_num(String mc_num) {
		this.mc_num = mc_num;
	}
	public String getRpt_mnum() {
		return rpt_mnum;
	}
	public void setRpt_mnum(String rpt_mnum) {
		this.rpt_mnum = rpt_mnum;
	}
	public String getClog_name() {
		return clog_name;
	}
	public void setClog_name(String clog_name) {
		this.clog_name = clog_name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getClog_time() {
		return clog_time;
	}
	public void setClog_time(Date clog_time) {
		this.clog_time = clog_time;
	}
	
}
