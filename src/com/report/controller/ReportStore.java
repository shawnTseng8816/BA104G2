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
import com.report_store.model.ReportStoreService;
import com.report_store.model.ReportStoreVO;
import com.store_comment.model.StoreCommentService;
import com.store_comment.model.StoreCommentVO;


public class ReportStore extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String session_sto_num = (String) session.getAttribute("sto_num");
		String selectStyleAgain = req.getParameter("selectStyleAgain");
		String reportStoreStyle = req.getParameter("reportStoreStyle");
		
		String reportStoreStyleChinese = null;
		String selectStyleAgainChinese = null;
		if(reportStoreStyle!=null){
			reportStoreStyleChinese = new String(reportStoreStyle.getBytes("ISO-8859-1"),"UTF-8");
		}
		if(selectStyleAgain!=null){
			selectStyleAgainChinese = new String(selectStyleAgain.getBytes("ISO-8859-1"),"UTF-8");
		}

		ReportStoreService reportStoreService = new ReportStoreService();
		List<ReportStoreVO> listReportStoreMem = reportStoreService.getReportStoreMem(session_sto_num);
		MemberProfileService memberProfileService = new MemberProfileService();
		List<ReportStoreVO> listReportStoreCom = reportStoreService.getReportStoreCom(session_sto_num);
		StoreCommentService storeCommentService = new StoreCommentService();
		List<ReportStoreDetail> listReportStoreDetail = new ArrayList<>();
		System.out.println("reportStoreStyleChinese" + reportStoreStyleChinese);
		if("會員".equals(selectStyleAgainChinese)){
			reportStoreStyleChinese = selectStyleAgainChinese;

		}else if("評論".equals(selectStyleAgainChinese)){
			reportStoreStyleChinese = selectStyleAgainChinese;
		}else if("全部".equals(selectStyleAgainChinese)){
			reportStoreStyleChinese = selectStyleAgainChinese;
		}else{
			
		}
		System.out.println("selectStyleAgainChinese" + selectStyleAgainChinese);
		
		if("會員".equals(reportStoreStyleChinese)){
			req.setAttribute("reportStoreStyleChinese", reportStoreStyleChinese);
			for(int i=0 ; i<listReportStoreMem.size();i++){
				ReportStoreDetail reportStoreDetailMem = new ReportStoreDetail();
				String mem_num = listReportStoreMem.get(i).getMem_num();
				MemberProfileVO memberProfileVO = memberProfileService.getMemberProfileByMem_num(mem_num);
				reportStoreDetailMem.setMem_num(listReportStoreMem.get(i).getMem_num());
				reportStoreDetailMem.setRpt_time(listReportStoreMem.get(i).getRpt_time());
				reportStoreDetailMem.setMem_name(memberProfileVO.getMem_name());
				reportStoreDetailMem.setContent(listReportStoreMem.get(i).getContent());
				reportStoreDetailMem.setStatus(listReportStoreMem.get(i).getStatus());
				reportStoreDetailMem.setWay(listReportStoreMem.get(i).getWay());
				listReportStoreDetail.add(reportStoreDetailMem);
			}
			
		}else if("評論".equals(reportStoreStyleChinese)){
			req.setAttribute("reportStoreStyleChinese", reportStoreStyleChinese);
			for(int i=0; i<listReportStoreCom.size();i++){
				ReportStoreDetail reportStoreDetailCom = new ReportStoreDetail();
				String com_num = listReportStoreCom.get(i).getCom_num();
				StoreCommentVO storeCommentVO = storeCommentService.getStoreComment(com_num);
				reportStoreDetailCom.setRpt_time(listReportStoreCom.get(i).getRpt_time());
				reportStoreDetailCom.setCom_comment(storeCommentVO.getCommentt());
				reportStoreDetailCom.setCom_title(storeCommentVO.getCom_title());
				reportStoreDetailCom.setContent(listReportStoreCom.get(i).getContent());
				reportStoreDetailCom.setStatus(listReportStoreCom.get(i).getStatus());
				reportStoreDetailCom.setWay(listReportStoreCom.get(i).getWay());
				listReportStoreDetail.add(reportStoreDetailCom);
			}
		}else if("全部".equals(reportStoreStyleChinese) || reportStoreStyleChinese==null){
			req.setAttribute("reportStoreStyleChinese", reportStoreStyleChinese);
			for(int i=0; i<listReportStoreCom.size();i++){
				ReportStoreDetail reportStoreDetailCom = new ReportStoreDetail();
				String com_num = listReportStoreCom.get(i).getCom_num();
				StoreCommentVO storeCommentVO = storeCommentService.getStoreComment(com_num);
				reportStoreDetailCom.setRpt_time(listReportStoreCom.get(i).getRpt_time());
				reportStoreDetailCom.setCom_comment(storeCommentVO.getCommentt());
				reportStoreDetailCom.setCom_title(storeCommentVO.getCom_title());
				reportStoreDetailCom.setContent(listReportStoreCom.get(i).getContent());
				reportStoreDetailCom.setStatus(listReportStoreCom.get(i).getStatus());
				reportStoreDetailCom.setWay(listReportStoreCom.get(i).getWay());
				listReportStoreDetail.add(reportStoreDetailCom);
			}
			for(int i=0 ; i<listReportStoreMem.size();i++){
				ReportStoreDetail reportStoreDetailMem = new ReportStoreDetail();
				String mem_num = listReportStoreMem.get(i).getMem_num();
				MemberProfileVO memberProfileVO = memberProfileService.getMemberProfileByMem_num(mem_num);
				reportStoreDetailMem.setMem_num(listReportStoreMem.get(i).getMem_num());
				reportStoreDetailMem.setRpt_time(listReportStoreMem.get(i).getRpt_time());
				reportStoreDetailMem.setMem_name(memberProfileVO.getMem_name());
				reportStoreDetailMem.setContent(listReportStoreMem.get(i).getContent());
				reportStoreDetailMem.setStatus(listReportStoreMem.get(i).getStatus());
				reportStoreDetailMem.setWay(listReportStoreMem.get(i).getWay());
				listReportStoreDetail.add(reportStoreDetailMem);
			}
			
		}
		
		
		
		req.setAttribute("listReportStoreDetail", listReportStoreDetail);
		
		String url = "/front-end/ReportStore/ReportStore.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url); 
		successView.forward(req, res);
		
		
		
	}
		

}
