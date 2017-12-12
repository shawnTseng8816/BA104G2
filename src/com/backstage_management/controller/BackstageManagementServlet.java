package com.backstage_management.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.auth_list.model.AuthListService;
import com.backstage_management.model.*;
import com.product.model.ProductService;
import com.product.model.ProductVO;
@WebServlet("/BackstageManagementServlet")
@MultipartConfig()
public class BackstageManagementServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
		
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action =req.getParameter("action");
		HttpSession se = req.getSession();
		
		if("insert".equals(action)){

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
			try {
				/************ 1.接收請求參數 -輸入格式處理  ******************/
				
				String bm_name = req.getParameter("bm_name");
				String bm_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,12}$";
				if (bm_name == null || bm_name.trim().length() == 0){
					errorMsgs.put("bm_name","員工名稱：請勿空白");
				} else if (!bm_name.trim().matches(bm_nameReg)) {
					errorMsgs.put("bm_name","員工名稱：只能是中、英文字母，長度2-10之間");
				}
				
				String bm_num = req.getParameter("bm_num");
				String account_Reg = "[(a-zA-Z0-9)]{2,10}";
				if (bm_num == null || bm_num.trim().length() == 0){
					errorMsgs.put("bm_number","員工帳號：請勿空白");
				}
				if(!bm_num.matches(account_Reg)){
					errorMsgs.put("bm_number_1","帳號只能大小寫英文數字，長度2-10之間");
				}
				BackstageManagementService bmSvc = new BackstageManagementService();		
				int count = bmSvc.checkBm_num(bm_num);
				if (count>0){
					errorMsgs.put("bm_number_2","帳號重複了!換一個吧:-)");
				}
				
				String bm_number = req.getParameter("bm_number");
				String phone_nameReg = "[0-9]{4}-[0-9]{6}";	
				if (bm_number == null || bm_number.trim().length() == 0){
					errorMsgs.put("bm_number","員工手機：請勿空白");
				} else if (!bm_number.matches(phone_nameReg)){
					errorMsgs.put("bm_number","手機格式錯誤，請依照09xx-xxxxxx格式輸入喔");
				}
				
				String bm_mail = req.getParameter("bm_mail");
				String email_nameReg = "^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$";
				if (bm_mail == null || bm_mail.trim().length() == 0){
					errorMsgs.put("bm_email","email請勿空白");
				} else if (!bm_mail.trim().matches(email_nameReg)){
					errorMsgs.put("bm_email","信箱格式錯誤");
				}				
						
				String bm_banknum = req.getParameter("bm_banknum");
				String bank_nameReg = "[0-9]{10,14}";
				if (bm_banknum == null || bm_banknum.trim().length() == 0){
					errorMsgs.put("bm_banknum","銀行帳戶請勿空白");
				}else if (!bm_banknum.trim().matches(bank_nameReg)){
					errorMsgs.put("bm_banknum","銀行帳號格式錯誤，請輸入10-14碼純數字");
				}
				
				Part imgs= req.getPart("bm_img");
				byte[] bm_img = getPictureByteArray(imgs); 
				
				//抓權限box
				String[] func = req.getParameterValues("func");
				List<String> funcList = new ArrayList<String>(); ;
				try{					
					for(int i= 0; i<func.length;i++){
						funcList.add(func[i]);					
					}
				req.setAttribute("funcList",funcList);
				} catch (NullPointerException ee){
					errorMsgs.put("func","請設定權限");
				}
			
	
				
			System.out.println(bm_name+","+bm_num+","+bm_number+","+bm_mail+","+bm_banknum);
				
			System.out.println(errorMsgs.toString());
			
				BackstageManagementVO bmVO = new BackstageManagementVO();
				bmVO.setBm_name(bm_name);
				bmVO.setBm_num(bm_num);
				bmVO.setBm_number(bm_number);
				bmVO.setBm_mail(bm_mail);
				bmVO.setBm_banknum(bm_banknum);
				bmVO.setBm_img(bm_img);				
			    bmVO.setBm_jstatus("在職");
				
				//send back if errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("bmVO", bmVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/bks_mng/addStaff.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/************ 2.開始加入資料  + 寄信  ****************************/
				
				//產生亂數密碼後insert
				String bm_pwd = getRandomPassword();				
				System.out.println(bm_pwd);	
				bmVO.setBm_pwd(bm_pwd);				
				bmVO = bmSvc.addStaff(bmVO);				
				String bm_no = bmVO.getBm_no();
		//寄信		
				//寄給誰
			    String to = bm_mail;
			    //主旨  
			    String subject = "揪茶趣新進員工密碼通知";
			    //內容
			    String messageText = 
			    		"Hello! " + bm_name + ",歡迎加入揪茶趣！\n您的帳密碼已經啟用囉\n請妥善保存此帳號密碼:\n帳號:" + bm_num + "\n密碼:"+ bm_pwd; 
			    MailService mailService = new MailService();
	     
			    mailService.sendMail(to, subject, messageText);				
				
			    //寫入權限資料
			    AuthListService alSvc = new AuthListService();
			    alSvc.insert(bm_no, funcList);
			    
				/************ 3.加入完成,準備轉交(Send the Success view)**/	
				se.removeAttribute("addform");				//把通行證拿掉防止f5重送表單
				String url = "/back-end/bks_mng/addStaff.jsp?bm_no="+bm_no;
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				
				/*********** 其他錯誤  ***************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/bks_mng/addStaff.jsp");
				failureView.forward(req, res);
			}		
		}
		
//		if("getAllStaff".equals(action)){	
//			System.out.println("---");
//				BackstageManagementService bmSvc = new BackstageManagementService();		
//				List<BackstageManagementVO> getAllStaff = bmSvc.getAll();	
//			System.out.println("---"+getAllStaff.size());
//				req.setAttribute("getAllStaff", getAllStaff);			
//				RequestDispatcher successView = req.getRequestDispatcher("/back-end/bks_mng/bksmng_select_page.jsp");
//				successView.forward(req, res);
//			}
//		
		if("check".equals(action)){	//ajax檢查帳號是否重複
			String bm_num = req.getParameter("bm_num");
			BackstageManagementService bmSvc = new BackstageManagementService();		
			int count = bmSvc.checkBm_num(bm_num);
			res.setContentType("application/json ; charset=UTF-8");
		    PrintWriter out = res.getWriter();
		    out.print(count);
		}
		
		if("getOne_For_Update".equals(action)){
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
			try{
				/************ 1.接收請求參數 -輸入格式處理  ******************/
				String bm_no = req.getParameter("bm_no");
				
				/************ 2.開始查詢資料   ****************************/
				BackstageManagementService bmSvc = new BackstageManagementService();		
				BackstageManagementVO bmVO = bmSvc.findbyPrimaryKey(bm_no);
				
				//查權限
				AuthListService alSvc = new AuthListService();
				List<String> funcList = alSvc.findByBm_no(bm_no);
				
				/************ 3.查詢完成,準備轉交(Send the Success view)**/				
				req.setAttribute("bmVO", bmVO);
				req.setAttribute("funcList", funcList);
				String url = "/back-end/bks_mng/update_staff_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/************ 其他錯誤處理  ******************************/					
			} catch (Exception e) {
				errorMsgs.put("修改資料失敗:",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/bks_mng/bksmng_select_page.jsp");
				failureView.forward(req, res);
			}			
		}
		
		if("getOne_For_Display".equals(action)){
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
			try{
				/************ 1.接收請求參數 -輸入格式處理  ******************/
				String bm_no = req.getParameter("bm_no");
				
				/************ 2.開始查詢資料   ****************************/
				BackstageManagementService bmSvc = new BackstageManagementService();		
				BackstageManagementVO bmVO = bmSvc.findbyPrimaryKey(bm_no);
				
				//查權限
				AuthListService alSvc = new AuthListService();
				List<String> funcList = alSvc.findByBm_no(bm_no);
				
				/************ 3.查詢完成,準備轉交(Send the Success view)**/				
				req.setAttribute("bmVO", bmVO);
				req.setAttribute("funcList", funcList);
				String url = "/back-end/bks_mng/update_staff_self.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/************ 其他錯誤處理  ******************************/					
			} catch (Exception e) {
				errorMsgs.put("修改資料失敗:",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/bks_mng/bksmng_select_page.jsp");
				failureView.forward(req, res);
			}			
		}
		
		if("update".equals(action)){
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
			req.removeAttribute("updateData");
			
			try {
				/************ 1.接收請求參數 -輸入格式處理  ******************/
				String bm_no = req.getParameter("bm_no");
				
				String bm_name = req.getParameter("bm_name");
				String bm_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,12}$";
				if (bm_name == null || bm_name.trim().length() == 0){
					errorMsgs.put("bm_name","員工名稱：請勿空白");
				} else if (!bm_name.trim().matches(bm_nameReg)) {
					errorMsgs.put("bm_name","員工名稱：只能是中、英文字母，長度2-10之間");
				}
				
				String bm_num = req.getParameter("bm_num");				
				
				String bm_number = req.getParameter("bm_number");
				String phone_nameReg = "[0-9]{4}-[0-9]{6}";	
				if (bm_number == null || bm_number.trim().length() == 0){
					errorMsgs.put("bm_number","員工手機：請勿空白");
				} else if (!bm_number.matches(phone_nameReg)){
					errorMsgs.put("bm_number","手機格式錯誤");
				}
				
				String bm_mail = req.getParameter("bm_mail");
				String email_nameReg = "^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$";
				if (bm_mail == null || bm_mail.trim().length() == 0){
					errorMsgs.put("bm_email","email請勿空白");
				} else if (!bm_mail.trim().matches(email_nameReg)){
					errorMsgs.put("bm_email","信箱格式錯誤");
				}				
						
				String bm_banknum = req.getParameter("bm_banknum");
				String bank_nameReg = "[0-9]{10,14}";
				if (bm_banknum == null || bm_banknum.trim().length() == 0){
					errorMsgs.put("bm_banknum","銀行帳戶請勿空白");
				}else if (!bm_banknum.trim().matches(bank_nameReg)){
					errorMsgs.put("bm_banknum","銀行帳號格式錯誤");
				}
		
				BackstageManagementService bmSvc2 = new BackstageManagementService();
				BackstageManagementVO bmVOORG = bmSvc2.findbyPrimaryKey(bm_no);

				Part imgs= req.getPart("bm_img");
				byte[] bm_img = null;

				if(imgs.getSize()!=0){
					System.out.println("new img");
					bm_img = getPictureByteArray(imgs); 
				} else {
					System.out.println("orgin img");
					bm_img = bmVOORG.getBm_img();
				}			
				System.out.println(bmVOORG.getBm_no());	
				
				String bm_jstatus = req.getParameter("bm_jstatus");
				
				//判斷修改密碼
				String bm_pwd;
				String bm_no_log = (String)se.getAttribute("bm_no");
				if(bm_no_log.equals(bm_no)){	
					bm_pwd = req.getParameter("bm_pwd");	//自己可以修改自己的密碼
					String account_Reg = "[(a-zA-Z0-9)]{2,10}";
					if (bm_pwd == null || bm_pwd.trim().length() == 0){
						errorMsgs.put("bm_pwd","密碼：請勿空白");
					}
					if(!bm_pwd.matches(account_Reg)){
						errorMsgs.put("bm_pwd_1","密碼只能大小寫英文數字，長度2-10之間");
					}
				}else{
					bm_pwd = bmVOORG.getBm_pwd();			//不是修改自己去資料庫查密碼再塞進去
				}
				
				//抓權限box
				List<String> funcList = new ArrayList<String>();;
				String auth = req.getParameter("auth");
				if(!"self".equals(auth)){	//不是self修改頁面過來的話
					String[] func = req.getParameterValues("func");
					try{
						for(int i= 0; i<func.length;i++){
							funcList.add(func[i]);					
						}
						req.setAttribute("funcList",funcList);
					} catch (NullPointerException ee){
						errorMsgs.put("func","請設定權限");
					}
					
				}
				
			System.out.println(bm_img+","+bm_name+","+bm_num+","+bm_number+","+bm_mail+","+bm_banknum+","+bm_jstatus);
				
			System.out.println(errorMsgs.toString());
			
				BackstageManagementVO bmVO = new BackstageManagementVO();
				bmVO.setBm_no(bm_no);
				bmVO.setBm_name(bm_name);
				bmVO.setBm_num(bm_num);
				bmVO.setBm_number(bm_number);
				bmVO.setBm_mail(bm_mail);
				bmVO.setBm_banknum(bm_banknum);
				bmVO.setBm_img(bm_img);				
				bmVO.setBm_jstatus(bm_jstatus);
				bmVO.setBm_pwd(bm_pwd);

				//send back if errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("bmVO", bmVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/bks_mng/update_staff_input.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/************ 2.開始更新資料****************************/
						
				BackstageManagementService bmSvc = new BackstageManagementService();
				bmVO = bmSvc.updateStaff(bmVO);
				
				if(!"self".equals(auth)){
				//更改權限(刪光光再新增)
				AuthListService alSvc = new AuthListService();
				alSvc.delete(bm_no);
				alSvc.insert(bm_no, funcList);
				}
				
				/************ 3.加入完成,準備轉交(Send the Success view)**/	
				
				req.setAttribute("bmVO",bmVO);
				if("self".equals(auth)){	//自己修改自己
					String requestURL = req.getParameter("requestURL");
					req.setAttribute("updateData", "updateData");
					RequestDispatcher successView = req.getRequestDispatcher(requestURL); 
					successView.forward(req, res);
				}else{		
					String url = "/back-end/bks_mng/bksmng_select_page.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); 					
					successView.forward(req, res);
				}
				
				
				/*********** 其他錯誤  ***************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/bks_mng/update_staff_input.jsp");
				failureView.forward(req, res);
			}		
		}
		
		
		

	}
	
	//圖片
	public static byte[] getPictureByteArray(Part part) throws IOException {
		
		InputStream in =  part.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];	//自定緩衝區
		int i;
		while ((i = in.read(buffer)) != -1) {
			baos.write(buffer, 0, i); //(哪個陣列,開始索引值,緩衝區資料大小)
		}		
		in.close();
		return baos.toByteArray();
	}
	
	//密碼產生器
	 private String getRandomPassword() {
		    int z;
		    StringBuilder sb = new StringBuilder();
		    int i;
		    for (i = 0; i < 6; i++) {
		      z = (int) ((Math.random() * 7) % 3);
		      if (z == 1) { // 放數字
		        sb.append((int) ((Math.random() * 10) + 48));
		      } else if (z == 2) { // 放大寫英文
		        sb.append((char) (((Math.random() * 26) + 65)));
		      } else {// 放小寫英文
		        sb.append(((char) ((Math.random() * 26) + 97)));
		      }
		    }
		    return sb.toString();
	}
}
