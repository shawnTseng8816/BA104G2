package com.order_detail.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.extra.model.ExtraService;
import com.extra_list.model.ExtraListService;
import com.extra_list.model.ExtraListVO;
import com.keep_record.model.KeepRecordService;
import com.member_profile.model.MemberProfileService;
import com.order_detail.model.OrderDetailForShoppingCar;
import com.order_detail.model.OrderDetailService;
import com.order_detail.model.OrderDetailVO;
import com.order_master.model.OrderMasterVO;
import com.product.model.ProductService;

public class OrderDetailController extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		try {
			
			String mem_num = (String) req.getSession().getAttribute("mem_num");
			String currentSto_num = (String) req.getSession().getAttribute("currentSto_num");
			String action = req.getParameter("action");

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			List<OrderDetailForShoppingCar> buylist = null;

			OrderDetailService ods = new OrderDetailService();
			ExtraListService els = new ExtraListService();
			KeepRecordService krs = new KeepRecordService();
			ProductService ps = new ProductService();

			if ("AddOne".equals(action)) {
				try {
					
					buylist = (Vector<OrderDetailForShoppingCar>) req.getSession().getAttribute("buylist" + currentSto_num);
					
					String size = "size" + req.getParameter("com_num");
					
					OrderDetailForShoppingCar order = getProDetail(req.getParameter("com_num"),
							req.getParameter(size).substring(0, 1), req.getParameter("ice"),
							req.getParameter("sweet"), req.getParameter("Mercom_num"), req.getParameterValues("extra"),
							req.getParameter("isKeep"), req.getParameter("total"),
							req.getParameter(size).substring(1));

					if (buylist == null) {
						
						buylist = new Vector<OrderDetailForShoppingCar>();
						buylist.add(order);
						
					} else {
						
						if (buylist.contains(order)) {
							
							OrderDetailForShoppingCar innerOrder = buylist.get(buylist.indexOf(order));
							innerOrder.setTotal(innerOrder.getTotal() + order.getTotal());
							
						} else {
							
							buylist.add(order);
							
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					errorMsgs.add("err");
				}
				
			} else if ("Remove".equals(action)) {
				try {
					
					buylist = (Vector<OrderDetailForShoppingCar>) req.getSession().getAttribute("buylist" + currentSto_num);

					int index = Integer.valueOf(req.getParameter("index"));

					if (req.getSession().getAttribute("orderNum" + currentSto_num) != null) {

						OrderDetailForShoppingCar myOrders = buylist.get(index);
						
						if ("keep".equals(myOrders.getIsKeep())) {
							
							for (String keepNum : myOrders.getKeepNum()) {
								
								krs.deleteKeepRecord(keepNum);
								
							}
							
						}
						
						for (String ordDetailNum : myOrders.getOrdDetailNum()) {
							
							els.deleteExtraList(ordDetailNum);
							ods.deleteOrderDetail(ordDetailNum);
							
						}
						
					}

					buylist.remove(index);

				} catch (Exception e) {
					e.printStackTrace();
					errorMsgs.add("err");
				}
				
			} else if ("AddNewOrder".equals(action)) {
				try {
					
					buylist = (Vector<OrderDetailForShoppingCar>) req.getSession().getAttribute("buylist" + currentSto_num);

					String or_num = (String) req.getSession().getAttribute("orderNum" + currentSto_num);

					for (OrderDetailForShoppingCar myOrders : buylist) {
						
						int j = myOrders.getTotal() - myOrders.getOrdDetailNum().size();
						
						for (int i = 0; i < j; i++) {
							
							String ordet_num = ods.insertOrderDetail(myOrders.getOrderDetail().getCom_num(),
									myOrders.getOrderDetail().getCom_size(), myOrders.getOrderDetail().getIce_num(),
									myOrders.getOrderDetail().getMercom_num(), or_num,
									myOrders.getOrderDetail().getSweet_num(), myOrders.getOrderDetail().getOd_price());
							
							myOrders.getOrdDetailNum().add(ordet_num);
							
							if (myOrders.getExtNumber() != null) {
								
								for (String extNum : myOrders.getExtNumber()) {
									
									els.addExtraList(extNum, ordet_num);
									
								}
								
							}
							
							if ("keep".equals(myOrders.getIsKeep())) {
								
								String keep_num = krs.insertKeepRecord(mem_num, currentSto_num, myOrders.getOrderDetail().getCom_num(), ordet_num);
								myOrders.getKeepNum().add(keep_num);
								
							}
						}
					}

					return;
					
				} catch (Exception e) {
					e.printStackTrace();
					errorMsgs.add("err");
				}
				
			} else if ("EditOrder".equals(action)) {

				buylist = new Vector<OrderDetailForShoppingCar>();

				try {

					for (OrderDetailVO orderDetailVO : ods.getOrderDetailByOrNum(req.getParameter("or_num"))) {

						String isKeep = null;

						ArrayList<String> extNum = new ArrayList<String>();
						for (ExtraListVO extraVO : els.getExtraNum(orderDetailVO.getOrdet_num())) {
							
							extNum.add(extraVO.getExt_num());
							
						}

						String[] extNumList = new String[extNum.size()];
						extNumList = extNum.toArray(extNumList);

						Integer pro_price = 0;
						if ("M".equals(orderDetailVO.getCom_size())) {
							
							pro_price = ps.getProductName(orderDetailVO.getCom_num()).getM_price();
							
						} else if ("L".equals(orderDetailVO.getCom_size())) {
							
							pro_price = ps.getProductName(orderDetailVO.getCom_num()).getL_price();
							
						}

						OrderDetailForShoppingCar order = getProDetail(orderDetailVO.getCom_num(),
								orderDetailVO.getCom_size(), orderDetailVO.getIce_num(), orderDetailVO.getSweet_num(),
								orderDetailVO.getMercom_num(), extNumList, isKeep, "1", pro_price.toString());

						try {
							
							order.getKeepNum().add(krs.getKeepRecordByDetpNum(orderDetailVO.getOrdet_num()).getKeep_num());
							order.setIsKeep("keep");
							
						} catch (RuntimeException re) {
							
							order.setIsKeep("notkeep");
							
						}

						if (buylist.contains(order)) {

							OrderDetailForShoppingCar innerOrder = buylist.get(buylist.indexOf(order));
							innerOrder.setTotal(innerOrder.getTotal() + order.getTotal());

							if ("keep".equals(innerOrder.getIsKeep())) {
								
								innerOrder.getKeepNum().add(krs.getKeepRecordByDetpNum(orderDetailVO.getOrdet_num()).getKeep_num());
							}

							innerOrder.getOrdDetailNum().add(orderDetailVO.getOrdet_num());

						} else {
							
							buylist.add(order);
							order.getOrdDetailNum().add(orderDetailVO.getOrdet_num());
						}

					}

					req.getSession().setAttribute("buylist" + currentSto_num, buylist);
					
					
					if (req.getSession().getAttribute("meror_num" + currentSto_num) != null) {
						
						RequestDispatcher successView = req.getRequestDispatcher("/GroupBuy/GBC.html");
						successView.forward(req, res);
						return;
						
					} else {
						
						RequestDispatcher successView = req.getRequestDispatcher("/front-end/Order_Master/OrderMaster.jsp");
						successView.forward(req, res);
						return;
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					errorMsgs.add("err");
				}

			} else if ("GetGroupBuyInfo".equals(action) || "GroupBuyCheckout".equals((String) req.getAttribute("action")) || "GroupBuyEdit".equals((String) req.getAttribute("action"))) {
				
				List<OrderDetailForShoppingCar> eachMemShoppingCar = null;
				MemberProfileService mps = new MemberProfileService();

				try {

					List<OrderMasterVO> GroupBuyOrderMasterList = (List<OrderMasterVO>) req.getAttribute("GroupBuyOrderMasterList");

					List<List<Object>> groupBuyInfo = new ArrayList<List<Object>>();
					
					for (OrderMasterVO orderMaster : GroupBuyOrderMasterList) {
						
						List<Object> eachDetail = new ArrayList<Object>();
						
						int total = 0;

						eachDetail.add(mps.getMyProfile(orderMaster.getMem_num()).getMem_name());
						eachDetail.add(orderMaster.getOr_num());
						eachDetail.add(orderMaster.getPay_men());

						eachMemShoppingCar = new Vector<OrderDetailForShoppingCar>();

						for (OrderDetailVO orderDetailVO : ods.getOrderDetailByOrNum(orderMaster.getOr_num())) {

							String isKeep = null;

							ArrayList<String> extNum = new ArrayList<String>();
							for (ExtraListVO extraVO : els.getExtraNum(orderDetailVO.getOrdet_num())) {
								
								extNum.add(extraVO.getExt_num());
								
							}

							String[] extNumList = new String[extNum.size()];
							extNumList = extNum.toArray(extNumList);

							Integer pro_price = 0;
							
							if ("M".equals(orderDetailVO.getCom_size())) {
								
								pro_price = ps.getProductName(orderDetailVO.getCom_num()).getM_price();
								
							} else if ("L".equals(orderDetailVO.getCom_size())) {
								
								pro_price = ps.getProductName(orderDetailVO.getCom_num()).getL_price();
								
							}

							OrderDetailForShoppingCar order = getProDetail(orderDetailVO.getCom_num(),
									orderDetailVO.getCom_size(), orderDetailVO.getIce_num(),
									orderDetailVO.getSweet_num(), orderDetailVO.getMercom_num(), extNumList, isKeep,
									"1", pro_price.toString());

							try {
								
								order.getKeepNum().add(krs.getKeepRecordByDetpNum(orderDetailVO.getOrdet_num()).getKeep_num());
								order.setIsKeep("keep");
								
							} catch (RuntimeException re) {
								
								order.setIsKeep("notkeep");
								
							}

							if (eachMemShoppingCar.contains(order)) {

								OrderDetailForShoppingCar innerOrder = eachMemShoppingCar.get(eachMemShoppingCar.indexOf(order));
								innerOrder.setTotal(innerOrder.getTotal() + order.getTotal());

								if ("keep".equals(innerOrder.getIsKeep())) {
									
									innerOrder.getKeepNum().add(krs.getKeepRecordByDetpNum(orderDetailVO.getOrdet_num()).getKeep_num());
								
								}

								innerOrder.getOrdDetailNum().add(orderDetailVO.getOrdet_num());

							} else {
								
								eachMemShoppingCar.add(order);
								order.getOrdDetailNum().add(orderDetailVO.getOrdet_num());
								
							}
							
							total += orderDetailVO.getOd_price();
						}
						
						if ("GroupBuyEdit".equals(req.getAttribute("action"))) {
							
							eachDetail.add(total);
							
						} else {
							
							eachDetail.add(orderMaster.getOr_amount());
						}
						
						eachDetail.add(eachMemShoppingCar);

						groupBuyInfo.add(eachDetail);
						
					}
					

					req.setAttribute("groupBuyInfo", groupBuyInfo);
					
					RequestDispatcher successView = req.getRequestDispatcher("/GroupBuy/GBC.html");
					successView.forward(req, res);
					return;

				} catch (Exception e) {
					e.printStackTrace();
					errorMsgs.add("err");
				}

			}
			
			req.getSession().setAttribute("buylist" + currentSto_num, buylist);

			RequestDispatcher successView = req.getRequestDispatcher("/front-end/Order_Detail/OrderDetail.jsp");
			successView.forward(req, res);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private OrderDetailForShoppingCar getProDetail(String com_num, String com_size, String ice_num, String sweet_num,
			String mercom_num, String[] extra, String isKeep, String total, String pro_price) {

		OrderDetailForShoppingCar orDeForShoppingCar = new OrderDetailForShoppingCar();

		OrderDetailVO orderDetail = orDeForShoppingCar.getOrderDetail();

		orderDetail.setCom_num(com_num);
		orderDetail.setCom_size(com_size);
		orderDetail.setIce_num(ice_num);
		orderDetail.setSweet_num(sweet_num);
		orderDetail.setMercom_num(mercom_num);

		ExtraService es = new ExtraService();

		int ext_total = 0;

		if (extra != null) {
			ArrayList<String> extNames = new ArrayList<String>();
			for (String ext : extra) {
				ext_total += es.getExtraAttr(ext).getExt_amount();
				extNames.add(es.getExtraAttr(ext).getExt_name());
			}
			orDeForShoppingCar.setExtNumber(extra);
			orDeForShoppingCar.setExtNames(extNames);
		}

		orderDetail.setOd_price(Integer.valueOf(pro_price) + ext_total);

		orDeForShoppingCar.setIsKeep(isKeep);
		orDeForShoppingCar.setTotal(Integer.valueOf(total));

		return orDeForShoppingCar;
	}
}