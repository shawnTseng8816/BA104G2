package com.order_detail.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.member_profile.model.MemberProfileVO;

public class OrderDetailDAO implements OrderDetailDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

//-------------KAHN-----------------------------------	
	private static final String INSERT = "INSERT INTO ORDER_DETAIL (ORDET_NUM, COM_NUM, OR_NUM, SWEET_NUM, ICE_NUM, COM_SIZE, MERCOM_NUM, OD_PRICE) VALUES ('OD'||TRIM(TO_CHAR(SEQ_ORDET_NUM.NEXTVAL, '0000000000')), ?, ?, ?, ?, ?, NVL(?, ''), ?)";
	private static final String DELETE = "DELETE FROM ORDER_DETAIL WHERE ORDET_NUM=?";
	private static final String GET_ORDERDETAIL = "SELECT * FROM ORDER_DETAIL WHERE ORDET_NUM=?";
	private static final String GET_ORDERDETAILNUM = "SELECT * FROM ORDER_DETAIL WHERE OR_NUM=?";
	
	

	@Override
	public String insert(OrderDetailVO orderDetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String key = null;

		try {

			con = ds.getConnection();
			
			String[] cols = {"ORDET_NUM"}; // 或 int cols[] = {1};
			pstmt = con.prepareStatement(INSERT, cols);

			con.setAutoCommit(false);
			
			pstmt.setString(1, orderDetailVO.getCom_num());
			pstmt.setString(2, orderDetailVO.getOr_num());
			pstmt.setString(3, orderDetailVO.getSweet_num());
			pstmt.setString(4, orderDetailVO.getIce_num());
			pstmt.setString(5, orderDetailVO.getCom_size());
			pstmt.setString(6, orderDetailVO.getMercom_num());
			pstmt.setInt(7, orderDetailVO.getOd_price());

			pstmt.executeUpdate();

			con.commit();
			
			rs = pstmt.getGeneratedKeys();
			rs.next();
			key = rs.getString(1);

		} catch (SQLException se) {
			try {
				con.rollback();
				throw new RuntimeException("A database error occured. " + se.getMessage());
			} catch (SQLException see) {
				see.printStackTrace();
			}
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
		return key;
	}

	@Override
	public OrderDetailVO getOrderDetail(OrderDetailVO orderDetailVO) {
		OrderDetailVO orderdetVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDERDETAIL);

			pstmt.setString(1, orderDetailVO.getOrdet_num());

			rs = pstmt.executeQuery();

			rs.next();
			orderdetVO = new OrderDetailVO();
			orderdetVO.setCom_num(rs.getString("COM_NUM"));
			orderdetVO.setCom_size(rs.getString("COM_SIZE"));
			orderdetVO.setIce_num(rs.getString("ICE_NUM"));
			orderdetVO.setMercom_num(rs.getString("MERCOM_NUM"));
			orderdetVO.setOr_num(rs.getString("OR_NUM"));
			orderdetVO.setSweet_num(rs.getString("SWEET_NUM"));

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return orderdetVO;
	}

	@Override
	public void delete(OrderDetailVO orderDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			con.setAutoCommit(false);
			
			pstmt.setString(1, orderDetailVO.getOrdet_num());

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

	@Override
	public List<OrderDetailVO> getOrderDetails(String orNum) {
		OrderDetailVO orderDetailVO = null;
		List<OrderDetailVO> commentsList = new ArrayList<OrderDetailVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDERDETAILNUM);

			pstmt.setString(1, orNum);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderDetailVO = new OrderDetailVO();
				orderDetailVO.setOrdet_num(rs.getString("ORDET_NUM"));
				orderDetailVO.setOr_num(rs.getString("OR_NUM"));
				orderDetailVO.setCom_size(rs.getString("COM_SIZE"));
				orderDetailVO.setIce_num(rs.getString("ICE_NUM"));
				orderDetailVO.setSweet_num(rs.getString("SWEET_NUM"));
				orderDetailVO.setOd_price(rs.getInt("OD_PRICE"));
				orderDetailVO.setCom_num(rs.getString("COM_NUM"));
				orderDetailVO.setMercom_num(rs.getString("MERCOM_NUM"));
				
				commentsList.add(orderDetailVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return commentsList;
	}
//-----------------------end KAHN---------------------------
	
	
//-------------------Shawn---------------------
	
	
	private static final String GET_ORDER_DETAIL = 
			"SELECT ordet_num, com_num, or_num, sweet_num, ice_num, com_size, mercom_num FROM ORDER_DETAIL WHERE ordet_num=?";


	@Override
	public OrderDetailVO getOrderDetailByOrdet_num(String ordet_num) {	
		
		OrderDetailVO orderDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDER_DETAIL);
			
			pstmt.setString(1, ordet_num);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				orderDetailVO = new OrderDetailVO();
				orderDetailVO.setOrdet_num(rs.getString("ordet_num"));
				orderDetailVO.setCom_num(rs.getString("com_num"));
				orderDetailVO.setOr_num(rs.getString("or_num"));
				orderDetailVO.setSweet_num(rs.getString("sweet_num"));
				orderDetailVO.setIce_num(rs.getString("ice_num"));
				orderDetailVO.setCom_size(rs.getString("com_size"));
				orderDetailVO.setMercom_num(rs.getString("mercom_num"));
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
		return orderDetailVO;

	}
//--------------end Shawn--------------------------	
	
	
	
	
	
}