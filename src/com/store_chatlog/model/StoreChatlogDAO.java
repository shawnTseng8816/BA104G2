package com.store_chatlog.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

public class StoreChatlogDAO implements StoreChatlogDAO_interface{
	private static DataSource ds = null;
	static{
		try {
			Context ctx = new InitialContext();
			ds =(DataSource)ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}		
	}
	
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
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setString(1, storeChatlogVO.getRpt_snum());
			pstmt.setString(2, storeChatlogVO.getClog_name());
			pstmt.setString(3, storeChatlogVO.getContent());
		
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
	public List<StoreChatlogVO> stoGetAll(String sto_num) {
		List<StoreChatlogVO> list = new ArrayList<StoreChatlogVO>();
		StoreChatlogVO storeChatlogVO = null;
		
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
			try {
				con = ds.getConnection();
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

			con = ds.getConnection();
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
}
