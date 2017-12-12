package com.auth_list.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class AuthListDAO implements AuthListDAO_interface{
	
	private static DataSource ds = null;
	static{
		try{
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}		
	}
	
	
//-----------------------Peiiun-----------------------
	private static final String INSERT =
			"INSERT INTO AUTH_LIST (BM_NO,FUNC_NO) VALUES (?,?)";
	private static final String DELETE = 
			"DELETE FROM AUTH_LIST WHERE BM_NO = ?";
	private static final String GET_ONE = 
			"SELECT * FROM AUTH_LIST WHERE BM_NO =?";
	private static final String GET_ALL = 
			"SELECT * FROM AUTH_LIST ORDER BY BM_NO";
	
	@Override
	public void insert(String bm_no, List<String> list) {
		PreparedStatement pstmt = null;
		Connection con = null;
	
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			
			for(int i = 0; i<list.size();i++){
				String func_no = list.get(i);
				pstmt.setString(1, bm_no);
				pstmt.setString(2, func_no);
				pstmt.addBatch();				
			}
			
			pstmt.executeBatch();
				
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
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(String bm_no) {
		PreparedStatement pstmt = null;
		Connection con = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, bm_no);			
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
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public List<String> findByBm_no(String bm_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);
			pstmt.setString(1, bm_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				String func_no = rs.getString("func_no");		
				list.add(func_no);
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
	
	@Override
	public Map<String, List<String>> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String,List<String>> auMap = new HashMap<String , List<String>>();
		List<String> funcSet  ;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String bm_no = rs.getString(1);
				String func_no = rs.getString(2);
				if(auMap.containsKey(bm_no)){
					auMap.get(bm_no).add(func_no);
				}else{
					funcSet = new ArrayList<String>();
					funcSet.add(func_no);
					auMap.put(bm_no, funcSet);
				}
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
		return auMap;
	}
	
	

//-----------------------end Peiiun-----------------------	

	

//-----------------------chi---------------------------------

	private static final String GET_ALL_STMT = 
			"SELECT bm_no,func_no FROM auth_list order by bm_no";
		private static final String GET_ONE_STMT = 
			"SELECT bm_no,func_no FROM auth_list where bm_no = ?";
	@Override
	public AuthListVO findByPrimaryKey(String bm_no) {

		AuthListVO authlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, bm_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				authlistVO = new AuthListVO();
				authlistVO.setBm_no(rs.getString("bm_no"));
				authlistVO.setFunc_no(rs.getString("func_no"));
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
		return authlistVO;
	}

	@Override
	public List<AuthListVO> getAllChi() {
		List<AuthListVO> list = new ArrayList<AuthListVO>();
		AuthListVO authlistVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				authlistVO = new AuthListVO();
				authlistVO.setBm_no(rs.getString("bm_no"));
				authlistVO.setFunc_no(rs.getString("func_no"));
				list.add(authlistVO); // Store the row in the list
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
	
	
//-----------------------end chi---------------------------------
}
