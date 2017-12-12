package com.report_store.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.member_profile.model.MemberProfileVO;
import com.store_profile.model.StoreProfileVO;

public class ReportStoreDAO implements ReportStoreDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
//---------Peiiun------------	
	//會員檢舉店家or會員or評論
	private static final String INSERT = 
			"INSERT INTO REPORT_STORE (RPT_SNUM,STO_NUM,MEM_NUM,COM_NUM,RPT_TIME,STATUS)"
			+ " VALUES ('RS'||LPAD(to_char(SEQ_RPT_SNUM.NEXTVAL),10,'0'),?,?,?,CURRENT_TIMESTAMP,?)";
	//會員修改滿意度
	private static final String STORE_UPDATE = 
				"UPDATE REPORT_STORE SET SCORE=? WHERE RPT_SNUM=?";
	//會員查詢自己的檢舉紀錄
	private static final String STORE_GET_ALL_RPT = 
				"SELECT * FROM REPORT_STORE WHERE STO_NUM=?";
	//選一個檢舉紀錄
	private static final String GET_ONE_RPT=
				"SELECT * FROM REPORT_STORE WHERE RPT_SNUM=?";
	//後台查詢所有會員檢舉紀錄
	private static final String GET_ALL=
				"SELECT * FROM REPORT_STORE ORDER BY RPT_SNUM DESC";
	//後台更新處理狀態
	private static final String STAFF_UPDATE = 
			"UPDATE REPORT_STORE SET STATUS=?, STAFF_NUM=?, WAY=? WHERE RPT_SNUM=?";	
	
	
	
	
	@Override
	public void insert(ReportStoreVO reportStoreVO) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		try {
			con = ds.getConnection();			
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setString(1, reportStoreVO.getSto_num());
			pstmt.setString(2, reportStoreVO.getMem_num());
			pstmt.setString(3, reportStoreVO.getCom_num());
			pstmt.setString(4, reportStoreVO.getStatus());//預設待處理
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}				
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}		
	}

	@Override
	public void stoUpdate(ReportStoreVO reportStoreVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(STORE_UPDATE);
			
			pstmt.setInt(1, reportStoreVO.getScore());
			pstmt.setString(2, reportStoreVO.getRpt_snum());
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}	
	}

	@Override
	public List<ReportStoreVO> stoGetAllRpt(String sto_num) {
		ReportStoreVO reportStoreVO = null;
		List<ReportStoreVO> list = new ArrayList<ReportStoreVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(STORE_GET_ALL_RPT);
			
			pstmt.setString(1,sto_num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				reportStoreVO = new ReportStoreVO();
				reportStoreVO.setRpt_snum(rs.getString("rpt_snum"));
				reportStoreVO.setSto_num(rs.getString("sto_num"));
				reportStoreVO.setMem_num(rs.getString("mem_num"));
				reportStoreVO.setCom_num(rs.getString("com_num"));
				reportStoreVO.setRpt_time(rs.getDate("rpt_time"));
				reportStoreVO.setStatus(rs.getString("status"));
				reportStoreVO.setStaff_num(rs.getString("staff_num"));
				reportStoreVO.setScore(rs.getInt("score"));
				reportStoreVO.setWay(rs.getString("way"));
				list.add(reportStoreVO);
			}
		
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public ReportStoreVO getRptbyPrimaryKey(String rpt_snum) {
		ReportStoreVO reportStoreVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_RPT);
			pstmt.setString(1, rpt_snum);
			rs = pstmt.executeQuery();
			while(rs.next()){
				reportStoreVO= new ReportStoreVO();
				reportStoreVO.setRpt_snum(rs.getString("rpt_snum"));
				reportStoreVO.setSto_num(rs.getString("sto_num"));
				reportStoreVO.setMem_num(rs.getString("mem_num"));
				reportStoreVO.setCom_num(rs.getString("com_num"));
				reportStoreVO.setRpt_time(rs.getDate("rpt_time"));
				reportStoreVO.setStatus(rs.getString("status"));
				reportStoreVO.setStaff_num(rs.getString("staff_num"));
				reportStoreVO.setScore(rs.getInt("score"));
				reportStoreVO.setWay(rs.getString("way"));
			}
		
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return reportStoreVO;
	}

	@Override
	public List<ReportStoreVO> getAll() {
		ReportStoreVO reportStoreVO = null;
		List<ReportStoreVO> list = new ArrayList<ReportStoreVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);			
			rs = pstmt.executeQuery();
			while(rs.next()){
				reportStoreVO = new ReportStoreVO();
				reportStoreVO.setRpt_snum(rs.getString("rpt_snum"));
				reportStoreVO.setSto_num(rs.getString("sto_num"));
				reportStoreVO.setMem_num(rs.getString("mem_num"));
				reportStoreVO.setCom_num(rs.getString("com_num"));
				reportStoreVO.setRpt_time(rs.getDate("rpt_time"));
				reportStoreVO.setStatus(rs.getString("status"));
				reportStoreVO.setStaff_num(rs.getString("staff_num"));
				reportStoreVO.setScore(rs.getInt("score"));
				reportStoreVO.setWay(rs.getString("way"));
				list.add(reportStoreVO);
			}
		
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public void stfUpdate(ReportStoreVO reportStoreVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(STAFF_UPDATE);
			
			pstmt.setString(1, reportStoreVO.getStatus());
			pstmt.setString(2, reportStoreVO.getStaff_num());
			pstmt.setString(3, reportStoreVO.getWay());
			pstmt.setString(4, reportStoreVO.getRpt_snum());
			pstmt.executeUpdate();
				
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
//---------end Peiiun------------		

	
	
//--------Shawn--------------
	private static final String INSERT_REPORT_STORE_COMMENT = 
			"INSERT INTO REPORT_STORE (rpt_snum, sto_num, com_num, rpt_time, status, content) VALUES ('RS' || TRIM(TO_CHAR(SEQ_RPT_SNUM.NEXTVAL, '0000000000')), ?, ?, ?, ?, ?)";
	private static final String INSERT_REPORT_STORE = 
			"INSERT INTO REPORT_STORE (rpt_snum, sto_num, mem_num, rpt_time, status, ) VALUES (?, ?, ?, ?, ?)";
	private static final String UPDATE_REPORT_STORE = 
			"UPDATE REPORT_STORE set status=?, staff_num=?, way=? WHERE rpt_snum=?";
	private static final String GET_MEMBER_CONTENT_ALL = 
			"SELECT RS.RPT_SNUM, RS.STO_NUM, RS.MEM_NUM, RS.RPT_TIME, RS.STATUS, "
			+ "RS.STAFF_NUM, RS.WAY, RS.CONTENT, STO.STO_NAME, STO.STO_STATUS, MP.MEM_NAME, MP.STATUS  "
			+ "FROM REPORT_STORE RS JOIN STORE_PROFILE STO ON RS.STO_NUM=STO.STO_NUM JOIN MEMBER_PROFILE "
			+ "MP ON RS.MEM_NUM=MP.MEM_NUM WHERE COM_NUM IS NULL ORDER BY RPT_TIME DESC";
	private static final String GET_MEMBER_CONTENT_STATUS = 
			"SELECT RS.RPT_SNUM, RS.STO_NUM, RS.MEM_NUM, RS.RPT_TIME, RS.STATUS, "
			+ "RS.STAFF_NUM, RS.WAY, RS.CONTENT, STO.STO_NAME, STO.STO_STATUS, MP.MEM_NAME, MP.STATUS "
			+ "FROM REPORT_STORE RS JOIN STORE_PROFILE STO ON RS.STO_NUM=STO.STO_NUM JOIN MEMBER_PROFILE "
			+ "MP ON RS.MEM_NUM=MP.MEM_NUM WHERE COM_NUM IS NULL AND RS.STATUS=? ORDER BY RPT_TIME DESC";
	private static final String GET_REPORT_STORE_MEM =
			"SELECT * FROM REPORT_STORE WHERE STO_NUM=? AND COM_NUM IS NULL ORDER BY RPT_TIME DESC";
	private static final String GET_REPORT_STORE_COM =
			"SELECT * FROM REPORT_STORE WHERE STO_NUM=? AND MEM_NUM IS NULL ORDER BY RPT_TIME DESC";
	private static final String GET_STO_COMMENT_ALL =
			"SELECT * FROM REPORT_STORE WHERE COM_NUM IS　NOT NULL ORDER BY RPT_TIME DESC";
	private static final String GET_STO_COMMENT_STATUS =
			"SELECT * FROM REPORT_STORE WHERE COM_NUM IS NOT NULL AND STATUS=? ORDER BY RPT_TIME DESC";
	
	@Override
	public void insertReportStoreComment(ReportStoreVO reportStoreVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_REPORT_STORE_COMMENT);

			pstmt.setString(1, reportStoreVO.getSto_num());
			pstmt.setString(2, reportStoreVO.getCom_num());
			pstmt.setTimestamp(3, new java.sql.Timestamp( reportStoreVO.getRpt_time().getTime()));
			pstmt.setString(4, reportStoreVO.getStatus());
			pstmt.setString(5, reportStoreVO.getContent());
			
			

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}
	
	@Override
	public void insertReportStore(ReportStoreVO reportStoreVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_REPORT_STORE);

			pstmt.setString(1, reportStoreVO.getRpt_snum());
			pstmt.setString(2, reportStoreVO.getSto_num());
			pstmt.setString(3, reportStoreVO.getMem_num());
			pstmt.setTimestamp(4, new java.sql.Timestamp( reportStoreVO.getRpt_time().getTime()));
			pstmt.setString(5, reportStoreVO.getStatus());
			

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public List<List> getMemContentAll() {
		List<Object> listAll = null;
		List<List> list = new ArrayList<List>();
		StoreProfileVO storeProfileVO = null;
		MemberProfileVO memberProfileVO = null;
		ReportStoreVO reportStoreVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEMBER_CONTENT_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				listAll = new ArrayList<>();
				reportStoreVO = new ReportStoreVO();
				storeProfileVO = new StoreProfileVO();
				memberProfileVO = new MemberProfileVO();
				reportStoreVO.setRpt_snum(rs.getString("RPT_SNUM"));
				reportStoreVO.setSto_num(rs.getString("STO_NUM"));
				reportStoreVO.setMem_num(rs.getString("MEM_NUM"));
				reportStoreVO.setRpt_time(rs.getDate("RPT_TIME"));
				reportStoreVO.setStatus(rs.getString("STATUS"));
				reportStoreVO.setStaff_num(rs.getString("STAFF_NUM"));
				reportStoreVO.setWay(rs.getString("WAY"));
				reportStoreVO.setContent(rs.getString("CONTENT"));
				storeProfileVO.setSto_name(rs.getString("STO_NAME"));
				storeProfileVO.setSto_status(rs.getString("STO_STATUS"));
				memberProfileVO.setMem_name(rs.getString("MEM_NAME"));
				memberProfileVO.setStatus(rs.getString("STATUS"));
				listAll.add(reportStoreVO);
				listAll.add(storeProfileVO);
				listAll.add(memberProfileVO);
				list.add(listAll);
				
				
			}

			
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public void updateReportStore(ReportStoreVO reportStoreVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_REPORT_STORE);

			pstmt.setString(1, reportStoreVO.getStatus());
			pstmt.setString(2, reportStoreVO.getStaff_num());
			pstmt.setString(3, reportStoreVO.getWay());
			pstmt.setString(4, reportStoreVO.getRpt_snum());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		
	}

	@Override
	public List<List> getMemContentByStatus(String status) {
		List<Object> listAll = null;
		List<List> list = new ArrayList<List>();
		StoreProfileVO storeProfileVO = null;
		MemberProfileVO memberProfileVO = null;
		ReportStoreVO reportStoreVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEMBER_CONTENT_STATUS);
			pstmt.setString(1, status);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				listAll = new ArrayList<>();
				reportStoreVO = new ReportStoreVO();
				storeProfileVO = new StoreProfileVO();
				memberProfileVO = new MemberProfileVO();
				reportStoreVO.setRpt_snum(rs.getString("RPT_SNUM"));
				reportStoreVO.setSto_num(rs.getString("STO_NUM"));
				reportStoreVO.setMem_num(rs.getString("MEM_NUM"));
				reportStoreVO.setRpt_time(rs.getDate("RPT_TIME"));
				reportStoreVO.setStatus(rs.getString("STATUS"));
				reportStoreVO.setStaff_num(rs.getString("STAFF_NUM"));
				reportStoreVO.setWay(rs.getString("WAY"));
				reportStoreVO.setContent(rs.getString("CONTENT"));
				storeProfileVO.setSto_name(rs.getString("STO_NAME"));
				storeProfileVO.setSto_status(rs.getString("STO_STATUS"));
				memberProfileVO.setMem_name(rs.getString("MEM_NAME"));
				memberProfileVO.setStatus(rs.getString("STATUS"));
				listAll.add(reportStoreVO);
				listAll.add(storeProfileVO);
				listAll.add(memberProfileVO);
				list.add(listAll);
				
				
			}

			
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<ReportStoreVO> getReportStoreMem(String sto_num) {
		ReportStoreVO reportStoreVO = null;
		List<ReportStoreVO> listReportStoreMem = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_REPORT_STORE_MEM);
			pstmt.setString(1, sto_num);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				reportStoreVO = new ReportStoreVO();
				reportStoreVO.setSto_num(rs.getString("STO_NUM"));
				reportStoreVO.setMem_num(rs.getString("MEM_NUM"));
				reportStoreVO.setRpt_time(rs.getDate("RPT_TIME"));
				reportStoreVO.setStatus(rs.getString("STATUS"));
				reportStoreVO.setWay(rs.getString("WAY"));
				reportStoreVO.setContent(rs.getString("CONTENT"));
				listReportStoreMem.add(reportStoreVO);
				
			}

			
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return listReportStoreMem;
	}

	@Override
	public List<ReportStoreVO> getReportStoreCom(String sto_num) {
		ReportStoreVO reportStoreVO = null;
		List<ReportStoreVO> listReportStoreMem = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_REPORT_STORE_COM);
			pstmt.setString(1, sto_num);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				reportStoreVO = new ReportStoreVO();
				reportStoreVO.setSto_num(rs.getString("STO_NUM"));
				reportStoreVO.setCom_num(rs.getString("COM_NUM"));
				reportStoreVO.setRpt_time(rs.getDate("RPT_TIME"));
				reportStoreVO.setStatus(rs.getString("STATUS"));
				reportStoreVO.setWay(rs.getString("WAY"));
				reportStoreVO.setContent(rs.getString("CONTENT"));
				listReportStoreMem.add(reportStoreVO);
				
			}

			
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return listReportStoreMem;
	}
	
	@Override
	public List<ReportStoreVO> getStoCommentAll() {
		List<ReportStoreVO> listReportStore = new ArrayList<ReportStoreVO>();
		ReportStoreVO reportStoreVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_STO_COMMENT_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				reportStoreVO = new ReportStoreVO();
				reportStoreVO.setRpt_snum(rs.getString("RPT_SNUM"));
				reportStoreVO.setSto_num(rs.getString("STO_NUM"));
				reportStoreVO.setCom_num(rs.getString("COM_NUM"));
				reportStoreVO.setRpt_time(rs.getDate("RPT_TIME"));
				reportStoreVO.setStatus(rs.getString("STATUS"));
				reportStoreVO.setWay(rs.getString("WAY"));
				reportStoreVO.setContent(rs.getString("CONTENT"));
				listReportStore.add(reportStoreVO);
			}

			
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return listReportStore;
	}

	@Override
	public List<ReportStoreVO> getStoCommentByStatus(String status) {
		List<ReportStoreVO> listReportStoreComment = new ArrayList<ReportStoreVO>();
		ReportStoreVO reportStoreVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_STO_COMMENT_STATUS);
			pstmt.setString(1, status);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				reportStoreVO = new ReportStoreVO();
				reportStoreVO.setRpt_snum(rs.getString("RPT_SNUM"));
				reportStoreVO.setSto_num(rs.getString("STO_NUM"));
				reportStoreVO.setCom_num(rs.getString("COM_NUM"));
				reportStoreVO.setRpt_time(rs.getDate("RPT_TIME"));
				reportStoreVO.setStatus(rs.getString("STATUS"));
				reportStoreVO.setWay(rs.getString("WAY"));
				reportStoreVO.setContent(rs.getString("CONTENT"));
				listReportStoreComment.add(reportStoreVO);
			}

			
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return listReportStoreComment;
	}
	
	
