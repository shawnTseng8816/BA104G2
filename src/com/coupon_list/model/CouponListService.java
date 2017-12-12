package com.coupon_list.model;

import java.util.List;

import com.coupon.model.CouponVO;




public class CouponListService {
	
	private  CouponListDAO_interface dao;
	
	public CouponListService (){
		dao = new CouponListDAO();
	}
	
	
	//搶折價券
	public void getcoupon(String coupon_num,String mem_num,Integer left){
		
		CouponListVO CouponListVO = new CouponListVO();
		CouponListVO.setCoupon_num(coupon_num);
		CouponListVO.setMem_num(mem_num);
		dao.getcoupon(CouponListVO,left);
	}
	
	
	public List<CouponListVO> getMycoupon(String mem_num){
		return dao.getMycoupon(mem_num);
	}
	
	public List<CouponListVO> getMyCoupons(String mem_num, String sto_num){
		
		CouponVO couponVO = new CouponVO();
				
		couponVO.setSto_num(sto_num);
		couponVO.setCoupon_status("審核通過");
		
		return dao.getMyCoupons(mem_num, couponVO);
	}
	
	
	public CouponListVO getCouponInfo(Integer coupon_amount, String coupon_num){
		
		CouponListVO couVO = new CouponListVO();
		
		couVO.setCoupon_amount(coupon_amount);
		couVO.setCoupon_num(coupon_num);
		
		return dao.getCouponInfo(couVO);
	}
	
	public void updateCouponUse(Integer coupon_amount, String coupon_num, String or_num){
		
		CouponListVO CouponListVO = new CouponListVO();
		
		CouponListVO.setCoupon_amount(coupon_amount);
		CouponListVO.setCoupon_num(coupon_num);
		CouponListVO.setOr_num(or_num);
		
		dao.updateCouponUse(CouponListVO);
	}
	
	
//---------Eric-----------------
	
	 public int getCouponCash(String or_num){
		 return dao.getCouponCash(or_num);
	 }
	
	//搶折價券
//	public void getcoupon(String coupon_num,String mem_num,Integer left){
//		
//		CouponListVO CouponListVO = new CouponListVO();
//		CouponListVO.setCoupon_num(coupon_num);
//		CouponListVO.setMem_num(mem_num);
//		dao.getcoupon(CouponListVO,left);
//	}
//	
//	
//	public List<CouponListVO> getMycoupon(String mem_num){
//		return dao.getMycoupon(mem_num);
//	}
	
//-------end Eric---------------	
}
