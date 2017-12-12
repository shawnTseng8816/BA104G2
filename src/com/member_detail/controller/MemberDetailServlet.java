package com.member_detail.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.friend_list.model.FriendListService;
import com.friend_list.model.FriendListVO;
import com.member_profile.model.MemberProfileService;
import com.member_profile.model.MemberProfileVO;


public class MemberDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		
		
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String mem_num = req.getParameter("mem_num");
		String session_mem_num = (String) session.getAttribute("session_mem_num");
		
		MemberProfileService memberProfileService = new MemberProfileService();
		MemberProfileVO memberProfileVO = memberProfileService.getMemberProfileByMem_num(mem_num);
		
		
		
		FriendListService friendListService = new FriendListService();
		FriendListVO friendListVO = friendListService.getIsFriend(session_mem_num, mem_num);
		FriendListVO friendListVO1 = friendListService.getIsFriend(mem_num, session_mem_num);
		String isfriend="N";
		String memA="N";
		String memB="N";
		
		if(friendListVO==null){
			memA="N";
		}else if("Y".equals(friendListVO.getIsfriend())){
			memA="Y";
		}else if("CONFIRM".equals(friendListVO.getIsfriend())){
			memA="CONFIRM";
		}else{
			memA="N";
		}
		
		if(friendListVO1==null){
			memB="N";
		}else if("Y".equals(friendListVO1.getIsfriend())){
			memB="Y";
		}else if("CONFIRM".equals(friendListVO1.getIsfriend())){
			memB="CONFIRM";
		}else{
			memB="N";
		}
		if("Y".equals(memA) || "Y".equals(memB)){
			isfriend = "Y";
		}else if("CONFIRM".equals(memA) || "CONFIRM".equals(memB)){
			isfriend = "CONFIRM";
		}
	
		
		req.setAttribute("memberProfileVO", memberProfileVO);
		req.setAttribute("isfriend", isfriend);
		req.setAttribute("mem_num", mem_num);
		
		String url = "/front-end/MemberDetail/MemberDetail.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交StoreDetail.jsp
		successView.forward(req, res);	
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
