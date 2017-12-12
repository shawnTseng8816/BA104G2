package com.merged_order.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import org.json.JSONException;
import org.json.JSONObject;

import com.group_buy.model.GroupBuyService;
import com.group_buy.model.GroupBuyVO;
import com.member_profile.model.MemberProfileService;
import com.merged_order.model.MergedOrderService;
import com.order_master.model.OrderMasterService;
import com.store_profile.model.StoreProfileService;

public class MergedOrderController extends HttpServlet {

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
			
			List<String> errorMsgs = null;
			if (req.getAttribute("errorMsgs") != null) {
				
				errorMsgs = (List<String>) req.getAttribute("errorMsgs");
				
			} else {
				
				errorMsgs = new LinkedList<String>();
				
			}
			
			MergedOrderService mos = new MergedOrderService();
			
			String meror_num = null;
			
			if ("Invite".equals(action)) {
				try {
					
					if(req.getParameterValues("invite_friends") != null) {
						
						if(req.getSession().getAttribute("meror_num" + currentSto_num) == null) {
							
							meror_num = mos.addMergedOrder(mem_num);
							
							req.getSession().setAttribute("meror_num" + currentSto_num, meror_num);
							
							OrderMasterService oms = new OrderMasterService();
							
							if (req.getSession().getAttribute("orderNum" + currentSto_num) == null) {
								
								String or_num = oms.addNewOrderMaster(mem_num, currentSto_num, 0, meror_num);
								
								req.getSession().setAttribute("orderNum" + currentSto_num, or_num);
								
							} else {
								
								oms.updateMyOrderMaster(null, null, null, null, meror_num, null, (String) req.getSession().getAttribute("orderNum" + currentSto_num));
								
							}
							
						} 
						
					}
					
					RequestDispatcher successView = req.getRequestDispatcher("/GroupBuy/GBC.html");
					successView.forward(req, res);
					return;
					
				} catch (Exception e) {
					e.printStackTrace();
					errorMsgs.add("err");
				}
				
			} else if ("ConfirmGroupBuy".equals(action)) {
				try {
					
					OrderMasterService oms = new OrderMasterService();
					
					meror_num = oms.getOneOrder(req.getParameter("or_num")).getMeror_num();
					
					mos.updateMergedOrder(meror_num, mem_num, (Integer)req.getAttribute("total"));
					
					String sto_num = oms.getGroupBuyOrderMasterWithoutPayMent(meror_num).get(0).getSto_num();
					
					StoreProfileService sps = new StoreProfileService();
					MemberProfileService mps = new MemberProfileService();
					GroupBuyService gbs = new GroupBuyService();
					
					List<GroupBuyVO> groupBuyMems = gbs.getGroupBuyMems(mem_num, meror_num);
					
					JSONObject sendJsonObj = new JSONObject();
					
					try {
						
						sendJsonObj.put("action", "groupBuyOrderHadBeenSend");
						sendJsonObj.put("inviter", mps.getMyProfile(mem_num).getMem_name());
						sendJsonObj.put("stoName", sps.getStoreProfile(sto_num).getSto_name());
						sendJsonObj.put("meror_num", meror_num);
						
					} catch (JSONException e) {
						
						e.printStackTrace();
					}
					
					for (GroupBuyVO gbv : groupBuyMems) {
								
						if (com.friend_list.model.FriendListChatServer.getFriendListSessions().containsKey(gbv.getInvd_mem_num())) {
									
							for (Session groupBuySession : com.friend_list.model.FriendListChatServer.getFriendListSessions().get(gbv.getInvd_mem_num())) {
									
								if (groupBuySession.isOpen()) {
										
									synchronized(groupBuySession){
											
										groupBuySession.getAsyncRemote().sendText(sendJsonObj.toString());
											
									}
											
								}
								
							}
						}
								
					}
					
					if ("Y".equals(req.getParameter("OrManeger"))) {
						
						res.sendRedirect(req.getContextPath() + "/front-end/order/memorder.jsp");
						return;
						
					} else {
						
						res.sendRedirect(req.getContextPath() + "/front-end/index.jsp");
						return;
						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					errorMsgs.add("err");
				}
			} 
			
			res.sendRedirect(req.getContextPath() + "/front-end/index.jsp");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}