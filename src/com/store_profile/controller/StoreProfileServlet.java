package com.store_profile.controller;

import java.io.*;
import java.util.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.store_profile.model.*;
import com.shop_ad.model.*;

/**
 * Servlet implementation class AccServlet
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
//當數據量大於fileSizeThreshold值時，內容將被寫入磁碟
//上傳過程中無論是單個文件超過maxFileSize值，或者上傳的總量大於maxRequestSize 值都會拋出IllegalStateException 異常

public class StoreProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");  // <input type="hidden" name="action"  value="login">

		HttpSession session = req.getSession();

		if("logout".equals(action)){
			req.getSession().invalidate();

			res.sendRedirect(req.getContextPath() + "/front-end/index.jsp");

		}
		else if ("login".equals(action)) { // 來自select_page.jsp的請求
			System.out.println("我進來拉!!!");
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();  // List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String acc = req.getParameter("sto_acc");
				String accReg = "^[(a-zA-Z0-9)]{2,12}$";
				String pwd = req.getParameter("sto_pwd");
				String pwdReg = "^[(a-zA-Z0-9_)]{2,12}$";
			System.out.println(acc);
			System.out.println(pwd);
				if ((acc == null || (acc.trim()).length() == 0)) { 
					// str.trim()：去除首尾空白符號
					errorMsgs.put("帳號","請勿空白");
				}else if(!acc.trim().matches(accReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("帳號","格式不正確");
					// errorMsgs.put("acc","帳號 : 只能是英文字母、數字 , 且長度必需在2到12之間");
	            }
				if ((pwd == null || (pwd.trim()).length() == 0)) { 
					// str.trim()：去除首尾空白符號
					errorMsgs.put("密碼","請勿空白");
				}else if(!pwd.trim().matches(pwdReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("密碼","格式不正確");
					// errorMsgs.put("pwd","密碼 : 只能是英文字母、數字 , 且長度必需在2到12之間");
	            }


				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req  
							.getRequestDispatcher("/front-end/storeprofile/login.jsp");  
					        // getRequestDispatcher(p.148)：請求轉發(與Redirect不同)
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/

				StoreProfileService storeprofileSrc = new StoreProfileService();  // service 方法
				StoreProfileVO storeprofileVO = new StoreProfileVO();             // 會員資訊物件memProVO

				//使用service 方法去 DAO 查詢帳號 如果有回傳 一個會員資訊物件 memProVO
				storeprofileVO =storeprofileSrc.checkLogin(acc);
				// MemberProfileService的登入確認方法：
				//     public MemberProfileVO checkLogin(String mem_acc){
				//    	    return dao.checkLogin(mem_acc);	
				//     }
				if (storeprofileVO == null) {
					System.out.println("帳號錯誤~!");
					errorMsgs.put("帳號","查無資料");
				}
				
				if(!storeprofileVO.getSto_pwd().equals(pwd)){
					// MemberProfileVO裡面有實作getXxx()、setXxx()
					System.out.println("密碼錯誤~!");
					errorMsgs.put("密碼","錯誤");
				}
				
				 String sto_status = "申請中";
				if(storeprofileVO.getSto_status().equals(sto_status)){
					errorMsgs.put("驗證","尚未通過");
				}
					
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/store-end/storeprofile/login.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}

				/***************************3.查詢完成,準備轉交(Send the Success view)*************/

//				//資料庫取的密碼 跟 輸入的相同

				session.invalidate();

				session = req.getSession();
				session.setAttribute("sto_num", storeprofileVO.getSto_num());
				session.setAttribute("sto_name", storeprofileVO.getSto_num());

				session.setAttribute("storeprofileVO", storeprofileVO);
//				String url = "/front-end/storeprofile/login_success.jsp";

				//轉到框中
				res.sendRedirect(req.getContextPath() + "/store-end/frameForStoMessage.jsp");
				return;

