package com.content.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member_profile.model.MemberProfileService;
import com.report_member.model.ReportMemberService;
import com.report_store.model.ReportStoreService;


public class ContentMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String url = null;
		String action = req.getParameter("action");
		String memContentStyle = req.getParameter("memContentStyle");
		String memContentStyleAgain = req.getParameter("memContentStyleAgain");
		String memStoContentStyle = req.getParameter("memStoContentStyle");
		String memStoContentStyleAgain = req.getParameter("memStoContentStyleAgain");
		String memContentStyleChinese = null;
		String memStoContentStyleChinese = null;
		String memContentStyleAgainChinese = null;
		String memStoContentStyleAgainChinese = null;
		HttpSession session = req.getSession();
		List<List> reportStoreList = null;
		List<List> reportMemberList = null;
		
		
		
		if(memStoContentStyle!=null){
			memStoContentStyleChinese = new String(memStoContentStyle.getBytes("ISO-8859-1"),"UTF-8");
		}
		if(memStoContentStyleAgain!=null){
			memStoContentStyleAgainChinese = new String(memStoContentStyleAgain.getBytes("ISO-8859-1"),"UTF-8");
		}
		if(memContentStyle!=null){
			memContentStyleChinese = new String(memContentStyle.getBytes("ISO-8859-1"),"UTF-8");
		}
		if(memContentStyleAgain!=null){
			memContentStyleAgainChinese = new String(memContentStyleAgain.getBytes("ISO-8859-1"),"UTF-8");
		}
		
		
		
		if("Mem_change".equals(action)){
			String session_mem_bn_no = (String) session.getAttribute("session_mem_bn_no");
			String rpt_mnum = req.getParameter("rpt_mnum");
			String rpt_snum = req.getParameter("rpt_snum");
			String mem_num = req.getParameter("mem_num");
			String way = req.getParameter("way");
			String way1 = new String(way.getBytes("ISO-8859-1"),"UTF-8");
			ReportStoreService reportStoreService = new ReportStoreService();
			reportStoreService.updateReportStore("已完成", session_mem_bn_no, way1, rpt_snum);
			ReportMemberService reportMemberService = new ReportMemberService();
			reportMemberService.updateReportMember("已完成", session_mem_bn_no, way1, rpt_mnum);
			MemberProfileService memberProfileService = new MemberProfileService();
			if(way1.equals("沒事")){
				
			}else if(way1.equals("停權")){
				memberProfileService.updateMemberProfile(way1, mem_num);
			}
			
			
		}
		
		
		ReportStoreService reportStoreService = new ReportStoreService();
		reportStoreList = reportStoreService.getMemContentAll();

		
		
		
		ReportMemberService reportMemberService = new ReportMemberService();
		reportMemberList = reportMemberService.getMemContentAll();
		
		
		if("待處理".equals(memStoContentStyleAgainChinese)){
			memStoContentStyleChinese = memStoContentStyleAgainChinese;
		}
		
		
		if("待處理".equals(memStoContentStyleChinese)){
			req.setAttribute("memStoContentStyleChinese", memStoContentStyleChinese);
			reportStoreList = reportStoreService.getMemContentByStatus("待處理");
		}else if("已完成".equals(memStoContentStyleChinese)){
			req.setAttribute("memStoContentStyleChinese", memStoContentStyleChinese);
			reportStoreList = reportStoreService.getMemContentByStatus("已完成");
		}else if("全部".equals(memStoContentStyleChinese)){
			req.setAttribute("memStoContentStyleChinese", memStoContentStyleChinese);
			reportStoreList = reportStoreService.getMemContentAll();
		}else{
			reportStoreList = reportStoreService.getMemContentAll();
		}
		
		
		
		
		if("待處理".equals(memContentStyleAgainChinese)){
			memContentStyleChinese = memContentStyleAgainChinese;
		}
		
		
		
		if("待處理".equals(memContentStyleChinese)){
			req.setAttribute("memContentStyleChinese", memContentStyleChinese);
			reportMemberList = reportMemberService.getMemContentByStatus("待處理");
		}else if("已完成".equals(memContentStyleChinese)){
			req.setAttribute("memContentStyleChinese", memContentStyleChinese);
			reportMemberList = reportMemberService.getMemContentByStatus("已完成");
		}else if("全部".equals(memContentStyleChinese)){
			req.setAttribute("memContentStyleChinese", memContentStyleChinese);
			reportMemberList = reportMemberService.getMemContentAll();
		}else{
			reportMemberList = reportMemberService.getMemContentAll();
		}
		
		
		
		req.setAttribute("reportStoreList", reportStoreList);
		req.setAttribute("reportMemberList", reportMemberList);
		url = "/back-end/MemberContent/MemContent.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交StoreDetail.jsp
		successView.forward(req, res);	
		
		
	}

}
