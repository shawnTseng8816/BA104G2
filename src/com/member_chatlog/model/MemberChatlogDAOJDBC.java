package com.member_chatlog.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberChatlogDAOJDBC implements MemberChatlogDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "BA104G2";
	String passwd = "BA104G2";
	
	private static final String INSERT =
			"INSERT INTO MEMBER_CHATLOG (MC_NUM,RPT_MNUM,CLOG_NAME,CONTENT,CLOG_TIME)"
			+ " VALUES ('MC'||LPAD(to_char(SEQ_SC_NUM.NEXTVAL),10,'0'),?,?,?,CURRENT_TIMESTAMP)";
	
	private static final String STO_Get_ALL = 
			"SELECT mc_num, MC.rpt_mnum , clog_name , content , to_char(clog_time,'yyyy-mm-dd') clog_time"
			+ " FROM MEMBER_CHATLOG MC LEFT JOIN (SELECT RPT_MNUM, MEM_NUM FROM REPORT_MEMBER) RM "
			+ " ON RM.RPT_MNUM = MC.RPT_MNUM WHERE MEM_num=?";
	
	private static final String GET_ALL = 
			"SELECT * FROM MEMBER_CHATLOG";
	@Override
	public void insert(MemberChatlogVO memberChatlogVO) {
		Connection con =null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setString(1, memberChatlogVO.getRpt_mnum());
			pstmt.setString(2, memberChatlogVO.getClog_name());
			pstmt.setString(3, memberChatlogVO.getContent());
		
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());		
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
	public List<MemberChatlogVO> memGetAll(String mem_num) {
		List<MemberChatlogVO> list = new ArrayList<MemberChatlogVO>();
		MemberChatlogVO memberChatlogVO = null;
		
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(STO_Get_ALL);
				
				pstmt.setString(1, mem_num);
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					memberChatlogVO = new MemberChatlogVO();
					memberChatlogVO.setMc_num(rs.getString("mc_num"));
					memberChatlogVO.setRpt_mnum(rs.getString("rpt_mnum"));
					memberChatlogVO.setClog_name(rs.getString("clog_name"));
					memberChatlogVO.setContent(rs.getString("content"));
					memberChatlogVO.setClog_time(rs.getDate("clog_time"));
					list.add(memberChatlogVO);
				}
				
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
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
	public List<MemberChatlogVO> getAll() {
		MemberChatlogVO memberChatlogVO = null;
		List<MemberChatlogVO> list = new ArrayList<MemberChatlogVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL);			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				memberChatlogVO = new MemberChatlogVO();
				memberChatlogVO.setMc_num(rs.getString("mc_num"));
				memberChatlogVO.setRpt_mnum(rs.getString("rpt_mnum"));
				memberChatlogVO.setClog_name(rs.getString("clog_name"));
				memberChatlogVO.setContent(rs.getString("content"));
				memberChatlogVO.setClog_time(rs.getDate("clog_time"));
				list.add(memberChatlogVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
	
	public static void main(String[] args){
		
		MemberChatlogDAOJDBC dao = new MemberChatlogDAOJDBC();
		MemberChatlogVO scVO = new MemberChatlogVO();
		
//		//insert
//		scVO.setRpt_mnum("RM0000000004");
//		scVO.setClog_name("會員");
//		scVO.setContent("不實廣告");
//		dao.insert(scVO);
		
//		//sto get all
//		List<MemberChatlogVO> list = dao.memGetAll("MB0000000001");
//		System.out.println(list.size());
//		for(MemberChatlogVO mall : list){			
//			System.out.print(mall.getMc_num() + ",");
//			System.out.print(mall.getRpt_mnum() + ",");
//			System.out.print(mall.getClog_name() + ",");
//			System.out.print(mall.getContent() + ",");
//			System.out.print(mall.getClog_time() + ",");
//			System.out.println();
//		}
		
		//get all
//		List<MemberChatlogVO> list = dao.getAll();
//		System.out.println(list.size());
//		for(MemberChatlogVO allSC : list){			
//			System.out.print(allSC.getMc_num() + ",");
//			System.out.print(allSC.getRpt_mnum() + ",");
//			System.out.print(allSC.getClog_name() + ",");
//			System.out.print(allSC.getContent() + ",");
//			System.out.print(allSC.getClog_time() + ",");
//			System.out.println();
//		}
	}









	

}
