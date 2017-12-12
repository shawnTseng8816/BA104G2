package com.merged_order.model;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MergedOrderDAO implements MergedOrderDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT = "INSERT INTO MERGED_ORDER (MEROR_NUM, MEM_NUM, TOL_AMOUNT) VALUES ('MO'||TRIM(TO_CHAR(SEQ_MEROR_NUM.NEXTVAL, '00000000')), ?, ?)";
	private static final String UPDATE = "UPDATE MERGED_ORDER SET TOL_AMOUNT=? WHERE MEROR_NUM=? AND MEM_NUM=?";
	private static final String GET_MERGEDORDER = "SELECT * FROM MERGED_ORDER WHERE MEROR_NUM=?";

	@Override
	public String insert(MergedOrderVO mergedOrderVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String key = null;

		try {

			con = ds.getConnection();
			
			String[] cols = {"MEROR_NUM"}; // 或 int cols[] = {1};

			pstmt = con.prepareStatement(INSERT, cols);

			con.setAutoCommit(false);
			
			pstmt.setString(1, mergedOrderVO.getMem_num());
			pstmt.setInt(2, mergedOrderVO.getTol_amount());

			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			rs.next();
			key = rs.getString(1);
			
			con.commit();
			
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
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return key;
	}

	@Override
	public MergedOrderVO getMergedOrder(MergedOrderVO mergedOrderVO) {
		MergedOrderVO merVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MERGEDORDER);

			pstmt.setString(1, mergedOrderVO.getMeror_num());

			rs = pstmt.executeQuery();

			rs.next();
			merVO = new MergedOrderVO();
			merVO.setMem_num(rs.getString("MEM_NUM"));
			merVO.setTol_amount(rs.getInt("TOL_AMOUNT"));

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
		return merVO;
	}

	@Override
	public void update(MergedOrderVO mergedOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE);

			con.setAutoCommit(false);
			
			
			pstmt.setInt(1, mergedOrderVO.getTol_amount());
			pstmt.setString(2, mergedOrderVO.getMeror_num());
			pstmt.setString(3, mergedOrderVO.getMem_num());

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
	
//------------Eric------------
	
	private static final String INSERT_ERIC = "INSERT INTO MERGED_ORDER (MEM_NUM, TOL_AMOUNT) VALUES (?, ?)";
//	private static final String GET_MERGEDORDER = "SELECT * FROM MERGED_ORDER WHERE MEROR_NUM=?";

	@Override
	public void insertEric(MergedOrderVO mergedOrderVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(INSERT_ERIC);

			con.setAutoCommit(false);

			pstmt.setString(1, mergedOrderVO.getMem_num());
			pstmt.setInt(2, mergedOrderVO.getTol_amount());

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

//	@Override
//	public MergedOrderVO getMergedOrder(MergedOrderVO mergedOrderVO) {
//		MergedOrderVO merVO = null;
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_MERGEDORDER);
//
//			pstmt.setString(1, mergedOrderVO.getMeror_num());
//
//			rs = pstmt.executeQuery();
//
//			rs.next();
//			merVO = new MergedOrderVO();
//			merVO.setMem_num(rs.getString("MEM_NUM"));
//			merVO.setTol_amount(rs.getInt("TOL_AMOUNT"));
//
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
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
//		return merVO;
//	}
	
//-------end Eric---------------	
}