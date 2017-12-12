package com.ad_block.model;

public class AdBlockVO implements java.io.Serializable {
	private String ab_no;
	private String ab_name;
	private Integer ab_price;
	
	public String getAb_no() {
		return ab_no;
	}
	public void setAb_no(String ab_no) {
		this.ab_no = ab_no;
	}
	public String getAb_name() {
		return ab_name;
	}
	public void setAb_name(String ab_name) {
		this.ab_name = ab_name;
	}
	public Integer getAb_price() {
		return ab_price;
	}
	public void setAb_price(Integer ab_price) {
		this.ab_price = ab_price;
	}
}