package com.report_member.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.member_profile.model.MemberProfileService;
import com.member_profile.model.MemberProfileVO;
import com.store_profile.model.StoreProfileVO;


public class ReportMemberDAO implements ReportMemberDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}	
	
	
//---------------Peiiun---------------------	
	//會員檢舉店家or會員or評論
	private static final String INSERT = 
			"INSERT INTO REPORT_MEMBER (RPT_MNUM,MEM_NUM,MEM_NUM2,STO_NUM,COM_NUM,RPT_TIME,STATUS)"
			+ " VALUES ('RM'||LPAD(to_char(SEQ_RPT_MNUM.NEXTVAL),10,'0'),?,?,?,?, CURRENT_TIMESTAMP,?)";
	//會員修改滿意度
	private static final String MEMBER_UPDATE = 
				"UPDATE REPORT_MEMBER SET SCORE=? WHERE RPT_MNUM=?";
	//會員查詢自己的檢舉紀錄
	private static final String MEMBER_GET_ALL_RPT = 
				"SELECT * FROM REPORT_MEMBER WHERE MEM_NUM=?";
	//選一個檢舉紀錄
	private static final String GET_ONE=
				"SELECT * FROM REPORT_MEMBER WHERE RPT_MNUM=?";
	//後台查詢所有會員檢舉紀錄
	private static final String GET_ALL=
				"SELECT * FROM REPORT_MEMBER ORDER BY RPT_TIME DESC";
	//後台更新處理狀態
	private static final String STAFF_UPDATE = 
			"UPDATE REPORT_MEMBER SET STATUS=?, STAFF_NUM=?, WAY=? WHERE RPT_MNUM=?";
	
	@Override
	public void insert(ReportMemberVO reportMemberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setString(1,reportMemberVO.getMem_num());
			pstmt.setString(2, reportMemberVO.getMem_num2());
			pstmt.setString(3, reportMemberVO.getSto_num());
			pstmt.setString(4, reportMemberVO.getCom_num());
			pstmt.setString(5, reportMemberVO.getStatus());//預設待處理
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
	public void memUpdate(ReportMemberVO reportMemberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {			
			con = ds.getConnection();
			pstmt = con.prepareStatement(MEMBER_UPDATE);
			
			pstmt.setDouble(1,reportMemberVO.getScore());
			pstmt.setString(2, reportMemberVO.getRpt_mnum());
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
	public List<ReportMemberVO> memGetAllRpt(String mem_num) {
		ReportMemberVO reportMemberVO = null;
		List<ReportMemberVO> list = new ArrayList<ReportMemberVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(MEMBER_GET_ALL_RPT);
			
			pstmt.setString(1,mem_num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				reportMemberVO = new ReportMemberVO();
				reportMemberVO.setRpt_mnum(rs.getString("rpt_mnum"));
				reportMemberVO.setMem_num(rs.getString("mem_num"));
				reportMemberVO.setMem_num2(rs.getString("mem_num2"));
				reportMemberVO.setSto_num(rs.getString("sto_num"));
				reportMemberVO.setCom_num(rs.getString("com_num"));
				reportMemberVO.setRpt_time(rs.getDate("rpt_time"));
				reportMemberVO.setStatus(rs.getString("status"));
				reportMemberVO.setStaff_num(rs.getString("staff_num"));
				reportMemberVO.setScore(rs.getInt("score"));
				reportMemberVO.setWay(rs.getString("way"));
				list.add(reportMemberVO);
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
	public ReportMemberVO getRptbyPrimaryKey(String rpt_mnum) {
		ReportMemberVO reportMemberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);
			pstmt.setString(1, rpt_mnum);
			rs = pstmt.executeQuery();
			while(rs.next()){
				reportMemberVO = new ReportMemberVO();
				reportMemberVO.setRpt_mnum(rs.getString("rpt_mnum"));
				reportMemberVO.setMem_num(rs.getString("mem_num"));
				reportMemberVO.setMem_num2(rs.getString("mem_num2"));
				reportMemberVO.setSto_num(rs.getString("sto_num"));
				reportMemberVO.setCom_num(rs.getString("com_num"));
				reportMemberVO.setRpt_time(rs.getDate("rpt_time"));
				reportMemberVO.setStatus(rs.getString("status"));
				reportMemberVO.setStaff_num(rs.getString("staff_num"));
				reportMemberVO.setScore(rs.getInt("score"));
				reportMemberVO.setWay(rs.getString("way"));
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
		return reportMemberVO;
	}

	@Override
	public List<ReportMemberVO> getAll() {
		ReportMemberVO reportMemberVO = null;
		List<ReportMemberVO> list = new ArrayList<ReportMemberVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);			
			rs = pstmt.executeQuery();
			while(rs.next()){
				reportMemberVO = new ReportMemberVO();
				reportMemberVO.setRpt_mnum(rs.getString("rpt_mnum"));
				reportMemberVO.setMem_num(rs.getString("mem_num"));
				reportMemberVO.setMem_num2(rs.getString("mem_num2"));
				reportMemberVO.setSto_num(rs.getString("sto_num"));
				reportMemberVO.setCom_num(rs.getString("com_num"));
				reportMemberVO.setRpt_time(rs.getDate("rpt_time"));
				reportMemberVO.setStatus(rs.getString("status"));
				reportMemberVO.setStaff_num(rs.getString("staff_num"));
				reportMemberVO.setScore(rs.getInt("score"));
				reportMemberVO.setWay(rs.getString("way"));
				list.add(reportMemberVO);
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
	public void stfUpdate(ReportMemberVO reportMemberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(STAFF_UPDATE);
			
			pstmt.setString(1, reportMemberVO.getStatus());
			pstmt.setString(2, reportMemberVO.getStaff_num());
			pstmt.setString(3, reportMemberVO.getWay());
			pstmt.setString(4, reportMemberVO.getRpt_mnum());
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
//---------------end Peiiun---------------------	

//--------------Shawn---------------------
	private static final String INSERT_REPORT_MEMBER_COMMENT = 
			"INSERT INTO REPORT_MEMBER (rpt_mnum, mem_num, com_num, rpt_time, status, content) VALUES ('RM' || TRIM(TO_CHAR(SEQ_RPT_SNUM.NEXTVAL, '0000000000')), ?, ?, ?, ?, ?)";
	private static final String INSERT_REPORT_MEMBER = 
			"INSERT INTO REPORT_MEMBER (rpt_mnum, mem_num, mem_num2, rpt_time, status) VALUES (?, ?, ?, ?, ?)";
	private static final String GET_MEMBER_PROFILE =
			"SELECT RM.RPT_MNUM, RM.MEM_NUM, RM.MEM_NUM2, RM.RPT_TIME, RM.CONTENT, RM.STATUS, RM.WAY ,MP.MEM_NAME "
			+ "FROM REPORT_MEMBER RM "
			+ "JOIN MEMBER_PROFILE MP ON RM.MEM_NUM=MP.MEM_NUM "
			+ "WHERE STO_NUM IS NULL AND RM.COM_NUM IS NULL ORDER BY RPT_TIME DESC";
	private static final String GET_MEMBER_PROFILE_STATUS =
			"SELECT RM.RPT_MNUM, RM.MEM_NUM, RM.MEM_NUM2, RM.RPT_TIME, RM.CONTENT, RM.STATUS, RM.WAY ,MP.MEM_NAME "
			+ "FROM REPORT_MEMBER RM "
			+ "JOIN MEMBER_PROFILE MP ON RM.MEM_NUM=MP.MEM_NUM "
			+ "WHERE STO_NUM IS NULL AND RM.COM_NUM IS NULL AND RM.STATUS=? ORDER BY RPT_TIME DESC";
	private static final String GET_STORE_PROFILE_STATUS =
			"SELECT RM.RPT_MNUM, RM.MEM_NUM, RM.STO_NUM, RM.RPT_TIME, RM.CONTENT, RM.STATUS, RM.WAY, STO.STO_NAME,STO.STO_STATUS, MP.MEM_NAME "
			+ "FROM REPORT_MEMBER RM "
			+ "JOIN STORE_PROFILE STO ON RM.STO_NUM=STO.STO_NUM "
			+ "JOIN MEMBER_PROFILE MP ON RM.MEM_NUM=MP.MEM_NUM "
			+ "WHERE COM_NUM IS NULL AND RM.STATUS=? ORDER BY RPT_TIME DESC";		
	private static final String GET_STORE_PROFILE = 
			"SELECT RM.RPT_MNUM, RM.MEM_NUM, RM.STO_NUM, RM.RPT_TIME, RM.CONTENT, RM.STATUS, RM.WAY, STO.STO_NAME,STO.STO_STATUS, MP.MEM_NAME "
			+ "FROM REPORT_MEMBER RM JOIN STORE_PROFILE STO ON RM.STO_NUM=STO.STO_NUM "
			+ "JOIN MEMBER_PROFILE MP ON RM.MEM_NUM=MP.MEM_NUM "
			+ "WHERE COM_NUM IS NULL ORDER BY RPT_TIME DESC" ;
	private static final String UPDATE_REPORT_MEMBER = 
			"UPDATE REPORT_MEMBER set status=?, staff_num=?, way=? WHERE rpt_mnum=?";
	private static final String GET_REPORT_MEMBER_STO =
			"SELECT * FROM REPORT_MEMBER WHERE MEM_NUM=? AND COM_NUM IS NULL AND MEM_NUM2 IS NULL ORDER BY RPT_TIME DESC";
	private static final String GET_REPORT_MEMBER_MEM =
			"SELECT * FROM REPORT_MEMBER WHERE MEM_NUM=? AND STO_NUM IS NULL AND COM_NUM IS NULL ORDER BY RPT_TIME DESC";
	private static final String GET_REPORT_MEMBER_COM =
			"SELECT * FROM REPORT_MEMBER WHERE MEM_NUM=? AND STO_NUM IS NULL AND MEM_NUM2 IS NULL ORDER BY RPT_TIME DESC";

	private static final String GET_MEM_COMMENT_ALL =
			"SELECT * FROM REPORT_MEMBER WHERE COM_NUM IS　NOT NULL ORDER BY RPT_TIME DESC";
	private static final String GET_MEM_COMMENT_STATUS =
			"SELECT * FROM REPORT_MEMBER WHERE COM_NUM IS　NOT NULL AND STATUS=? ORDER BY RPT_TIME DESC";
	
	@Override
	public void insertReportMemberComment(ReportMemberVO reportMemberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_REPORT_MEMBER_COMMENT);

			pstmt.setString(1, reportMemberVO.getMem_num());
			pstmt.setString(2, reportMemberVO.getCom_num());
			pstmt.setTimestamp(3, new java.sql.Timestamp( reportMemberVO.getRpt_time().getTime()));
			pstmt.setString(4, reportMemberVO.getStatus());
			pstmt.setString(5, reportMemberVO.getContent());

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
	public void insertReportMember(ReportMemberVO reportMemberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_REPORT_MEMBER);

			pstmt.setString(1, reportMemberVO.getRpt_mnum());
			pstmt.setString(2, reportMemberVO.getMem_num());
			pstmt.setString(3, reportMemberVO.getMem_num2());
			pstmt.setTimestamp(4, new java.sql.Timestamp( reportMemberVO.getRpt_time().getTime()));
			pstmt.setString(5, reportMemberVO.getStatus());
			

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
	public List<List> getStoContentAll(){
		List<Object> listAll = null;
		List<List> list = new ArrayList<List>();
		StoreProfileVO storeProfileVO = null;
		MemberProfileVO memberProfileVO = null;
		ReportMemberVO reportMemberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_STORE_PROFILE);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				listAll = new ArrayList<>();
				reportMemberVO = new ReportMemberVO();
				storeProfileVO = new StoreProfileVO();
				memberProfileVO = new MemberProfileVO();
				reportMemberVO.setRpt_mnum(rs.getString("RPT_MNUM"));
				reportMemberVO.setSto_num(rs.getString("STO_NUM"));
				reportMemberVO.setStatus(rs.getString("STATUS"));
				reportMemberVO.setWay(rs.getString("WAY"));
				reportMemberVO.setMem_num(rs.getString("MEM_NUM"));
				reportMemberVO.setRpt_time(rs.getDate("RPT_TIME"));
				reportMemberVO.setContent(rs.getString("CONTENT"));
				storeProfileVO.setSto_name(rs.getString("STO_NAME"));
				storeProfileVO.setSto_status(rs.getString("STO_STATUS"));
				memberProfileVO.setMem_name(rs.getString("MEM_NAME"));
				listAll.add(reportMemberVO);
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
	public void updateReportMember(ReportMemberVO reportMemberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_REPORT_MEMBER);

			pstmt.setString(1, reportMemberVO.getStatus());
			pstmt.setString(2, reportMemberVO.getStaff_num());
			pstmt.setString(3, reportMemberVO.getWay());
			pstmt.setString(4, reportMemberVO.getRpt_mnum());

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
		List<Object> listAll2 = null;
		List<List> list1 = new ArrayList<List>();
		MemberProfileService memberProfileService = new MemberProfileService();
		MemberProfileVO memberProfileVO = null;
		MemberProfileVO memberProfileVO1 = null;
		ReportMemberVO reportMemberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEMBER_PROFILE);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				listAll2 = new ArrayList<>();
				reportMemberVO = new ReportMemberVO();
				memberProfileVO = new MemberProfileVO();
				memberProfileVO1 = new MemberProfileVO();
				reportMemberVO.setRpt_mnum(rs.getString("RPT_MNUM"));
				reportMemberVO.setMem_num(rs.getString("MEM_NUM"));
				reportMemberVO.setMem_num2(rs.getString("MEM_NUM2"));
				reportMemberVO.setStatus(rs.getString("STATUS"));
				reportMemberVO.setWay(rs.getString("WAY"));
				String mem_num = rs.getString("MEM_NUM2");
				memberProfileVO1 = memberProfileService.getMemberProfileByMem_num(mem_num);
				reportMemberVO.setRpt_time(rs.getDate("RPT_TIME"));
				reportMemberVO.setContent(rs.getString("CONTENT"));
				memberProfileVO.setMem_name(rs.getString("MEM_NAME"));
				listAll2.add(reportMemberVO);
				listAll2.add(memberProfileVO);
				listAll2.add(memberProfileVO1);
				list1.add(listAll2);
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
		return list1;
	}

	@Override
	public List<List> getStoContentByStatus(String status) {
		List<Object> listAll = null;
		List<List> list = new ArrayList<List>();
		StoreProfileVO storeProfileVO = null;
		MemberProfileVO memberProfileVO = null;
		ReportMemberVO reportMemberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_STORE_PROFILE_STATUS);
			pstmt.setString(1, status);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				listAll = new ArrayList<>();
				reportMemberVO = new ReportMemberVO();
				storeProfileVO = new StoreProfileVO();
				memberProfileVO = new MemberProfileVO();
				reportMemberVO.setRpt_mnum(rs.getString("RPT_MNUM"));
				reportMemberVO.setSto_num(rs.getString("STO_NUM"));
				reportMemberVO.setStatus(rs.getString("STATUS"));
				reportMemberVO.setWay(rs.getString("WAY"));
				reportMemberVO.setMem_num(rs.getString("MEM_NUM"));
				reportMemberVO.setRpt_time(rs.getDate("RPT_TIME"));
				reportMemberVO.setContent(rs.getString("CONTENT"));
				storeProfileVO.setSto_name(rs.getString("STO_NAME"));
				storeProfileVO.setSto_status(rs.getString("STO_STATUS"));
				memberProfileVO.setMem_name(rs.getString("MEM_NAME"));
				listAll.add(reportMemberVO);
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
	public List<List> getMemContentByStatus(String status) {
		List<Object> listAll2 = null;
		List<List> list1 = new ArrayList<List>();
		MemberProfileService memberProfileService = new MemberProfileService();
		MemberProfileVO memberProfileVO = null;
		MemberProfileVO memberProfileVO1 = null;
		ReportMemberVO reportMemberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEMBER_PROFILE_STATUS);
			pstmt.setString(1,status);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				listAll2 = new ArrayList<>();
				reportMemberVO = new ReportMemberVO();
				memberProfileVO = new MemberProfileVO();
				memberProfileVO1 = new MemberProfileVO();
				reportMemberVO.setRpt_mnum(rs.getString("RPT_MNUM"));
				reportMemberVO.setMem_num(rs.getString("MEM_NUM"));
				reportMemberVO.setMem_num2(rs.getString("MEM_NUM2"));
				reportMemberVO.setStatus(rs.getString("STATUS"));
				reportMemberVO.setWay(rs.getString("WAY"));
				String mem_num = rs.getString("MEM_NUM2");
				memberProfileVO1 = memberProfileService.getMemberProfileByMem_num(mem_num);
				reportMemberVO.setRpt_time(rs.getDate("RPT_TIME"));
				reportMemberVO.setContent(rs.getString("CONTENT"));
				memberProfileVO.setMem_name(rs.getString("MEM_NAME"));
				listAll2.add(reportMemberVO);
				listAll2.add(memberProfileVO);
				listAll2.add(memberProfileVO1);
				list1.add(listAll2);
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
		return list1;
	}

	@Override
	public List<ReportMemberVO> getReportMemberSto(String mem_num) {
		ReportMemberVO reportMemberVO = null;
		List<ReportMemberVO> listReportMemberSto = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_REPORT_MEMBER_STO);
			pstmt.setString(1, mem_num);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				reportMemberVO = new ReportMemberVO();
				reportMemberVO.setSto_num(rs.getString("STO_NUM"));
				reportMemberVO.setRpt_time(rs.getDate("RPT_TIME"));
				reportMemberVO.setStatus(rs.getString("STATUS"));
				reportMemberVO.setWay(rs.getString("WAY"));
				reportMemberVO.setContent(rs.getString("CONTENT"));
				listReportMemberSto.add(reportMemberVO);
				
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
		return listReportMemberSto;
	}

	@Override
	public List<ReportMemberVO> getReportMemberMem(String mem_num) {
		ReportMemberVO reportMemberVO = null;
		List<ReportMemberVO> listReportMemberMem = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_REPORT_MEMBER_MEM);
			pstmt.setString(1, mem_num);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				reportMemberVO = new ReportMemberVO();
				reportMemberVO.setMem_num2(rs.getString("MEM_NUM2"));
				reportMemberVO.setRpt_time(rs.getDate("RPT_TIME"));
				reportMemberVO.setStatus(rs.getString("STATUS"));
				reportMemberVO.setWay(rs.getString("WAY"));
				reportMemberVO.setContent(rs.getString("CONTENT"));
				listReportMemberMem.add(reportMemberVO);
				
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
		return listReportMemberMem;
	}

	@Override
	public List<ReportMemberVO> getReportMemberCom(String mem_num) {
		ReportMemberVO reportMemberVO = null;
		List<ReportMemberVO> listReportMemberCom = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_REPORT_MEMBER_COM);
			pstmt.setString(1, mem_num);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				reportMemberVO = new ReportMemberVO();
				reportMemberVO.setCom_num(rs.getString("COM_NUM"));
				reportMemberVO.setRpt_time(rs.getDate("RPT_TIME"));
				reportMemberVO.setStatus(rs.getString("STATUS"));
				reportMemberVO.setWay(rs.getString("WAY"));
				reportMemberVO.setContent(rs.getString("CONTENT"));
				listReportMemberCom.add(reportMemberVO);
				
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
		return listReportMemberCom;
	}
	
	
	@Override
	public List<ReportMemberVO> getMemCommentAll() {
		List<ReportMemberVO> listReportMember = new ArrayList<ReportMemberVO>();
		ReportMemberVO reportMemberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEM_COMMENT_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				reportMemberVO = new ReportMemberVO();
				reportMemberVO.setRpt_mnum(rs.getString("RPT_MNUM"));
				reportMemberVO.setMem_num(rs.getString("MEM_NUM"));
				reportMemberVO.setCom_num(rs.getString("COM_NUM"));
				reportMemberVO.setRpt_time(rs.getDate("RPT_TIME"));
				reportMemberVO.setStatus(rs.getString("STATUS"));
				reportMemberVO.setWay(rs.getString("WAY"));
				reportMemberVO.setContent(rs.getString("CONTENT"));
				listReportMember.add(reportMemberVO);
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
		return listReportMember;
	}
	
	@Override
	public List<ReportMemberVO> getMemCommentByStatus(String status) {
		List<ReportMemberVO> listReportMemberComment = new ArrayList<ReportMemberVO>();
		ReportMemberVO reportMemberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEM_COMMENT_STATUS);
			pstmt.setString(1, status);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				reportMemberVO = new ReportMemberVO();
				reportMemberVO.setRpt_mnum(rs.getString("RPT_MNUM"));
				reportMemberVO.setMem_num(rs.getString("MEM_NUM"));
				reportMemberVO.setCom_num(rs.getString("COM_NUM"));
				reportMemberVO.setRpt_time(rs.getDate("RPT_TIME"));
				reportMemberVO.setStatus(rs.getString("STATUS"));
				reportMemberVO.setWay(rs.getString("WAY"));
				reportMemberVO.setContent(rs.getString("CONTENT"));
				listReportMemberComment.add(reportMemberVO);
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
		return listReportMemberComment;
	}
	
//--------------end Shawn-----------------	
	
	
}
