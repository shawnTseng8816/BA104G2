package com.set_session.model;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SessionSetReportStore extends HttpServlet {


	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("text/html; charset=UTF-8");

		HttpSession session = req.getSession();
		session.setAttribute("session_sto_num",req.getParameter("session_sto_num"));
        res.sendRedirect("/BA104G2/reportStore/reportStore.do");
        
	}
}
