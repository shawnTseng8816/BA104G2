package com.ice_list.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ice_list.model.IceListService;
import com.ice_list.model.IceListVO;
import com.order_master.model.OrderMasterService;
import com.store_comment.model.StoreCommentService;

public class IceListController extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		try {
			String mem_num = (String) req.getSession().getAttribute("mem_num");

			if (mem_num == null || (mem_num.trim()).length() == 0) {
				res.sendRedirect("Login.jsp");
				return;
			}
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String action = req.getParameter("action");

			IceListService ils = new IceListService();

			if ("ADD".equals(action)) {
				try {
					
				} catch (Exception e) {
					errorMsgs.add("err");
				}
			} else if ("DELETE".equals(action)) {
				try {
					
				} catch (Exception e) {
					errorMsgs.add("err");
				}
			}
			

			List<IceListVO> iceList = ils.getIceList(req.getParameter("sto_num"));
			req.setAttribute("iceList", iceList);
			
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/Store_Comment/StoreComment.jsp");
			successView.forward(req, res);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}