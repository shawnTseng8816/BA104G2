package com.coupon.model;

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

public class CouponDAO implements CouponDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
//-------------KAHN----------------	
	private static final String GET_COUPONPIC = "SELECT IMAGE FROM COUPON WHERE COUPON_NUM=?";

	private static final String GET_COUPON_APPLY = "SELECT * FROM COUPON WHERE COUPON_STATUS='審核中'";

	private static final String GET_COUPON_FINISH = "SELECT * FROM COUPON WHERE COUPON_STATUS NOT LIKE '審核中'";

	private static final String GET_COUPON = "SELECT * FROM COUPON WHERE COUPON_STATUS ='審核通過' AND DOWN_DATE > sysdate order by UP_DATE ASC";

	private static final String UPDATE_COUPON_STATUS_PASS = "UPDATE COUPON SET COUPON_STATUS ='審核通過' WHERE Coupon_num = ?";

	private static final String CREATE_COUPON_LIST = "INSERT INTO COUPON_LIST(COUPON_NUM,COUPON_AMOUNT)VALUES(?,?)";


	private static final String GET_ONE_STO_COUPON = "SELECT * FROM COUPON WHERE STO_NUM=?";

	private static final String UPDATE_COUPON_STATUS_NOPASS = "UPDATE COUPON SET COUPON_STATUS ='審核失敗' WHERE Coupon_num = ?";

	// 取得一張折價券資訊
	private static final String GET_LEFT = "SELECT * FROM COUPON WHERE COUPON_NUM=?";

	private static final String APPLY_COUPON = "INSERT INTO COUPON(COUPON_NUM, STO_NUM, COUPON_CASH, COUPON_DESC, LEFT, TOTAL, UP_DATE, DOWN_DATE, EXP_DATE,NOTICE_DESC,  NOTICE_UP_DATE, NOTICE_DOWN_DATE, IMAGE)"
			+ "VALUES('CP'||LPAD(to_char(SEQ_COUPON_NUM.NEXTVAL),6,'0'),?,?,?,?,?,?,?,?,?,?,?,?)";

	// 取得一張折價券資訊
	public byte[] getPic(String coupon_num) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		byte[] pic = null;
		
	try {
		con = ds.getConnection();
		pstmt = con.prepareStatement(GET_COUPONPIC);
		pstmt.setString(1, coupon_num);
		rs = pstmt.executeQuery();
		rs.next();
		pic = rs.getBytes(1);

		// Handle any driver errors
	}catch(SQLException se)
	{
		throw new RuntimeException("A database error occured. " + se.getMessage());
		// Clean up JDBC resources
	}finally
	{
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

	}return pic;

	}

	// 取得一張折價券資訊
	public CouponVO getOneCoupon(String coupon_num) {

		CouponVO couponVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			couponVO = new CouponVO();
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LEFT);
			pstmt.setString(1, coupon_num);
			rs = pstmt.executeQuery();
			rs.next();
			couponVO.setCoupon_cash(rs.getInt("coupon_cash"));
			couponVO.setExp_date(rs.getTimestamp("exp_date"));
			couponVO.setLeft(rs.getInt("left"));
			couponVO.setUp_date(rs.getTimestamp("up_date"));
			couponVO.setSto_num(rs.getString("sto_num"));
			

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
		return couponVO;

	}

	// 查詢所有尚未省核折價券清單
	@Override
	public List<CouponVO> getApply() {

		List<CouponVO> list = new ArrayList<CouponVO>();
		CouponVO couponVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_COUPON_APPLY);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				couponVO = new CouponVO();
				couponVO.setSto_num(rs.getString("sto_num"));
				couponVO.setCoupon_num(rs.getString("coupon_num"));
				couponVO.setCoupon_cash(rs.getInt("coupon_cash"));
				couponVO.setCoupon_desc(rs.getString("coupon_desc"));
				couponVO.setLeft(rs.getInt("left"));
				couponVO.setTotal(rs.getInt("total"));
				couponVO.setApply_date(rs.getTimestamp("apply_date"));
				couponVO.setUp_date(rs.getTimestamp("up_date"));
				couponVO.setDown_date(rs.getTimestamp("down_date"));
				couponVO.setExp_date(rs.getTimestamp("exp_date"));
				couponVO.setNotice_desc(rs.getString("notice_desc"));
				couponVO.setNotice_down_date(rs.getTimestamp("notice_down_date"));
				couponVO.setNotice_up_date(rs.getTimestamp("notice_up_date"));
				couponVO.setCoupon_status(rs.getString("coupon_status"));
				list.add(couponVO); // Store the row in the list
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

	// 更新折價券狀態為通過
	@Override
	public void Pass(String coupon_num, Integer total) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			con.setAutoCommit(false);
			// 更新狀態為通過
			pstmt = con.prepareStatement(UPDATE_COUPON_STATUS_PASS);
			pstmt.setString(1, coupon_num);
			pstmt.executeUpdate();

			// 新增折價券清單數量
			pstmt = con.prepareStatement(CREATE_COUPON_LIST);
			for (Integer i = 1; i <= total; i++) {
				pstmt.setString(1, coupon_num);
				pstmt.setInt(2, i);
				pstmt.executeUpdate();
			}

			con.commit();
			// Handle any SQL errors
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	// 查詢所有已省核折價券清單
	@Override
	public List<CouponVO> getFinish() {
		List<CouponVO> list = new ArrayList<CouponVO>();
		CouponVO couponVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_COUPON_FINISH);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				couponVO = new CouponVO();
				couponVO.setSto_num(rs.getString("sto_num"));
				couponVO.setCoupon_num(rs.getString("coupon_num"));
				couponVO.setCoupon_cash(rs.getInt("coupon_cash"));
				couponVO.setCoupon_desc(rs.getString("coupon_desc"));
				couponVO.setLeft(rs.getInt("left"));
				couponVO.setTotal(rs.getInt("total"));
				couponVO.setApply_date(rs.getTimestamp("apply_date"));
				couponVO.setUp_date(rs.getTimestamp("up_date"));
				couponVO.setDown_date(rs.getTimestamp("down_date"));
				couponVO.setExp_date(rs.getTimestamp("exp_date"));
				couponVO.setNotice_desc(rs.getString("notice_desc"));
				couponVO.setNotice_down_date(rs.getTimestamp("notice_down_date"));
				couponVO.setNotice_up_date(rs.getTimestamp("notice_up_date"));
				couponVO.setCoupon_status(rs.getString("coupon_status"));
				list.add(couponVO); // Store he list
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

	// 新增折價券
	@Override
	public void insertApply(CouponVO couponVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(APPLY_COUPON);
			System.out.println("進入");
			pstmt.setString(1, couponVO.getSto_num());
			pstmt.setInt(2, couponVO.getCoupon_cash());
			pstmt.setString(3, couponVO.getCoupon_desc());
			pstmt.setInt(4, couponVO.getTotal());
			pstmt.setInt(5, couponVO.getTotal());
			pstmt.setTimestamp(6, couponVO.getUp_date());
			pstmt.setTimestamp(7, couponVO.getDown_date());
			pstmt.setTimestamp(8, couponVO.getExp_date());
			pstmt.setString(9, couponVO.getNotice_desc());
			pstmt.setTimestamp(10, couponVO.getNotice_up_date());
			pstmt.setTimestamp(11, couponVO.getNotice_down_date());
			pstmt.setBytes(12, couponVO.getImage());
			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public List<CouponVO> getOneStoCoupon(String sto_num) {

		List<CouponVO> list = new ArrayList<CouponVO>();
		CouponVO couponVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STO_COUPON);
			pstmt.setString(1, sto_num);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				couponVO = new CouponVO();
				couponVO.setCoupon_num(rs.getString("coupon_num"));
				couponVO.setCoupon_cash(rs.getInt("coupon_cash"));
				couponVO.setCoupon_desc(rs.getString("coupon_desc"));
				couponVO.setLeft(rs.getInt("left"));
				couponVO.setTotal(rs.getInt("total"));
				couponVO.setApply_date(rs.getTimestamp("apply_date"));
				couponVO.setUp_date(rs.getTimestamp("up_date"));
				couponVO.setDown_date(rs.getTimestamp("down_date"));
				couponVO.setExp_date(rs.getTimestamp("exp_date"));
				couponVO.setNotice_desc(rs.getString("notice_desc"));
				couponVO.setNotice_down_date(rs.getTimestamp("notice_down_date"));
				couponVO.setNotice_up_date(rs.getTimestamp("notice_up_date"));
				couponVO.setCoupon_status(rs.getString("coupon_status"));
				list.add(couponVO);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public void noPass(String coupon_num) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			// 更新狀態為通過
			pstmt = con.prepareStatement(UPDATE_COUPON_STATUS_NOPASS);
			pstmt.setString(1, coupon_num);
			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {

			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	@Override
	public List<CouponVO> getCoupon() {
		List<CouponVO> list = new ArrayList<CouponVO>();
		CouponVO CouponVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_COUPON);
			rs = pstmt.executeQuery();

			while (rs.next()) {
			
				// empVO 也稱為 Domain objects
				CouponVO = new CouponVO();
				CouponVO.setCoupon_num(rs.getString("Coupon_num"));
				CouponVO.setCoupon_cash(rs.getInt("Coupon_cash"));
				CouponVO.setCoupon_desc(rs.getString("Coupon_desc"));
				CouponVO.setSto_num(rs.getString("sto_num"));
				CouponVO.setUp_date(rs.getTimestamp("up_date"));
				CouponVO.setLeft(rs.getInt("Left"));
				CouponVO.setTotal(rs.getInt("Total"));
				CouponVO.setDown_date(rs.getTimestamp("down_date"));
				CouponVO.setNotice_desc(rs.getString("notice_desc"));
				CouponVO.setCoupon_status(rs.getString("Coupon_status"));
				list.add(CouponVO); // Store he list
				
				
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
	
//----------end KAHN--------------------------
	
	
//-----------Eric------------------------------
//	private static final String GET_COUPONPIC = "SELECT IMAGE FROM COUPON WHERE COUPON_NUM=?";
//
//	private static final String GET_COUPON_APPLY = "SELECT * FROM COUPON WHERE COUPON_STATUS='審核中'";
//
//	private static final String GET_COUPON_FINISH = "SELECT * FROM COUPON WHERE COUPON_STATUS NOT LIKE '審核中'";
//
//	private static final String GET_COUPON = "SELECT * FROM COUPON WHERE COUPON_STATUS ='審核通過' AND DOWN_DATE > sysdate order by UP_DATE ASC";
//
//	private static final String UPDATE_COUPON_STATUS_PASS = "UPDATE COUPON SET COUPON_STATUS ='審核通過' WHERE Coupon_num = ?";
//
//	private static final String CREATE_COUPON_LIST = "INSERT INTO COUPON_LIST(COUPON_NUM,COUPON_AMOUNT)VALUES(?,?)";

	private static final String GET_COUPON_UPDATE = "SELECT UP_DATE FROM COUPON WHERE UP_DATE>SYSDATE";

//	private static final String GET_ONE_STO_COUPON = "SELECT * FROM COUPON WHERE STO_NUM=?";
//
//	private static final String UPDATE_COUPON_STATUS_NOPASS = "UPDATE COUPON SET COUPON_STATUS ='審核失敗' WHERE Coupon_num = ?";
//
//	// 取得一張折價券資訊
//	private static final String GET_LEFT = "SELECT * FROM COUPON WHERE COUPON_NUM=?";
//
//	private static final String APPLY_COUPON = "INSERT INTO COUPON(COUPON_NUM, STO_NUM, COUPON_CASH, COUPON_DESC, LEFT, TOTAL, UP_DATE, DOWN_DATE, EXP_DATE,NOTICE_DESC,  NOTICE_UP_DATE, NOTICE_DOWN_DATE, IMAGE)"
//			+ "VALUES('CP'||LPAD(to_char(SEQ_COUPON_NUM.NEXTVAL),6,'0'),?,?,?,?,?,?,?,?,?,?,?,?)";

	
	
	
	
	@Override
	public List<Timestamp> getCouponUpDate() {

		List<Timestamp> list = new ArrayList<Timestamp>();
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Timestamp timestamp = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_COUPON_UPDATE);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				timestamp  = rs.getTimestamp("UP_DATE");
			
				list.add(timestamp); // Store the row in the list
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

	
	
	
	
	
	
	
	
	
	
	
	
	
//	// 取得一張折價券資訊
//	public byte[] getPic(String coupon_num) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		byte[] pic = null;
//		
//	try {
//		con = ds.getConnection();
//		pstmt = con.prepareStatement(GET_COUPONPIC);
//		pstmt.setString(1, coupon_num);
//		rs = pstmt.executeQuery();
//		rs.next();
//		pic = rs.getBytes(1);
//
//		// Handle any driver errors
//	}catch(SQLException se)
//	{
//		throw new RuntimeException("A database error occured. " + se.getMessage());
//		// Clean up JDBC resources
//	}finally
//	{
//		if (rs != null) {
//			try {
//				rs.close();
//			} catch (SQLException se) {
//				se.printStackTrace(System.err);
//			}
//		}
//		if (pstmt != null) {
//			try {
//				pstmt.close();
//			} catch (SQLException se) {
//				se.printStackTrace(System.err);
//			}
//		}
//		if (con != null) {
//			try {
//				con.close();
//			} catch (Exception e) {
//				e.printStackTrace(System.err);
//			}
//		}
//
//	}return pic;
//
//	}
//
//	// 取得一張折價券資訊
//	public CouponVO getOneCoupon(String coupon_num) {
//
//		CouponVO couponVO = null;
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//	
//		try {
//			couponVO = new CouponVO();
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_LEFT);
//			pstmt.setString(1, coupon_num);
//			rs = pstmt.executeQuery();
//			rs.next();
//			couponVO.setCoupon_cash(rs.getInt("coupon_cash"));
//			couponVO.setExp_date(rs.getTimestamp("exp_date"));
//			couponVO.setLeft(rs.getInt("left"));
//			couponVO.setUp_date(rs.getTimestamp("up_date"));
//			couponVO.setSto_num(rs.getString("sto_num"));
//			
//
//			// Handle any driver errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//
//		}
//		return couponVO;
//
//	}
//
//	// 查詢所有尚未審核折價券清單
//	@Override
//	public List<CouponVO> getApply() {
//
//		List<CouponVO> list = new ArrayList<CouponVO>();
//		CouponVO CouponVO = null;
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_COUPON_APPLY);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//
//				CouponVO = new CouponVO();
//				CouponVO.setCoupon_num(rs.getString("Coupon_num"));
//				CouponVO.setCoupon_cash(rs.getInt("Coupon_cash"));
//				CouponVO.setCoupon_desc(rs.getString("Coupon_desc"));
//				CouponVO.setSto_num(rs.getString("sto_num"));
//				CouponVO.setLeft(rs.getInt("Left"));
//				CouponVO.setTotal(rs.getInt("Total"));
//				CouponVO.setCoupon_status(rs.getString("Coupon_status"));
//				list.add(CouponVO); // Store the row in the list
//			}
//
//			// Handle any driver errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return list;
//
//	}
//
//	// 更新折價券狀態為通過
//	@Override
//	public void Pass(String coupon_num, Integer total) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			con = ds.getConnection();
//			con.setAutoCommit(false);
//			// 更新狀態為通過
//			pstmt = con.prepareStatement(UPDATE_COUPON_STATUS_PASS);
//			pstmt.setString(1, coupon_num);
//			pstmt.executeUpdate();
//			// 新增折價券清單數量
//			pstmt = con.prepareStatement(CREATE_COUPON_LIST);
//			for (Integer i = 1; i <= total; i++) {
//				pstmt.setString(1, coupon_num);
//				pstmt.setInt(2, i);
//				pstmt.addBatch();
//			}
//			pstmt.executeBatch();
//			con.commit();
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			try {
//				con.rollback();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
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
//
//	}
//
//	// 查詢所有已審核折價券清單
//	@Override
//	public List<CouponVO> getFinish() {
//		List<CouponVO> list = new ArrayList<CouponVO>();
//		CouponVO CouponVO = null;
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//
//			con = ds.getConnection();
//
//			pstmt = con.prepareStatement(GET_COUPON_FINISH);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				// empVO 也稱為 Domain objects
//				CouponVO = new CouponVO();
//				CouponVO.setCoupon_num(rs.getString("Coupon_num"));
//				CouponVO.setCoupon_cash(rs.getInt("Coupon_cash"));
//				CouponVO.setCoupon_desc(rs.getString("Coupon_desc"));
//				CouponVO.setSto_num(rs.getString("sto_num"));
//				CouponVO.setUp_date(rs.getTimestamp("up_date"));
//				CouponVO.setLeft(rs.getInt("Left"));
//				CouponVO.setTotal(rs.getInt("Total"));
//				CouponVO.setNotice_desc(rs.getString("notice_desc"));
//				CouponVO.setCoupon_status(rs.getString("Coupon_status"));
//				list.add(CouponVO); // Store he list
//			}
//
//			// Handle any driver errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return list;
//	}
//
//	// 新增折價券
//	@Override
//	public void insertApply(CouponVO couponVO) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(APPLY_COUPON);
//			System.out.println("進入");
//			pstmt.setString(1, couponVO.getSto_num());
//			pstmt.setInt(2, couponVO.getCoupon_cash());
//			pstmt.setString(3, couponVO.getCoupon_desc());
//			pstmt.setInt(4, couponVO.getTotal());
//			pstmt.setInt(5, couponVO.getTotal());
//			pstmt.setTimestamp(6, couponVO.getUp_date());
//			pstmt.setTimestamp(7, couponVO.getDown_date());
//			pstmt.setTimestamp(8, couponVO.getExp_date());
//			pstmt.setString(9, couponVO.getNotice_desc());
//			pstmt.setTimestamp(10, couponVO.getNotice_up_date());
//			pstmt.setTimestamp(11, couponVO.getNotice_down_date());
//			pstmt.setBytes(12, couponVO.getImage());
//			pstmt.executeUpdate();
//
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
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
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//
//	}
//
//	@Override
//	public List<CouponVO> getOneStoCoupon(String sto_num) {
//
//		List<CouponVO> list = new ArrayList<CouponVO>();
//		CouponVO couponVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_ONE_STO_COUPON);
//			pstmt.setString(1, sto_num);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//
//				couponVO = new CouponVO();
//				couponVO.setCoupon_num(rs.getString("coupon_num"));
//				couponVO.setCoupon_cash(rs.getInt("coupon_cash"));
//				couponVO.setCoupon_desc(rs.getString("coupon_desc"));
//				couponVO.setLeft(rs.getInt("left"));
//				couponVO.setTotal(rs.getInt("total"));
//				couponVO.setApply_date(rs.getTimestamp("apply_date"));
//				couponVO.setUp_date(rs.getTimestamp("up_date"));
//				couponVO.setDown_date(rs.getTimestamp("down_date"));
//				couponVO.setExp_date(rs.getTimestamp("exp_date"));
//				couponVO.setNotice_desc(rs.getString("notice_desc"));
//				couponVO.setNotice_down_date(rs.getTimestamp("notice_down_date"));
//				couponVO.setNotice_up_date(rs.getTimestamp("notice_up_date"));
//				couponVO.setCoupon_status(rs.getString("coupon_status"));
//				list.add(couponVO);
//			}
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return list;
//	}
//
//	@Override
//	public void noPass(String coupon_num) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			con = ds.getConnection();
//
//			// 更新狀態為通過
//			pstmt = con.prepareStatement(UPDATE_COUPON_STATUS_NOPASS);
//			pstmt.setString(1, coupon_num);
//			pstmt.executeUpdate();
//
//			// Handle any SQL errors
//		} catch (SQLException se) {
//
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
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
//
//	}
//
//	@Override
//	public List<CouponVO> getCoupon() {
//		List<CouponVO> list = new ArrayList<CouponVO>();
//		CouponVO CouponVO = null;
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//
//			con = ds.getConnection();
//
//			pstmt = con.prepareStatement(GET_COUPON);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				// empVO 也稱為 Domain objects
//				CouponVO = new CouponVO();
//				CouponVO.setCoupon_num(rs.getString("Coupon_num"));
//				CouponVO.setCoupon_cash(rs.getInt("Coupon_cash"));
//				CouponVO.setCoupon_desc(rs.getString("Coupon_desc"));
//				CouponVO.setSto_num(rs.getString("sto_num"));
//				CouponVO.setUp_date(rs.getTimestamp("up_date"));
//				CouponVO.setLeft(rs.getInt("Left"));
//				CouponVO.setTotal(rs.getInt("Total"));
//				CouponVO.setDown_date(rs.getTimestamp("down_date"));
//				CouponVO.setNotice_desc(rs.getString("notice_desc"));
//				CouponVO.setCoupon_status(rs.getString("Coupon_status"));
//				list.add(CouponVO); // Store he list
//			}
//
//			// Handle any driver errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return list;
//	}
	
	
//---------end Eric---------------------------	

}
