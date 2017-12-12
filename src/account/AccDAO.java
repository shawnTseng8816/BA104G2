package account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class AccDAO implements AccDAOInterface{
	
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String UPDATE_VALUE = "UPDATE ACCOUNTS set POINT=? where ACCOUNT = ?";
	private static final String INSERT_VALUE_RECORD ="INSERT INTO VALUE_RECORD(VALUE_NUM, MEM_NUM, VALUE_CASH)VALUES(SEQ_VALUE_NUM.NEXTVAL,?,?)";
	private static final String GET_POINT = "SELECT POINT FROM ACCOUNTS where ACCOUNT = ?";
	private static final String INSERT_REM_RECORD ="INSERT INTO REM_RECORD(REM_NUM, STO_NUM, REM_ACCOUNT, REM_CASH)VALUES(SEQ_REM_NUM.NEXTVAL,?,?,?)";
	
	
	
	
	@Override
	public void addvalue(AccVO accVO,int cash) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			//更新帳號點數
			pstmt = con.prepareStatement(UPDATE_VALUE);
			pstmt.setInt(1, accVO.getPoint());
			pstmt.setString(2, accVO.getAccount());
			pstmt.executeUpdate();
			
			//儲值紀錄
			pstmt = con.prepareStatement(INSERT_VALUE_RECORD);
			pstmt.setString(1, accVO.getAccount());
			pstmt.setInt(2, cash);
			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void remBack(AccVO accVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			//更新帳號點數
			pstmt = con.prepareStatement(UPDATE_VALUE);
			pstmt.setInt(1, accVO.getPoint());
			pstmt.setString(2, accVO.getAccount());
			pstmt.executeUpdate();
			

			// Handle any driver errors
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
	public int getpoint(String acc) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AccVO accVO = null;
		int point = 0;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_POINT);	
			pstmt.setString(1, acc);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				point =  rs.getInt("point");
				 // Store the row in the list
			}
			

			// Handle any driver errors
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
		return point;
		
	}


	@Override
	public void remvalue(AccVO accVO, int remcash,int rem_acc) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			//更新點數數量
			pstmt = con.prepareStatement(UPDATE_VALUE);
			pstmt.setInt(1, accVO.getPoint());
			pstmt.setString(2, accVO.getAccount());
			pstmt.executeUpdate();
			//新增資料到 提款紀錄
			pstmt = con.prepareStatement(INSERT_REM_RECORD);
			pstmt.setString(1, accVO.getAccount());
			pstmt.setInt(2, rem_acc);
			pstmt.setInt(3, remcash);	
			pstmt.executeUpdate();
			

			// Handle any driver errors
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


	


	
		
	

}
