package com.set_session.model;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SessionSetMemberDetail extends HttpServlet {


	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("text/html; charset=UTF-8");

		HttpSession session = req.getSession();
		session.setAttribute("mem_bn_no",req.getParameter("mem_bn_no"));
        session.setAttribute("session_mem_num",req.getParameter("session_mem_num"));
        session.setAttribute("sto_num",req.getParameter("sto_num"));
        res.sendRedirect("/BA104G2/front-end/MemberDetail/MemberDetail.html");
        
	}
}
