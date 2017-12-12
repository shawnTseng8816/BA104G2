package com.content.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.report_member.model.ReportMemberService;
import com.store_profile.model.StoreProfileService;
import com.store_profile.model.StoreProfileVO;


public class ContentStore extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String url = null;
		String action = req.getParameter("action");
		String stoContentStyle = req.getParameter("stoContentStyle");
		String stoContentStyleAgain = req.getParameter("stoContentStyleAgain");
		HttpSession session = req.getSession();
		String stoContentStyleChinese = null;
		String stoContentStyleAgainChinese = null;
		ReportMemberService reportMemberService = new ReportMemberService();
		List<List> listReportMember = null;
		if(stoContentStyle!=null){
			stoContentStyleChinese = new String(stoContentStyle.getBytes("ISO-8859-1"),"UTF-8");
		}
		
		
		
		
		if("Sto_change".equals(action)){
			String session_sto_bn_no = (String) session.getAttribute("session_sto_bn_no");
			String rpt_mnum = req.getParameter("rpt_mnum");
			String sto_num = req.getParameter("sto_num");
			String way = req.getParameter("way");
			String way1 = new String(way.getBytes("ISO-8859-1"),"UTF-8");
			ReportMemberService reportMemberSer = new ReportMemberService();
			reportMemberSer.updateReportMember("已完成",session_sto_bn_no , way1, rpt_mnum);
			StoreProfileService storeProfileService = new StoreProfileService();
			Set <StoreProfileVO> setStoreProfile = storeProfileService.getStoreProfileBySto_num(sto_num);
			Iterator itStoreProfile = setStoreProfile.iterator();
			StoreProfileVO storeProfileVO = (StoreProfileVO) itStoreProfile.next();
			String stoStatus = storeProfileVO.getSto_status();
			if(way1.equals("沒事")){
			}else if(way1.equals("後端下架")){
				if(stoStatus.equals("停權")){
				}else{
					storeProfileService.updateStoreProfile(way1, sto_num);
				}
			}else if(way1.equals("停權")){
				storeProfileService.updateStoreProfile(way1, sto_num);
			}
			
		}
		if(stoContentStyleAgain!=null){
			stoContentStyleAgainChinese = new String(stoContentStyleAgain.getBytes("ISO-8859-1"),"UTF-8");
	    }
		if("待處理".equals(stoContentStyleAgainChinese)){
			stoContentStyleChinese = stoContentStyleAgainChinese;
		}
		
		if("待處理".equals(stoContentStyleChinese)){
			req.setAttribute("stoContentStyleChinese", stoContentStyleChinese);
			listReportMember = reportMemberService.getStoContentByStatus(stoContentStyleChinese);
		}else if("已完成".equals(stoContentStyleChinese)){
			req.setAttribute("stoContentStyleChinese", stoContentStyleChinese);
			listReportMember = reportMemberService.getStoContentByStatus(stoContentStyleChinese);
		}else if("全部".equals(stoContentStyleChinese)){
			req.setAttribute("stoContentStyleChinese", stoContentStyleChinese);
			listReportMember = reportMemberService.getStoContentAll();
		}else{
			listReportMember = reportMemberService.getStoContentAll();
		}
		req.setAttribute("stoContentStyleChinese", stoContentStyleChinese);
		req.setAttribute("listReportMember", listReportMember);
		
		url = "/back-end/StoreContent/StoContent.jsp";
		
		RequestDispatcher successView = req.getRequestDispatcher(url); 
		successView.forward(req, res);	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
