package com.coupon_list.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coupon.model.CouponService;
import com.coupon.model.CouponVO;
import com.coupon_list.model.CouponListService;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class AccServlet
 */

public class CouponListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);

	}

	synchronized protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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


		
	
		
		/************************** 搶折價券 ****************************/

		if ("get_coupon".equals(action)) {
			// 來自 listRemCL.jsp的請求
			System.out.println("進入搶折價券!!!!");

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/

				//
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front-end/coupon/getCoupon.jsp");
				String coupon_num = req.getParameter("coupon_num");
				
				System.out.println("coupon_num " +coupon_num );
				
				CouponService CouponSvc = new CouponService();
				CouponVO couponVO = new CouponVO();
				couponVO = CouponSvc.getOneCoupon(coupon_num);
				int left = couponVO.getLeft();
				Timestamp up_date = couponVO.getUp_date();
				Timestamp now = new Timestamp(System.currentTimeMillis());
				String mem_num = (String) session.getAttribute("mem_num");
				
			
				
				if(up_date.getTime()>now.getTime()){
					errorMsgs.add(" 時間未到 !!");
					dispatcher.forward(req, res);
					return;
				}
					
				
				if (left != 0 ) {
					// 因測試所已先暫時給他一個編號
					if(mem_num==null){

						obj.put("nologin", "請先登入後再進行搶購 !");
						out.write(obj.toString());
						out.flush();
						out.close();
						System.out.println("XXXXXXXXXXXXXXXXX2");
						return;
					}
					
				
					CouponListService CouponListSvc = new CouponListService();

					/*************************** 2.開始修改資料 **************************************/

					CouponListSvc.getcoupon(coupon_num, mem_num, left);
				
					/***************************
					 * 3.修改完成,準備轉交(Send the Success view)
					 ***********/	
				
					
					obj.put("message", "恭喜搶購成功 !!");
					obj.put("left", "倒數 " + (left-1) + " 張");
					if((left-1)==0){
					obj.put("left", "搶購一空 !");
					}
					
					
					out.write(obj.toString());
					out.flush();
					out.close();
//					JSONObject obj = new JSONObject();
//					String value = new String();
				
				} else {
					
					
					obj.put("errormessage", "已搶購完畢 ， 慢了一步 !");					
					out.write(obj.toString());
					out.flush();
					out.close();

				}
				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("新增搶購資料失敗:" + e.getMessage());
				
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/coupon/getCoupon.jsp");
				failureView.forward(req, res);
			}
		}

	}

}
