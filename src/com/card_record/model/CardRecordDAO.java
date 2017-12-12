package com.card_record.model;

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

public class CardRecordDAO implements CardRecordDAO_interface  {

	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String GET_ALL = "SELECT * FROM CARD_RECORD";
	
	private static final String GET_ONE_RECORD ="SELECT CR.CARD_NUM, CR.OR_NUM, CR.ADD_VALUE, CR.GET_DATE FROM CARD_RECORD CR JOIN CARD_LIST CL ON CR.CARD_NUM = CL.CARD_NUM AND CL.MEM_NUM = ? ORDER BY  CR.GET_DATE DESC";
	
	//後台查詢所有集點卡紀錄清單
	@Override
	public List<CardRecordVO> getAll() {
		
		List<CardRecordVO> list = new ArrayList<CardRecordVO>();
		CardRecordVO cardRecordVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				cardRecordVO = new CardRecordVO();
				cardRecordVO.setCard_num(cardRecordVO.getCard_num());
				cardRecordVO.setOr_num(cardRecordVO.getOr_num());
				cardRecordVO.setAdd_value(cardRecordVO.getAdd_value());
				cardRecordVO.setGet_date(cardRecordVO.getGet_date());
				list.add(cardRecordVO); // Store he list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	//取得某個會員的集點歷史紀錄
	@Override
	public List<CardRecordVO> getMemRecordByMemNum(String mem_num) {
		
		List<CardRecordVO> list = new ArrayList<CardRecordVO>();
		CardRecordVO cardRecordVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_RECORD);
			pstmt.setString(1, mem_num);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				cardRecordVO = new CardRecordVO();
				cardRecordVO.setCard_num(rs.getString("card_num"));
				cardRecordVO.setOr_num(rs.getString("or_num"));
				cardRecordVO.setAdd_value(rs.getInt("add_value"));
				cardRecordVO.setGet_date(rs.getTimestamp("get_date"));
				list.add(cardRecordVO); // Store he list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
