package com.coupon.model;

import java.sql.Timestamp;
import java.util.List;

public class CouponService {
	private  CouponDAO_interface dao;
	
	public CouponService (){
		dao = new CouponDAO();
	}

//--------KAHN-----------------	
	//查詢所有尚未省核清單
	public List<CouponVO> getApply(){
		return dao.getApply();
	}
	
	//取得一張折價券資訊
	public CouponVO getOneCoupon(String coupon_num){
		return dao.getOneCoupon(coupon_num);
	}
	
	//查詢所有已省核折價券清單
	public List<CouponVO> getFinish(){
		return dao.getFinish();
	}
	
	
	//顯示給會員搶購清單
		public List<CouponVO> getCoupon(){
			return dao.getCoupon();
		}
		
		
	//折價券省核通過
	public void Pass(String coupon_num,Integer total){
	
		dao.Pass(coupon_num,total);
	}
	
	//新增折價券
	public void insertApply(CouponVO couponVO) {
		dao.insertApply(couponVO);
	}
	
	//查詢單個店家所以集點卡資訊
	 public List<CouponVO> getOneStoCoupon(String sto_num){
		return dao.getOneStoCoupon(sto_num);
	 }
	
	 
	//折價券省核未通過
	public void noPass(String coupon_num){
		
			dao.noPass(coupon_num);
	}
		
	public byte[] getPic(String coupon_num){
		
		byte[] pic = dao.getPic(coupon_num);
		
		return pic;
}
	
//---------end KAHN------------------------------
	
//---------Eric-----------------------------------
	public List<Timestamp> getCouponUpDate(){
		
		return dao.getCouponUpDate();
		
	}
	//查詢所有尚未省核清單
//	public List<CouponVO> getApply(){
//		return dao.getApply();
//	}
//	
//	//取得一張折價券資訊
//	public CouponVO getOneCoupon(String coupon_num){
//		return dao.getOneCoupon(coupon_num);
//	}
//	
//	//查詢所有已省核折價券清單
//	public List<CouponVO> getFinish(){
//		return dao.getFinish();
//	}
//	
//	
//	//顯示給會員搶購清單
//		public List<CouponVO> getCoupon(){
//			return dao.getCoupon();
//		}
//		
//		
//	//折價券省核通過
//	public void Pass(String coupon_num,Integer total){
//	
//		dao.Pass(coupon_num,total);
//	}
//	
//	//新增折價券
//	public void insertApply(CouponVO couponVO) {
//		dao.insertApply(couponVO);
//	}
//	
//	//查詢單個店家所以集點卡資訊
//	 public List<CouponVO> getOneStoCoupon(String sto_num){
//		return dao.getOneStoCoupon(sto_num);
//	 }
//	
//	 
//	//折價券省核未通過
//	public void noPass(String coupon_num){
//		
//			dao.noPass(coupon_num);
//	}
//		
//	public byte[] getPic(String coupon_num){
//		
//		byte[] pic = dao.getPic(coupon_num);
//		
//		return pic;
//}
	
	
//---------end Eric-------------------------------	
	
	
	
}
