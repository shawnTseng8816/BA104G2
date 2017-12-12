package com.report_member.model;
import java.util.List;

public interface ReportMemberDAO_interface {
	
//---------Peiiun-------------	
	public void insert(ReportMemberVO reportMemberVO);	
	public void memUpdate(ReportMemberVO reportMemberVO);
	public List<ReportMemberVO> memGetAllRpt(String mem_num);	
	public ReportMemberVO getRptbyPrimaryKey(String rpt_mnum);
	public List<ReportMemberVO> getAll();
	public void stfUpdate(ReportMemberVO reportMemberVO);
//-----------end Peiiun------------
	
//-----------Shawn--------------
	public void insertReportMemberComment(ReportMemberVO reportMemberVO);
	public void insertReportMember(ReportMemberVO reportMemberVO);
	public List<List> getStoContentAll(); 
	public List<List> getMemContentAll(); 
	public List<List> getStoContentByStatus(String status);
	public List<List> getMemContentByStatus(String status);
	public void updateReportMember(ReportMemberVO reportMemberVO);
	public List<ReportMemberVO> getReportMemberSto(String mem_num);
	public List<ReportMemberVO> getReportMemberMem(String mem_num);
	public List<ReportMemberVO> getReportMemberCom(String mem_num);
	public List<ReportMemberVO> getMemCommentAll();
	
	public List<ReportMemberVO> getMemCommentByStatus(String status);

//---------end Shawn--------------	
	
}
