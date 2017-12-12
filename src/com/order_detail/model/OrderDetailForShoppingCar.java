package com.order_detail.model;

import java.util.ArrayList;
import java.util.Arrays;

public class OrderDetailForShoppingCar implements java.io.Serializable {

	private OrderDetailVO orderDetail;
	private String[] extNumber;
	private ArrayList<String> extNames;
	private Integer total;
	private String isKeep;
	private ArrayList<String> keepNum;
	private ArrayList<String> ordDetailNum;
	
	public OrderDetailForShoppingCar() {
		orderDetail = new OrderDetailVO();
		keepNum = new ArrayList<String>();
		ordDetailNum = new ArrayList<String>();
	}

	public ArrayList<String> getKeepNum() {
		return keepNum;
	}

	public void setKeepNum(ArrayList<String> keepNum) {
		this.keepNum = keepNum;
	}

	public ArrayList<String> getOrdDetailNum() {
		return ordDetailNum;
	}

	public void setOrdDetailNum(ArrayList<String> ordDetailNum) {
		this.ordDetailNum = ordDetailNum;
	}

	public String getIsKeep() {
		return isKeep;
	}

	public void setIsKeep(String isKeep) {
		this.isKeep = isKeep;
	}

	public OrderDetailVO getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrderDetailVO orderDetail) {
		this.orderDetail = orderDetail;
	}

	public String[] getExtNumber() {
		return extNumber;
	}

	public void setExtNumber(String[] extNumber) {
		this.extNumber = extNumber;
	}

	public ArrayList<String> getExtNames() {
		return extNames;
	}

	public void setExtNames(ArrayList<String> extNames) {
		this.extNames = extNames;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(extNumber);
		result = prime * result + ((isKeep == null) ? 0 : isKeep.hashCode());
		result = prime * result + ((orderDetail == null) ? 0 : orderDetail.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDetailForShoppingCar other = (OrderDetailForShoppingCar) obj;
		if (!Arrays.equals(extNumber, other.extNumber))
			return false;
		if (isKeep == null) {
			if (other.isKeep != null)
				return false;
		} else if (!isKeep.equals(other.isKeep))
			return false;
		if (orderDetail == null) {
			if (other.orderDetail != null)
				return false;
		} else if (!orderDetail.equals(other.orderDetail))
			return false;
		return true;
	}

}
