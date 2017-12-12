package login.test;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Session_Set extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("text/html; charset=UTF-8");

		HttpSession session = req.getSession();
		session.setAttribute("mem_num", req.getParameter("mem_num"));
		
//		RequestDispatcher successView = req.getRequestDispatcher("/OrderMaster/OMC.html");
//		successView.forward(req, res);
		
		res.sendRedirect(req.getContextPath() + "/MemberProfile/MPC.html");
//		res.sendRedirect("/BA104G2/OrderMaster/OMC.html?sto_num="+req.getParameter("sto_num"));
	}
}
