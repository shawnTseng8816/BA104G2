package com.order_master.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import org.json.JSONException;
import org.json.JSONObject;

import com.card.model.CardService;
import com.card_list.model.CardListService;
import com.coupon.model.CouponService;
import com.coupon_list.model.CouponListService;
import com.friend_list.model.FriendListChatServer;
import com.group_buy.model.GroupBuyService;
import com.group_buy.model.GroupBuyVO;
import com.member_profile.model.MemberProfileService;
import com.merged_order.model.MergedOrderService;
import com.order_detail.model.OrderDetailForShoppingCar;
import com.order_detail.model.OrderDetailService;
import com.order_detail.model.OrderDetailVO;
import com.order_master.model.OrderMasterService;
import com.order_master.model.OrderMasterVO;
import com.product.model.ProductService;
import com.store_profile.model.StoreProfileService;

public class OrderMasterController extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		try {
			
			String mem_num = (String) req.getSession().getAttribute("mem_num");
			String currentSto_num = null;
			String action = req.getParameter("action");
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			OrderMasterService oms = new OrderMasterService();
			StoreProfileService sps = new StoreProfileService();
			
			if ("Purchase".equals(action)) {
				
				try {
					
					currentSto_num = req.getParameter("currentSto_num");
					
					if (req.getSession().getAttribute("meror_num" + currentSto_num) != null){
						
						req.getSession().removeAttribute("meror_num" + currentSto_num);
						
						if (req.getSession().getAttribute("orderNum" + currentSto_num) != null){
							
							req.getSession().removeAttribute("orderNum" + currentSto_num);
							
						}
						
					}
					
					if (sps.getMyProfile(currentSto_num).getSto_status().equals("已上架")) {
						
						req.getSession().setAttribute("currentSto_num", currentSto_num);
						
						res.sendRedirect(req.getContextPath() + "/front-end/Order_Master/OrderMaster.jsp");
						return;
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					errorMsgs.add("err");
				}
					
			} else if ("AddNewOrder".equals(action)) {
				
				try {
					
					currentSto_num = (String) req.getSession().getAttribute("currentSto_num");
					String meror_num = (String) req.getSession().getAttribute("meror_num" + currentSto_num);
					
					@SuppressWarnings("unchecked")
					List<OrderDetailForShoppingCar> buylist = (Vector<OrderDetailForShoppingCar>) req.getSession().getAttribute("buylist" + currentSto_num);
					int or_amount = 0;
					
					for(OrderDetailForShoppingCar myOrders : buylist){
						
						or_amount += ( myOrders.getTotal() * myOrders.getOrderDetail().getOd_price() );
						
					}
					
					if(req.getSession().getAttribute("orderNum" + currentSto_num) == null) {
						
						String or_num = oms.addNewOrderMaster(mem_num, currentSto_num, or_amount, meror_num);
						req.getSession().setAttribute("orderNum" + currentSto_num, or_num);
						
					} else {
						
						oms.updateMyOrderMaster(null, null, null, or_amount, null, null,(String) req.getSession().getAttribute("orderNum" + currentSto_num));

					}
					
					RequestDispatcher successView = req.getRequestDispatcher("/OrderDetail/ODC.html");
					successView.forward(req, res);
					return;
					
				} catch (Exception e) {
					e.printStackTrace();
					errorMsgs.add("err");
				}
				
			} else if ("ConfirmOrder".equals(action) && !"GroupBuyCheckout".equals((String)req.getAttribute("action"))) {
				try {
					
					res.addHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0"); 
					res.addHeader("Pragma", "no-cache"); 
					res.addDateHeader ("Expires", 0);
					
					currentSto_num = (String) req.getSession().getAttribute("currentSto_num");
					
					@SuppressWarnings("unchecked")
					List<OrderDetailForShoppingCar> buylist = (Vector<OrderDetailForShoppingCar>) req.getSession().getAttribute("buylist" + currentSto_num);
					
					String address = null;
					if (req.getParameter("ADDRESS") != null) {
						
						address = req.getParameter("ADDRESS");
						
						Pattern addressPattern = Pattern.compile("(\\D+?[縣市])(\\D+?[路街巷])(.+?[號])(.*+)");
						Matcher addressmatcher = addressPattern.matcher(address);
						
			            if (!addressmatcher.matches()) {
			            	
			            	errorMsgs.add("地址格式錯誤");
			            	
			            	RequestDispatcher successView = req.getRequestDispatcher("/front-end/Order_Master/OrderMaster.jsp");
							successView.forward(req, res);
							return;
			            }
			            
					}
					
					int orAmount = 0;
					
					for(OrderDetailForShoppingCar myOrders : buylist){
						
						orAmount += ( myOrders.getTotal() * myOrders.getOrderDetail().getOd_price() );
						
					}
					
					int cardDiscount = 0;
					CardListService cls = null;
					CardService cs = null;
					if(req.getParameter("myCards") != null && req.getParameter("cardType") != null){
						
						cls = new CardListService();
						cs = new CardService();
						
						if(cls.getCardInfo(req.getParameter("myCards")).getCard_kinds().equals(req.getParameter("cardType"))){
							
							if(cls.getCardInfo(req.getParameter("myCards")).getUsed_date() == null){
								
								cardDiscount = cs.getOneCradInfo(req.getParameter("cardType")).getPoints_cash();
								
							}
							
						}
					}
					
					int couponDiscount = 0;
					CouponListService couponls = null;
					CouponService coupons = null;
					if(req.getParameter("myCoupons") != null && req.getParameter("couponType") != null){
						
						couponls = new CouponListService();
						coupons = new CouponService();
						
						if(couponls.getCouponInfo(Integer.valueOf(req.getParameter("myCoupons")), req.getParameter("couponType")).getUsed_date() == null){
							
							couponDiscount = coupons.getOneCoupon(req.getParameter("couponType")).getCoupon_cash();
							
						}
						
					}
					
					orAmount -= (couponDiscount + cardDiscount);
					orAmount = orAmount < 0 ? 0 : orAmount;
					
					if ("點數".equals(req.getParameter("payment"))) {
						
						MemberProfileService mps = new MemberProfileService();
						
						if (orAmount > mps.getMyProfile(mem_num).getRem_point()) {
							
							errorMsgs.add("點數不足，請檢查訂單");
							
							if (req.getSession().getAttribute("meror_num" + currentSto_num) != null) {
								
								req.setAttribute("action", "TurnBackWhileGroupBuy");
								RequestDispatcher successView = req.getRequestDispatcher("/GroupBuy/GBC.html");
								successView.forward(req, res);
								return;
								
							} else {
								
								RequestDispatcher successView = req.getRequestDispatcher("/front-end/Order_Master/OrderMaster.jsp");
								successView.forward(req, res);
								return;
							}
							
						} else {
							
							int myPoint = mps.getMyProfile(mem_num).getRem_point() - orAmount;
							
							mps.editeMyProfile(null, null, null, null, null, null, mem_num, myPoint);
							
						}
						
					}
					
					if (req.getParameter("myCards") != null && req.getParameter("cardType") != null) {
						
						cls.upDateMyCard(req.getParameter("myCards"), (String) req.getSession().getAttribute("orderNum" + currentSto_num)); 
						
						
					}
					
					if (req.getParameter("myCoupons") != null && req.getParameter("couponType") != null) {
						
						couponls.updateCouponUse(Integer.valueOf(req.getParameter("myCoupons")), req.getParameter("couponType"), (String) req.getSession().getAttribute("orderNum" + currentSto_num));
					}
					
					String or_stanum = null;
					if (req.getSession().getAttribute("meror_num" + currentSto_num) == null) {
						
						or_stanum = "待審核";
						
					} else {
						
						or_stanum = "已確認";
						
					}
					
					oms.updateMyOrderMaster(or_stanum, req.getParameter("pickUpType"), req.getParameter("payment"), orAmount, null, address, (String) req.getSession().getAttribute("orderNum" + currentSto_num));
					
					//店家接收訂單訊息
					
					String message =" 您有新的訂單 !" ;
					String webSocketAction = "stoGetOrderMessage";
					System.out.print("test =" + currentSto_num);
					FriendListChatServer.OneByOne(currentSto_num, webSocketAction, message);

					
					req.setAttribute("cardDiscount", cardDiscount);
					req.setAttribute("couponDiscount", couponDiscount);
					req.setAttribute("cost", orAmount);
					req.setAttribute("payment", req.getParameter("payment"));
					req.setAttribute("buylist" + currentSto_num, buylist);
					req.setAttribute("orderNum", (String) req.getSession().getAttribute("orderNum" + currentSto_num));
					
					req.getSession().removeAttribute("buylist" + currentSto_num);
					req.getSession().removeAttribute("orderNum" + currentSto_num);
					
					if (req.getSession().getAttribute("meror_num" + currentSto_num) == null){
						
						RequestDispatcher successView = req.getRequestDispatcher("/front-end/Order_Master/CheckOut.jsp");
						successView.forward(req, res);
						return;
						
					} else {
						
						req.setAttribute("action", "GroupBuyCheckout");
						RequestDispatcher successView = req.getRequestDispatcher("/OrderMaster/OMC.html");
						successView.forward(req, res);
						return;
						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					errorMsgs.add("err");
				}
			} else if ("EditOrder".equals(action)) {
				try {
				
					String orderNum = req.getParameter("or_num");
					currentSto_num = oms.getMyOrderMaster(orderNum).getSto_num();
					
					if (mem_num.equals(oms.getMyOrderMaster(orderNum).getMem_num())) {
						
						req.getSession().setAttribute("orderNum" + currentSto_num, orderNum);
						req.getSession().setAttribute("currentSto_num", currentSto_num);
						
						if (oms.getMyOrderMaster(orderNum).getMeror_num() != null) {
							
							req.setAttribute("action", "GroupBuyEdit");
							req.getSession().setAttribute("meror_num" + currentSto_num, oms.getMyOrderMaster(orderNum).getMeror_num());
							
						}
							
						RequestDispatcher successView = req.getRequestDispatcher("/OrderDetail/ODC.html");
						successView.forward(req, res);
						return;
						
					} else {
						
						res.sendRedirect(req.getContextPath() + "/front-end/index.jsp");
						return;
						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					errorMsgs.add("err");
				}
				
			} else if ("ConfirmGroupBuy".equals(action)) {
				try {
					
					String or_stanum = "待審核";
					
					OrderMasterVO myOrderMaster = oms.getOneOrder(req.getParameter("or_num"));
					
					List<OrderMasterVO> groupBuyOrderMasters = oms.getGroupBuyOrderMaster(myOrderMaster.getMeror_num(), "已確認");
					
					int total = 0;
					
					String address = myOrderMaster.getAddress();
					String pickUpType = myOrderMaster.getRece();
					
					OrderDetailService ods = new OrderDetailService();
					CardListService cls = new CardListService();
					CouponListService couponls = new CouponListService();
					
					for (OrderMasterVO eachOrderMaster : groupBuyOrderMasters) {
						
						int or_amount = 0;
						
						for (OrderDetailVO odv : ods.getOrderDetailByOrNum(eachOrderMaster.getOr_num())) {
							
							or_amount += odv.getOd_price();
							
						}
						
						int cardDiscount = cls.getCardCash(eachOrderMaster.getOr_num());
						int couponDiscount = couponls.getCouponCash(eachOrderMaster.getOr_num());
						
						or_amount -= (cardDiscount + couponDiscount);
						or_amount = or_amount < 0 ? 0 : or_amount; 
						
//						total += eachOrderMaster.getOr_amount();
						total += or_amount;
						
						oms.updateMyOrderMaster(or_stanum, pickUpType, null, or_amount, null, address, eachOrderMaster.getOr_num());
						
					}
					
//					for (OrderMasterVO eachOrderMaster : groupBuyOrderMasters) {
//						
//						total += eachOrderMaster.getOr_amount();
//						
//						oms.updateMyOrderMaster(or_stanum, pickUpType, null, null, null, address, eachOrderMaster.getOr_num());
//						
//					}
					String webSocketAction = "stoGetOrderMessage";
					String message =" 您有新的團購訂單 !!" ;
					String sto_num = myOrderMaster.getSto_num();
						
					FriendListChatServer.OneByOne(sto_num,webSocketAction,message );


					
					req.setAttribute("total", total);
					
					RequestDispatcher successView = req.getRequestDispatcher("/MergedOrder/MOC.html");
					successView.forward(req, res);
					return;
				
				} catch (Exception e) {
					e.printStackTrace();
					errorMsgs.add("err");
				}
				
			} else if ("GetGroupBuyInfo".equals(action) || "GroupBuyCheckout".equals(req.getAttribute("action"))) {
				
				try {
					
					String meror_num = null;
					currentSto_num = (String) req.getSession().getAttribute("currentSto_num");
					
					if (req.getSession().getAttribute("meror_num" + currentSto_num) != null) {
						
						meror_num = (String) req.getSession().getAttribute("meror_num" + currentSto_num);
						
					} else {
						
						meror_num = req.getParameter("meror_num");
						
					}
					
//					currentSto_num = (String) req.getSession().getAttribute("currentSto_num");
//					String meror_num = (String) req.getSession().getAttribute("meror_num" + currentSto_num);
					
					List<OrderMasterVO> GroupBuyOrderMasterList = oms.getGroupBuyOrderMaster(meror_num, "已確認");
					req.setAttribute("GroupBuyOrderMasterList", GroupBuyOrderMasterList);
					
					RequestDispatcher successView = req.getRequestDispatcher("/OrderDetail/ODC.html");
					successView.forward(req, res);
					return;
				
				} catch (Exception e) {
					e.printStackTrace();
					errorMsgs.add("err");
				}
				
			} else if ("AcceptInvite".equals(req.getParameter("isAccept"))) {
				try {
					
					GroupBuyService gbs = new GroupBuyService();
					
					String meror_num = req.getParameter("meror_num");
					
					gbs.setIsAccept("Y", mem_num, meror_num);
					currentSto_num = oms.getGroupBuyOrderMasterWithoutPayMent(meror_num).get(0).getSto_num();
					
					@SuppressWarnings("unchecked")
					List<OrderDetailForShoppingCar> buylist = (Vector<OrderDetailForShoppingCar>) req.getSession().getAttribute("buylist" + currentSto_num);
					int or_amount = 0;
					if (buylist != null) {
						
						for(OrderDetailForShoppingCar myOrders : buylist){
							
							or_amount += ( myOrders.getTotal() * myOrders.getOrderDetail().getOd_price() );
							
						}
						
					}
					
					if(req.getSession().getAttribute("orderNum" + currentSto_num) == null) {
						
						String or_num = oms.addNewOrderMaster(mem_num, currentSto_num, or_amount, meror_num);
						req.getSession().setAttribute("orderNum" + currentSto_num, or_num);
						
					} else {
						
						oms.updateMyOrderMaster(null, null, null, or_amount, null, null,(String) req.getSession().getAttribute("orderNum" + currentSto_num));

					}
					
					req.getSession().setAttribute("currentSto_num", currentSto_num);
					req.getSession().setAttribute("meror_num" + currentSto_num, meror_num);
					
					RequestDispatcher successView = req.getRequestDispatcher("/GroupBuy/GBC.html");
					successView.forward(req, res);
					return;
					
				} catch (Exception e) {
					e.printStackTrace();
					errorMsgs.add("err");
				}
				
			} else if ("RejectInvite".equals(req.getParameter("isAccept"))) {
				try {
					
					GroupBuyService gbs = new GroupBuyService();
					
					String meror_num = req.getParameter("meror_num");
					gbs.setIsAccept("N", mem_num, meror_num);

					res.sendRedirect(req.getContextPath() + "/front-end/index.jsp");
					return;
					
				} catch (Exception e) {
					e.printStackTrace();
					errorMsgs.add("err");
				}
			}
			
			res.sendRedirect(req.getContextPath() + "/front-end/index.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}