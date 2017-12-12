package com.extra_list.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.extra.model.ExtraVO;

public class ExtraListDAO implements ExtraListDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

//-------------Peiiun---------------
	private static final String INSERT_EXTRALIST = "INSERT INTO EXTRA_LIST (ORDET_NUM, EXT_NUM) VALUES (?, ?)";
	private static final String GET_EXTRA = "SELECT * FROM EXTRA WHERE EXT_NUM=?";
	private static final String GET_EXT_NUM = "SELECT * FROM EXTRA_LIST WHERE ORDET_NUM=?";
	private static final String DELETE_EXTRA = "DELETE FROM EXTRA_LIST WHERE ORDET_NUM=?";

	@Override
	public void insert(ExtraListVO extraListVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_EXTRALIST);

			con.setAutoCommit(false);

			pstmt.setString(1, extraListVO.getOrdet_num());
			pstmt.setString(2, extraListVO.getExt_num());

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
	public ExtraVO getExtra(ExtraListVO extraListVO) {
		ExtraVO extVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_EXTRA);

			pstmt.setString(1, extraListVO.getExt_num());

			rs = pstmt.executeQuery();

			rs.next();
			extVO = new ExtraVO();
			extVO.setExt_num(rs.getString("EXT_NUM"));
			extVO.setExt_name(rs.getString("EXT_NAME"));
			extVO.setExt_amount(rs.getInt("EXT_AMOUNT"));
			extVO.setSto_num(rs.getString("STO_NUM"));
			extVO.setStatus(rs.getString("STATUS"));

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
		return extVO;
	}

	@Override
	public void deleteExtraList(ExtraListVO extraListVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_EXTRA);

			con.setAutoCommit(false);

			pstmt.setString(1, extraListVO.getOrdet_num());

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
	public List<ExtraListVO> getExtraNum(String OrdetNum) {
		ExtraListVO extraListVO = null;
		List<ExtraListVO> list = new ArrayList<ExtraListVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_EXT_NUM);

			pstmt.setString(1, OrdetNum);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				extraListVO = new ExtraListVO();
				extraListVO.setExt_num(rs.getString("EXT_NUM"));
				extraListVO.setOrdet_num(rs.getString("ORDET_NUM"));
				
				list.add(extraListVO);
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
		return list;
	}
	
//-------------end Peiiun---------------
	
	
	
//------------Shawn---------------------
	private static final String GET_EXT_NUM_SHAWN = 
			"SELECT * FROM EXTRA_LIST WHERE ordet_num=?";
	
	@Override
	public Set<ExtraListVO> getExtNum(String ordet_num) {
		ExtraListVO extraListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Set<ExtraListVO> setExtraList = new LinkedHashSet<ExtraListVO>();

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_EXT_NUM_SHAWN);
			
			pstmt.setString(1, ordet_num);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				extraListVO = new ExtraListVO();

				extraListVO.setOrdet_num(rs.getString("ordet_num"));
				extraListVO.setExt_num(rs.getString("ext_num"));
				setExtraList.add(extraListVO);

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
		return setExtraList;
	}
	
//----------end Shawn-------------------	
	
//----------Eric----------------------
	

//	private static final String INSERT_EXTRALIST = "INSERT INTO EXTRA_LIST (ORDET_NUM, EXT_NUM) VALUES (?, ?)";
//	private static final String GET_EXTRA = "SELECT * FROM EXTRA WHERE EXT_NUM=?";
//	private static final String DELETE_EXTRA = "DELETE FROM EXTRA_LIST WHERE ORDET_NUM=?";
	
	private static final String GET_LIST_BY_ODNUM = "select * from extra_list where ordet_num = ?";

	
	@Override
	public List<ExtraListVO> getListByOdNum(String ordet_num) {
		List<ExtraListVO> exList = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIST_BY_ODNUM);

			pstmt.setString(1, ordet_num);

			rs = pstmt.executeQuery();
			
			while(rs.next()){
				ExtraListVO exVO = new ExtraListVO();
				exVO.setExt_num(rs.getString("ext_num"));
				exVO.setOrdet_num(rs.getString("ordet_num"));
				
//				System.out.println("ext_num " + exVO.getExt_num());
//				System.out.println("ordet_num " + exVO.getOrdet_num());
				exList.add(exVO);
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
		
		return exList;
	}

	
	
//	@Override
//	public void insert(ExtraListVO extraListVO) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(INSERT_EXTRALIST);
//
//			con.setAutoCommit(false);
//
//			pstmt.setString(1, extraListVO.getOrdet_num());
//			pstmt.setString(2, extraListVO.getExt_num());
//
//			pstmt.executeUpdate();
//
//			con.commit();
//
//		} catch (SQLException se) {
//			try {
//				con.rollback();
//				throw new RuntimeException("A database error occured. " + se.getMessage());
//			} catch (SQLException see) {
//				see.printStackTrace();
//			}
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
//	}
//
//	@Override
//	public ExtraVO getExtra(ExtraListVO extraListVO) {
//		ExtraVO extVO = null;
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_EXTRA);
//
//			pstmt.setString(1, extraListVO.getExt_num());
//
//			rs = pstmt.executeQuery();
//
//			rs.next();
//			extVO = new ExtraVO();
//			extVO.setExt_num(rs.getString("EXT_NUM"));
//			extVO.setExt_name(rs.getString("EXT_NAME"));
//			extVO.setExt_amount(rs.getInt("EXT_AMOUNT"));
//			extVO.setSto_num(rs.getString("STO_NUM"));
//			extVO.setStatus(rs.getString("STATUS"));
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
//		return extVO;
//	}
//
//	@Override
//	public void deleteExtraList(ExtraListVO extraListVO) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(DELETE_EXTRA);
//
//			con.setAutoCommit(false);
//
//			pstmt.setString(1, extraListVO.getOrdet_num());
//
//			pstmt.executeUpdate();
//
//			con.commit();
//
//		} catch (SQLException se) {
//			try {
//				con.rollback();
//				throw new RuntimeException("A database error occured. " + se.getMessage());
//			} catch (SQLException see) {
//				see.printStackTrace();
//			}
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
//	}
	
//---------end Eric-----------------	
}