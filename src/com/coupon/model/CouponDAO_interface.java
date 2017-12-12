package com.coupon.model;

import java.sql.Timestamp;
import java.util.List;

public interface CouponDAO_interface {
	
//------------KAHN----------------------------	
	public byte[] getPic(String coupon_num);
	//查詢所有尚未省核清單
	 public List<CouponVO> getApply();
	 
	 //查詢所有以省核清單
	 public List<CouponVO> getFinish();
	 
	//顯示給會員搶購清單
	 public List<CouponVO> getCoupon();
	 
	 //通過省核
	 public void Pass(String coupon_num,Integer total);
	 
	//省核失敗
    public void noPass(String coupon_num);
	  
	 //查詢一個店家所有折價券資訊
	 public List<CouponVO> getOneStoCoupon(String sto_num);
	 
	 //新增一筆折價券申請
	 public void insertApply(CouponVO couponVO);
	 
	 //查詢某折價券明細
	 public CouponVO getOneCoupon(String coupon_num);
//--------end KAHN--------------------
	 
//--------Eric----------------------------
	 
//	 public byte[] getPic(String coupon_num);
//		//查詢所有尚未省核清單
//		 public List<CouponVO> getApply();
		 
		 
		 public List<Timestamp> getCouponUpDate();
		 
		 //查詢所有以省核清單
//		 public List<CouponVO> getFinish();
//		 
//		//顯示給會員搶購清單
//		 public List<CouponVO> getCoupon();
//		 
//		 //通過省核
//		 public void Pass(String coupon_num,Integer total);
//		 
//		//省核失敗
//	    public void noPass(String coupon_num);
//		  
//		 //查詢一個店家所有折價券資訊
//		 public List<CouponVO> getOneStoCoupon(String sto_num);
//		 
//		 //新增一筆折價券申請
//		 public void insertApply(CouponVO couponVO);
//		 
//		 //查詢某折價券明細
//		 public CouponVO getOneCoupon(String coupon_num);
	 
//--------end Eric-----------------------	 
	 
	
}
