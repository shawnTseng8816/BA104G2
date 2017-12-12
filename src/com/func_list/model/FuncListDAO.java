package com.func_list.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.backstage_management.model.BackstageManagementVO;

public class FuncListDAO implements FuncListDAO_interface{
	
	private static DataSource ds =null;
	static {
		try{
			Context ctx = new javax.naming.InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		}catch (NamingException e) {
			e.printStackTrace(System.err);
		}
	}

//-----------Peiiun---------------------------	
	private static String GET_ALL = 
			"SELECT * FROM FUNC_LIST";
	
	@Override
	public List<FuncListVO> getAll() {
		List<FuncListVO> list = new ArrayList<FuncListVO>();
		FuncListVO funcListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {				
				funcListVO = new FuncListVO();
				funcListVO.setFunc_no(rs.getString("func_no"));
				funcListVO.setFunc_name(rs.getString("func_name"));

				list.add(funcListVO);
			}
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
//-----------end Peiiun---------------------------	
	
	
//----------------Chi-----------------------------
	
	private static final String GET_ALL_STMT = 
			"SELECT ab_no,ab_name,ab_price FROM ad_block order by ab_no";
	private static final String GET_ONE_STMT = 
			"SELECT ab_no,ab_name,ab_price FROM ad_block where ab_no = ?";
	@Override
	public FuncListVO findByPrimaryKey(String func_no) {

		FuncListVO funclistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, func_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				funclistVO = new FuncListVO();
				funclistVO.setFunc_no(rs.getString("func_no"));
				funclistVO.setFunc_name(rs.getString("func_name"));
			}

			// Handle any driver errors
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
		return funclistVO;
	}

	@Override
	public List<FuncListVO> getAllChi() {
		List<FuncListVO> list = new ArrayList<FuncListVO>();
		FuncListVO funclistVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				funclistVO = new FuncListVO();
				funclistVO.setFunc_no(rs.getString("func_no"));
				funclistVO.setFunc_name(rs.getString("func_name"));
				list.add(funclistVO); // Store the row in the list
			}

			// Handle any driver errors
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
	
	
//----------------end Chi------------------------	
	
	private static String GET_ONE_FUNCNAME_BY_NO = "SELECT FUNC_NAME FROM FUNC_LIST WHERE FUNC_NO=?";
	
	
	public String getOneFuncNameByFuncNo(String func_no){
		String funcName = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_FUNCNAME_BY_NO);
			pstmt.setString(1, func_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {				
				funcName = rs.getString("func_name");
			}
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
		return funcName;
		
		
	}
}
