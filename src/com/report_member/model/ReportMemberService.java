package com.report_member.model;

import java.util.List;

public class ReportMemberService {

	private ReportMemberDAO_interface dao;
	
	public ReportMemberService(){
		dao = new ReportMemberDAO();
	}

	
//------------------Peiiun---------------	
	public ReportMemberVO addReport(ReportMemberVO reportMemberVO){
		dao.insert(reportMemberVO);
		return reportMemberVO;		
	}
	
	public ReportMemberVO memUpdate(ReportMemberVO reportMemberVO){
		dao.memUpdate(reportMemberVO);
		return reportMemberVO;		
	}
	
	public List<ReportMemberVO> memGetAll(String mem_num){
		return dao.memGetAllRpt(mem_num);
	}
	
	public ReportMemberVO getOne(String rpt_mnum){		
		return dao.getRptbyPrimaryKey(rpt_mnum);
	}
	
	public List<ReportMemberVO> getAll(){
		return dao.getAll();
	}
	
	public ReportMemberVO stfUpdate(ReportMemberVO reportMemberVO){
		dao.stfUpdate(reportMemberVO);
		return reportMemberVO;
	}
//----------------end Peiiun---------------------

	
//----------------Shawn-----------------	
	public ReportMemberVO addReportMemberComment(String mem_num, String com_num, java.util.Date rpt_time, String status, String content) {

		ReportMemberVO reportMemberVO = new ReportMemberVO();

		reportMemberVO.setMem_num(mem_num);
		reportMemberVO.setCom_num(com_num);
		reportMemberVO.setRpt_time(rpt_time);
		reportMemberVO.setStatus(status);
		reportMemberVO.setContent(content);
		dao.insertReportMemberComment(reportMemberVO);

		return reportMemberVO;
	}
	
	public ReportMemberVO addReportMember(String rpt_mnum, String mem_num, String mem_num2, java.util.Date rpt_time, String status) {

		ReportMemberVO reportMemberVO = new ReportMemberVO();

		reportMemberVO.setRpt_mnum(rpt_mnum);
		reportMemberVO.setMem_num(mem_num);
		reportMemberVO.setMem_num2(mem_num2);
		reportMemberVO.setRpt_time(rpt_time);
		reportMemberVO.setStatus(status);
		dao.insertReportMemberComment(reportMemberVO);

		return reportMemberVO;
	}
	
	public List<List> getStoContentAll() {
		return dao.getStoContentAll();
	}
	
	public List<List> getMemContentAll() {
		return dao.getMemContentAll();
	}
	
	public List<List> getStoContentByStatus(String status) {
		return dao.getStoContentByStatus(status);
	}
	
	public List<List> getMemContentByStatus(String status) {
		return dao.getMemContentByStatus(status);
	}
	
	public List<ReportMemberVO> getReportMemberSto(String mem_num) {
		return dao.getReportMemberSto(mem_num);
	}
	
	public List<ReportMemberVO> getReportMemberMem(String mem_num) {
		return dao.getReportMemberMem(mem_num);
	}
	
	public List<ReportMemberVO> getReportMemberCom(String mem_num) {
		return dao.getReportMemberCom(mem_num);
	}
	
	public ReportMemberVO updateReportMember(String status, String staff_num,  String way, String rpt_mnum) {

		ReportMemberVO reportMemberVO = new ReportMemberVO();

		reportMemberVO.setStatus(status);
		reportMemberVO.setStaff_num(staff_num);
		reportMemberVO.setWay(way);
		reportMemberVO.setRpt_mnum(rpt_mnum);		

		dao.updateReportMember(reportMemberVO);

		return reportMemberVO;
	}
	
	public List<ReportMemberVO> getMemCommentAll() {
		return dao.getMemCommentAll();
	}
	
	public List<ReportMemberVO> getMemCommentByStatus(String status) {
		return dao.getMemCommentByStatus(status);
	}
//----------------end Shawn-----------------	
}
