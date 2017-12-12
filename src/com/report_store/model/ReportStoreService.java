package com.report_store.model;

import java.util.List;

public class ReportStoreService {
	private ReportStoreDAO_interface dao;
	
	public ReportStoreService(){
		dao = new ReportStoreDAO();
	}
//--------------Peiiun-----------------
	public ReportStoreVO addReport(ReportStoreVO reportStoreVO){
		dao.insert(reportStoreVO);
		return reportStoreVO;		
	}
	
	public ReportStoreVO stoUpdate(ReportStoreVO reportStoreVO){
		dao.stoUpdate(reportStoreVO);
		return reportStoreVO;		
	}
	
	public List<ReportStoreVO> memGetAll(String mem_num){
		return dao.stoGetAllRpt(mem_num);
	}
	
	public ReportStoreVO getOne(String rpt_mnum){		
		return dao.getRptbyPrimaryKey(rpt_mnum);
	}
	
	public List<ReportStoreVO> getAll(){
		return dao.getAll();
	}
	
	public ReportStoreVO stfUpdate(ReportStoreVO reportStoreVO){
		dao.stfUpdate(reportStoreVO);
		return reportStoreVO;
	}
//-------end Peiiun--------------
	
//------Shawn------------------------	
	public ReportStoreVO addReportStoreComment(String session_sto_num, String com_num, java.util.Date rpt_time, String status, String content) {

		ReportStoreVO reportStoreVO = new ReportStoreVO();

		reportStoreVO.setSto_num(session_sto_num);
		reportStoreVO.setCom_num(com_num);
		reportStoreVO.setRpt_time(rpt_time);
		reportStoreVO.setStatus(status);
		reportStoreVO.setContent(content);
		dao.insertReportStoreComment(reportStoreVO);

		return reportStoreVO;
	}
	
	public ReportStoreVO addReportStore(String rpt_snum, String sto_num, String mem_num, java.util.Date rpt_time, String status) {

		ReportStoreVO reportStoreVO = new ReportStoreVO();

		reportStoreVO.setRpt_snum(rpt_snum);
		reportStoreVO.setSto_num(sto_num);
		reportStoreVO.setMem_num(mem_num);
		reportStoreVO.setRpt_time(rpt_time);
		reportStoreVO.setStatus(status);
		dao.insertReportStore(reportStoreVO);

		return reportStoreVO;
	}
	
	public List<List> getMemContentAll() {
		return dao.getMemContentAll();
	}
	
	public List<List> getMemContentByStatus(String status) {
		return dao.getMemContentByStatus(status);
	}
	
	public List<ReportStoreVO> getReportStoreMem(String sto_num) {
		return dao.getReportStoreMem(sto_num);
	}
	
	public List<ReportStoreVO> getReportStoreCom(String sto_num) {
		return dao.getReportStoreCom(sto_num);
	}
	
	public ReportStoreVO updateReportStore(String status, String staff_num, String way, String rpt_snum) {

		ReportStoreVO reportStoreVO = new ReportStoreVO();

		reportStoreVO.setStatus(status);
		reportStoreVO.setStaff_num(staff_num);
		reportStoreVO.setWay(way);
		reportStoreVO.setRpt_snum(rpt_snum);

		dao.updateReportStore(reportStoreVO);

		return reportStoreVO;
	}
	
	public List<ReportStoreVO> getStoCommentAll() {
		return dao.getStoCommentAll();
	}
	
	public List<ReportStoreVO> getStoCommentByStatus(String status) {
		return dao.getStoCommentByStatus(status);
	}
//------end Shawn---------------------------
	
	
//------------Eric------------------------
//	public ReportStoreVO addReport(ReportStoreVO reportStoreVO){
//		dao.insert(reportStoreVO);
//		return reportStoreVO;		
//	}
//	
//	public ReportStoreVO stoUpdate(ReportStoreVO reportStoreVO){
//		dao.stoUpdate(reportStoreVO);
//		return reportStoreVO;		
//	}
//	
//	public List<ReportStoreVO> memGetAll(String mem_num){
//		return dao.stoGetAllRpt(mem_num);
//	}
//	
//	public ReportStoreVO getOne(String rpt_mnum){		
//		return dao.getRptbyPrimaryKey(rpt_mnum);
//	}
//	
//	public List<ReportStoreVO> getAll(){
//		return dao.getAll();
//	}
//	
//	public ReportStoreVO stfUpdate(ReportStoreVO reportStoreVO){
//		dao.stfUpdate(reportStoreVO);
//		return reportStoreVO;
//	}
	
	public void insertReport(ReportStoreVO reportStoreVO){
		dao.insertReport(reportStoreVO);
	}
	
//---------------end Eric------------------------------	
	
	
}
