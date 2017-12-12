package com.order_master.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.card.model.CardService;
import com.card.model.CardVO;
import com.friend_list.model.FriendListChatServer;
import com.order_master.model.OrderMasterService;
import com.order_master.model.OrderMasterVO;
import com.store_comment.model.StoreCommentService;
import com.store_comment.model.StoreCommentVO;




@WebServlet("/OrderMasterServlet")
public class OrderMasterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		// TODO Auto-generated method stub
		res.setHeader("Cache-Control", "no-store");
		res.setHeader("Pragma", "no-cache");
		res.setDateHeader("Expires", 0);

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		HttpSession session = req.getSession();

		// 複合查詢 訂單資訊**********************************************
		if ("getOrderInfo".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				System.out.println("進入複合查詢");

				Map<String, String[]> map = req.getParameterMap();

				OrderMasterService orderMasterService = new OrderMasterService();
				String nnn = map.get("sto_num")[0];
				System.out.println("sto_num " + nnn);

				List<OrderMasterVO> list = orderMasterService.getOrderInfo(map);
				System.out.println("list=" + list.size());
				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				req.setAttribute("getOrderInfo", list);

				// storeCommentVO.setMem_num((String)session.getAttribute("mem_num"));

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 ***********/
				RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/order/order.jsp");
				dispatcher.forward(req, res);
			//	res.sendRedirect(req.getContextPath() + "/store-end/order/order.jsp");
				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/store-end/order/order.jsp");
				failureView.forward(req, res);
			}
		}

		// 評價店家*********************************************
		else if ("insertComment".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
					
				String com_title = req.getParameter("com_title");
				
				
				String sto_num = req.getParameter("sto_num");
				String or_num = req.getParameter("or_num");
				String stars_point = req.getParameter("stars_point");
				String commentt = req.getParameter("commentt");

				
				int stars = 0;
				
				try {
					 stars = Integer.parseInt(req.getParameter(stars_point));
				} catch (NumberFormatException e) {
					
					 stars = 0;

				}
				
			if(com_title.isEmpty()||sto_num.isEmpty()||or_num.isEmpty()||commentt.isEmpty()||stars<=0||stars>5){
					

				String message = "您的資料填寫不完整 。" ;
				
				String webSocketAction = "memGetOrderErrMessage";
				
				FriendListChatServer.OneByOne((String)session.getAttribute("mem_num"),webSocketAction ,message);
			
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/order/memorder.jsp");
				failureView.forward(req, res);
				return;
				}

				req.setAttribute("or_num", or_num);
				StoreCommentService storeCommentService = new StoreCommentService();
				StoreCommentVO storeCommentVO = new StoreCommentVO();

				
				storeCommentVO.setCom_title(com_title);
				storeCommentVO.setCommentt(commentt);
				storeCommentVO.setOr_num(or_num);
				storeCommentVO.setStars(stars);
				storeCommentVO.setSto_num(sto_num);	
				storeCommentVO.setMem_num((String)session.getAttribute("mem_num"));
				storeCommentService.insert(storeCommentVO);

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 ***********/
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front-end/order/roller.jsp");
				dispatcher.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/order/memorder.jsp");
				failureView.forward(req, res);
			}
		}

		// 訂單準備好*********************************
		else if ("orderAlready".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/

				String or_num = req.getParameter("or_num");
				OrderMasterService ordermasterSrc = new OrderMasterService();
				ordermasterSrc.orderAlready(or_num);

				/*************************** 2.開始修改資料 **************************************/

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 ***********/
//				RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/order/order.jsp");
//				dispatcher.forward(req, res);
				String mem_num = ordermasterSrc.getOneOrder(or_num).getMem_num();
				String message = "您的訂單編號 : "+or_num+"<br> 商品已準備好 !" ;
				
				String webSocketAction = "memGetOrderMessage";
				
				FriendListChatServer.OneByOne(mem_num,webSocketAction ,message);
				res.sendRedirect(req.getContextPath() + "/store-end/order/order.jsp?tab=tab3");

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/store-end/order/order.jsp");
				failureView.forward(req, res);
			}
		}

		// 商品已交貨*********************************
		else if ("delivered".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/

				String or_num = req.getParameter("or_num");
				OrderMasterService ordermasterSrc = new OrderMasterService();
				ordermasterSrc.orderDelivered(or_num);

				/*************************** 2.開始修改資料 **************************************/

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 ***********/
//				RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/order/order.jsp");
//				dispatcher.forward(req, res);

				res.sendRedirect(req.getContextPath() + "/store-end/order/order.jsp?tab=tab3");
				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/store-end/order/order.jsp");
				failureView.forward(req, res);
			}
		}

		// 訂單審核通過*****************************************************
		else if ("passOrder".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.接收請求參數 ***************************************/

				String or_num = req.getParameter("or_num");
				OrderMasterService ordermasterSrc = new OrderMasterService();
				ordermasterSrc.orderPass(or_num);
				/*************************** 2.開始修改資料 **************************************/

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 ***********/
				String mem_num = ordermasterSrc.getOneOrder(or_num).getMem_num();
				
			
			
				String message = "您的訂單編號 : "+or_num+"<br>已審核通過 !" ;
				
				String webSocketAction = "memGetOrderMessage";
				
				FriendListChatServer.OneByOne(mem_num,webSocketAction ,message);
				
	
				
				
				res.sendRedirect(req.getContextPath() + "/store-end/order/order.jsp?tab=tab2");
				
				
//				RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/order/order.jsp");
//				dispatcher.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/store-end/order/order.jsp");
				failureView.forward(req, res);
			}
		}

		// 訂單團購審核通過*******************************************
		else if ("groupPassOrder".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.接收請求參數 ***************************************/

				
				
				String meror_num = req.getParameter("meror_num");
				OrderMasterService ordermasterSrc = new OrderMasterService();
				ordermasterSrc.groupOrderPass(meror_num);
				/*************************** 2.開始修改資料 **************************************/
				
				List<OrderMasterVO> list = new ArrayList<OrderMasterVO>();
				list =ordermasterSrc.getGroupAllOrderNumByMerorNum(meror_num);
				String webSocketAction = "memGetOrderMessage";
				String message = "您的團購訂單已審核通過 !" ;
				for(OrderMasterVO eachMem:list){
							
				FriendListChatServer.OneByOne(eachMem.getMem_num(),webSocketAction ,message);
					}
					
					
				
				
			
				
				
				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 ***********/
				res.sendRedirect(req.getContextPath() + "/store-end/order/order.jsp?tab=tab2");
//				RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/order/order.jsp");
//				dispatcher.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/store-end/order/order.jsp");

				failureView.forward(req, res);
			}
		}

		// 完成訂單**********************************************************
		else if ("finishOrder".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/

				OrderMasterVO orderMasterVO = new OrderMasterVO();
				OrderMasterService ordermasterSrc = new OrderMasterService();
				List<OrderMasterVO> list = new ArrayList<OrderMasterVO>();
				String meror_num = req.getParameter("meror_num");

				if (meror_num != null && meror_num.trim().length() != 0) {
					
					
					list =ordermasterSrc.getGroupAllOrderNumByMerorNum(meror_num);
					System.out.println("進入團購" + list.size());
					ordermasterSrc.finishOrder(list);

				} else {

					String or_num = req.getParameter("or_num");
					orderMasterVO = ordermasterSrc.getOneOrder(or_num);
					list.add(orderMasterVO);
					ordermasterSrc.finishOrder(list);
				}
				/*************************** 2.開始修改資料 **************************************/

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 ***********/
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front-end/order/memorder.jsp");
				dispatcher.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/order/memorder.jsp");

				failureView.forward(req, res);
			}
		}

		// *****************************************************
		// 訂單省核失敗
		else if ("nopassOrder".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/

				String or_num = req.getParameter("or_num");
				OrderMasterService ordermasterSrc = new OrderMasterService();
				ordermasterSrc.orderCancel(or_num);

				/*************************** 2.開始修改資料 **************************************/
				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 ***********/
//				RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/order/order.jsp");
//				dispatcher.forward(req, res);

				res.sendRedirect(req.getContextPath() + "/store-end/order/order.jsp?tab=tab2");
				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/store-end/order/order.jsp");

				failureView.forward(req, res);
			}
		}

	}

}
