package com.backstage_management.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.sql.Date;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.auth_list.model.AuthListService;
import com.backstage_management.model.*;
import com.google.gson.Gson;
import com.shop_ad.model.*;
import com.store_profile.model.*;


/**
 * Servlet implementation class AccServlet
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
//當數據量大於fileSizeThreshold值時，內容將被寫入磁碟
//上傳過程中無論是單個文件超過maxFileSize值，或者上傳的總量大於maxRequestSize 值都會拋出IllegalStateException 異常

public class BackstageManagementServletChi extends HttpServlet {
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
			
			res.sendRedirect(req.getContextPath()+"/back-end/backstagemanagement/login.jsp");
			return;
		}
		if ("login".equals(action)) { // 來自select_page.jsp的請求
//			System.out.println("我進來拉!!!");
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();  // List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String acc = req.getParameter("bm_num");
				String accReg = "^[(a-zA-Z0-9)]{2,12}$";
				String pwd = req.getParameter("bm_pwd");
				String pwdReg = "^[(a-zA-Z0-9_)]{2,12}$";
//				System.out.println(acc);
//				System.out.println(pwd);
				if ((acc == null || (acc.trim()).length() == 0)) { 
					// str.trim()：去除首尾空白符號
					errorMsgs.put("acc","帳號 : 請勿空白");
				}else if(!acc.trim().matches(accReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("acc","帳號 : 格式不正確");
					// errorMsgs.put("acc","帳號 : 只能是英文字母、數字 , 且長度必需在2到12之間");
	            }
				if ((pwd == null || (pwd.trim()).length() == 0)) { 
					// str.trim()：去除首尾空白符號
					errorMsgs.put("pwd","密碼 : 請勿空白");
				}else if(!pwd.trim().matches(pwdReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("pwd","密碼 : 格式不正確");
					// errorMsgs.put("pwd","密碼 : 只能是英文字母、數字 , 且長度必需在2到12之間");
	            }

				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req  
							.getRequestDispatcher("/back-end/backstagemanagement/login.jsp");  
					        // getRequestDispatcher(p.148)：請求轉發(與Redirect不同)
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/

				BackstageManagementService backstagemanagementSrc = new BackstageManagementService();  // service 方法
				BackstageManagementVO backstagemanagementVO = new BackstageManagementVO();             // 會員資訊物件memProVO

				//使用service 方法去 DAO 查詢帳號 如果有回傳 一個會員資訊物件 memProVO
				backstagemanagementVO =backstagemanagementSrc.checkLogin(acc);
				// MemberProfileService的登入確認方法：
				//     public MemberProfileVO checkLogin(String mem_acc){
				//    	    return dao.checkLogin(mem_acc);	
				//     }
				if (backstagemanagementVO == null) {
					System.out.println("帳號錯誤~!");
					errorMsgs.put("acc","帳號：查無資料");
				}
				
				if(!backstagemanagementVO.getBm_pwd().equals(pwd)){
					// MemberProfileVO裡面有實作getXxx()、setXxx()
					System.out.println("密碼錯誤~!");
					errorMsgs.put("pwd","密碼 : 錯誤");
				}
					
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/backstagemanagement/login.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				
				//資料庫取的密碼 跟 輸入的相同
				
				AuthListService alSvc = new AuthListService();
				List<String> funcList = alSvc.findByBm_no(backstagemanagementVO.getBm_no());	
//			System.out.println(backstagemanagementVO.getBm_no());	
				Gson gson = new Gson();
				String authList = gson.toJson(funcList);
				
				//Session設定後臺人員編號+權限清單
				session.setAttribute("authList", authList);			
				session.setAttribute("bm_no", backstagemanagementVO.getBm_no());
				
				
				
				session.setAttribute("backstagemanagementVO", backstagemanagementVO);
				session.setAttribute("bm_num", backstagemanagementVO.getBm_num());
				String url = "/back-end/index.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 select_page1_free.jsp
				successView.forward(req, res);
				
				
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/backstagemanagement/login.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>(); 
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String bm_name = req.getParameter("bm_name");
				String bm_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{2,50}$";
				if (bm_name == null || bm_name.trim().length() == 0) {
					errorMsgs.put("bm_name","後台人員名稱: 請勿空白");
				} else if(!bm_name.trim().matches(bm_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("bm_name","後台人員名稱: 只能是中文、英文字母、數字 , 且長度必需在2到50之間");
	            }
			//System.out.println("bm_name進來拉!!!");
				
				String bm_number = req.getParameter("bm_number");
				if (bm_number == null || bm_number.trim().length() == 0) {
					errorMsgs.put("bm_number","手機號碼: 請勿空白");
				}
			//System.out.println("bm_number進來拉!!!");
				
				String bm_mail = req.getParameter("bm_mail");
				if (bm_mail == null || bm_mail.trim().length() == 0) {
					errorMsgs.put("bm_mail","電子信箱: 請勿空白");
				}
			//System.out.println("bm_mail進來拉!!!");
				
				String bm_banknum = req.getParameter("bm_banknum");
				String bm_banknumReg = "^[(0-9)]{2,14}$";
				if (bm_banknum == null || bm_banknum.trim().length() == 0) {
					errorMsgs.put("bm_banknum","銀行帳號: 請勿空白");
				} else if(!bm_banknum.trim().matches(bm_banknumReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("bm_banknum","銀行帳號: 只能是數字 , 且長度必需在2到14之間");
	            }
			//System.out.println("bm_banknum進來拉!!!");
				
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
					errorMsgs.put("bm_img","大頭貼: 尚未選擇");
					// 很好，圖片錯誤處理終於出來惹
				}
				baos.close();	
				in.close();
				byte[] bm_img = baos.toByteArray();
				
				// https://www.bbsmax.com/A/pRdBnBX1dn/
				//ByteArrayOutputStream轉成位元陣列
			System.out.println("乾~需要圖圖!!!");
			// https://openhome.cc/Gossip/ServletJSP/Part.html
			// http://www.srikanthtechnologies.com/blog/java/fileupload.aspx
			// http://gn00466269.blogspot.tw/2015/09/jsp.html
				
				String bm_num = req.getParameter("bm_num");
				String bm_numReg = "^[(a-zA-Z0-9)]{2,50}$";
				if (bm_num == null || bm_num.trim().length() == 0) {
					errorMsgs.put("bm_num","後台人員帳號: 請勿空白");
				} else if(!bm_num.trim().matches(bm_numReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("bm_num","後台人員帳號: 只能是英文字母、數字 , 且長度必需在2到50之間");
				}
				//System.out.println("bm_num進來拉!!!");
			
				String bm_pwd = req.getParameter("bm_pwd");
				String bm_pwdReg = "^[(a-zA-Z0-9)]{2,50}$";
				if (bm_pwd == null || bm_pwd.trim().length() == 0) {
					errorMsgs.put("bm_pwd","後台人員密碼: 請勿空白");
				} else if(!bm_pwd.trim().matches(bm_pwdReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("bm_pwd","後台人員密碼: 只能是英文字母、數字 , 且長度必需在2到50之間");
	            }
			//System.out.println("bm_pwd進來拉!!!");

				String bm_jstatus = req.getParameter("bm_jstatus");
				// https://www.mkyong.com/spring-mvc/spring-mvc-radiobutton-and-radiobuttons-example/
				if (bm_jstatus == null || bm_jstatus.trim().length() == 0) {
					errorMsgs.put("bm_jstatus","後台人員在職狀態: 未上架");
					// 經由後台人員驗證後 → 通過電話驗證(行191) → 狀態：正常    這樣?
				}
			System.out.println("bm_jstatus進來拉!!!");

			BackstageManagementVO backstagemanagementVO = new BackstageManagementVO();

				backstagemanagementVO.setBm_name(bm_name);
				backstagemanagementVO.setBm_number(bm_number);
				backstagemanagementVO.setBm_mail(bm_mail);
				backstagemanagementVO.setBm_banknum(bm_banknum);              //setInterest(sb.toString());
				backstagemanagementVO.setBm_img(bm_img);
//				if (getFileNameFromPart(sto_logo) != null) {
//				memberProfileVO.setSto_logo(getPictureByteArray(sto_logo.getInputStream()));
//			} else {
//				memberProfileVO.setSto_logo(null);
//			}
				backstagemanagementVO.setBm_num(bm_num);
				backstagemanagementVO.setBm_pwd(bm_pwd);
				backstagemanagementVO.setBm_jstatus(bm_jstatus);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("backstagemanagementVO", backstagemanagementVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/backstagemanagement/addBackstageManagement.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				BackstageManagementService backstagemanagementSvc = new BackstageManagementService();
				backstagemanagementVO = backstagemanagementSvc.addBackstageManagement(bm_name, bm_number, bm_mail, bm_banknum, 
																			   bm_img, bm_num, bm_pwd, bm_jstatus);
				
				/***************************3.新增完成,準備轉交(Send the login view)***********/
				String url = "/back-end/backstagemanagement/login.jsp";
				RequestDispatcher loginView = req.getRequestDispatcher(url); // 新增成功後轉交login.jsp
				loginView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/backstagemanagement/addBackstageManagement.jsp"); 
				failureView.forward(req, res);
			}	
		}
		
		if ("app_auth_search".equals(action)) { // 來自listAllEmp.jsp 或  /dept/listEmps_ByDeptno.jsp 的請求
			System.out.println(" app_auth_search ");
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>(); 
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑
			//System.out.println("requestURL:"+requestURL);
			
			try {
				StoreProfileService storeprofileSrc = new StoreProfileService();
				List<StoreProfileVO> appstatus = storeprofileSrc.getStatus("申請中");
				
			//System.out.println("appstatus："+ appstatus);  // [com.store_profile.model.StoreProfileVO@5c59664f]
	
				req.setAttribute("appstatus", appstatus);  // 資料庫取出的VO物件,存入req
				
				String url = "/back-end/backstagemanagement/auth_search.jsp";
				RequestDispatcher appauthView = req.getRequestDispatcher(url); // 成功轉交authbtn.jsp
				appauthView.forward(req, res);
				System.out.println("yyyyyyyyyyyy");
				/***************************其他可能的錯誤處理************************************/
			} catch (Exception e) {
				errorMsgs.put("修改資料失敗",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		
		if ("auth_change".equals(action)) { // 來自update_emp_input.jsp的請求(0403筆)
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>(); 
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");  // 送出修改的來源網頁路徑
			System.out.println("requestURL:"+requestURL);
			

				
				/***************************2.開始修改資料*****************************************/
			try {	
//				StoreProfileService storeprofileSvc = new StoreProfileService();
				
//				storeprofileVO = empSvc.update(sto_num, sto_name, sto_pwd, mobile, address, email,
//						sto_logo, sto_introduce, sto_status);  // 寫個改sto_status試試...
				String sto_num = req.getParameter("sto_num");  // 找sto_status值要強制轉型~
				String sto_acc = req.getParameter("sto_acc");  // 如果用Attribute找sto_status值要強制轉型~
				String sto_name = req.getParameter("sto_name");
				String guy = req.getParameter("guy");
				String mobile = req.getParameter("mobile");
				String area = req.getParameter("area");
				String address = req.getParameter("address");
				
				String email = req.getParameter("email");
				
				Date set_time = Date.valueOf(req.getParameter("set_time"));
				//byte[] sto_logo = req.getParameter("sto_logo");
				
				String sto_introduce = req.getParameter("sto_introduce");
				Integer rem_point = new Integer(req.getParameter("rem_point"));
				String sto_status = req.getParameter("sto_status");
				
			System.out.println("屋咪訝某ˊ _ ˋ"+sto_num);
			System.out.println("屋咪訝某ˊ _ ˋ"+sto_status);
					
				StoreProfileService storeprofileSvc = new StoreProfileService();
				StoreProfileVO storeprofileVO = new StoreProfileVO();
				storeprofileVO = storeprofileSvc.changestatus(sto_num, "未上架");
				storeprofileVO.setSto_acc(sto_acc);
				storeprofileVO.setSto_name(sto_name);
				storeprofileVO.setGuy(guy);
				storeprofileVO.setMobile(mobile);
				storeprofileVO.setArea(area);
				storeprofileVO.setAddress(address);
				storeprofileVO.setEmail(email);
				storeprofileVO.setSet_time(set_time);
				//storeprofileVO.setSto_logo(sto_logo);
				storeprofileVO.setSto_introduce(sto_introduce);
				storeprofileVO.setRem_point(rem_point);
				String to = email;
				String subject = "通知：認證已通過";
				String ch_name = sto_name;
				String boss = guy;
//				String passRandom = req.getServletContext().getResource("/store-end/storeprofile/login.jsp").toString();
				String messageText = "Hello! " + boss+ "老闆您好!您的" + ch_name + " 商店已通過認證，感謝您使用本站的資源!請由揪茶趣網站登入"; 
							       
					//MailService mailService = new MailService();
					//mailService.sendMail(to, subject, messageText);
				//sendMail(to, subject, messageText);
//			public void sendMail(String to, String subject, String messageText) {
				try {
					   // 設定使用SSL連線至 Gmail smtp Server
					   Properties props = new Properties();
					   props.put("mail.smtp.host", "smtp.gmail.com");
					   props.put("mail.smtp.socketFactory.port", "465");
					   props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
					   props.put("mail.smtp.auth", "true");
					   props.put("mail.smtp.port", "465");

					   

			       // ●設定 gmail 的帳號 & 密碼 (將藉由你的Gmail來傳送Email)
			       // ●須將myGmail的【安全性較低的應用程式存取權】打開
					   final String myGmail = "liauba104g2@gmail.com";
					   final String myGmail_password = "ba104g2go";
					   Session sessione = Session.getInstance(props, new Authenticator() {
						   protected PasswordAuthentication getPasswordAuthentication() {
							   return new PasswordAuthentication(myGmail, myGmail_password);
						   }
					   });

					   Message message = new MimeMessage(sessione);
					   message.setFrom(new InternetAddress(myGmail));
					   message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
					  
					   //設定信中的主旨  
					   message.setSubject(subject);
					   //設定信中的內容 
					   message.setText(messageText);

					   Transport.send(message);
					   System.out.println("傳送成功!");
			     }catch (MessagingException e){
				     System.out.println("傳送失敗!");
				     e.printStackTrace();
			     }
//			}
			System.out.println("storeprofileVOˊ _ ˋ"+storeprofileVO);

				req.setAttribute("storeprofileVO", storeprofileVO); // 資料庫update成功後,將storeprofileVO物件,存入req
				String url = "/back-end/backstagemanagement/auth_change.jsp";
				RequestDispatcher authchangeView = req.getRequestDispatcher(url);   // 修改成功後,轉交回送出修改的來源網頁
				authchangeView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
				
			} catch (Exception e) {
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
if ("sa_addmode_select".equals(action)) { // 來自update_emp_input.jsp的請求(0403筆)
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>(); 
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");  // 送出修改的來源網頁路徑
			System.out.println("requestURL:"+requestURL);
			

				
				/***************************2.開始修改資料*****************************************/
//			try {	

				String sa_addmode = req.getParameter("sa_addmode");  // 找sto_status值要強制轉型~
//			System.out.println("sa_addmode：" + sa_addmode );
				
//				ShopAdVO shopadVO = new ShopAdVO();
//				
//				String sa_no = shopadVO.getSa_no();
//			System.out.println("sa_no" + sa_no );
				
				ShopAdService shopadSvc = new ShopAdService();
				List<ShopAdVO> list = new ArrayList();
				
//				list = shopadSvc.findBySa_no(sa_no);
//				System.out.println("list" + list );
//				
				
				
				if ("edit".equals(sa_addmode)){
					list = shopadSvc.getAllBySa_addmode("廣告編輯中");
//				System.out.println("list" + list );
					
				}else if("onConfirm".equals(sa_addmode)){
					list = shopadSvc.getAllBySa_addmode("待審核");
				} else if("up".equals(sa_addmode)){
					list = shopadSvc.getAllBySa_addmode("上架");
				} else if("down".equals(sa_addmode)){
					list = shopadSvc.getAllBySa_addmode("下架");
				}else{
					list = shopadSvc.getAllExceptEdit();
					
				}
				req.setAttribute("sa_addmode", sa_addmode);
				req.setAttribute("list", list);  // 資料庫update成功後,將storeprofileVO物件,存入req
				String url = "/back-end/backstagemanagement/bm_ad_control.jsp";
				RequestDispatcher authchangeView = req.getRequestDispatcher(url);   // 修改成功後,轉交回送出修改的來源網頁
				authchangeView.forward(req, res);
				/***************************其他可能的錯誤處理*************************************/
				
//			} catch (Exception e) {
//				errorMsgs.put("Exception",e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher(requestURL);
//				failureView.forward(req, res);
//			}
		} if("sa_confirm".equals(action)){
			
			String sa_addmode = req.getParameter("sa_addmode");
			String sa_no = req.getParameter("sa_no");
			ShopAdService shopadSvc = new ShopAdService();
			List<ShopAdVO> list = new ArrayList();
			ShopAdVO adVO = shopadSvc.getOneFuncList(sa_no);
			adVO.setSa_addmode("上架");
			
	
			shopadSvc.updateShopAd(adVO);
			
			if ("edit".equals(sa_addmode)){
				list = shopadSvc.getAllBySa_addmode("廣告編輯中");
//			System.out.println("list" + list );
				
			}else if("onConfirm".equals(sa_addmode)){
				list = shopadSvc.getAllBySa_addmode("待審核");
			} else if("up".equals(sa_addmode)){
				list = shopadSvc.getAllBySa_addmode("上架");
			} else if("down".equals(sa_addmode)){
				list = shopadSvc.getAllBySa_addmode("下架");
			}else{
				list = shopadSvc.getAllExceptEdit();
				
			}
			
			
			req.setAttribute("sa_addmode", sa_addmode);
			req.setAttribute("list", list);  // 資料庫update成功後,將storeprofileVO物件,存入req
			String url = "/back-end/backstagemanagement/bm_ad_control.jsp";
			RequestDispatcher authchangeView = req.getRequestDispatcher(url);   // 修改成功後,轉交回送出修改的來源網頁
			authchangeView.forward(req, res);
		}else if("sa_reject".equals(action)){
			String sa_addmode = req.getParameter("sa_addmode");
			String sa_no = req.getParameter("sa_no");
			ShopAdService shopadSvc = new ShopAdService();
			List<ShopAdVO> list = new ArrayList();
			ShopAdVO adVO = shopadSvc.getOneFuncList(sa_no);
			adVO.setSa_addmode("廣告編輯中");
			shopadSvc.updateShopAd(adVO);
			
			if ("edit".equals(sa_addmode)){
				list = shopadSvc.getAllBySa_addmode("廣告編輯中");
//			System.out.println("list" + list );
				
			}else if("onConfirm".equals(sa_addmode)){
				list = shopadSvc.getAllBySa_addmode("待審核");
			} else if("up".equals(sa_addmode)){
				list = shopadSvc.getAllBySa_addmode("上架");
			} else if("down".equals(sa_addmode)){
				list = shopadSvc.getAllBySa_addmode("下架");
			}else{
				list = shopadSvc.getAllExceptEdit();
				
			}
			
			
			req.setAttribute("sa_addmode", sa_addmode);
			req.setAttribute("list", list);  // 資料庫update成功後,將storeprofileVO物件,存入req
			String url = "/back-end/backstagemanagement/bm_ad_control.jsp";
			RequestDispatcher authchangeView = req.getRequestDispatcher(url);   // 修改成功後,轉交回送出修改的來源網頁
			authchangeView.forward(req, res);
		}else if("sa_up".equals(action)){
			String sa_addmode = req.getParameter("sa_addmode");
			String sa_no = req.getParameter("sa_no");
			ShopAdService shopadSvc = new ShopAdService();
			List<ShopAdVO> list = new ArrayList();
			ShopAdVO adVO = shopadSvc.getOneFuncList(sa_no);
			adVO.setSa_addmode("上架");
			shopadSvc.updateShopAd(adVO);
			
			if ("edit".equals(sa_addmode)){
				list = shopadSvc.getAllBySa_addmode("廣告編輯中");
//			System.out.println("list" + list );
				
			}else if("onConfirm".equals(sa_addmode)){
				list = shopadSvc.getAllBySa_addmode("待審核");
			} else if("up".equals(sa_addmode)){
				list = shopadSvc.getAllBySa_addmode("上架");
			} else if("down".equals(sa_addmode)){
				list = shopadSvc.getAllBySa_addmode("下架");
			}else{
				list = shopadSvc.getAllExceptEdit();
				
			}
			
			
			req.setAttribute("sa_addmode", sa_addmode);
			req.setAttribute("list", list);  // 資料庫update成功後,將storeprofileVO物件,存入req
			String url = "/back-end/backstagemanagement/bm_ad_control.jsp";
			RequestDispatcher authchangeView = req.getRequestDispatcher(url);   // 修改成功後,轉交回送出修改的來源網頁
			authchangeView.forward(req, res);
		}else if("sa_down".equals(action)){
			String sa_addmode = req.getParameter("sa_addmode");
			String sa_no = req.getParameter("sa_no");
			ShopAdService shopadSvc = new ShopAdService();
			List<ShopAdVO> list = new ArrayList();
			ShopAdVO adVO = shopadSvc.getOneFuncList(sa_no);
			adVO.setSa_addmode("下架");
			shopadSvc.updateShopAd(adVO);
			
			if ("edit".equals(sa_addmode)){
				list = shopadSvc.getAllBySa_addmode("廣告編輯中");
//			System.out.println("list" + list );
				
			}else if("onConfirm".equals(sa_addmode)){
				list = shopadSvc.getAllBySa_addmode("待審核");
			} else if("up".equals(sa_addmode)){
				list = shopadSvc.getAllBySa_addmode("上架");
			} else if("down".equals(sa_addmode)){
				list = shopadSvc.getAllBySa_addmode("下架");
			}else{
				list = shopadSvc.getAllExceptEdit();
				
			}
			 
			
			req.setAttribute("sa_addmode", sa_addmode);
			req.setAttribute("list", list);  // 資料庫update成功後,將storeprofileVO物件,存入req
			String url = "/back-end/backstagemanagement/bm_ad_control.jsp";
			RequestDispatcher authchangeView = req.getRequestDispatcher(url);   // 修改成功後,轉交回送出修改的來源網頁
			authchangeView.forward(req, res);
		}
		
		
	}
}