//-------end Shawn-------------	

	
//-----------Eric---------------
	//會員檢舉店家or會員or評論
//		private static final String INSERT = 
//				"INSERT INTO REPORT_STORE (RPT_SNUM,STO_NUM,MEM_NUM,COM_NUM,RPT_TIME,STATUS)"
//				+ " VALUES ('RS'||LPAD(to_char(SEQ_RPT_SNUM.NEXTVAL),10,'0'),?,?,?,CURRENT_TIMESTAMP,?)";
//		//會員修改滿意度
//		private static final String STORE_UPDATE = 
//					"UPDATE REPORT_STORE SET SCORE=? WHERE RPT_SNUM=?";
//		//會員查詢自己的檢舉紀錄
//		private static final String STORE_GET_ALL_RPT = 
//					"SELECT * FROM REPORT_STORE WHERE STO_NUM=?";
//		//選一個檢舉紀錄
//		private static final String GET_ONE_RPT=
//					"SELECT * FROM REPORT_STORE WHERE RPT_SNUM=?";
//		//後台查詢所有會員檢舉紀錄
//		private static final String GET_ALL=
//					"SELECT * FROM REPORT_STORE ORDER BY RPT_SNUM DESC";
//		//後台更新處理狀態
//		private static final String STAFF_UPDATE = 
//				"UPDATE REPORT_STORE SET STATUS=?, STAFF_NUM=?, WAY=? WHERE RPT_SNUM=?";	
//		
		//店家新增一筆檢舉
		private static final String INSERT_REPORT = 
				"INSERT INTO REPORT_STORE (RPT_SNUM,STO_NUM,MEM_NUM,COM_NUM,RPT_TIME,CONTENT)"
				+ " VALUES ('RS'||LPAD(to_char(SEQ_RPT_SNUM.NEXTVAL),10,'0'),?,?,?,CURRENT_TIMESTAMP,?)";
		
		
		
		//店家新增一筆檢舉
		@Override
		public void insertReport(ReportStoreVO reportStoreVO) {
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				con= ds.getConnection();
				pstmt = con.prepareStatement(INSERT_REPORT);
				pstmt.setString(1, reportStoreVO.getSto_num());
				pstmt.setString(2,reportStoreVO.getMem_num());
				pstmt.setString(3, reportStoreVO.getCom_num());
				pstmt.setString(4, reportStoreVO.getContent());
				pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(pstmt!=null){
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(con!=null){
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
			
		}
		
		
		
		
//		@Override
//		public void insert(ReportStoreVO reportStoreVO) {
//			Connection con = null;
//			PreparedStatement pstmt = null;	
//			try {
//				con = ds.getConnection();			
//				pstmt = con.prepareStatement(INSERT);
//				
//				pstmt.setString(1, reportStoreVO.getSto_num());
//				pstmt.setString(2, reportStoreVO.getMem_num());
//				pstmt.setString(3, reportStoreVO.getCom_num());
//				pstmt.setString(4, reportStoreVO.getStatus());//預設待處理
//				pstmt.executeUpdate();
//				
//			} catch (SQLException se) {
//				throw new RuntimeException("A database error occured. "
//						+ se.getMessage());
//			} finally {
//				if(pstmt != null){
//					try {
//						pstmt.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}				
//				}
//				if(con != null){
//					try {
//						con.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//			}		
//		}
//
//		@Override
//		public void stoUpdate(ReportStoreVO reportStoreVO) {
//			Connection con = null;
//			PreparedStatement pstmt = null;
//			try {
//				
//				con = ds.getConnection();
//				pstmt = con.prepareStatement(STORE_UPDATE);
//				
//				pstmt.setInt(1, reportStoreVO.getScore());
//				pstmt.setString(2, reportStoreVO.getRpt_snum());
//				pstmt.executeUpdate();
//				
//			} catch (SQLException se) {
//				throw new RuntimeException("A database error occured. "
//						+ se.getMessage());
//			} finally {
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (Exception e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}	
//		}
//
//		@Override
//		public List<ReportStoreVO> stoGetAllRpt(String sto_num) {
//			ReportStoreVO reportStoreVO = null;
//			List<ReportStoreVO> list = new ArrayList<ReportStoreVO>();
//			Connection con = null;
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//			try {
//
//				con = ds.getConnection();
//				pstmt = con.prepareStatement(STORE_GET_ALL_RPT);
//				
//				pstmt.setString(1,sto_num);
//				rs = pstmt.executeQuery();
//				while(rs.next()){
//					reportStoreVO = new ReportStoreVO();
//					reportStoreVO.setRpt_snum(rs.getString("rpt_snum"));
//					reportStoreVO.setSto_num(rs.getString("sto_num"));
//					reportStoreVO.setMem_num(rs.getString("mem_num"));
//					reportStoreVO.setCom_num(rs.getString("com_num"));
//					reportStoreVO.setRpt_time(rs.getDate("rpt_time"));
//					reportStoreVO.setStatus(rs.getString("status"));
//					reportStoreVO.setStaff_num(rs.getString("staff_num"));
//					reportStoreVO.setScore(rs.getInt("score"));
//					reportStoreVO.setWay(rs.getString("way"));
//					list.add(reportStoreVO);
//				}
//			
//			} catch (SQLException se) {
//				throw new RuntimeException("A database error occured. "
//						+ se.getMessage());
//				// Clean up JDBC resources
//			} finally {
//				if (rs != null) {
//					try {
//						rs.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (Exception e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
//			return list;
//		}
//
//		@Override
//		public ReportStoreVO getRptbyPrimaryKey(String rpt_snum) {
//			ReportStoreVO reportStoreVO = null;
//			Connection con = null;
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//			try{
//				con = ds.getConnection();
//				pstmt = con.prepareStatement(GET_ONE_RPT);
//				pstmt.setString(1, rpt_snum);
//				rs = pstmt.executeQuery();
//				while(rs.next()){
//					reportStoreVO= new ReportStoreVO();
//					reportStoreVO.setRpt_snum(rs.getString("rpt_snum"));
//					reportStoreVO.setSto_num(rs.getString("sto_num"));
//					reportStoreVO.setMem_num(rs.getString("mem_num"));
//					reportStoreVO.setCom_num(rs.getString("com_num"));
//					reportStoreVO.setRpt_time(rs.getDate("rpt_time"));
//					reportStoreVO.setStatus(rs.getString("status"));
//					reportStoreVO.setStaff_num(rs.getString("staff_num"));
//					reportStoreVO.setScore(rs.getInt("score"));
//					reportStoreVO.setWay(rs.getString("way"));
//				}
//			
//			} catch (SQLException se) {
//				throw new RuntimeException("A database error occured. "
//						+ se.getMessage());
//				// Clean up JDBC resources
//			} finally {
//				if (rs != null) {
//					try {
//						rs.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (Exception e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
//			return reportStoreVO;
//		}
//
//		@Override
//		public List<ReportStoreVO> getAll() {
//			ReportStoreVO reportStoreVO = null;
//			List<ReportStoreVO> list = new ArrayList<ReportStoreVO>();
//			Connection con = null;
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//			try {
//
//				con = ds.getConnection();
//				pstmt = con.prepareStatement(GET_ALL);			
//				rs = pstmt.executeQuery();
//				while(rs.next()){
//					reportStoreVO = new ReportStoreVO();
//					reportStoreVO.setRpt_snum(rs.getString("rpt_snum"));
//					reportStoreVO.setSto_num(rs.getString("sto_num"));
//					reportStoreVO.setMem_num(rs.getString("mem_num"));
//					reportStoreVO.setCom_num(rs.getString("com_num"));
//					reportStoreVO.setRpt_time(rs.getDate("rpt_time"));
//					reportStoreVO.setStatus(rs.getString("status"));
//					reportStoreVO.setStaff_num(rs.getString("staff_num"));
//					reportStoreVO.setScore(rs.getInt("score"));
//					reportStoreVO.setWay(rs.getString("way"));
//					list.add(reportStoreVO);
//				}
//			
//			} catch (SQLException se) {
//				throw new RuntimeException("A database error occured. "
//						+ se.getMessage());
//				// Clean up JDBC resources
//			} finally {
//				if (rs != null) {
//					try {
//						rs.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (Exception e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
//			return list;
//		}
//
//		@Override
//		public void stfUpdate(ReportStoreVO reportStoreVO) {
//			Connection con = null;
//			PreparedStatement pstmt = null;
//			try {
//				
//				con = ds.getConnection();
//				pstmt = con.prepareStatement(STAFF_UPDATE);
//				
//				pstmt.setString(1, reportStoreVO.getStatus());
//				pstmt.setString(2, reportStoreVO.getStaff_num());
//				pstmt.setString(3, reportStoreVO.getWay());
//				pstmt.setString(4, reportStoreVO.getRpt_snum());
//				pstmt.executeUpdate();
//					
//			} catch (SQLException se) {
//				throw new RuntimeException("A database error occured. "
//						+ se.getMessage());
//			} finally {
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (Exception e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
//		}
		
//-----------end Eric----------------------		
}
