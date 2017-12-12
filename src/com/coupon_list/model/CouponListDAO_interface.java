package com.coupon_list.model;

import java.util.List;

import com.coupon.model.CouponVO;

public interface CouponListDAO_interface {
	
	
//---------KAHN------------------	
	 //搶折價券
	 public void getcoupon(CouponListVO coupon_ListVO,Integer left);
	
	 //取得個人折價券資訊
	 public List getMycoupon(String mem_num);
	 
	//取得個人折價券資訊-未過期可使用的
	 public List<CouponListVO> getMyCoupons(String mem_num, CouponVO couponVO);
	 
	 public CouponListVO getCouponInfo(CouponListVO coupon_ListVO);
	 
	 public void updateCouponUse(CouponListVO coupon_ListVO);
//-------end KAHN-----------------
	 
	 
//-----Eric----------------
	 
	 //搶折價券
//	 public void getcoupon(CouponListVO coupon_ListVO,Integer left);
//	
//	 //取得個人折價券資訊
//	 public List getMycoupon(String mem_num);
	
	 public int getCouponCash(String or_num);
	 
	 
//-----end Eric-------------	 
	 
	 
}
