package com.keep_record_controller;

import java.io.IOException;
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
import com.keep_record.model.KeepRecordService;
import com.keep_record.model.KeepRecordVO;
import com.order_master.model.OrderMasterService;
import com.order_master.model.OrderMasterVO;

@WebServlet("/KeepRecordServlet")

public class KeepRecordServlet extends HttpServlet {
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

		// 寄杯審核通過
		if ("passKeep".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.接收請求參數 ***************************************/

				
				String keep_num = req.getParameter("keep_num");
				String keep_status = "審核通過";
				KeepRecordService keepRecordService = new KeepRecordService();
				
				keepRecordService.UpdateKeepRecord(keep_num, keep_status);
				/*************************** 2.開始修改資料 **************************************/

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 ***********/
//				RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/keeprecord/keeprecord.jsp");
//				dispatcher.forward(req, res);
				res.sendRedirect(req.getContextPath()+"/store-end/keeprecord/keeprecord.jsp?tabs=tab2");
				
				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/store-end/keeprecord/keeprecord.jsp");

				failureView.forward(req, res);
			}
		}
		
		// 寄杯領取完成
		if ("FinishKeep".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.接收請求參數 ***************************************/

				
				String keep_num = req.getParameter("keep_num");
				String keep_status = "已領取";
				KeepRecordService keepRecordService = new KeepRecordService();
				
				keepRecordService.UpdateKeepRecord(keep_num, keep_status);
				/*************************** 2.開始修改資料 **************************************/

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 ***********/
//				RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/keeprecord/keeprecord.jsp");
//				dispatcher.forward(req, res);
				res.sendRedirect(req.getContextPath()+"/store-end/keeprecord/keeprecord.jsp?tabs=tab2");
				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/store-end/keeprecord/keeprecord.jsp");

				failureView.forward(req, res);
			}
		}
		
		// 寄杯審核失敗
				if ("noPassKeep".equals(action)) {

					List<String> errorMsgs = new LinkedList<String>();

					req.setAttribute("errorMsgs", errorMsgs);
					try {
						/*************************** 1.接收請求參數 ***************************************/

						
						String keep_num = req.getParameter("keep_num");
						String keep_status = "未領取";
						KeepRecordService keepRecordService = new KeepRecordService();
						
						keepRecordService.UpdateKeepRecord(keep_num, keep_status);
						/*************************** 2.開始修改資料 **************************************/

						/***************************
						 * 3.修改完成,準備轉交(Send the Success view)
						 ***********/
//						RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/keeprecord/keeprecord.jsp");
//						dispatcher.forward(req, res);
						res.sendRedirect(req.getContextPath()+"/store-end/keeprecord/keeprecord.jsp?tabs=tab2");
						/*************************** 其他可能的錯誤處理 ***********************************/
					} catch (Exception e) {
						errorMsgs.add("新增資料失敗:" + e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/store-end/keeprecord/keeprecord.jsp");

						failureView.forward(req, res);
					}
				}
				
				
				// 電話查詢 訂單資訊**********************************************
				if ("getKeepInfoByMobile".equals(action)) {

					List<String> errorMsgs = new LinkedList<String>();

					req.setAttribute("errorMsgs", errorMsgs);

					try {
						/*************************** 1.接收請求參數 ***************************************/
					

						String mobile =req.getParameter("mobile");
						
						String sto_num = (String)session.getAttribute("sto_num");
						KeepRecordService keepRecordService = new KeepRecordService();
						
						
						List<KeepRecordVO> list = keepRecordService.getKeepInfoByMobile(mobile,sto_num);

						/***************************
						 * 3.查詢完成,準備轉交(Send the Success view)
						 ************/
						req.setAttribute("getKeepInfo", list);
						req.setAttribute("tabs", "tab1");
						// storeCommentVO.setMem_num((String)session.getAttribute("mem_num"));

						/***************************
						 * 3.修改完成,準備轉交(Send the Success view)
						 ***********/
						RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/keeprecord/keeprecord.jsp");
						dispatcher.forward(req, res);

						/*************************** 其他可能的錯誤處理 ***********************************/
					} catch (Exception e) {
						errorMsgs.add("新增資料失敗:" + e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/store-end/keeprecord/keeprecord.jsp");
						failureView.forward(req, res);
					}
				}
				
				
				// 複合查詢 訂單資訊**********************************************
				if ("getKeepInfo".equals(action)) {

					List<String> errorMsgs = new LinkedList<String>();

					req.setAttribute("errorMsgs", errorMsgs);

					try {
						/*************************** 1.接收請求參數 ***************************************/
						System.out.println("進入複合查詢");

						Map<String, String[]> map = req.getParameterMap();
						
						String keep_num = map.get("keep_num")[0];
						System.out.println("keep_num = "+ keep_num);
						KeepRecordService keepRecordService = new KeepRecordService();


						List<KeepRecordVO> list = keepRecordService.getKeepInfo(map);

						/***************************
						 * 3.查詢完成,準備轉交(Send the Success view)
						 ************/
						req.setAttribute("getKeepInfo", list);
						req.setAttribute("tabs", "tab1");
						
						// storeCommentVO.setMem_num((String)session.getAttribute("mem_num"));

						/***************************
						 * 3.修改完成,準備轉交(Send the Success view)
						 ***********/
						RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/keeprecord/keeprecord.jsp");
						dispatcher.forward(req, res);

						/*************************** 其他可能的錯誤處理 ***********************************/
					} catch (Exception e) {
						errorMsgs.add("新增資料失敗:" + e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/store-end/keeprecord/keeprecord.jsp");
						failureView.forward(req, res);
					}
				}
				
	}

}
