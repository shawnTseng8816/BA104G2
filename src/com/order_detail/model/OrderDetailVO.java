package com.order_detail.model;

public class OrderDetailVO implements java.io.Serializable {
	private String ordet_num;
	private String com_num;
	private String or_num;
	private String sweet_num;
	private String ice_num;
	private String com_size;
	private String mercom_num;
	private Integer od_price;

	public Integer getOd_price() {
		return od_price;
	}

	public void setOd_price(Integer od_price) {
		this.od_price = od_price;
	}

	public String getOrdet_num() {
		return ordet_num;
	}

	public void setOrdet_num(String ordet_num) {
		this.ordet_num = ordet_num;
	}

	public String getCom_num() {
		return com_num;
	}

	public void setCom_num(String com_num) {
		this.com_num = com_num;
	}

	public String getOr_num() {
		return or_num;
	}

	public void setOr_num(String or_num) {
		this.or_num = or_num;
	}

	public String getSweet_num() {
		return sweet_num;
	}

	public void setSweet_num(String sweet_num) {
		this.sweet_num = sweet_num;
	}

	public String getIce_num() {
		return ice_num;
	}

	public void setIce_num(String ice_num) {
		this.ice_num = ice_num;
	}

	public String getCom_size() {
		return com_size;
	}

	public void setCom_size(String com_size) {
		this.com_size = com_size;
	}

	public String getMercom_num() {
		return mercom_num;
	}

	public void setMercom_num(String mercom_num) {
		this.mercom_num = mercom_num;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((com_num == null) ? 0 : com_num.hashCode());
		result = prime * result + ((com_size == null) ? 0 : com_size.hashCode());
		result = prime * result + ((ice_num == null) ? 0 : ice_num.hashCode());
		result = prime * result + ((mercom_num == null) ? 0 : mercom_num.hashCode());
		result = prime * result + ((sweet_num == null) ? 0 : sweet_num.hashCode());
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
		OrderDetailVO other = (OrderDetailVO) obj;
		if (com_num == null) {
			if (other.com_num != null)
				return false;
		} else if (!com_num.equals(other.com_num))
			return false;
		if (com_size == null) {
			if (other.com_size != null)
				return false;
		} else if (!com_size.equals(other.com_size))
			return false;
		if (ice_num == null) {
			if (other.ice_num != null)
				return false;
		} else if (!ice_num.equals(other.ice_num))
			return false;
		if (mercom_num == null) {
			if (other.mercom_num != null)
				return false;
		} else if (!mercom_num.equals(other.mercom_num))
			return false;
		if (sweet_num == null) {
			if (other.sweet_num != null)
				return false;
		} else if (!sweet_num.equals(other.sweet_num))
			return false;
		return true;
	}
	
}
