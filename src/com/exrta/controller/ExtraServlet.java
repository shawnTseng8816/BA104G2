package com.exrta.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.extra.model.*;

public class ExtraServlet extends HttpServlet {       
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession se = req.getSession();

		if ("insert".equals(action) ){
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
			String requestURL = req.getParameter("requestURL");
			req.setAttribute("requestURL", requestURL);
			try{
				/************ 1.接收請求參數 -輸入格式處理  ******************/	
				String sto_num = req.getParameter("sto_num");
				String ext_name = req.getParameter("ext_name");
				String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_\r\t\n\f)]{1,6}$";
								
				if (ext_name == null || ext_name.trim().length() == 0){
					errorMsgs.put("ext_name","加料名稱：請勿空白");
				} else if (!ext_name.trim().matches(nameReg)) {
					errorMsgs.put("ext_name","加料名稱：請勿含有特殊符號, 長度必需在1到6之間");
				}
				
				String price_nameReg = "^[0-9]{1,3}$";	
				Integer ext_amount = null;
				String ext_amount2 = req.getParameter("ext_amount");
				if(ext_amount2.trim().matches(price_nameReg) && !ext_amount2.equals("0")){
					try{
					ext_amount = new Integer (Integer.parseInt(ext_amount2));
					} catch(NumberFormatException e) {
						errorMsgs.put("ext_amount","價錢值錯誤");
					}
				}else{
					ext_amount = new Integer(0);
					errorMsgs.put("ext_amount","加料價錢：請輸入1-3位數數字");
				}	
				
				ExtraVO extraVO = new ExtraVO();
				extraVO.setSto_num(sto_num);
				extraVO.setExt_name(ext_name);
				extraVO.setExt_amount(ext_amount);
				extraVO.setStatus("上架");
				//send back if errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("extraVO", extraVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/store-end/pdc_mng/addExtra.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/************ 2.開始加入資料   ****************************/
				ExtraService extSvc = new ExtraService();
				extraVO = extSvc.addExtra(extraVO);				
				String ext_num = extraVO.getExt_num();
				
				/************ 3.加入完成,準備轉交(Send the Success view)**/	
				req.setAttribute("getAllExt", "getAllExt");		//跟select_page說要顯示				
				se.removeAttribute("addform");				//把通行證拿掉防止f5重送表單
				String url = "/store-end/pdc_mng/addExtra.jsp?ext_num="+ext_num;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);				
				
				/************ 其他錯誤處理  ******************************/
			} catch (Exception e) {
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store-end/pdc_mng/addExtra.jsp");
				failureView.forward(req, res);
			}			
		}
		
		if("getOne_For_Update".equals(action)){
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try{
				/************ 1.接收請求參數 -輸入格式處理  ******************/
				String ext_num = req.getParameter("ext_num");
				
				/************ 2.開始查詢資料   ****************************/
				ExtraService extSvc = new ExtraService();
				ExtraVO extraVO = extSvc.getOneExtra(ext_num);
				
				/************ 3.查詢完成,準備轉交(Send the Success view)**/				
				req.setAttribute("requestURL", requestURL);
				req.setAttribute("extraVO", extraVO);
				String url = "/store-end/pdc_mng/update_ext_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);				
				
				/************ 其他錯誤處理  ******************************/					
			} catch (Exception e) {
				errorMsgs.put("修改資料失敗:",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}	
		}
		
		if("update".equals(action)){
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
			String requestURL = req.getParameter("requestURL");
			req.setAttribute("requestURL", requestURL);
			try{
				/************ 1.接收請求參數 -輸入格式處理  ******************/
				String ext_num = req.getParameter("ext_num");
				String sto_num = req.getParameter("sto_num");
				String ext_name = req.getParameter("ext_name");
				
				String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_\r\t\n\f)]{1,6}$";
				if (ext_name == null || ext_name.trim().length() == 0){
					errorMsgs.put("ext_name","加料名稱：請勿空白");
				} else if (!ext_name.trim().matches(nameReg)) {
					errorMsgs.put("ext_name","加料名稱：請勿含有特殊符號, 長度必需在1到6之間");
				}
				
				String price_nameReg = "^[0-9]{1,3}$";	
				Integer ext_amount = null;
				String ext_amount2 = req.getParameter("ext_amount");
				if(ext_amount2.trim().matches(price_nameReg) && !ext_amount2.equals("0")){
					try{
					ext_amount = new Integer (Integer.parseInt(ext_amount2));
					} catch(NumberFormatException e) {
						errorMsgs.put("ext_amount","價錢值錯誤");
					}
				}else{
					ext_amount = new Integer(0);
					errorMsgs.put("ext_amount","加料價錢：請輸入1-3位數數字");
				}
				
				String status = req.getParameter("status");
		System.out.println(ext_num+" , "+sto_num+" , "+ext_name+" , "+ext_amount+" , "+status);
				ExtraVO extraVO = new ExtraVO();
				extraVO.setExt_num(ext_num);
				extraVO.setSto_num(sto_num);
				extraVO.setExt_name(ext_name);
				extraVO.setExt_amount(ext_amount);
				extraVO.setStatus(status);
				//send back if errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("extraVO", extraVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/store-end/pdc_mng/update_ext_input.jsp");
					failureView.forward(req, res);
					return;
				}				
				
				/************ 2.開始查詢資料   ****************************/
				ExtraService extSvc = new ExtraService();
				extraVO = extSvc.updateExtra(extraVO);
				
				/************ 3.查詢完成,準備轉交(Send the Success view)**/	
				req.setAttribute("getAllExt", "getAllExt");
				String url = requestURL+"?ext_num="+ext_num;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);				
				
				/************ 其他錯誤處理  ******************************/					
			} catch (Exception e) {
				errorMsgs.put("修改資料失敗:",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store-end/pdc_mng/update_ext_input.jsp");
				failureView.forward(req, res);
			}	
		}
		
		if("delete".equals(action)){
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try{
				/************ 1.接收請求參數 -輸入格式處理  ******************/
				String ext_num = req.getParameter("ext_num");	
								
				/************ 2.開始修改資料   ****************************/
				ExtraService extSvc = new ExtraService();
				ExtraVO extraVO = extSvc.getOneExtra(ext_num);
				extraVO.setStatus("刪除");
				extraVO = extSvc.updateExtra(extraVO);
				
				/************ 3.修改完成，準備轉交   ***********************/
				req.setAttribute("getAllExt", "getAllExt");		
				String url = requestURL; // 送出修改的來源網頁(listAllExtra)和修改的是哪一筆
				RequestDispatcher successView = req.getRequestDispatcher(url);   // 修改成功後,轉交回送出修改的來源網頁
				successView.forward(req, res);
				
				/************ 其他錯誤處理  ******************************/	
			} catch (Exception e){
				errorMsgs.put("刪除資料失敗:",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		if("getAllExt".equals(action)){			
			req.setAttribute("getAllExt", "getAllExt");			
			RequestDispatcher successView = req.getRequestDispatcher("/store-end/pdc_mng/store_select_page.jsp");
			successView.forward(req, res);
		}
		
		
	}
}
