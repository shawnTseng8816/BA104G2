package com.report.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.store_comment.model.StoreCommentService;
import com.store_comment.model.StoreCommentVO;
import com.store_profile.model.StoreProfileService;
import com.store_profile.model.StoreProfileVO;


public class ReportMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String session_mem_num = (String) session.getAttribute("mem_num");
		String reportMemberStyle = req.getParameter("reportMemberStyle");
		String selectMemStyleAgain = req.getParameter("selectMemStyleAgain");
		String reportMemberStyleChinese = null;
		String selectMemStyleAgainChinese = null;
		ReportMemberService reportMemberService = new ReportMemberService();
		List<ReportMemberVO> listReportMemberSto = reportMemberService.getReportMemberSto(session_mem_num);
		List<ReportMemberVO> listReportMemberMem = reportMemberService.getReportMemberMem(session_mem_num);
		List<ReportMemberVO> listReportMemberCom = reportMemberService.getReportMemberCom(session_mem_num);
		StoreProfileService storeProfileService = new StoreProfileService();
		MemberProfileService memberProfileService = new MemberProfileService();
		StoreCommentService storeCommentService = new StoreCommentService();
		List<ReportMemberDetail> listReportMemberDetail = new ArrayList<>();
		

		
		
		if(reportMemberStyle!=null){
			reportMemberStyleChinese = new String(reportMemberStyle.getBytes("ISO-8859-1"),"UTF-8");
		}
		if(selectMemStyleAgain!=null){
			selectMemStyleAgainChinese = new String(selectMemStyleAgain.getBytes("ISO-8859-1"),"UTF-8");
		}
		if("店家".equals(selectMemStyleAgainChinese)){
			reportMemberStyleChinese = selectMemStyleAgainChinese;

		}else if("會員".equals(selectMemStyleAgainChinese)){
			reportMemberStyleChinese = selectMemStyleAgainChinese;
		}else if("評論".equals(selectMemStyleAgainChinese)){
			reportMemberStyleChinese = selectMemStyleAgainChinese;
		}else if("全部".equals(selectMemStyleAgainChinese)){
			reportMemberStyleChinese = selectMemStyleAgainChinese;
		}
		
		
		if("店家".equals(reportMemberStyleChinese)){
			req.setAttribute("reportMemberStyleChinese", reportMemberStyleChinese);
			for(int i=0; i<listReportMemberSto.size();i++){
				ReportMemberDetail reportMemberDetailSto = new ReportMemberDetail();
				String sto_num = listReportMemberSto.get(i).getSto_num();
				StoreProfileVO storeProfileVO = storeProfileService.getStoreProfile(sto_num);
				
				reportMemberDetailSto.setRpt_time(listReportMemberSto.get(i).getRpt_time());
				reportMemberDetailSto.setSto_name(storeProfileVO.getSto_name());
				reportMemberDetailSto.setSto_num(listReportMemberSto.get(i).getSto_num());
				reportMemberDetailSto.setStatus(listReportMemberSto.get(i).getStatus());
				reportMemberDetailSto.setWay(listReportMemberSto.get(i).getWay());
				reportMemberDetailSto.setContent(listReportMemberSto.get(i).getContent());
				listReportMemberDetail.add(reportMemberDetailSto);
			}
			
		}else if("會員".equals(reportMemberStyleChinese)){
			req.setAttribute("reportMemberStyleChinese", reportMemberStyleChinese);
			for(int i=0; i<listReportMemberMem.size();i++){
				ReportMemberDetail reportMemberDetailMem = new ReportMemberDetail();
				String mem_num2 = listReportMemberMem.get(i).getMem_num2();
				MemberProfileVO memberProfileVO = memberProfileService.getMemberProfileByMem_num(mem_num2);
				reportMemberDetailMem.setRpt_time(listReportMemberMem.get(i).getRpt_time());
				reportMemberDetailMem.setMem_name(memberProfileVO.getMem_name());
				reportMemberDetailMem.setMem_num(listReportMemberMem.get(i).getMem_num2());
				reportMemberDetailMem.setStatus(listReportMemberMem.get(i).getStatus());
				reportMemberDetailMem.setWay(listReportMemberMem.get(i).getWay());
				reportMemberDetailMem.setContent(listReportMemberMem.get(i).getContent());
				listReportMemberDetail.add(reportMemberDetailMem);
			}
			
		}else if("評論".equals(reportMemberStyleChinese)){
			req.setAttribute("reportMemberStyleChinese", reportMemberStyleChinese);
			for(int i=0; i<listReportMemberCom.size();i++){
				ReportMemberDetail reportMemberDetailCom = new ReportMemberDetail();
				String com_num = listReportMemberCom.get(i).getCom_num();
				StoreCommentVO storeCommentVO = storeCommentService.getStoreComment(com_num);
				reportMemberDetailCom.setRpt_time(listReportMemberCom.get(i).getRpt_time());
				reportMemberDetailCom.setCom_title(storeCommentVO.getCom_title());
				reportMemberDetailCom.setCom_comment(storeCommentVO.getCommentt());
				reportMemberDetailCom.setStatus(listReportMemberCom.get(i).getStatus());
				reportMemberDetailCom.setWay(listReportMemberCom.get(i).getWay());
				reportMemberDetailCom.setContent(listReportMemberCom.get(i).getContent());
				listReportMemberDetail.add(reportMemberDetailCom);
			}
			
		}else if("全部".equals(reportMemberStyleChinese) || reportMemberStyleChinese==null){
			req.setAttribute("reportMemberStyleChinese", reportMemberStyleChinese);
			for(int i=0; i<listReportMemberSto.size();i++){
				ReportMemberDetail reportMemberDetailSto = new ReportMemberDetail();
				String sto_num = listReportMemberSto.get(i).getSto_num();
				StoreProfileVO storeProfileVO = storeProfileService.getStoreProfile(sto_num);
				
				reportMemberDetailSto.setRpt_time(listReportMemberSto.get(i).getRpt_time());
				reportMemberDetailSto.setSto_name(storeProfileVO.getSto_name());
				reportMemberDetailSto.setSto_num(listReportMemberSto.get(i).getSto_num());
				reportMemberDetailSto.setStatus(listReportMemberSto.get(i).getStatus());
				reportMemberDetailSto.setWay(listReportMemberSto.get(i).getWay());
				reportMemberDetailSto.setContent(listReportMemberSto.get(i).getContent());
				listReportMemberDetail.add(reportMemberDetailSto);
			}
			for(int i=0; i<listReportMemberMem.size();i++){
				ReportMemberDetail reportMemberDetailMem = new ReportMemberDetail();
				String mem_num2 = listReportMemberMem.get(i).getMem_num2();
				MemberProfileVO memberProfileVO = memberProfileService.getMemberProfileByMem_num(mem_num2);
				reportMemberDetailMem.setRpt_time(listReportMemberMem.get(i).getRpt_time());
				reportMemberDetailMem.setMem_name(memberProfileVO.getMem_name());
				reportMemberDetailMem.setMem_num(listReportMemberMem.get(i).getMem_num2());
				reportMemberDetailMem.setStatus(listReportMemberMem.get(i).getStatus());
				reportMemberDetailMem.setWay(listReportMemberMem.get(i).getWay());
				reportMemberDetailMem.setContent(listReportMemberMem.get(i).getContent());
				listReportMemberDetail.add(reportMemberDetailMem);
			}
			for(int i=0; i<listReportMemberCom.size();i++){
				ReportMemberDetail reportMemberDetailCom = new ReportMemberDetail();
				String com_num = listReportMemberCom.get(i).getCom_num();
				StoreCommentVO storeCommentVO = storeCommentService.getStoreComment(com_num);
				reportMemberDetailCom.setRpt_time(listReportMemberCom.get(i).getRpt_time());
				reportMemberDetailCom.setCom_title(storeCommentVO.getCom_title());
				reportMemberDetailCom.setCom_comment(storeCommentVO.getCommentt());
				reportMemberDetailCom.setStatus(listReportMemberCom.get(i).getStatus());
				reportMemberDetailCom.setWay(listReportMemberCom.get(i).getWay());
				reportMemberDetailCom.setContent(listReportMemberCom.get(i).getContent());
				listReportMemberDetail.add(reportMemberDetailCom);
			}
			
		}
		req.setAttribute("listReportMemberDetail", listReportMemberDetail);
		String url = "/front-end/ReportMember/ReportMember.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url); 
		successView.forward(req, res);
	}
		

}

