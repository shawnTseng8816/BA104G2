package com.card.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.card.model.CardService;
import com.card.model.CardVO;
import com.coupon.model.CouponService;
import com.coupon.model.CouponVO;
import com.friend_list.model.FriendListChatServer;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class CardServlet
 */
@WebServlet("/CardServlet")
public class CardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		res.setHeader("Cache-Control", "no-store");
		res.setHeader("Pragma", "no-cache");
		res.setDateHeader("Expires", 0);
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		res.setCharacterEncoding("utf-8");
		JSONObject obj = new JSONObject();
		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession();
		
		
		// 新增集點卡
				if ("newCard".equals(action)) {

				

					List<String> errorMsgs = new LinkedList<String>();

					req.setAttribute("errorMsgs", errorMsgs);

					try {
						/*************************** 1.接收請求參數 ***************************************/
					
						
							
						String sto_num = (String)session.getAttribute("sto_num");		
						
						
						int points =0;
						try {
							points = Integer.parseInt(req.getParameter("points"));
							if(points>50){
								String message = "點數最高 50 點哦 。";

								String webSocketAction = "stoGetErrMessage";

								FriendListChatServer.OneByOne(sto_num, webSocketAction, message);

								RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/store/storeProfile.jsp");
								dispatcher.forward(req, res);
								return;
								
							}
							
						} catch (Exception e) {
							 points =0;
						}
						int points_cash = 0;
						
						try{
							points_cash = Integer.parseInt(req.getParameter("points_cash"));
						if(points_cash >99){
							String message = "折價金額最高不得超過 100。";

							String webSocketAction = "stoGetErrMessage";

							FriendListChatServer.OneByOne(sto_num, webSocketAction, message);

							RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/store/storeProfile.jsp");
							dispatcher.forward(req, res);
							return;
							
						}
						 
						}catch (Exception e) {
							 points =0;
						}
						String card_des = req.getParameter("card_des");
						
						int exp_date = 0;
						
						try{
							 exp_date = Integer.parseInt(req.getParameter("exp_date"));
						if(exp_date >99){
							String message = "有效期限不得超過 99 個月。";

							String webSocketAction = "stoGetErrMessage";

							FriendListChatServer.OneByOne(sto_num, webSocketAction, message);

							RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/store/storeProfile.jsp");
							dispatcher.forward(req, res);
							return;
							
						}
						 
						}catch (Exception e) {
							exp_date =0;
						}
						
						if(exp_date==0||points==0||points_cash==0||card_des.isEmpty()){
							
							String message = "資料輸入有誤 。";

							String webSocketAction = "stoGetErrMessage";

							FriendListChatServer.OneByOne(sto_num, webSocketAction, message);

							RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/store/storeProfile.jsp");
							dispatcher.forward(req, res);
							return;
							
						}
						
						/*************************** 2.開始修改資料 **************************************/
				
						CardVO cardVO = new CardVO();
						cardVO.setPoints(points);
						cardVO.setCard_des(card_des);
						cardVO.setExp_date(exp_date);
						cardVO.setPoints_cash(points_cash);
						cardVO.setSto_num(sto_num);
						CardService cardSvc = new CardService();
						cardSvc.insertCard(cardVO);
						
						String value = "新增成功 !";		
						obj.put("message", value);
						out.write(obj.toString());
						out.flush();
						out.close();
						
						/***************************
						 * 3.修改完成,準備轉交(Send the Success view)
						 ***********/
//						RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/store/storeProfile.jsp");
//						dispatcher.forward(req, res);

						/*************************** 其他可能的錯誤處理 ***********************************/
					} catch (Exception e) {
						errorMsgs.add("新增資料失敗:" + e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/store-end/store/storeProfile.jsp");

						failureView.forward(req, res);
					}
				}
				
				
				
				// 上架集點卡
				if ("upCard".equals(action)) {

						System.out.println("進入上架集點卡");
					List<String> errorMsgs = new LinkedList<String>();

					req.setAttribute("errorMsgs", errorMsgs);

					try {
						/*************************** 1.接收請求參數 ***************************************/
					
							
						String sto_num = (String)session.getAttribute("sto_num");		
			
						String card_kinds = req.getParameter("card_kinds");
						
						
						/*************************** 2.開始修改資料 **************************************/
						
						CardService cardSvc = new CardService();
						cardSvc.upCard(sto_num, card_kinds);
						
						String value = "修改完成 !";		
						obj.put("message", value);
						out.write(obj.toString());
						out.flush();
						out.close();
						
						/***************************
						 * 3.修改完成,準備轉交(Send the Success view)
						 ***********/
					

						/*************************** 其他可能的錯誤處理 ***********************************/
					} catch (Exception e) {
						errorMsgs.add("新增資料失敗:" + e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/store-end/store/storeProfile.jsp");

						failureView.forward(req, res);
					}
				}
				
				// 下架集點卡
				if ("downCard".equals(action)) {

					

					List<String> errorMsgs = new LinkedList<String>();

					req.setAttribute("errorMsgs", errorMsgs);

					try {
						/*************************** 1.接收請求參數 ***************************************/
					
						
							
						String sto_num = (String)session.getAttribute("sto_num");		
			
						
						
						
						/*************************** 2.開始修改資料 **************************************/
						
						CardService cardSvc = new CardService();
						cardSvc.downCard(sto_num);
						/***************************
						 * 3.修改完成,準備轉交(Send the Success view)
						 ***********/
						String value = "修改完成 !";		
						obj.put("message", value);
						out.write(obj.toString());
						out.flush();
						out.close();

						/*************************** 其他可能的錯誤處理 ***********************************/
					} catch (Exception e) {
						errorMsgs.add("新增資料失敗:" + e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/store-end/store/storeProfile.jsp");

						failureView.forward(req, res);
					}
				}
				
	}

}
