package com.ice_list.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ice_list.model.*;

public class IceListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession se = req.getSession();

		if("insert".equals(action)){			
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
	
			try{
				/************ 1.接收請求參數 -輸入格式處理  ******************/	
				String sto_num = req.getParameter("sto_num");
				String ice_type = req.getParameter("ice_type");
				String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_\r\t\n\f)]{1,6}$";
				
				if (ice_type == null || ice_type.trim().length() == 0){
					errorMsgs.put("sweet_type","冰塊名稱：請勿空白");
				} else if (!ice_type.trim().matches(nameReg)) {
					errorMsgs.put("sweet_type","冰塊名稱：請勿含有特殊符號, 長度必需在1到6之間");
				}				
				
				IceListVO iceListVO = new IceListVO();
				iceListVO.setSto_num(sto_num);
				iceListVO.setIce_type(ice_type);
				iceListVO.setStatus("上架");
				//send back if errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("iceListVO", iceListVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/store-end/pdc_mng/addIce.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/************ 2.開始加入資料   ****************************/
				IceListService swtSvc = new IceListService();
				iceListVO = swtSvc.insertIceList(iceListVO);				
				String ice_num = iceListVO.getIce_num();
				
				/************ 3.加入完成,準備轉交(Send the Success view)**/	
				req.setAttribute("getAllIce", "getAllIce");	//跟select_page說要顯示冰
				se.removeAttribute("addform");				//把通行證拿掉防止f5重送表單

				String url = "/store-end/pdc_mng/addIce.jsp?ice_num="+ice_num;
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);				
				
				/************ 其他錯誤處理  ******************************/
			} catch (Exception e) {
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store-end/pdc_mng/addIce.jsp");
				failureView.forward(req, res);
			}	
		}
		
		if("getOne_For_Update".equals(action)){
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try{
				/************ 1.接收請求參數 -輸入格式處理  ******************/
				String ice_num = req.getParameter("ice_num");
				
				/************ 2.開始查詢資料   ****************************/
				IceListService iceSvc = new IceListService();
				IceListVO iceListVO = iceSvc.getOneIce(ice_num);
				
				/************ 3.查詢完成,準備轉交(Send the Success view)**/				
				req.setAttribute("requestURL", requestURL);
				req.setAttribute("iceListVO", iceListVO);
				String url = "/store-end/pdc_mng/update_ice_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_pdc_input.jsp
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
				String ice_num = req.getParameter("ice_num");
				String sto_num = req.getParameter("sto_num");
				String ice_type = req.getParameter("ice_type");
				String status = req.getParameter("status");
				
				String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_\r\t\n\f)]{1,6}$";
				if (ice_type == null || ice_type.trim().length() == 0){
					errorMsgs.put("sweet_type","冰塊名稱：請勿空白");
				} else if (!ice_type.trim().matches(nameReg)) {
					errorMsgs.put("sweet_type","冰塊名稱：請勿含有特殊符號, 長度必需在1到6之間");
				}
				IceListVO iceListVO = new IceListVO();
				iceListVO.setIce_num(ice_num);
				iceListVO.setSto_num(sto_num);
				iceListVO.setIce_type(ice_type);
				iceListVO.setStatus(status);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("iceListVO", iceListVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/store-end/pdc_mng/update_ice_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}	
				
				/************ 2.開始修改資料   ****************************/
				IceListService iceSvc = new IceListService();
				iceListVO = iceSvc.updateIceList(iceListVO);
		
				/************ 3.修改完成，準備轉交   ***********************/
				req.setAttribute("getAllIce", "getAllIce");
				String url = requestURL+"?ice_num="+ice_num; // 送出修改的來源網頁(listAllSweet)和修改的是哪一筆
				RequestDispatcher successView = req.getRequestDispatcher(url);   // 修改成功後,轉交回送出修改的來源網頁
				successView.forward(req, res);
				
				/************ 其他錯誤處理  ******************************/	
			} catch (Exception e){
				errorMsgs.put("修改資料失敗:",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store-end/pdc_mng/update_ice_input.jsp");
				failureView.forward(req, res);
			}
		}
	
		if(("delete").equals(action)){
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try{
				/************ 1.接收請求參數 -輸入格式處理  ******************/
				String ice_num = req.getParameter("ice_num");	
								
				/************ 2.開始修改資料   ****************************/
				IceListService iceSvc = new IceListService();
				IceListVO iceListVO = iceSvc.getOneIce(ice_num);
				iceListVO.setStatus("刪除");
				iceListVO = iceSvc.updateIceList(iceListVO);
				
				/************ 3.修改完成，準備轉交   ***********************/
				req.setAttribute("getAllIce", "getAllIce");
				String url = requestURL; // 送出修改的來源網頁(listAllSweet)和修改的是哪一筆
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
		
		if("getAllIce".equals(action)){			
			req.setAttribute("getAllIce", "getAllIce");
			RequestDispatcher successView = req.getRequestDispatcher("/store-end/pdc_mng/store_select_page.jsp");
			successView.forward(req, res);
		}
	}
}
