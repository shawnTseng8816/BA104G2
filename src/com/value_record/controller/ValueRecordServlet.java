package com.value_record.controller;

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

import com.keep_record.model.KeepRecordService;
import com.member_profile.model.MemberProfileService;
import com.order_master.model.OrderMasterService;
import com.value_record.model.ValueRecordService;

import account.AccService;
import account.AccVO;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AccServlet
 */
@WebServlet("/ValueRecordServlet")
public class ValueRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		res.setHeader("Cache-Control", "no-store");
		res.setHeader("Pragma", "no-cache");
		res.setDateHeader("Expires", 0);

		String action = req.getParameter("action");

		HttpSession session = req.getSession();
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();		
		JSONObject obj = new JSONObject();
		
		// 小遊戲
				if ("getPoint".equals(action)) {

					List<String> errorMsgs = new LinkedList<String>();

					req.setAttribute("errorMsgs", errorMsgs);
					try {
						/*************************** 1.接收請求參數 ***************************************/
							System.out.println("進入小遊戲 !");
						
					
						String or_num = req.getParameter("or_num");
							
						OrderMasterService orderSvc = new  OrderMasterService();	
						int go = orderSvc.getOneOrder(or_num).getOr_amount();
						
						Integer canPass =orderSvc.getPlay(or_num);
						System.out.println("can"+ canPass);
						String mem_num = (String)session.getAttribute("mem_num");
						ValueRecordService valueSvc = new ValueRecordService();
						
							if(canPass==0){	
								int value_cash =(int)(Math.random()*25)+1;
								String value = "恭喜您獲得 "+ value_cash +" 點<br> 已匯入您的帳戶 ";
								
								if(go==120){
									value_cash=100000;
									valueSvc.addValue(mem_num, value_cash);	
									value = "恭喜您獲得頭獎 100,000  點!! <br> 已匯入您的帳戶 ";
									obj.put("canPass", "Yes");
									obj.put("message", value);
									obj.put("money", value_cash);
									out.write(obj.toString());
									out.flush();
									out.close();
									return;
								}
								
								
								switch(value_cash){
								case 1 :
								case 2 :
								case 3 :
								case 4 :
									value = " 再接再厲 ... ";
									break;
								
								
							
								default:
									value_cash = (int)(Math.random()*3)+1;
									valueSvc.addValue(mem_num, value_cash);	
									value = "恭喜您獲得 "+ value_cash +" 點<br> 已匯入您的帳戶 ";
									break;
								}
								
								orderSvc.upDateGame(or_num, value_cash);
								
								obj.put("canPass", "Yes");
								obj.put("message", value);
								obj.put("money", value_cash);
								out.write(obj.toString());
								out.flush();
								out.close();
								
							}else{
								
								obj.put("canPass", "No");
								out.write(obj.toString());
								out.flush();
								out.close();
							}
						
						// Send the use back to the form, if there were errors

						/*************************** 2.開始修改資料 *****************************************/

						/***************************
						 * 3.修改完成,準備轉交(Send the Success view)
						 *************/
						
						

						/*************************** 其他可能的錯誤處理 ***********************************/
					} catch (Exception e) {
						errorMsgs.add("新增資料失敗:" + e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/store-end/keeprecord/keeprecord.jsp");

						failureView.forward(req, res);
					}
				}
				
		// 會員儲值點數
		if ("addPoint".equals(action)) { // 來自front-end addPoint.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/

				String card_num = req.getParameter("number").replaceAll(" ", "");

				// 4560 3245 6234 6650
				
				// 卡號錯誤
				if (!card_num.equals("4560324562346650")) {

					String value = " 卡號有誤  ， 請重新確認 !";
					
					obj.put("errormessage", value);
					out.write(obj.toString());
					out.flush();
					out.close();
					
					return;
				}

				Integer value_cash = null;
				
				try {
					value_cash = new Integer(req.getParameter("value_cash").trim());
				} catch (NumberFormatException e) {

					value_cash = 0;

					String value = " 金額輸數錯誤 !";
					
					obj.put("errormessage", value);
					out.write(obj.toString());
					out.flush();
					out.close();				
					return;			

				}
				if(value_cash>1000000){
					

					String value = " 會玩壞掉喔 XD !";
					
					obj.put("errormessage", value);
					out.write(obj.toString());
					out.flush();
					out.close();				
					return;			
				}

				ValueRecordService valueSvc = new ValueRecordService();
				MemberProfileService memSvc = new MemberProfileService();
				
				String mem_num = (String)session.getAttribute("mem_num");
				
				valueSvc.addValue(mem_num, value_cash);
				int rem_point =  memSvc.getOneMemInfo(mem_num).getRem_point();
				// Send the use back to the form, if there were errors

				/*************************** 2.開始修改資料 *****************************************/

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/
		
				String value = "儲值成功 ! <br> 儲值金額為 : $ "+ value_cash;
				
				obj.put("message", value);
				obj.put("rem_point", rem_point);
				out.write(obj.toString());
				out.flush();
				out.close();
//				errorMsgs.add("儲值成功 !!");
//
//				RequestDispatcher dispatcher = req.getRequestDispatcher("/front-end/point/addPoint.jsp");
//				dispatcher.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				
				errorMsgs.add("錯誤訊息 : " + e);
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front-end/point/addPoint.jsp");
				dispatcher.forward(req, res);
			}
		}

	}

}
