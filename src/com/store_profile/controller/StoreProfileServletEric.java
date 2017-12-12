package com.store_profile.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONException;
import org.json.JSONObject;

import com.friend_list.model.FriendListChatServer;
import com.report_store.model.ReportStoreService;
import com.report_store.model.ReportStoreVO;
import com.store_image.model.StoreImageService;
import com.store_image.model.StoreImageVO;
import com.store_profile.model.StoreProfileService;
import com.store_profile.model.StoreProfileVO;

import javax.servlet.annotation.MultipartConfig;

/**
 * Servlet implementation class StoreProfileServlet
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
// 當數據量大於fileSizeThreshold值時，內容將被寫入磁碟
// 上傳過程中無論是單個文件超過maxFileSize值，或者上傳的總量大於maxRequestSize 值都會拋出IllegalStateException
// 異常

public class StoreProfileServletEric extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setHeader("Cache-Control", "no-store");
		res.setHeader("Pragma", "no-cache");
		res.setDateHeader("Expires", 0);

		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json");
		JSONObject obj = new JSONObject();
		PrintWriter out = res.getWriter();
		String action = req.getParameter("action");

		HttpSession session = req.getSession();

		if ("insertImg".equals(action)) {
			// Servlet3.0新增了Part介面，讓我們方便的進行檔案上傳處理

			String[] img_numArray = req.getParameterValues("img_num");

		
			
			StoreImageService stoImgSvc = new StoreImageService();
			List<StoreImageVO> list = new ArrayList<StoreImageVO>();

			StoreImageVO storeImageVO = null;

			String sto_num = (String) session.getAttribute("sto_num");

			int count = 0;
			Collection<Part> parts = req.getParts();

			System.out.println("partsize: " + parts.size());
			for (Part part : parts) {
//				if(count>img_numArray.length){
//					break;
//				}
				if (part.getContentType() != null) {
					InputStream in = part.getInputStream();

					if (in.available() != 0) {

						byte[] buf = new byte[in.available()];
						in.read(buf);
						in.close();

						if (!img_numArray[count].equals("")) {
							storeImageVO = new StoreImageVO();

							storeImageVO.setImg(buf);
							storeImageVO.setImg_num(img_numArray[count]);
							stoImgSvc.updateImg(storeImageVO);

						} else {
							storeImageVO = new StoreImageVO();

							storeImageVO.setImg(buf);
							storeImageVO.setSto_num(sto_num);
							list.add(storeImageVO);

							// storeImageVO.setImg_num(img_num);

						}
						
					}
					count++;
				}
				
			}
		
			if (list.size() != 0) {
				stoImgSvc.insertImg(list);
			}

			// // 更新店家廣告圖片
			// for(int i =0;i<img_numArray.length;i++){
			// String img_num = img_numArray[i];
			// storeImageVO = new StoreImageVO();
			//
			// storeImageVO.setImg(imgs.get(i));
			// storeImageVO.setImg_num(img_num);
			// stoImgSvc.updateImg(storeImageVO);
			// }
			// }
			// }
			//
			// for(int
			// i=img_numArray.length;i<(parts.size()-img_numArray.length);i++){
			//
			// if( parts.get(i).getContentType()!=null){
			// storeImageVO = new StoreImageVO();
			// InputStream in = parts.get(i).getInputStream();
			// if(in.available()!=0){
			// byte[] buf = new byte[in.available()];
			// in.read(buf);
			// in.close();
			// storeImageVO.setImg(buf);
			// storeImageVO.setSto_num(sto_num);
			// list.add(storeImageVO);
			//
			// }
			// }
			//
			// }
			// if(!list.isEmpty()){
			// stoImgSvc.insertImg(list);
			// }
			res.sendRedirect(req.getContextPath() + "/store-end/store/storeProfile.jsp?tab=tab5");

		}

		// 更新店家資訊
		if ("updateStoPro".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				StoreProfileService storeProfileService = new StoreProfileService();
				StoreProfileVO storeProfileVO = new StoreProfileVO();

				String mobile = req.getParameter("mobile");
				String guy = req.getParameter("guy");
				String sto_num = (String) session.getAttribute("sto_num");
				String sto_introduce = req.getParameter("sto_introduce");

				String sto_pwd = req.getParameter("sto_pwd");
				String sto_pwd2 = req.getParameter("sto_pwd2");
				if(!sto_pwd.equals(sto_pwd2)){
						String message = "您的密碼輸入不一致，請再次確認。" ;
					
					String webSocketAction = "stoGetErrMessage";
					
					FriendListChatServer.OneByOne(sto_num,webSocketAction ,message);

					RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/store/storeProfile.jsp");
					dispatcher.forward(req, res);
					return;
				}
				
				if (guy.isEmpty()||sto_introduce.isEmpty()||mobile.isEmpty()) {
					
					String message = "您的資料填寫不完整 。" ;
					
					String webSocketAction = "stoGetErrMessage";
					
					FriendListChatServer.OneByOne(sto_num,webSocketAction ,message);

					RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/store/storeProfile.jsp");
					dispatcher.forward(req, res);
					return;
				}

				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				storeProfileVO = storeProfileService.getOneStoInfo(sto_num);

				InputStream in = req.getPart("upfile").getInputStream();
				
				byte[] sto_logo = null;

				if (in.available() == 0) {

					sto_logo = storeProfileVO.getSto_logo();

				} else {

					int i;
					byte[] buffer = new byte[8192];
					while ((i = in.read(buffer)) != -1) {

						baos.write(buffer, 0, i);
					}
					in.close();
					baos.close();
					sto_logo = baos.toByteArray();
				}

				storeProfileVO.setGuy(guy);
				storeProfileVO.setMobile(mobile);
				storeProfileVO.setSto_logo(sto_logo);
				storeProfileVO.setSto_num(sto_num);
				storeProfileVO.setSto_introduce(sto_introduce);
				storeProfileVO.setSto_pwd(sto_pwd);
				storeProfileService.updateStoInfo(storeProfileVO);

				String message = "您的資料已經修改完成 。" ;
				
				String webSocketAction = "stoGetMessage";
				
				FriendListChatServer.OneByOne(sto_num,webSocketAction ,message);
				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 ***********/
				// RequestDispatcher dispatcher =
				// req.getRequestDispatcher("/store-end/store/storeProfile.jsp");
				// dispatcher.forward(req, res);
				res.sendRedirect(req.getContextPath() + "/store-end/store/storeProfile.jsp?tab=tab1");

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				System.out.println("錯誤訊息 " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/store-end/store/storeProfile.jsp");

				failureView.forward(req, res);
			}

		}

		// 店家上架
		if ("upStore".equals(action)) {

			List<String> errorMsgs = new ArrayList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String sto_num = (String) session.getAttribute("sto_num");

				String status = "已上架";

				StoreProfileService storeProfileService = new StoreProfileService();
				storeProfileService.updateStoStatus(sto_num, status);
				obj.put("message", status);
				out.write(obj.toString());
				out.flush();
				out.close();

			} catch (Exception e) {

				System.out.println("錯誤訊息 " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/store-end/store/storeProfile.jsp");

				failureView.forward(req, res);

			}

		}

		// 店家下架
		else if ("downStore".equals(action)) {

			List<String> errorMsgs = new ArrayList();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String sto_num = (String) session.getAttribute("sto_num");

				String status = "未上架";

				StoreProfileService storeProfileService = new StoreProfileService();
				storeProfileService.updateStoStatus(sto_num, status);
				obj.put("message", "已下架");
				out.write(obj.toString());
				out.flush();
				out.close();

			} catch (Exception e) {

				System.out.println("錯誤訊息 " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/store-end/store/storeProfile.jsp");

				failureView.forward(req, res);

			}

		}

		// 店家新增一筆檢舉紀錄

		else if ("insertReport".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			errorMsgs.add("進入新增 ");
			try {

				String sto_num = (String) session.getAttribute("sto_num");
				String mem_num = req.getParameter("mem_num");
				String com_num = req.getParameter("com_num");
				String content = req.getParameter("content");
				if(content.isEmpty()||com_num.isEmpty()||mem_num.isEmpty()||sto_num.isEmpty()){
					String value = "資料填寫不完整 。";
					obj.put("canPass", "Yes");
					obj.put("errormessage", value);
					out.write(obj.toString());
					out.flush();
					out.close();
					return;
					
				}
				ReportStoreService reportStoreService = new ReportStoreService();
				ReportStoreVO reportStoreVO = new ReportStoreVO();
				reportStoreVO.setSto_num(sto_num);
				reportStoreVO.setMem_num(mem_num);
				reportStoreVO.setCom_num(com_num);
				reportStoreVO.setContent(content);
				reportStoreService.insertReport(reportStoreVO);
				String value = "已經收到您提出的檢舉申請 。";
				obj.put("canPass", "Yes");
				obj.put("message", value);
				out.write(obj.toString());
				out.flush();
				out.close();

				// RequestDispatcher dispatcher =
				// req.getRequestDispatcher("/store-end/store/storeMessage.jsp");
				// dispatcher.forward(req, res);

			} catch (Exception e) {

				System.out.println("錯誤訊息 " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/store-end/store/storeMessage.jsp");

				failureView.forward(req, res);
			}

		}

	}

}
