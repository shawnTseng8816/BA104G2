package com.search_profile.controller;

import java.io.IOException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member_profile.model.MemberProfileService;
import com.member_profile.model.MemberProfileVO;
import com.store_profile.model.StoreProfileService;
import com.store_profile.model.StoreProfileVO;


@WebServlet("/Search_all")
public class SearchProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
				
		String action = req.getParameter("action");
		String sto_name = req.getParameter("sto_name");
		String mem_name = req.getParameter("mem_name");
		String url = null;
		
		if("storeName".equals(action)){
			StoreProfileService storeProfileService = new StoreProfileService();
			Set<StoreProfileVO> setStoreProfile = storeProfileService.getStoreProfileAllName(sto_name);
			req.setAttribute("setStoreProfile", setStoreProfile);
			url = "/back-end/SearchStore/StoreProfile.jsp";
		}
		
		if("memberName".equals(action)){
			MemberProfileService memberProfileService = new MemberProfileService();
			Set<MemberProfileVO> setMemberProfile = memberProfileService.getMemberProfileAllName(mem_name);
			req.setAttribute("setMemberProfile", setMemberProfile);
			url = "/back-end/SearchMember/MemberProfile.jsp";
		}
		
		if("changeStoStatus".equals(action)){
			StoreProfileService storeProfileService = new StoreProfileService();
			String sto_num = req.getParameter("sto_num");
			String sto_status = req.getParameter("sto_status");
			String sto_status1 = new String(sto_status.getBytes("ISO-8859-1"),"UTF-8");
			if(sto_status1.equals("沒事")){
				
			}else{
				storeProfileService.updateStoreProfile(sto_status1, sto_num);
			}
			res.sendRedirect("/BA104G2/back-end/SearchStore/SearchStore.jsp");
			return;
		}
		
		if("changeMemStatus".equals(action)){
			MemberProfileService memberProfileService = new MemberProfileService();
			String mem_num = req.getParameter("mem_num");
			String mem_status = req.getParameter("mem_status");
			String mem_status1 = new String(mem_status.getBytes("ISO-8859-1"),"UTF-8");
			if(mem_status1.equals("沒事")){
				
			}else{
				memberProfileService.updateMemberProfile(mem_status1, mem_num);
			}
			res.sendRedirect("/BA104G2/back-end/SearchMember/SearchMember.jsp");
			return;
		}
		
		
		RequestDispatcher successView = req.getRequestDispatcher(url); 
		successView.forward(req, res);
		
		
		
	}

}
