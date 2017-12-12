package com.store_chatlog.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreChatlogDAOJDBC implements StoreChatlogDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "BA104G2";
	String passwd = "BA104G2";
	
	private static final String INSERT =
			"INSERT INTO STORE_CHATLOG (SC_NUM,RPT_SNUM,CLOG_NAME,CONTENT,CLOG_TIME)"
			+ " VALUES ('SC'||LPAD(to_char(SEQ_SC_NUM.NEXTVAL),10,'0'),?,?,?,CURRENT_TIMESTAMP)";
	
	private static final String STO_Get_ALL = 
			" SELECT sc_num, SC.rpt_snum , clog_name , content , to_char(clog_time,'yyyy-mm-dd') clog_time "
			+ " FROM STORE_CHATLOG SC LEFT JOIN (SELECT RPT_SNUM,STO_NUM FROM REPORT_STORE) RS "
			+ " ON RS.RPT_SNUM = SC.RPT_SNUM WHERE sto_num=?";
	
	private static final String GET_ALL = 
			"SELECT * FROM STORE_CHATLOG";
	
	@Override
	public void insert(StoreChatlogVO storeChatlogVO) {
		Connection con =null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setString(1, storeChatlogVO.getRpt_snum());
			pstmt.setString(2, storeChatlogVO.getClog_name());
			pstmt.setString(3, storeChatlogVO.getContent());
		
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
	public List<StoreChatlogVO> stoGetAll(String sto_num) {
		List<StoreChatlogVO> list = new ArrayList<StoreChatlogVO>();
		StoreChatlogVO storeChatlogVO = null;
		
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(STO_Get_ALL);
				
				pstmt.setString(1, sto_num);
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					storeChatlogVO = new StoreChatlogVO();
					storeChatlogVO.setSc_num(rs.getString("sc_num"));
					storeChatlogVO.setRpt_snum(rs.getString("rpt_snum"));
					storeChatlogVO.setClog_name(rs.getString("clog_name"));
					storeChatlogVO.setContent(rs.getString("content"));
					storeChatlogVO.setClog_time(rs.getDate("clog_time"));
					list.add(storeChatlogVO);
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
	public List<StoreChatlogVO> getAll() {
		StoreChatlogVO storeChatlogVO = null;
		List<StoreChatlogVO> list = new ArrayList<StoreChatlogVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL);			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				storeChatlogVO = new StoreChatlogVO();
				storeChatlogVO.setSc_num(rs.getString("sc_num"));
				storeChatlogVO.setRpt_snum(rs.getString("rpt_snum"));
				storeChatlogVO.setClog_name(rs.getString("clog_name"));
				storeChatlogVO.setContent(rs.getString("content"));
				storeChatlogVO.setClog_time(rs.getDate("clog_time"));
				list.add(storeChatlogVO);
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
		
//		StoreChatlogDAOJDBC dao = new StoreChatlogDAOJDBC();
//		StoreChatlogVO scVO = new StoreChatlogVO();
//		
		//insert
//		scVO.setRpt_snum("RS0000000004");
//		scVO.setClog_name("店家");
//		scVO.setContent("不實廣告");
//		dao.insert(scVO);
		
		//sto get all
//		List<StoreChatlogVO> list = dao.stoGetAll("ST0000000001");
//		
//		System.out.println(list.size());
//		for(StoreChatlogVO scVO : list){			
//			System.out.print(scVO.getSc_num() + ",");
//			System.out.print(scVO.getRpt_snum() + ",");
//			System.out.print(scVO.getClog_name() + ",");
//			System.out.print(scVO.getContent() + ",");
//			System.out.print(scVO.getClog_time() + ",");
//			System.out.println();
//		}
		
		//get all
//		List<StoreChatlogVO> list = dao.getAll();
//		System.out.println(list.size());
//		for(StoreChatlogVO allSC : list){			
//			System.out.print(allSC.getSc_num() + ",");
//			System.out.print(allSC.getRpt_snum() + ",");
//			System.out.print(allSC.getClog_name() + ",");
//			System.out.print(allSC.getContent() + ",");
//			System.out.print(allSC.getClog_time() + ",");
//			System.out.println();
//		}
	}




}
