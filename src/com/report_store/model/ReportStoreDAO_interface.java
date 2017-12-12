package com.report_store.model;

import java.util.List;

public interface ReportStoreDAO_interface {
//--------------Peiiun------------	
	public void insert(ReportStoreVO reportStoreVO);	
	public void stoUpdate(ReportStoreVO reportStoreVO);
	public List<ReportStoreVO> stoGetAllRpt(String sto_num);	
	public ReportStoreVO getRptbyPrimaryKey(String rpt_snum);
	public List<ReportStoreVO> getAll();
	public void stfUpdate(ReportStoreVO reportStoreVO);
//-----------end Peiiun-----------
	
//----------Shawn----------------
	public void insertReportStoreComment(ReportStoreVO reportStoreVO);
	public void insertReportStore(ReportStoreVO reportStoreVO);
	public List<List> getMemContentAll();
	public List<List> getMemContentByStatus(String status); 
	public void updateReportStore(ReportStoreVO reportStoreVO);
	public List<ReportStoreVO> getReportStoreMem(String sto_num);
	public List<ReportStoreVO> getReportStoreCom(String sto_num);
	
	public List<ReportStoreVO> getStoCommentAll();
	public List<ReportStoreVO> getStoCommentByStatus(String status);
//---------end Shawn----------------	

//-----------Eric------------------
	
//	sert(ReportStoreVO reportStoreVO);
//
//	public void stoUpdate(ReportStoreVO reportStoreVO);
//
//	public List<ReportStoreVO> stoGetAllRpt(String sto_num);
//
//	public ReportStoreVO getRptbyPrimaryKey(String rpt_snum);
//
//	public List<ReportStoreVO> getAll();
//
//	public void stfUpdate(ReportStoreVO reportStoreVO);

	public void insertReport(ReportStoreVO reportStoreVO);
	
//---------end Eric------------------------------	
}
