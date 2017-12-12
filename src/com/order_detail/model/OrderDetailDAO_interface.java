package com.order_detail.model;

import java.util.List;
import java.util.Set;

import com.member_profile.model.MemberProfileVO;

public interface OrderDetailDAO_interface {

//----------------KAHN-------------------------	
	public String insert(OrderDetailVO orderDetailVO);
	
	public void delete(OrderDetailVO orderDetailVO);
	
	public OrderDetailVO getOrderDetail(OrderDetailVO orderDetailVO);
	
	public List<OrderDetailVO> getOrderDetails(String orNum);
//------------end KAHN----------------------------------------	
	
//----------------Shawn--------------------
	public OrderDetailVO getOrderDetailByOrdet_num(String ordet_num);
	
//---------------end Shawn------------------	
	
}
