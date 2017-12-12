package com.coupon.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.coupon.model.CouponService;
import com.coupon.model.CouponTask;
import com.coupon.model.CouponVO;
import com.friend_list.model.FriendListChatServer;

import javax.servlet.annotation.MultipartConfig;

/**
 * Servlet implementation class AccServlet
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
// 當數據量大於fileSizeThreshold值時，內容將被寫入磁碟
// 上傳過程中無論是單個文件超過maxFileSize值，或者上傳的總量大於maxRequestSize 值都會拋出IllegalStateException
// 異常
public class CouponServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Timer timer;

	public void destroy() {
		timer.cancel();
	}

	public void init() {

		timer = new Timer();

		CouponService coupon = new CouponService();

		List<Timestamp> list = coupon.getCouponUpDate();

		for (int i = 0; i < coupon.getCouponUpDate().size(); i++) {

			// 觸發時間為 剩餘1分鐘
			long time = list.get(i).getTime() - 60 * 1000L;

			Timestamp time2 = new Timestamp(time);

			timer.schedule(new CouponTask(), time2);
		}

		System.out.println("折價券預告資訊已載入");
	}

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

		// 後台省核通過
		if ("couponPass".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/

				//
				String coupon_num = req.getParameter("coupon_num");

				Integer total = Integer.parseInt(req.getParameter("total"));

				/*************************** 2.開始修改資料 **************************************/

				CouponService CouponSvc = new CouponService();

				CouponSvc.Pass(coupon_num, total);

				Timestamp up_date = CouponSvc.getOneCoupon(coupon_num).getUp_date();

				long time = up_date.getTime() - 60 * 1000L;

				Timestamp time2 = new Timestamp(time);

				timer.schedule(new CouponTask(), time2);

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 ***********/
				RequestDispatcher dispatcher = req.getRequestDispatcher("/back-end/coupon/checkCoupon.jsp");
				dispatcher.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("錯誤 : " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/coupon/checkCoupon.jsp");
				failureView.forward(req, res);
			}
		}

		// 省核失敗
		if ("couponNoPass".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/

				//
				String coupon_num = req.getParameter("coupon_num");

				/*************************** 2.開始修改資料 **************************************/

				CouponService CouponSvc = new CouponService();
				CouponSvc.noPass(coupon_num);

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 ***********/
				RequestDispatcher dispatcher = req.getRequestDispatcher("/back-end/coupon/checkCoupon.jsp");
				dispatcher.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("錯誤 :" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/coupon/checkCoupon.jsp");
				failureView.forward(req, res);
			}
		}

		// 新增折價券
		if ("applyCoupon".equals(action)) {

			try {
				/*************************** 1.接收請求參數 ***************************************/
				// InputStream fin = req.getPart("upfile1").getInputStream();
				// long flen = fin.available();
				//

				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				InputStream in = req.getPart("upfile").getInputStream();

				if (in.available() != 0) {
					int i;
					byte[] buffer = new byte[in.available()];
					while ((i = in.read(buffer)) != -1) {
						baos.write(buffer, 0, i);
					}
					in.close();
					baos.close();
				}

				byte[] image = baos.toByteArray();

				String sto_num = (String) session.getAttribute("sto_num");
				int coupon_cash = 0;
				try {
					coupon_cash = Integer.parseInt(req.getParameter("coupon_cash"));
					if(coupon_cash>99){
						String message = "折價金額請設定在 99 元以下 ";

						String webSocketAction = "stoGetErrMessage";

						FriendListChatServer.OneByOne(sto_num, webSocketAction, message);

						RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/coupon/couponApply.jsp");
						dispatcher.forward(req, res);
						return;
						
					}
					
				} catch (Exception e) {
					coupon_cash = 0;
				}

				String coupon_desc = req.getParameter("coupon_desc");
				String notice_desc = req.getParameter("notice_desc");

				int total = 0;
				try {
					total = Integer.parseInt(req.getParameter("total"));
					if(total>999){
	
						String message = "單次申請最多不得超過999張";

						String webSocketAction = "stoGetErrMessage";

						FriendListChatServer.OneByOne(sto_num, webSocketAction, message);

						RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/coupon/couponApply.jsp");
						dispatcher.forward(req, res);
						return;
					}
				} catch (Exception e) {
					total = 0;
				}

				Boolean TimeError = false;
				Timestamp up_date = null;
				Timestamp down_date = null;
				Timestamp exp_date = null;
				Timestamp notice_up_date = null;
				Timestamp notice_down_date = null;
				try {
					up_date = Timestamp.valueOf((req.getParameter("up_date")) + ":00");
					down_date = Timestamp.valueOf((req.getParameter("down_date")) + ":00");
					exp_date = Timestamp.valueOf((req.getParameter("exp_date")) + ":00");
					notice_up_date = Timestamp.valueOf((req.getParameter("notice_up_date")) + ":00");
					notice_down_date = Timestamp.valueOf((req.getParameter("notice_down_date")) + ":00");
				} catch (Exception e) {

					TimeError = true;
				}

				if (TimeError || image.length == 0 || coupon_desc.isEmpty() || notice_desc.isEmpty() || total <= 0
						|| coupon_cash <= 0) {
					String message = "資料填寫不完整，請再次確認 。";

					String webSocketAction = "stoGetErrMessage";

					FriendListChatServer.OneByOne(sto_num, webSocketAction, message);

					RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/coupon/couponApply.jsp");
					dispatcher.forward(req, res);
					return;
				}
				/*************************** 2.開始修改資料 **************************************/
				CouponVO couponVO = new CouponVO();
				couponVO.setSto_num(sto_num);
				couponVO.setCoupon_cash(coupon_cash);
				couponVO.setLeft(total);
				couponVO.setTotal(total);
				couponVO.setCoupon_desc(coupon_desc);
				couponVO.setNotice_desc(notice_desc);
				couponVO.setUp_date(up_date);
				couponVO.setDown_date(down_date);
				couponVO.setNotice_down_date(notice_down_date);
				couponVO.setNotice_up_date(notice_up_date);
				couponVO.setExp_date(exp_date);
				couponVO.setImage(image);
				CouponService CouponSvc = new CouponService();
				CouponSvc.insertApply(couponVO);

				String message = "我們已經收到您的折價券申請 。";

				String webSocketAction = "stoGetMessage";

				FriendListChatServer.OneByOne(sto_num, webSocketAction, message);
				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 ***********/
				RequestDispatcher dispatcher = req.getRequestDispatcher("/store-end/coupon/couponApply.jsp");
				dispatcher.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				System.out.println(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/store-end/coupon/couponApply.jsp");

				failureView.forward(req, res);
			}
		}

	}

}
