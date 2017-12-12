package com.coupon_list.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.coupon.model.CouponVO;

public class CouponListDAO implements CouponListDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	
//--------------KAHN----------------------	
	private static final String UPDATE_GET_COUPON_MEM = "UPDATE COUPON_LIST SET MEM_NUM =?,GET_DATE= ? WHERE Coupon_num = ? AND COUPON_AMOUNT=?";

	private static final String GET_ONE_MEM_COUPON = "SELECT * FROM COUPON_LIST WHERE MEM_NUM=?";

	private static final String GET_MY_COUPON = "SELECT * FROM COUPON_LIST WHERE MEM_NUM=? AND USED_DATE IS NULL AND COUPON_NUM IN (SELECT COUPON_NUM FROM COUPON WHERE STO_NUM=? AND COUPON_STATUS=? AND EXP_DATE >= SYSTIMESTAMP)";

	// 更新折價券剩餘張數
	private static final String UPDATE_COUPON_LEFT = "UPDATE COUPON SET LEFT =? WHERE Coupon_num = ?";

	private static final String GET_COUPON_INFO = "SELECT * FROM COUPON_LIST WHERE COUPON_AMOUNT=? AND COUPON_NUM=?";

	private static final String UPDATE_COUPON_USE = "UPDATE COUPON_LIST SET USED_DATE=?, OR_NUM=? WHERE COUPON_AMOUNT=? AND COUPON_NUM=?";

	// 搶折價券
	@Override
	public void getcoupon(CouponListVO coupon_ListVO, Integer left) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			long t = System.currentTimeMillis();
			Timestamp ts = new Timestamp(t);

			con = ds.getConnection();
			// 更新折價券清單新增取得會員
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_GET_COUPON_MEM);
			pstmt.setString(1, coupon_ListVO.getMem_num());
			pstmt.setTimestamp(2, ts);
			pstmt.setString(3, coupon_ListVO.getCoupon_num());
			pstmt.setInt(4, left);
			pstmt.executeUpdate();

			// 更改折價券的剩餘張數
			pstmt = con.prepareStatement(UPDATE_COUPON_LEFT);
			pstmt.setInt(1, left - 1);
			pstmt.setString(2, coupon_ListVO.getCoupon_num());
			pstmt.executeUpdate();
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

	@Override
	public List<CouponListVO> getMycoupon(String mem_num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<CouponListVO> list = new ArrayList<CouponListVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_MEM_COUPON);
			pstmt.setString(1, mem_num);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CouponListVO couponListVO = new CouponListVO();

				couponListVO.setUsed_date(rs.getTimestamp("used_date"));
				// couponListVO.setCoupon_amount(rs.getInt("coupon_amount"));
				couponListVO.setGet_date(rs.getTimestamp("get_date"));
				couponListVO.setCoupon_num(rs.getString("coupon_num"));
				couponListVO.setOr_num(rs.getString("or_num"));
				list.add(couponListVO);

			}

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

	@Override
	public List<CouponListVO> getMyCoupons(String mem_num, CouponVO couponVO) {
		List<CouponListVO> list = new ArrayList<CouponListVO>();
		CouponListVO couponListVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_MY_COUPON);
			pstmt.setString(1, mem_num);
			pstmt.setString(2, couponVO.getSto_num());
			pstmt.setString(3, couponVO.getCoupon_status());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				couponListVO = new CouponListVO();
				couponListVO.setUsed_date(rs.getTimestamp("used_date"));
				couponListVO.setCoupon_amount(rs.getInt("coupon_amount"));
				couponListVO.setGet_date(rs.getTimestamp("get_date"));
				couponListVO.setCoupon_num(rs.getString("coupon_num"));
				couponListVO.setOr_num(rs.getString("or_num"));
				list.add(couponListVO);
			}

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

	@Override
	public CouponListVO getCouponInfo(CouponListVO couponListVO) {
		CouponListVO couListVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_COUPON_INFO);

			pstmt.setInt(1, couponListVO.getCoupon_amount());
			pstmt.setString(2, couponListVO.getCoupon_num());

			rs = pstmt.executeQuery();

			rs.next();
			couListVO = new CouponListVO();

			couListVO.setUsed_date(rs.getTimestamp("used_date"));
			// couponListVO.setCoupon_amount(rs.getInt("coupon_amount"));
			couListVO.setGet_date(rs.getTimestamp("get_date"));
			// couListVO.setCoupon_num(rs.getString("coupon_num"));
			couListVO.setOr_num(rs.getString("or_num"));

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
		return couListVO;
	}

	@Override
	public void updateCouponUse(CouponListVO coupon_ListVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(UPDATE_COUPON_USE);
			pstmt.setTimestamp(1, new java.sql.Timestamp(new Date().getTime()));
			pstmt.setString(2, coupon_ListVO.getOr_num());
			pstmt.setInt(3, coupon_ListVO.getCoupon_amount());
			pstmt.setString(4, coupon_ListVO.getCoupon_num());
			pstmt.executeUpdate();

			con.commit();
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
//----------end KAHN---------------------
	
	
//------------Eric----------------------
	
//	private static final String UPDATE_GET_COUPON_MEM = "UPDATE COUPON_LIST SET MEM_NUM =?,GET_DATE= ? WHERE Coupon_num = ? AND COUPON_AMOUNT=?";
//
//	private static final String GET_ONE_MEM_COUPON = "SELECT * FROM COUPON_LIST WHERE MEM_NUM=?";
//
//	//更新折價券剩餘張數
//	private static final String UPDATE_COUPON_LEFT = "UPDATE COUPON SET LEFT =? WHERE Coupon_num = ?";
	
	private static final String GET_COUPON_CASH = "SELECT C.COUPON_CASH FROM COUPON C JOIN COUPON_LIST CL" 
													+" ON C.COUPON_NUM = CL.COUPON_NUM AND CL.OR_NUM =?";
	@Override
	 public int getCouponCash(String or_num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		int cash = 0;
	
			try {
				con =ds.getConnection();
				pstmt = con.prepareStatement(GET_COUPON_CASH);
				pstmt.setString(1,  or_num);
				rs =pstmt.executeQuery();
				if(rs.next()){
					cash = rs.getInt("coupon_cash");		
				}
				
				
				
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
			return cash;

		
		
	 }

	//搶折價券
//		@Override
//		public void getcoupon(CouponListVO coupon_ListVO,Integer left) {
//			Connection con = null;
//			PreparedStatement pstmt = null;
//
//			try {
//
//				long t = System.currentTimeMillis();
//				Timestamp ts = new Timestamp(t);
//				
//				con = ds.getConnection();
//				//更新折價券清單新增取得會員
//				con.setAutoCommit(false);
//				pstmt = con.prepareStatement(UPDATE_GET_COUPON_MEM);
//				pstmt.setString(1, coupon_ListVO.getMem_num());
//				pstmt.setTimestamp(2, ts);
//				pstmt.setString(3, coupon_ListVO.getCoupon_num());
//				pstmt.setInt(4, left);
//				pstmt.executeUpdate();
//				
//				//更改折價券的剩餘張數
//				pstmt = con.prepareStatement(UPDATE_COUPON_LEFT);
//				pstmt.setInt(1, left-1);
//				pstmt.setString(2, coupon_ListVO.getCoupon_num());
//				pstmt.executeUpdate();
//				con.commit();
//				// Handle any SQL errors
//			} catch (SQLException se) {
//				try {
//					con.rollback();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				throw new RuntimeException("A database error occured. " + se.getMessage());
//				// Clean up JDBC resources
//			} finally {
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (con != null) {
//					try {
//						con.setAutoCommit(true);
//						con.close();
//					} catch (Exception e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
//
//		}
//		


//		@Override
//		public List<CouponListVO> getMycoupon(String mem_num) {
//			Connection con = null;
//			PreparedStatement pstmt = null;
//			ResultSet rs= null;
//			
//			List<CouponListVO> list = new ArrayList<CouponListVO>();
//				try {
//					con =ds.getConnection();
//					pstmt = con.prepareStatement(GET_ONE_MEM_COUPON);
//					pstmt.setString(1, mem_num);
//					rs =pstmt.executeQuery();
//					while(rs.next()){
//						CouponListVO couponListVO = new CouponListVO();
//										
//						couponListVO.setUsed_date(rs.getTimestamp("used_date"));
//						//couponListVO.setCoupon_amount(rs.getInt("coupon_amount"));
//						couponListVO.setGet_date(rs.getTimestamp("get_date"));
//						couponListVO.setCoupon_num(rs.getString("coupon_num"));
//						couponListVO.setOr_num(rs.getString("or_num"));	
//						list.add(couponListVO);
//				
//					}
//					
//					
//					
//				} catch (SQLException se) {
//					throw new RuntimeException("A database error occured. " + se.getMessage());
//					// Clean up JDBC resources
//				} finally {
//					if (rs != null) {
//						try {
//							rs.close();
//						} catch (SQLException se) {
//							se.printStackTrace(System.err);
//						}
//					}
//					if (pstmt != null) {
//						try {
//							pstmt.close();
//						} catch (SQLException se) {
//							se.printStackTrace(System.err);
//						}
//					}
//					if (con != null) {
//						try {
//							con.close();
//						} catch (Exception e) {
//							e.printStackTrace(System.err);
//						}
//					}
//				}
//				return list;
//
//		}
//	
//-----------end Eric------------------	
	
}
