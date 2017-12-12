package com.ad_block.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AdBlockDAO implements AdBlockDAO_interface {
	
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String GET_ALL_STMT = 
			"SELECT ab_no,ab_name,ab_price FROM ad_block order by ab_no";
	private static final String GET_ONE_STMT = 
			"SELECT ab_no,ab_name,ab_price FROM ad_block where ab_no = ?";
	@Override
	public AdBlockVO findByPrimaryKey(String ab_no) {

		AdBlockVO adblockVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ab_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				adblockVO = new AdBlockVO();
				adblockVO.setAb_no(rs.getString("ab_no"));
				adblockVO.setAb_name(rs.getString("ab_name"));
				adblockVO.setAb_price(rs.getInt("ab_price"));
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
		return adblockVO;
	}

	@Override
	public List<AdBlockVO> getAll() {
		List<AdBlockVO> list = new ArrayList<AdBlockVO>();
		AdBlockVO adblockVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				adblockVO = new AdBlockVO();
				adblockVO.setAb_no(rs.getString("ab_no"));
				adblockVO.setAb_name(rs.getString("ab_name"));
				adblockVO.setAb_price(rs.getInt("ab_price"));
				list.add(adblockVO); // Store the row in the list
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