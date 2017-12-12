package com.group_buy.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
import com.member_profile.model.MemberProfileVO;
import com.store_profile.model.StoreProfileService;

public class GroupBuyController extends HttpServlet {

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

			GroupBuyService gbs = new GroupBuyService();

			if ("Invite".equals(action) || "AcceptInvite".equals(req.getParameter("isAccept")) || "TurnBackWhileGroupBuy".equals((String) req.getAttribute("action")) || "GroupBuyEdit".equals((String) req.getAttribute("action"))) {
				try {

					String[] invitedMems = req.getParameterValues("invite_friends");
					
					Set<String> hadInvited = new HashSet<String>();
					
					String inviter_num = null;
					
					String meror_num = (String) req.getSession().getAttribute("meror_num" + currentSto_num);
					
					try {
						
						inviter_num = gbs.getInviter(meror_num).getMem_num();
						
					} catch (RuntimeException re) {
						
						inviter_num = mem_num;
						
					}
					
					hadInvited.add(inviter_num);
					
					if (invitedMems != null) {
						
						StoreProfileService sps = new StoreProfileService();
						MemberProfileService mps = new MemberProfileService();
						
						String storeName = sps.getMyProfile(currentSto_num).getSto_name();
						String inviter = mps.getMyProfile(mem_num).getMem_name();
						
						JSONObject sendJsonObj = new JSONObject();
						
						try {
							
							sendJsonObj.put("action", "groupBuyInvite");
							sendJsonObj.put("inviter", inviter);
							sendJsonObj.put("meror_num", meror_num);
							sendJsonObj.put("storeName", storeName);
							
						} catch (JSONException e) {
							
							e.printStackTrace();
						}

						for (String invitedMem : invitedMems) {
							
							if (inviter_num != invitedMem) {
								
								try {

									gbs.addGroupBuy(inviter_num, invitedMem, meror_num); //addGroupBuyRecord
									
//									if (login.test.LoginInfo.getLoginRecord().containsKey(invitedMem)) {  //sendGroupBuyMessage
										
										if (com.friend_list.model.FriendListChatServer.getFriendListSessions().containsKey(invitedMem)) {
											
											for (Session friendSession : com.friend_list.model.FriendListChatServer.getFriendListSessions().get(invitedMem)) {
												
												if (friendSession.isOpen()) {
													
													synchronized(friendSession){
													
														friendSession.getAsyncRemote().sendText(sendJsonObj.toString());
														
													}
													
												}
											
											}
											
										}
										
//									}

								} catch (RuntimeException re) {

									System.out.println("團購重複邀請");

								}
								
							}

						}

					}

					List<GroupBuyVO> groupBuyMem = gbs.getGroupBuyMems(inviter_num, meror_num);
					
					for (GroupBuyVO invited : groupBuyMem) {

						hadInvited.add(invited.getInvd_mem_num());

					}

					req.setAttribute("hadInvited", hadInvited);
					
					RequestDispatcher successView = req.getRequestDispatcher("/front-end/Order_Master/OrderMaster.jsp");
					successView.forward(req, res);
					return;

				} catch (Exception e) {
					e.printStackTrace();
					errorMsgs.add("err");
				}
				
			} else if ("GetGroupBuyInfo".equals(action) || "GroupBuyCheckout".equals((String) req.getAttribute("action"))) {
				
				try {
					
					String meror_num = null;
					
					if (req.getSession().getAttribute("meror_num" + currentSto_num) != null) {
						
						meror_num = (String) req.getSession().getAttribute("meror_num" + currentSto_num);
						
					} else {
						
						meror_num = req.getParameter("meror_num");
						
					}
						
					MemberProfileVO inviter = gbs.getInviter(meror_num);

					List<GroupBuyVO> groupBuyMems = gbs.getGroupBuyMems(inviter.getMem_num(), meror_num);
					
					req.setAttribute("inviter", inviter);
					req.setAttribute("groupBuyMems", groupBuyMems);
					
					if ("GroupBuyCheckout".equals((String) req.getAttribute("action"))) {

						JSONObject sendJsonObj = new JSONObject();
						
						MemberProfileService mps = new MemberProfileService();
						
						try {
							
							sendJsonObj.put("action", "someOneCheckout");
							sendJsonObj.put("checkoutName", mps.getMyProfile(mem_num).getMem_name());
							sendJsonObj.put("meror_num", meror_num);
							
						} catch (JSONException e) {
							
							e.printStackTrace();
						}
						
						for (GroupBuyVO gbv : groupBuyMems) {
								
							if (!gbv.getInvd_mem_num().equals(mem_num) && com.friend_list.model.FriendListChatServer.getFriendListSessions().containsKey(gbv.getInvd_mem_num())) {
									
								for (Session groupBuySession : com.friend_list.model.FriendListChatServer.getFriendListSessions().get(gbv.getInvd_mem_num())) {
									
									if (groupBuySession.isOpen()) {
										
										synchronized(groupBuySession){
											
											groupBuySession.getAsyncRemote().sendText(sendJsonObj.toString());
											
										}
											
									}
								
								}	
									
							}
								
						}
						
						if (!mem_num.equals(inviter.getMem_num()) && com.friend_list.model.FriendListChatServer.getFriendListSessions().containsKey(inviter.getMem_num())) {
							
							for (Session inviterSession : com.friend_list.model.FriendListChatServer.getFriendListSessions().get(inviter.getMem_num())) {
								
								if (inviterSession.isOpen()) {
									
									synchronized(inviterSession){
										
										inviterSession.getAsyncRemote().sendText(sendJsonObj.toString());
										
									}
										
								}
							
							}	
								
						}
						
						req.setAttribute("meror_num", meror_num);
						
						if (req.getSession().getAttribute("meror_num" + currentSto_num) != null) {
							
							req.getSession().removeAttribute("meror_num" + currentSto_num);
							
						}

						RequestDispatcher successView = req.getRequestDispatcher("/front-end/Order_Master/CheckOut.jsp");
						successView.forward(req, res);
						return;

					} else {

						RequestDispatcher successView = req.getRequestDispatcher("/front-end/Group_Buy/GroupBuyInfo.jsp");
						successView.forward(req, res);
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