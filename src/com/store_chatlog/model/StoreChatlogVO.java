package com.store_chatlog.model;

import java.sql.Date;

public class StoreChatlogVO implements java.io.Serializable{
	private String sc_num;
	private String rpt_snum;
	private String clog_name;
	private String content;
	private Date clog_time;
	
	public String getSc_num() {
		return sc_num;
	}
	public void setSc_num(String sc_num) {
		this.sc_num = sc_num;
	}
	public String getRpt_snum() {
		return rpt_snum;
	}
	public void setRpt_snum(String rpt_snum) {
		this.rpt_snum = rpt_snum;
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
