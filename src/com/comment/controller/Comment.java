package com.comment.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.member_profile.model.MemberProfileVO;
import com.report_member.model.ReportMemberService;
import com.report_member.model.ReportMemberVO;
import com.report_store.model.ReportStoreService;
import com.report_store.model.ReportStoreVO;
import com.store_comment.model.StoreCommentService;
import com.store_comment.model.StoreCommentVO;
import com.store_profile.model.StoreProfileService;
import com.store_profile.model.StoreProfileVO;



public class Comment extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	 
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		
		
		ReportMemberService reportMemberService = new ReportMemberService();
		ReportStoreService reportStoreService = new ReportStoreService();
		StoreProfileService storeProfileService = new StoreProfileService();
		MemberProfileService memberProfileService = new MemberProfileService();
		StoreCommentService storeCommentService = new StoreCommentService();
		List<CommentDetail> listCommentDetailSto = new ArrayList<>();
		List<CommentDetail> listCommentDetailMem = new ArrayList<>();
		List<ReportMemberVO> listReportMember = reportMemberService.getMemCommentAll();
		List<ReportStoreVO> listReportStore = reportStoreService.getStoCommentAll();
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		String memCommentStyle = req.getParameter("memCommentStyle");
		String stoCommentStyle = req.getParameter("stoCommentStyle");
		String stoCommentStyleChineseAgain = req.getParameter("stoCommentStyleChinese");
		String memCommentStyleChineseAgain = req.getParameter("memCommentStyleChinese");
		String memCommentStyleChinese = null;
		String stoCommentStyleChinese = null;
		String stoCommentStyleAgainChinese = null;
		String memCommentStyleAgainChinese = null;
		String selectMemStyleAgainChinese = null;
		
		if(memCommentStyle!=null){
			memCommentStyleChinese = new String(memCommentStyle.getBytes("ISO-8859-1"),"UTF-8");
		}
		
		if(stoCommentStyle!=null){
			stoCommentStyleChinese = new String(stoCommentStyle.getBytes("ISO-8859-1"),"UTF-8");
		}
		if(stoCommentStyleChineseAgain!=null){
			stoCommentStyleAgainChinese = new String(stoCommentStyleChineseAgain.getBytes("ISO-8859-1"),"UTF-8");
		}
		if(memCommentStyleChineseAgain!=null){
			memCommentStyleAgainChinese = new String(memCommentStyleChineseAgain.getBytes("ISO-8859-1"),"UTF-8");
		}
		
		if("Mem_change_comment".equals(action)){
			String session_mem_bn_no = (String) session.getAttribute("session_mem_bn_no");
			System.out.println(session_mem_bn_no);
			String rpt_mnum = req.getParameter("rpt_mnum");
			String com_num = req.getParameter("com_num");
			String way = req.getParameter("way");
			String way1 = new String(way.getBytes("ISO-8859-1"),"UTF-8");
			ReportMemberService reportMemberService1 = new ReportMemberService();
			reportMemberService1.updateReportMember("已完成", session_mem_bn_no, way1, rpt_mnum);
			StoreCommentService storeCommentService1 = new StoreCommentService();
			if(way1.equals("沒事")){
				
			}else if(way1.equals("刪除")){
				storeCommentService1.updateCommentProfile(way1, com_num);
			}
			
			
		}
		if("sto_change_comment".equals(action)){
			String session_mem_bn_no = (String) session.getAttribute("session_mem_bn_no");
			System.out.println(session_mem_bn_no);
			String rpt_snum = req.getParameter("rpt_snum");
			String com_num = req.getParameter("com_num");
			String way = req.getParameter("way");
			String way1 = new String(way.getBytes("ISO-8859-1"),"UTF-8");
			ReportStoreService reportStoreService1 = new ReportStoreService();
			reportStoreService1.updateReportStore("已完成",session_mem_bn_no , way1, rpt_snum);
			StoreCommentService storeCommentService1 = new StoreCommentService();
			if(way1.equals("沒事")){
				System.out.println(way1);
			}else if(way1.equals("刪除")){
				System.out.println(way1);
				System.out.println(com_num);
				storeCommentService1.updateCommentProfile(way1, com_num);
			}
			
		}
		
		if("待處理".equals(memCommentStyleAgainChinese)){
			memCommentStyleChinese = memCommentStyleAgainChinese;
		}else if("已完成".equals(memCommentStyleAgainChinese)){
			memCommentStyleChinese = memCommentStyleAgainChinese;
		}else if("全部".equals(memCommentStyleAgainChinese)){
			memCommentStyleChinese = memCommentStyleAgainChinese;
		}else{
			
		}
		if("待處理".equals(stoCommentStyleAgainChinese)){
			stoCommentStyleChinese = stoCommentStyleAgainChinese;
		}else if("已完成".equals(stoCommentStyleAgainChinese)){
			stoCommentStyleChinese = stoCommentStyleAgainChinese;
		}else if("全部".equals(stoCommentStyleAgainChinese)){
			stoCommentStyleChinese = stoCommentStyleAgainChinese;
		}else{
			
		}
		
		
		if("待處理".equals(memCommentStyleChinese)){
			req.setAttribute("memCommentStyleChinese", memCommentStyleChinese);
			listReportMember = reportMemberService.getMemCommentByStatus("待處理");
		}else if("已完成".equals(memCommentStyleChinese)){
			req.setAttribute("memCommentStyleChinese", memCommentStyleChinese);
			listReportMember = reportMemberService.getMemCommentByStatus("已完成");
		}else if("全部".equals(memCommentStyleChinese)){
			req.setAttribute("memCommentStyleChinese", memCommentStyleChinese);
			listReportMember = reportMemberService.getMemCommentAll();
		}else{
			listReportMember = reportMemberService.getMemCommentAll();
		}
		
		if("待處理".equals(stoCommentStyleChinese)){
			req.setAttribute("stoCommentStyleChinese", stoCommentStyleChinese);
			listReportStore = reportStoreService.getStoCommentByStatus("待處理");
		}else if("已完成".equals(stoCommentStyleChinese)){
			req.setAttribute("stoCommentStyleChinese", stoCommentStyleChinese);
			listReportStore = reportStoreService.getStoCommentByStatus("已完成");
		}else if("全部".equals(stoCommentStyleChinese)){
			req.setAttribute("stoCommentStyleChinese", stoCommentStyleChinese);
			listReportStore = reportStoreService.getStoCommentAll();
		}else{
			listReportStore = reportStoreService.getStoCommentAll();
		}
		
		
		
		for(int i=0; i<listReportMember.size();i++){
			CommentDetail commentDetailMem = new CommentDetail();
			String mem_num = listReportMember.get(i).getMem_num();
			String com_num = listReportMember.get(i).getCom_num();
			MemberProfileVO memberProfileVO = memberProfileService.getMemberProfileByMem_num(mem_num);
			StoreCommentVO storeCommentVO = storeCommentService.getStoreComment(com_num);
			commentDetailMem.setMem_num(mem_num);
			commentDetailMem.setCom_num(com_num);
			commentDetailMem.setMem_name(memberProfileVO.getMem_name());
			commentDetailMem.setCom_title(storeCommentVO.getCom_title());
			commentDetailMem.setComment(storeCommentVO.getCommentt());
			commentDetailMem.setContent(listReportMember.get(i).getContent());
			commentDetailMem.setWay(listReportMember.get(i).getWay());
			commentDetailMem.setRpt_time(listReportMember.get(i).getRpt_time());
			commentDetailMem.setStatus(listReportMember.get(i).getStatus());
			commentDetailMem.setRpt_mnum(listReportMember.get(i).getRpt_mnum());
			listCommentDetailMem.add(commentDetailMem);
		}
		
		for(int i=0; i<listReportStore.size();i++){
			CommentDetail commentDetailSto = new CommentDetail();
			String sto_num = listReportStore.get(i).getSto_num();
			String com_num = listReportStore.get(i).getCom_num();
			StoreProfileVO storeProfileVO = storeProfileService.getStoreProfile(sto_num);
			StoreCommentVO storeCommentVO = storeCommentService.getStoreComment(com_num);
			commentDetailSto.setSto_num(sto_num);
			commentDetailSto.setCom_num(com_num);
			commentDetailSto.setRpt_snum(listReportStore.get(i).getRpt_snum());
			commentDetailSto.setSto_name(storeProfileVO.getSto_name());
			commentDetailSto.setCom_title(storeCommentVO.getCom_title());
			commentDetailSto.setComment(storeCommentVO.getCommentt());
			commentDetailSto.setContent(listReportStore.get(i).getContent());
			commentDetailSto.setWay(listReportStore.get(i).getWay());
			commentDetailSto.setRpt_time(listReportStore.get(i).getRpt_time());
			commentDetailSto.setStatus(listReportStore.get(i).getStatus());
			listCommentDetailSto.add(commentDetailSto);
		}
		

		
		
		
		
		
		
		
		req.setAttribute("listCommentDetailMem", listCommentDetailMem);
		req.setAttribute("listCommentDetailSto", listCommentDetailSto);
		String url = "/back-end/Comment/Comment.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url); 
		successView.forward(req, res);
		
		

	}
	
}
