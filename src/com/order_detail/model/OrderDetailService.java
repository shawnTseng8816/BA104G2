package com.order_detail.model;

import java.util.List;
import java.util.Set;

import com.member_profile.model.MemberProfileVO;

public class OrderDetailService {

	private OrderDetailDAO_interface dao;

	public OrderDetailService() {
		dao = new OrderDetailDAO();
	}

	
//-----------------KAHN----------------------------	
	public String insertOrderDetail(String com_num, String com_size, String ice_num, String mercom_num,
			String or_num, String sweet_num, Integer od_price) {

		OrderDetailVO orderDetailVO = new OrderDetailVO();

		orderDetailVO.setCom_num(com_num);
		orderDetailVO.setCom_size(com_size);
		orderDetailVO.setIce_num(ice_num);
		orderDetailVO.setMercom_num(mercom_num);
		orderDetailVO.setOr_num(or_num);
		orderDetailVO.setSweet_num(sweet_num);
		orderDetailVO.setOd_price(od_price);;

		return dao.insert(orderDetailVO);
	}

	public OrderDetailVO getOrderDetail(String ordet_num) {

		OrderDetailVO orderDetailVO = new OrderDetailVO();

		orderDetailVO.setOrdet_num(ordet_num);

		dao.getOrderDetail(orderDetailVO);

		return orderDetailVO;
	}
	
	public void deleteOrderDetail(String ordet_num){
		
		OrderDetailVO orderDetailVO = new OrderDetailVO();

		orderDetailVO.setOrdet_num(ordet_num);

		dao.delete(orderDetailVO);;
	}
	
	public List<OrderDetailVO> getOrderDetailByOrNum(String or_num){
		return dao.getOrderDetails(or_num);
	}
	
//---------------end KAHN-------------------------------
	
	
	
//---------------Shawn---------------------------------
	
	public OrderDetailVO getOrderDetailByOrdet_num(String ordet_num) {
		return dao.getOrderDetailByOrdet_num(ordet_num);
	}
	
//---------------------end Shawn-----------------------------	

}
