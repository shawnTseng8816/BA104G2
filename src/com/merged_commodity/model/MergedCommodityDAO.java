package com.merged_commodity.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.member_profile.model.MemberProfileVO;

public class MergedCommodityDAO implements MergedCommodityDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT = "INSERT INTO MERGED_COMMODITY COM_NUM VALUES ?";

	private static final String INSERT_PEIIUN = 
			"INSERT INTO MERGED_COMMODITY (MERCOM_NUM, COM_NUM) "
			+ " VALUES ('MC'||LPAD(to_char(SEQ_MERCOM_NUM.CURRVAL),10,'0'), ? ) ";
	private static final String NEXTVAL= "SELECT SEQ_MERCOM_NUM.NEXTVAL FROM DUAL";
	private static final String CURR= "SELECT * FROM MERGED_COMMODITY";
	private static final String DELETE = "DELETE FROM MERGED_COMMODITY WHERE MERCOM_NUM = ?";
	private static final String GET_BY_MERNUM = "SELECT * FROM MERGED_COMMODITY WHERE MERCOM_NUM = ?";
	
	

	@Override
	public String insert(List<String> list) {
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		Connection con = null;
		ResultSet rs = null;
		String mercom_num = null;
		try {
			
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(NEXTVAL);//抓下一個流水號
			pstmt.executeQuery();
			
			pstmt2 = con.prepareStatement(INSERT_PEIIUN);	
			
			for(int i =0;i<list.size();i++){
				String com_num = list.get(i);
				pstmt2.setString(1, com_num);
				pstmt2.addBatch();	
			}		
			 pstmt2.executeBatch();
			
			
			 pstmt3 = con.prepareStatement(CURR,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			 rs = pstmt3.executeQuery();
			 rs.absolute(-1);
			 mercom_num = rs.getString("MERCOM_NUM");			 
			 
			 con.commit();
		} catch (SQLException se) {
			try {
				con.rollback();
				throw new RuntimeException("A database error occured. " + se.getMessage());
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return mercom_num;
	}


	@Override
	public void delete(String mercom_num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{			
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, mercom_num);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());			// Clean up JDBC resources
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
	public List<MergedCommodityVO> getMerList(String mercom_num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MergedCommodityVO mcVO = null;
		List<MergedCommodityVO> list= new ArrayList<MergedCommodityVO>();
		try{			
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_MERNUM);
			pstmt.setString(1, mercom_num);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				mcVO = new MergedCommodityVO();
				mcVO.setMercom_num(mercom_num);
				mcVO.setCom_num(rs.getString("com_num"));
				list.add(mcVO);
			}			
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());			// Clean up JDBC resources
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
		return list;
	}
	
	
	
	@Override
	public void insert(MergedCommodityVO mergedCommodityVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(INSERT);

			con.setAutoCommit(false);

			pstmt.setString(1, mergedCommodityVO.getCom_num());

			pstmt.executeUpdate();

			con.commit();

		} catch (SQLException se) {
			try {
				con.rollback();
				throw new RuntimeException("A database error occured. " + se.getMessage());
			} catch (SQLException see) {
				see.printStackTrace();
			}
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
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	
//--------Eric------------
//	private static final String INSERT = "INSERT INTO MERGED_COMMODITY COM_NUM VALUES ?";
//
//	@Override
//	public void insert(MergedCommodityVO mergedCommodityVO) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			con = ds.getConnection();
//
//			pstmt = con.prepareStatement(INSERT);
//
//			con.setAutoCommit(false);
//
//			pstmt.setString(1, mergedCommodityVO.getCom_num());
//
//			pstmt.executeUpdate();
//
//			con.commit();
//
//		} catch (SQLException se) {
//			try {
//				con.rollback();
//				throw new RuntimeException("A database error occured. " + se.getMessage());
//			} catch (SQLException see) {
//				see.printStackTrace();
//			}
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.setAutoCommit(true);
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//	}
	
//---------end Eric-----------	
}