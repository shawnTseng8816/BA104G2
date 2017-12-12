package com.order_master.model;

import java.io.Serializable;
import java.util.List;

public class OrderDetailToolVO implements Serializable {

	private String or_num;
	private String sweet_type;
	private String ice_type;
	private String com_size;
	private Integer od_price;
	private Integer amount ;
	private String com_name;
	private Integer point;
	private Integer tol_amount;
	private String ordet_num;
	private List<String> extras;

	public List<String> getExtras() {
		return extras;
	}

	public void setExtras(List<String> extras) {
		this.extras = extras;
	}

	public OrderDetailToolVO(){
		
	}

	public String getOrdet_num() {
		return ordet_num;
	}

	public void setOrdet_num(String ordet_num) {
		this.ordet_num = ordet_num;
	}

	public String getOr_num() {
		return or_num;
	}

	public void setOr_num(String or_num) {
		this.or_num = or_num;
	}

	public String getSweet_type() {
		return sweet_type;
	}

	public void setSweet_type(String sweet_type) {
		this.sweet_type = sweet_type;
	}

	public String getIce_type() {
		return ice_type;
	}

	public void setIce_type(String ice_type) {
		this.ice_type = ice_type;
	}

	public String getCom_size() {
		return com_size;
	}

	public void setCom_size(String com_size) {
		this.com_size = com_size;
	}

	public Integer getOd_price() {
		return od_price;
	}

	public void setOd_price(Integer od_price) {
		this.od_price = od_price;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getCom_name() {
		return com_name;
	}

	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getTol_amount() {
		return tol_amount;
	}

	public void setTol_amount(Integer tol_amount) {
		this.tol_amount = tol_amount;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((com_name == null) ? 0 : com_name.hashCode());
		result = prime * result + ((com_size == null) ? 0 : com_size.hashCode());
		result = prime * result + ((extras == null) ? 0 : extras.hashCode());
		result = prime * result + ((ice_type == null) ? 0 : ice_type.hashCode());
		result = prime * result + ((sweet_type == null) ? 0 : sweet_type.hashCode());
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
		OrderDetailToolVO other = (OrderDetailToolVO) obj;
		if (com_name == null) {
			if (other.com_name != null)
				return false;
		} else if (!com_name.equals(other.com_name))
			return false;
		if (com_size == null) {
			if (other.com_size != null)
				return false;
		} else if (!com_size.equals(other.com_size))
			return false;
		if (extras == null) {
			if (other.extras != null)
				return false;
		} else if (!extras.equals(other.extras))
			return false;
		if (ice_type == null) {
			if (other.ice_type != null)
				return false;
		} else if (!ice_type.equals(other.ice_type))
			return false;
		if (sweet_type == null) {
			if (other.sweet_type != null)
				return false;
		} else if (!sweet_type.equals(other.sweet_type))
			return false;
		return true;
	}

	
	
}