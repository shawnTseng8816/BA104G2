package com.value_record.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;




public class ValueRecordDAO implements ValueRecordDAO_interface {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//取得所有儲值紀錄
	private static final String GET_ALL_VALUE ="SELECT * FROM VALUE_RECORD";
	
	//取得會員目前剩餘點數
	private static final String GET_ONE_MEM_VALUE ="SELECT REM_POINT FROM MEMBER_PROFILE WHERE MEM_NUM = ?";
		
	//取得店家目前剩餘點數
	private static final String GET_ONE_STO_VALUE ="SELECT REM_POINT FROM STO_PROFILE WHERE STO_NUM = ?";
		
	//取得一個儲值紀錄
	private static final String GET_ONE_MEM_VALUE_RECORD ="SELECT * FROM VALUE_RECORD WHERE MEM_NUM = ? ORDER BY VALUE_NUM DESC";
		
	//更新會員剩餘點數
	private static final String UPDATE_MEM_VALUE = "UPDATE MEMBER_PROFILE SET REM_POINT=REM_POINT+? WHERE MEM_NUM = ?";
	
	//新增儲值紀錄
	private static final String INSERT_VALUE_RECORD ="INSERT INTO VALUE_RECORD(VALUE_NUM, MEM_NUM, VALUE_CASH)VALUES('VR'||LPAD(to_char(SEQ_VALUE_NUM.NEXTVAL),10,'0'),?,?)";
	
	
	
	
	
	
	
	
	// 查詢所有儲值紀錄 後台用
	@Override
	public List<ValueRecordVO> getAll() {
			
		List<ValueRecordVO> list = new ArrayList<ValueRecordVO>();
		ValueRecordVO value_RecordVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_VALUE);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// value_RecordVO 也稱為 Domain objects
				value_RecordVO = new ValueRecordVO();
				value_RecordVO.setValue_num(rs.getString("value_num"));
				value_RecordVO.setMem_num(rs.getString("mem_num"));
				value_RecordVO.setValue_date(rs.getTimestamp("value_date"));
				value_RecordVO.setValue_cash(rs.getInt("value_cash"));		
				list.add(value_RecordVO); // Store the row in the list
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


	//儲值後順便新增紀錄  會員用

	@Override
	public void addValue(String mem_num, Integer value_cash) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			con.setAutoCommit(false);
			
			//將儲值點數加入
			pstmt = con.prepareStatement(UPDATE_MEM_VALUE);
			pstmt.setInt(1, value_cash);
			pstmt.setString(2, mem_num);
			pstmt.executeUpdate();
			//新增儲值紀錄
			pstmt = con.prepareStatement(INSERT_VALUE_RECORD);
			pstmt.setString(1, mem_num);
			pstmt.setInt(2, value_cash);
			pstmt.executeUpdate();
			con.commit();
			// Handle any driver errors
		} catch (SQLException se) {	
				try {
					con.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		
	}

	//取得個人儲值資訊 會員用
	@Override
	public List<ValueRecordVO> getMyRecord(String mem_num) {
		
		List<ValueRecordVO> list = new ArrayList<ValueRecordVO>();
		ValueRecordVO value_RecordVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_MEM_VALUE_RECORD);
			pstmt.setString(1, mem_num);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// value_RecordVO 也稱為 Domain objects
				value_RecordVO = new ValueRecordVO();
				value_RecordVO.setValue_num(rs.getString("value_num"));
				value_RecordVO.setValue_date(rs.getTimestamp("value_date"));
				value_RecordVO.setValue_cash(rs.getInt("value_cash"));		
				list.add(value_RecordVO); // Store the row in the list
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

	
	

}
