package com.member_profile.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.member_profile.model.*;

/**
 * Servlet implementation class AccServlet
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
//當數據量大於fileSizeThreshold值時，內容將被寫入磁碟
//上傳過程中無論是單個文件超過maxFileSize值，或者上傳的總量大於maxRequestSize 值都會拋出IllegalStateException 異常

public class MemberProfileServlet extends HttpServlet {
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
		
		if("logoutm".equals(action)){
			
			session.removeAttribute("mem_name");
			session.removeAttribute("mem_num");
			
			req.getSession().invalidate();
			
			res.sendRedirect(req.getContextPath()+"/front-end/index.jsp");
			return;
			
//			String url = "/front-end/index.jsp";
//			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 select_page1_free.jsp
//			successView.forward(req, res);
		}
		else if ("login".equals(action)) { // 來自select_page.jsp的請求
//			System.out.println("我進來拉!!!");
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();  // List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String acc = req.getParameter("mem_acc");
				String accReg = "^[(a-zA-Z0-9)]{2,12}$";
				String pwd = req.getParameter("mem_pwd");
				String pwdReg = "^[(a-zA-Z0-9_)]{2,12}$";
//				System.out.println(acc);
//				System.out.println(pwd);
				if ((acc == null || (acc.trim()).length() == 0)) { 
					// str.trim()：去除首尾空白符號
					errorMsgs.put("帳號 "," 請勿空白");
				}else if(!acc.trim().matches(accReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("帳號 "," 格式不正確");
					// errorMsgs.put("acc","帳號 : 只能是英文字母、數字 , 且長度必需在2到12之間");
	            }
				if ((pwd == null || (pwd.trim()).length() == 0)) { 
					// str.trim()：去除首尾空白符號
					errorMsgs.put("密碼 "," 請勿空白");
				}else if(!pwd.trim().matches(pwdReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("密碼 "," 格式不正確");
					// errorMsgs.put("pwd","密碼 : 只能是英文字母、數字 , 且長度必需在2到12之間");
	            }

				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req  
							.getRequestDispatcher("/front-end/memberprofile/login.jsp");  
					        // getRequestDispatcher(p.148)：請求轉發(與Redirect不同)
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/

				MemberProfileService memberprofileSrc = new MemberProfileService();  // service 方法
				MemberProfileVO memberprofileVO = new MemberProfileVO();             // 會員資訊物件memProVO

				//使用service 方法去 DAO 查詢帳號 如果有回傳 一個會員資訊物件 memProVO
				memberprofileVO =memberprofileSrc.checkLogin(acc);
				// MemberProfileService的登入確認方法：
				//     public MemberProfileVO checkLogin(String mem_acc){
				//    	    return dao.checkLogin(mem_acc);	
				//     }
				if (memberprofileVO == null) {
					System.out.println("帳號錯誤~!");
					errorMsgs.put("帳號"," 查無資料");
				}
				
				if(!memberprofileVO.getMem_pwd().equals(pwd)){
					// MemberProfileVO裡面有實作getXxx()、setXxx()
					System.out.println("密碼錯誤~!");
					errorMsgs.put("密碼 "," 錯誤");
				}
					
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/memberprofile/login.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				
				//資料庫取的密碼 跟 輸入的相同
				
				session.invalidate();
				
				session = req.getSession();
				session.setAttribute("mem_name", memberprofileVO.getMem_name());
				session.setAttribute("mem_num", memberprofileVO.getMem_num());
				
//				String url = "/front-end/memberprofile/login_success.jsp";
				res.sendRedirect(req.getContextPath() + "/front-end/frameForChat.jsp");
				return;
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 select_page1_free.jsp
//				successView.forward(req, res);
				
				
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/memberprofile/login.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		
		
		if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			MemberProfileService memberProfileSvc = new MemberProfileService();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String mem_acc = req.getParameter("mem_acc");
				String mem_accReg = "^[(a-zA-Z0-9)]{2,50}$";
				List<String> allAcc = memberProfileSvc.getAllAccount();
				if (mem_acc == null || mem_acc.trim().length() == 0) {
					errorMsgs.put("會員帳號"," 請勿空白");
				} else if(allAcc.contains(mem_acc)){
					errorMsgs.put("會員帳號"," 已使用的帳號");
				}else if(!mem_acc.trim().matches(mem_accReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("會員帳號"," 只能是英文字母、數字 , 且長度必需在2到50之間");
	            }
				
				
				String mem_pwd = req.getParameter("mem_pwd");
				String mem_pwdReg = "^[(a-zA-Z0-9)]{2,50}$";
				if (mem_pwd == null || mem_pwd.trim().length() == 0) {
					errorMsgs.put("會員密碼"," 請勿空白");
				} else if(!mem_pwd.trim().matches(mem_pwdReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("會員密碼"," 只能是中、英文字母、數字 , 且長度必需在2到50之間");
	            }
				
				String mem_name = req.getParameter("mem_name");
				String mem_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{2,50}$";
				if (mem_pwd == null || mem_name.trim().length() == 0) {
					errorMsgs.put("會員名稱"," 請勿空白");
				} else if(!mem_name.trim().matches(mem_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("會員名稱"," 只能是中、英文字母、數字 , 且長度必需在2到50之間");
	            }
			// System.out.println("我進來拉!!!");
				
//				String sex = req.getParameter("sex");
//				// https://www.mkyong.com/spring-mvc/spring-mvc-radiobutton-and-radiobuttons-example/
//				if (sex == null || sex.trim().length() == 0) {
//					errorMsgs.put("sex","性別: 尚未選擇");
//				}
				String sex = req.getParameter("sex");
				if (sex == null || sex.trim().length() == 0) {
					errorMsgs.put("性別"," 尚未選擇");
				}
//				if ("male".equals(sex)) {
//					//do what
//				} else if ("female".equals(sex)) {
//					//do what
//				}
				
				Integer age = null;
				try {
					age = new Integer(req.getParameter("age").trim());
				} catch (NumberFormatException e) {
					age = 0;
					errorMsgs.put("年齡"," 請填寫年齡");
				}
			// System.out.println("我進來拉!!!");
				
				String mobile = req.getParameter("mobile");
				// https://www.mkyong.com/spring-mvc/spring-mvc-radiobutton-and-radiobuttons-example/
				if (mobile == null || mobile.trim().length() == 0) {
					errorMsgs.put("電話號碼"," 尚未填寫");
				}
				
				String cek_mobile = req.getParameter("cek_mobile");
				// https://www.mkyong.com/spring-mvc/spring-mvc-radiobutton-and-radiobuttons-example/
				if (cek_mobile == null || cek_mobile.trim().length() == 0) {
					errorMsgs.put("cek_mobile","是否通過電話驗證: 否");
					// 經由後台人員驗證後 → 通過電話驗證 → 狀態：正常(行237)
				}
			// System.out.println("我進來拉!!!");
				
				String email = req.getParameter("email");
				// https://www.mkyong.com/spring-mvc/spring-mvc-radiobutton-and-radiobuttons-example/
				if (email == null || email.trim().length() == 0) {
					errorMsgs.put("電子信箱"," 尚未填寫");
				}
				
				String address = req.getParameter("address");
				// https://www.mkyong.com/spring-mvc/spring-mvc-radiobutton-and-radiobuttons-example/
				if (address == null || address.trim().length() == 0) {
					errorMsgs.put("住址"," 尚未填寫");
				}
				
				Integer rem_point = null;
				try {
					rem_point = new Integer(req.getParameter("rem_point").trim());
				} catch (NumberFormatException e) {
					rem_point = 0;
					errorMsgs.put("rem_point","剩餘點數:0");
					// 剛開始都是0，可以去儲值
				}
			System.out.println("我進來拉!!!");
				

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
					errorMsgs.put("圖片"," 尚未選擇");
					// 很好，圖片錯誤處理終於出來惹
				}
				baos.close();	
				in.close();
				byte[] mem_img = baos.toByteArray();


				

				// https://www.bbsmax.com/A/pRdBnBX1dn/

				//ByteArrayOutputStream轉成位元陣列
			System.out.println("乾~需要圖圖!!!");
			// https://openhome.cc/Gossip/ServletJSP/Part.html
			// http://www.srikanthtechnologies.com/blog/java/fileupload.aspx
			// http://gn00466269.blogspot.tw/2015/09/jsp.html

				String status = req.getParameter("status");
				// https://www.mkyong.com/spring-mvc/spring-mvc-radiobutton-and-radiobuttons-example/
				if (status == null || status.trim().length() == 0) {
					errorMsgs.put("status","狀態: 等待驗證中");
					// 經由後台人員驗證後 → 通過電話驗證(行191) → 狀態：正常
				}
//			System.out.println("進!!!");

				MemberProfileVO memberProfileVO = new MemberProfileVO();

				memberProfileVO.setMem_acc(mem_acc);
				memberProfileVO.setMem_pwd(mem_pwd);
				memberProfileVO.setMem_name(mem_name);
				memberProfileVO.setSex(sex);              //setInterest(sb.toString());
				memberProfileVO.setAge(age);
				memberProfileVO.setMobile(mobile);
				memberProfileVO.setCek_mobile(cek_mobile);
				memberProfileVO.setEmail(email);
				memberProfileVO.setAddress(address);
				memberProfileVO.setRem_point(rem_point);
				memberProfileVO.setMem_img(mem_img);
//				if (getFileNameFromPart(mem_img) != null) {
//					memberProfileVO.setMem_img(getPictureByteArray(mem_img.getInputStream()));
//				} else {
//					memberProfileVO.setMem_img(null);
//				}
				memberProfileVO.setStatus(status);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memberProfileVO", memberProfileVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/memberprofile/addMemberProfile.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				
				memberProfileVO = memberProfileSvc.addMemberProfile(mem_acc, mem_pwd, mem_name, sex, age, mobile, cek_mobile, email, address, rem_point, mem_img, status);
				
				/***************************3.新增完成,準備轉交(Send the login view)***********/
				String url = "/front-end/memberprofile/login.jsp";
				RequestDispatcher loginView = req.getRequestDispatcher(url); // 新增成功後轉交login.jsp
				loginView.forward(req, res);
				return;
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/memberprofile/addMemberProfile.jsp"); 
				failureView.forward(req, res);
				return;
			}	
		}
	}

	public static byte[] getByteArray3(String filename) throws java.io.IOException {
	    java.io.InputStream in = new java.io.FileInputStream(filename);
		byte[] buf = new byte[in.available()];
		in.read(buf);
		in.close();
		System.out.println(buf.length); // 測試用only
		return buf;
	}
}
