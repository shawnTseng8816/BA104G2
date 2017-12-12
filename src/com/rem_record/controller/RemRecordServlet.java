package com.rem_record.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rem_record.model.MailService;
import com.rem_record.model.RemRecordService;
import com.rem_record.model.RemRecordVO;
import com.value_record.model.ValueRecordService;

import account.AccService;
import account.AccVO;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AccServlet
 */

public class RemRecordServlet extends HttpServlet {
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
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		
		JSONObject obj = new JSONObject();
		/*******匯款通過申請 !!***************************************/
		
		if ("rem_Pass".equals(action)) { // 來自 listRemCL.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String rem_num  = req.getParameter("rem_num");
				
				/***************************2.開始修改資料**************************************/
				
				RemRecordService remSvc = new RemRecordService();
				String rem_status = "匯款完成";
				remSvc.pass(rem_num, rem_status);
				
				
				
				
		
				/***************************3.修改完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/point/checkRem.jsp";
				RequestDispatcher dispatcher = req.getRequestDispatcher(url);
				dispatcher.forward(req, res);
				
				/***************************其他可能的錯誤處理***********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				String url = "/back-end/point/checkRem.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				
				failureView.forward(req, res);
			}
		}

		
		/*******沒有通過申請***************************************/
		if ("rem_NoPass".equals(action)) { // 來自 listRemCL.jsp的請求
			
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String rem_num  = req.getParameter("rem_num");
				System.out.println(rem_num );
				String sto_num  = req.getParameter("sto_num");
				System.out.println(sto_num );
				String rem_cash = req.getParameter("rem_cash");
				System.out.println(rem_cash );
				Integer backcash = Integer.parseInt(rem_cash);
			
				
				
				/**************************2.開始修改資料***************************************/
				
				RemRecordService remSvc = new RemRecordService();
							
				String rem_status = "審核失敗: 匯出帳戶錯誤 ";
				RemRecordVO remVO = new RemRecordVO();
				remVO.setRem_num(rem_num);
				remVO.setSto_num(sto_num);
				remVO.setRem_cash(backcash);
				remVO.setRem_status(rem_status);	
				remSvc.noPass(remVO);
								
				
				/***************************3.修改完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/point/checkRem.jsp";
				RequestDispatcher dispatcher = req.getRequestDispatcher(url);
				dispatcher.forward(req, res);
				
				/***************************其他可能的錯誤處理***********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				String url = "/back-end/point/checkRem.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}
		
		
		/******提款申請*********************************/
		if ("remPoint".equals(action)) {

			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/	
				String sto_num = (String) session.getAttribute("sto_num");			
				RemRecordService remSrc = new RemRecordService();
				
				//店家剩餘點數 
				int rem_point = remSrc.getStoRemPoint(sto_num);
				String rem_account = req.getParameter("rem_acc");
				Integer rem_cash = null;
				
				try{
				rem_cash = new Integer(req.getParameter("rem_cash").trim());
				}catch(NumberFormatException e){
					rem_cash = 0 ;
					errorMsgs.add("匯款金額輸入不正確 !");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/store-end/point/remPoint.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				String url = "/store-end/point/remPoint.jsp";
				//如果店家剩餘點 低於 要提款的金額
				if(rem_point < rem_cash){
			
					String value = "帳戶餘額不足 !!";		
					obj.put("errormessage", value);
					out.write(obj.toString());
					out.flush();
					out.close();
				/*	RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);*/
					return;
				}
					
				

				/*************************** 2.開始修改資料 *****************************************/
				RemRecordVO remVO = new RemRecordVO();
				remVO.setRem_account(rem_account);
				remVO.setRem_cash(rem_cash);
				remVO.setSto_num(sto_num);				
				remSrc.insertRem(remVO);
							
				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/
				String value = "我們已經收到您提出的申請 !";		
				obj.put("message", value);
				out.write(obj.toString());
				out.flush();
				out.close();
				
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("Exception : "+e);
				String url = "/store-end/point/remPoint.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}

		}

	

	}

}
