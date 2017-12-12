package com.order_master.model;

import java.sql.Timestamp;

import com.coupon.model.CouponDAO;
import com.coupon.model.CouponVO;

public class Test {

	public static void main(String[] args) {
		
		long now = System.currentTimeMillis();
		long time = now + 6*30*24*60*60*1000L;
		Timestamp ts = new Timestamp(time);
	

		

		
		//System.out.println(ts);
		System.out.println(ts);
	}

}
