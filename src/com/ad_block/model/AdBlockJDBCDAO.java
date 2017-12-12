package com.ad_block.model;

import java.util.*;
import java.sql.*;

public class AdBlockJDBCDAO implements AdBlockDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "BA104G2";
	String passwd = "BA104G2";

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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

	public static void main(String[] args) {

		AdBlockJDBCDAO dao = new AdBlockJDBCDAO();

		// 查詢
		// SELECT ab_no,ab_name,ab_price FROM ad_block order by ab_no
		// SELECT ab_no,ab_name,ab_price FROM ad_block where ab_no = ?
		AdBlockVO adblockVO3 = dao.findByPrimaryKey("AB0000000001");
		System.out.print(adblockVO3.getAb_no() + ",");
		System.out.print(adblockVO3.getAb_name() + ",");
		System.out.println(adblockVO3.getAb_price());
		System.out.println("---------------------");

		// 查詢
		List<AdBlockVO> list = dao.getAll();
		for (AdBlockVO aAdBlock : list) {
			System.out.print(aAdBlock.getAb_no() + ",");
			System.out.print(aAdBlock.getAb_name() + ",");
			System.out.print(aAdBlock.getAb_price() + ",");
			System.out.println();
		}
	}
}