//				String url = "/store-end/pdc_mng/store_select_page.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 select_page1_free.jsp
//				successView.forward(req, res);
//
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store-end/storeprofile/login.jsp");
				failureView.forward(req, res);
			}
		}


		if ("insertsto".equals(action)) { // 來自addEmp.jsp的請求  

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>(); 
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String sto_acc = req.getParameter("sto_acc");
				String sto_accReg = "^[(a-zA-Z0-9)]{2,50}$";
				StoreProfileService storeprofileSrc = new StoreProfileService();  // service 方法
				//店家資訊物件storeprofileVO + 使用service 方法去 DAO 查詢帳號 如果有回傳 一個會員資訊物件 storeprofileVO
				StoreProfileVO storeprofileaccVO = storeprofileSrc.checkLogin(sto_acc);

				if (storeprofileaccVO != null){
					errorMsgs.put("商店帳號","已被申請");
				}
				if (sto_acc == null || sto_acc.trim().length() == 0) {
					errorMsgs.put("商店帳號","請勿空白");
				} else if(!sto_acc.trim().matches(sto_accReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("商店帳號","只能是英文字母、數字 , 且長度必需在2到50之間");
	            }
//				if(storeprofileVOcheck.equals(sto_acc)){
//					errorMsgs.put("sto_acc","會員帳號:帳號已被註冊");
//				}
//				else if(storeprofileVO.getSto_acc().equals(sto_acc.trim())){
//	            	errorMsgs.put("mem_acc","會員帳號: 帳號已被使用");
//	            }
			//System.out.println("sto_acc進來拉!!!");

				String sto_pwd = req.getParameter("sto_pwd");
				String sto_pwdReg = "^[(a-zA-Z0-9)]{2,50}$";
				//店家資訊物件storeprofileVO + 使用service 方法去 DAO 查詢帳號 如果有回傳 一個會員資訊物件 storeprofileVO
				StoreProfileVO storeprofilepwdVO = storeprofileSrc.checkLogin(sto_pwd);

				if (storeprofilepwdVO != null){
					errorMsgs.put("商店密碼","已被使用");
				}
				if (sto_pwd == null || sto_pwd.trim().length() == 0) {
					errorMsgs.put("商店密碼","請勿空白");
				} else if(!sto_pwd.trim().matches(sto_pwdReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("商店密碼","只能是中、英文字母、數字 , 且長度必需在2到50之間");
	            }
//				if(storeprofileVOcheck != null){
//					errorMsgs.put("sto_pwd","會員密碼:帳號已被使用");
//				}
			//System.out.println("sto_pwd進來拉!!!");
				
				String sto_name = req.getParameter("sto_name");
				String sto_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,50}$";

				if (sto_name == null || sto_name.trim().length() == 0) {
					errorMsgs.put("商店名稱","請勿空白");
				} else if(!sto_name.trim().matches(sto_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("商店名稱","只能是中、英文字母、數字和_ , 且長度必需在2到50之間");
	            }
			//System.out.println("sto_name進來拉!!!");

				String guy = req.getParameter("guy");
				String guyReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,50}$";
				if (guy == null || guy.trim().length() == 0) {
					errorMsgs.put("商店名稱","請勿空白");
				} else if(!guy.trim().matches(guyReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("商店名稱","只能是中、英文字母 , 且長度必需在2到50之間");
	            }
			//System.out.println("guy進來拉!!!");

				Integer uni_num = null;
				try {
					uni_num = new Integer(req.getParameter("uni_num").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("統一編號","尚未填寫");
				}  // 是不是要自動產生?先隱藏，註冊完後開啟，做兩個畫面比較快
			//System.out.println("uni_num進來拉!!!");

				String mobile = req.getParameter("mobile");
				// https://www.mkyong.com/spring-mvc/spring-mvc-radiobutton-and-radiobuttons-example/
				if (mobile == null || mobile.trim().length() == 0) {
					errorMsgs.put("商店電話號碼","尚未填寫");
				}
			//System.out.println("mobile進來拉!!!");

				String area = req.getParameter("area");
				// https://www.mkyong.com/spring-mvc/spring-mvc-radiobutton-and-radiobuttons-example/
				if (area == null || area.trim().length() == 0) {
					errorMsgs.put("商店所在地區","尚未填寫");
					// 經由後台人員驗證後 → 通過電話驗證 → 狀態：正常(行237)
				}
			System.out.println("area進來拉!!!");

				String address = req.getParameter("address");
				String addressReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{0,50}$";
				// https://www.mkyong.com/spring-mvc/spring-mvc-radiobutton-and-radiobuttons-example/
				if (address == null || address.trim().length() == 0) {
					errorMsgs.put("商店地址","尚未填寫");
					// 經由後台人員驗證後 → 通過電話驗證 → 狀態：正常(行237)
				} else if(!address.trim().matches(addressReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("商店地址","只能是中、英文字母和數字，長度必需在0到50之間 ");
	            }
			System.out.println("address之前的都進來拉!!!");
			
				String email = req.getParameter("email");
				// https://www.mkyong.com/spring-mvc/spring-mvc-radiobutton-and-radiobuttons-example/
				if (email == null || email.trim().length() == 0) {
					errorMsgs.put("商店信箱","尚未填寫");
					// 經由後台人員驗證後 → 通過電話驗證 → 狀態：正常(行237)
				}
			System.out.println("email進來拉!!!");

				Date set_time = null;
				try {
					set_time = Date.valueOf(req.getParameter("set_time").trim());
				} catch (IllegalArgumentException e) {
					set_time=new Date(System.currentTimeMillis());
					errorMsgs.put("商店設立時間", "請輸入日期!");
				}
			System.out.println("set_time進來拉!!!");

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				//緩衝
				InputStream in = req.getPart("upfile").getInputStream();
				//暫存BYTE陣列
				int i;
				byte[] buffer = new byte[8192];
				//紀錄讀進來長度
				while ((i = in.read(buffer)) != -1) {
				//從緩衝區讀取buffer裡面0~length-1的位置
				//假如等於-1代表沒有資料了
					baos.write(buffer, 0, i);
				}
				if (baos.size() == 0){
					errorMsgs.put("圖片","尚未選擇");
					// 很好，圖片錯誤處理終於出來惹
				}
				baos.close();	
				in.close();
				byte[] sto_logo = baos.toByteArray();

				// https://www.bbsmax.com/A/pRdBnBX1dn/
				//ByteArrayOutputStream轉成位元陣列
			System.out.println("乾~需要圖圖!!!");
			// https://openhome.cc/Gossip/ServletJSP/Part.html
			// http://www.srikanthtechnologies.com/blog/java/fileupload.aspx
			// http://gn00466269.blogspot.tw/2015/09/jsp.html
				
//				String email = req.getParameter("email");
//				// https://www.mkyong.com/spring-mvc/spring-mvc-radiobutton-and-radiobuttons-example/
//				if (email == null || email.trim().length() == 0) {
//					errorMsgs.put("email","電子信箱: 尚未填寫");
//				}

				String sto_introduce = req.getParameter("sto_introduce");
				// https://www.mkyong.com/spring-mvc/spring-mvc-radiobutton-and-radiobuttons-example/
				if (sto_introduce == null || sto_introduce.trim().length() == 0) {
					errorMsgs.put("商店介紹","尚未填寫");
				}
			System.out.println("sto_introduce進來拉!!!");

				Integer rem_point = 0;
//				try {
//					rem_point = new Integer(req.getParameter("rem_point").trim());
//				} catch (NumberFormatException e) {
//					rem_point = 0;
//					errorMsgs.put("rem_point","剩餘點數:0");
//					// 剛開始都是0，可以去儲值
//				}
			System.out.println("進!!!");

				String sto_status = "申請中";

			System.out.println("sto_status進來拉!!!");

				StoreProfileVO storeprofileVO = new StoreProfileVO();
				storeprofileVO.setSto_name(sto_name);
				storeprofileVO.setGuy(guy);              //setInterest(sb.toString());
				storeprofileVO.setUni_num(uni_num);
				storeprofileVO.setMobile(mobile);
				storeprofileVO.setArea(area);
				storeprofileVO.setAddress(address);
				storeprofileVO.setEmail(email);
				storeprofileVO.setSet_time(set_time);
				storeprofileVO.setSto_logo(sto_logo);
				storeprofileVO.setSto_introduce(sto_introduce);
				storeprofileVO.setSto_status(sto_status);
				storeprofileVO.setRem_point(rem_point);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("storeprofileVO", storeprofileVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/store-end/storeprofile/addStoreProfile.jsp");
					failureView.forward(req, res);
					return;
				}

				/***************************2.開始新增資料***************************************/
				StoreProfileService storeprofileSvc = new StoreProfileService();

				storeprofileVO = storeprofileSvc.addStoreProfile(sto_acc, sto_pwd, sto_name, guy, uni_num, 
																 mobile, area, address, email, set_time, 
																 sto_logo, sto_introduce, sto_status, rem_point);

				/***************************3.新增完成,準備轉交(Send the login view)***********/

				String url = "/store-end/storeprofile/login.jsp";
				RequestDispatcher loginView = req.getRequestDispatcher(url); // 新增成功後轉交login.jsp
				loginView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store-end/storeprofile/addStoreProfile.jsp"); 
				failureView.forward(req, res);
			}	
		}

		if ("insertad".equals(action)) { // 來自addad.jsp的請求  

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>(); 
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			// 送出修改的來源網頁路徑

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/

				String sto_num = req.getParameter("sto_num").trim();  // 從session取來der
			System.out.println("sto_num：" + sto_num );

				String sa_title = req.getParameter("sa_title").trim();
				if (sa_title == null || sa_title.trim().length() == 0) {
					errorMsgs.put("廣告標題","請勿空白");
				}
			System.out.println("sa_title：" + sa_title );

				String sa_text = req.getParameter("sa_text");
				if (sa_text == null || sa_text.trim().length() == 0) {
					errorMsgs.put("廣告內容文字","請勿空白");
				}
			System.out.println("sa_text：" + sa_text );

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				//緩衝
				InputStream in = req.getPart("upfile").getInputStream();
				//暫存BYTE陣列
				int i;
				byte[] buffer = new byte[8192];
				//紀錄讀進來長度
				while ((i = in.read(buffer)) != -1) {
				//從緩衝區讀取buffer裡面0~length-1的位置
				//假如等於-1代表沒有資料了
					baos.write(buffer, 0, i);
				}
				if (baos.size() == 0){
					errorMsgs.put("圖片","尚未選擇");
					// 很好，圖片錯誤處理終於出來惹
				}
				baos.close();	
				in.close();
				byte[] sa_img = baos.toByteArray();
				// https://www.bbsmax.com/A/pRdBnBX1dn/
				//ByteArrayOutputStream轉成位元陣列
			System.out.println("sa_img：" + sa_img );
			// https://openhome.cc/Gossip/ServletJSP/Part.html
			// http://www.srikanthtechnologies.com/blog/java/fileupload.aspx
			// http://gn00466269.blogspot.tw/2015/09/jsp.html

				Integer sa_views = 0;
			System.out.println("sa_views：" + sa_views );

				Date sa_apptime = new Date(System.currentTimeMillis());
//			Date date = new Date(System.currentTimeMillis());
//			DateFormat df = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
//			Date sa_apptime = Date.valueOf(df.format(date));
			System.out.println("sa_apptime[自動]：" + sa_apptime);

//				Date sa_addtime = null;
//				try {
//					sa_addtime = Date.valueOf(req.getParameter("sa_addtime").trim());
//				} catch (IllegalArgumentException e) {
//					sa_addtime=new Date(System.currentTimeMillis());
//					errorMsgs.put("sa_addtime", "商店設立時間:請輸入日期!");
//				}			
			Date sa_addtime = Date.valueOf(req.getParameter("sa_addtime").trim());
			if( sa_addtime == null ) {
				errorMsgs.put("商店設立時間", "請輸入日期!");
			}
			System.out.println("sa_addtime" + sa_addtime );

//				Date sa_preofftime = null;
//				try {
//					sa_preofftime = Date.valueOf(req.getParameter("sa_preofftime").trim());
//				} catch (IllegalArgumentException e) {
//					sa_preofftime=new Date(System.currentTimeMillis());
//					errorMsgs.put("sa_preofftime", "商店設立時間:請輸入日期!");
//				}
				Date sa_preofftime = Date.valueOf(req.getParameter("sa_preofftime").trim());
				if( sa_preofftime == null ) {
					errorMsgs.put("商店設立時間", "請輸入日期!");
				}
			System.out.println("sa_preofftime：" + sa_preofftime );
			
				String ab_no = req.getParameter("ab_no");
				if (ab_no == "請選擇" ) {
					errorMsgs.put("廣告區塊","尚未選取");
				}
			System.out.println("ab_no：" + ab_no );

				String sa_addmode = "廣告編輯中";
			System.out.println("sa_addmode：" + sa_addmode );

				ShopAdVO shopadVO = new ShopAdVO();
				shopadVO.setSto_num(sto_num);              //setInterest(sb.toString());
				shopadVO.setSa_title(sa_title);
				shopadVO.setSa_text(sa_text);
				shopadVO.setSa_img(sa_img);
				shopadVO.setSa_views(sa_views);
				shopadVO.setSa_apptime(sa_apptime);
				shopadVO.setSa_addtime(sa_addtime);
				shopadVO.setSa_preofftime(sa_preofftime);
				shopadVO.setAb_no(ab_no);
				shopadVO.setSa_addmode(sa_addmode);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {

//					String upup = req.getParameter("upup");

//					if("upup".equals(upup)){
//						String sa_no = req.getParameter("sa_no");
//						shopadVO.setSa_no(sa_no);
//						System.out.println("sa_no:"+sa_no);
//					}

					req.setAttribute("shopadVO", shopadVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}

				/***************************2.開始新增資料***************************************/

				ShopAdService shopadSvc = new ShopAdService();

//				shopadVO = shopadSvc.addShopAd(sto_num, sa_title, sa_text, sa_img, sa_views, 
//						sa_apptime, sa_addtime, sa_preofftime, ab_no, sa_addmode);

				/***************************3.新增完成,準備轉交(Send the login view)***********/

//				String upup = req.getParameter("upup");
//
//				if("insertad".equals(upup)){
				shopadVO = shopadSvc.addShopAd(sto_num, sa_title, sa_text, sa_img, sa_views, 
											   sa_apptime, sa_addtime, sa_preofftime, ab_no, 
											   sa_addmode);
				req.setAttribute("shopadVO", shopadVO);
//				}else if ("upup".equals(upup)){
//
//					String sa_no = req.getParameter("sa_no").trim();
//
//					shopadVO.setSa_title(sa_no);
//
//					shopadVO = shopadSvc.updateShopAd(sa_no, sa_title, sa_text, sa_img, 
//						   							  sa_addtime, sa_preofftime, ab_no);
//					req.setAttribute("shopadVO", shopadVO);
//				}else{
//					System.out.println("wrong in storeprofileservlet insertad--------------------------");
//				}
//				req.setAttribute("shopadVO", shopadVO);
				String url = "/store-end/storeprofile/addadlist.jsp";
				RequestDispatcher loginView = req.getRequestDispatcher(url); // 新增成功後轉交login.jsp
				loginView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL); 
				failureView.forward(req, res);
			}	
		}
		
		if ("ad_update".equals(action)) {
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>(); 
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");

			try {
				String sa_no = req.getParameter("sa_no");
			System.out.println("sa_no：" + sa_no );
				ShopAdService shopadSvc = new ShopAdService();
				ShopAdVO shopadVO = shopadSvc.getOneFuncList(sa_no);  // update where比較正常吧-_-
				
				/***************************3.新增完成,準備轉交(Send the login view)***********/
				req.setAttribute("shopadVO", shopadVO);
				String url = "/store-end/storeprofile/updatead.jsp";
				RequestDispatcher loginView = req.getRequestDispatcher(url); // 新增成功後轉交login.jsp
				loginView.forward(req, res);


				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL); 
				failureView.forward(req, res);
			}	
		}
		
		if ("upup".equals(action)) { // 來自addad.jsp的請求  

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>(); 
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			// 送出修改的來源網頁路徑

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/

				String sa_no = req.getParameter("sa_no");
			System.out.println("sa_no：" + sa_no );

				String sa_title = req.getParameter("sa_title").trim();
				if (sa_title == null || sa_title.trim().length() == 0) {
					errorMsgs.put("廣告標題","請勿空白");
				}
			System.out.println("sa_title：" + sa_title );

				String sa_text = req.getParameter("sa_text");
				if (sa_text == null || sa_text.trim().length() == 0) {
					errorMsgs.put("廣告內容文字","請勿空白");
				}
			System.out.println("sa_text：" + sa_text );

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				//緩衝
				InputStream in = req.getPart("upfile").getInputStream();
				//暫存BYTE陣列
				int i;
				byte[] buffer = new byte[8192];
				//紀錄讀進來長度
				while ((i = in.read(buffer)) != -1) {
				//從緩衝區讀取buffer裡面0~length-1的位置
				//假如等於-1代表沒有資料了
					baos.write(buffer, 0, i);
				}
				if (baos.size() == 0){
					errorMsgs.put("圖片","尚未選擇");
					// 很好，圖片錯誤處理終於出來惹
				}
				baos.close();	
				in.close();
				byte[] sa_img = baos.toByteArray();
				// https://www.bbsmax.com/A/pRdBnBX1dn/
				//ByteArrayOutputStream轉成位元陣列
			System.out.println("sa_img：" + sa_img );
			// https://openhome.cc/Gossip/ServletJSP/Part.html
			// http://www.srikanthtechnologies.com/blog/java/fileupload.aspx
			// http://gn00466269.blogspot.tw/2015/09/jsp.html

			Date sa_addtime = Date.valueOf(req.getParameter("sa_addtime").trim());
			if( sa_addtime == null ) {
				errorMsgs.put("商店設立時間", "請輸入日期!");
			}
			System.out.println("sa_addtime" + sa_addtime );

//				Date sa_preofftime = null;
//				try {
//					sa_preofftime = Date.valueOf(req.getParameter("sa_preofftime").trim());
//				} catch (IllegalArgumentException e) {
//					sa_preofftime=new Date(System.currentTimeMillis());
//					errorMsgs.put("sa_preofftime", "商店設立時間:請輸入日期!");
//				}
				Date sa_preofftime = Date.valueOf(req.getParameter("sa_preofftime").trim());
				if( sa_preofftime == null ) {
					errorMsgs.put("商店設立時間", "請輸入日期!");
				}
			System.out.println("sa_preofftime：" + sa_preofftime );
			
				String ab_no = req.getParameter("ab_no");
				if (ab_no == "請選擇" ) {
					errorMsgs.put("廣告區塊","尚未選取");
				}
			System.out.println("ab_no：" + ab_no );

				ShopAdVO shopadVO = new ShopAdVO();
				shopadVO.setSa_no(sa_no);
				shopadVO.setSa_title(sa_title);
				shopadVO.setSa_text(sa_text);
				shopadVO.setSa_img(sa_img);
				shopadVO.setSa_addtime(sa_addtime);
				shopadVO.setSa_preofftime(sa_preofftime);
				shopadVO.setAb_no(ab_no);

				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {

//					String upup = req.getParameter("upup");

//					if("upup".equals(upup)){
//						String sa_no = req.getParameter("sa_no");
//						shopadVO.setSa_no(sa_no);
//						System.out.println("sa_no:"+sa_no);
//					}

					req.setAttribute("shopadVO", shopadVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/store-end/storeprofile/updatead.jsp");
					failureView.forward(req, res);
					return;
				}

				/***************************2.開始新增資料***************************************/

				ShopAdService shopadSvc = new ShopAdService();

//				shopadVO = shopadSvc.addShopAd(sto_num, sa_title, sa_text, sa_img, sa_views, 
//						sa_apptime, sa_addtime, sa_preofftime, ab_no, sa_addmode);

				/***************************3.新增完成,準備轉交(Send the login view)***********/

//				String upup = req.getParameter("upup");
//
//				if("insertad".equals(upup)){
				shopadVO = shopadSvc.updateShopAd(sa_no, sa_title, sa_text, sa_img, sa_addtime, 
												  sa_preofftime, ab_no);
				req.setAttribute("shopadVO", shopadVO);
//				}else if ("upup".equals(upup)){
//
//					String sa_no = req.getParameter("sa_no").trim();
//
//					shopadVO.setSa_title(sa_no);
//
//					shopadVO = shopadSvc.updateShopAd(sa_no, sa_title, sa_text, sa_img, 
//						   							  sa_addtime, sa_preofftime, ab_no);
//					req.setAttribute("shopadVO", shopadVO);
//				}else{
//					System.out.println("wrong in storeprofileservlet insertad--------------------------");
//				}
//				req.setAttribute("shopadVO", shopadVO);
				String url = "/store-end/storeprofile/addadlist.jsp";
				RequestDispatcher loginView = req.getRequestDispatcher(url); // 新增成功後轉交login.jsp
				loginView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL); 
				failureView.forward(req, res);
			}	
		}
		
		
		
		if ("adlist_to_bm".equals(action)) { // 來自addEmp.jsp的請求  
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>(); 
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");

//			try {
				
				String sa_no = req.getParameter("sa_no");
			System.out.println("sa_no：" + sa_no );
				ShopAdService shopadSvc = new ShopAdService();
				ShopAdVO shopadVO = shopadSvc.getOneFuncList(sa_no);  // update where比較正常吧-_-
				
				String sa_addmode = shopadVO.getSa_addmode();
			System.out.println("sa_addmode：" + sa_addmode );
				req.setAttribute("shopadVO", shopadVO);
				
				if( sa_addmode.equals("廣告編輯中") ){
					
					shopadVO.setSa_addmode("待審核");
					shopadSvc.updateShopAd(shopadVO);
					
					
					String url = "/store-end/storeprofile/adlist_to_bm_success.jsp";
					RequestDispatcher loginView = req.getRequestDispatcher(url); // 新增成功後轉交login.jsp
					loginView.forward(req, res);
				}else{
					System.out.println("sa_addmode廣告審核:已通過!");
				}

				/***************************其他可能的錯誤處理**********************************/
				
//			} catch (Exception e) {
//				errorMsgs.put("Exception",e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher(requestURL); 
//				failureView.forward(req, res);
//			}	
		}
		
		if ("ad_insert".equals(action)){
			String url = "/store-end/storeprofile/addad.jsp";
			RequestDispatcher loginView = req.getRequestDispatcher(url); // 新增成功後轉交login.jsp
			loginView.forward(req, res);
		}
	}

//	public static byte[] getByteArray3(String filename) throws java.io.IOException {
//	    java.io.InputStream in = new java.io.FileInputStream(filename);
//		byte[] buf = new byte[in.available()];
//		in.read(buf);
//		in.close();
//		System.out.println(buf.length); // 測試用only
//		return buf;
//	}
}
