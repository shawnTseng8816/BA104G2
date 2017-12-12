package com.sweetness.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.store_profile.model.StoreProfileVO;

public class SweetnessDAO implements SweetnessDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}


	//INSERT改成序列版本
		private static final String INSERT_SWEETNESS = 
				"INSERT INTO SWEETNESS (SWEET_NUM , STO_NUM , SWEET_TYPE, STATUS)"
				+ " VALUES ('SW'||LPAD(to_char(SEQ_SWEET_NUM.NEXTVAL),10,'0'),?,?,?)";
		private static final String UPDATE =
				"UPDATE SWEETNESS SET SWEET_TYPE=?,STATUS=? WHERE SWEET_NUM=?";
		private static final String GET_SWEETNESS =
				"SELECT * FROM SWEETNESS WHERE STO_NUM=? AND STATUS<>'刪除' ORDER BY SWEET_NUM DESC";
		private static final String GET_ONE_SWEETNESS=
				"SELECT * FROM SWEETNESS WHERE SWEET_NUM=?";
		
//	private static final String INSERT_SWEETNESS = "INSERT INTO SWEETNESS STO_NUM=?, SWEET_TYPE=?, STATUS=?";
//	private static final String UPDATE = "UPDATE SWEETNESS SET STATUS=? WHERE SWEET_NUM=?";
	private static final String GET_SWEETNESS_KAHN = "SELECT * FROM SWEETNESS WHERE STO_NUM=?";
	private static final String GET_SWEETTYPE_KAHN = "SELECT * FROM SWEETNESS WHERE SWEET_NUM=?";

	
	
	
	@Override
	public String insert(SweetnessVO sweetnessVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		String sweet_num = null;
		try {
			String[] col = {"sweet_num"};
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_SWEETNESS,col);

			pstmt.setString(1, sweetnessVO.getSto_num());
			pstmt.setString(2, sweetnessVO.getSweet_type());
			pstmt.setString(3, sweetnessVO.getStatus());

			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				do {
					for (int i = 1; i <= columnCount; i++) {
						sweet_num = rs.getString(i);						
					}
				} while (rs.next());
			}			
			rs.close();

		} catch (SQLException se) {
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
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}			
		}
		return sweet_num;
	}

	@Override
	public void update(SweetnessVO sweetnessVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, sweetnessVO.getSweet_type());
			pstmt.setString(2, sweetnessVO.getStatus());
			pstmt.setString(3, sweetnessVO.getSweet_num());

			pstmt.executeUpdate();

		} catch (SQLException se) {
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
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public List<SweetnessVO> getSweetness(String sto_num) {

		SweetnessVO sweetnessVO = null;
		List<SweetnessVO> sweetList = new ArrayList<SweetnessVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_SWEETNESS);
			pstmt.setString(1, sto_num);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				sweetnessVO = new SweetnessVO();
				sweetnessVO.setSweet_num(rs.getString("sweet_num"));
				sweetnessVO.setSto_num(sto_num);
				sweetnessVO.setSweet_type(rs.getString("sweet_type"));
				sweetnessVO.setStatus(rs.getString("status"));
				sweetList.add(sweetnessVO);
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
		return sweetList;
	}
	
	@Override
	public SweetnessVO getOneSweetness(String sweet_num){
		
		SweetnessVO sweetnessVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_SWEETNESS);			
			pstmt.setString(1, sweet_num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				sweetnessVO = new SweetnessVO();
				sweetnessVO.setSweet_num(rs.getString("sweet_num"));
				sweetnessVO.setSto_num(rs.getString("sto_num"));
				sweetnessVO.setSweet_type(rs.getString("sweet_type"));
				sweetnessVO.setStatus(rs.getString("status"));
			}
			
		} catch (Exception se){
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return sweetnessVO;
	}
	
	
//	@Override
//	public void insert(SweetnessVO sweetnessVO) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(INSERT_SWEETNESS);
//
//			con.setAutoCommit(false);
//
//			pstmt.setString(1, sweetnessVO.getSto_num());
//			pstmt.setString(2, sweetnessVO.getSweet_type());
//			pstmt.setString(3, sweetnessVO.getStatus());
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
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//	}
//
//	@Override
//	public void update(SweetnessVO sweetnessVO) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(UPDATE);
//
//			con.setAutoCommit(false);
//
//			pstmt.setString(1, sweetnessVO.getStatus());
//			pstmt.setString(2, sweetnessVO.getSweet_num());
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
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//	}

	@Override
	public List<SweetnessVO> getSweetnessKAHN(SweetnessVO sweetnessVO) {
		SweetnessVO sweetVO = null;
		List<SweetnessVO> sweetList = new ArrayList<SweetnessVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_SWEETNESS_KAHN);

			pstmt.setString(1, sweetnessVO.getSto_num());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				sweetVO = new SweetnessVO();
				sweetVO.setSweet_num(rs.getString("SWEET_NUM"));
				sweetVO.setSweet_type(rs.getString("SWEET_TYPE"));
				sweetVO.setStatus(rs.getString("STATUS"));
				sweetList.add(sweetVO);
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
		return sweetList;
	}

	@Override
	public SweetnessVO getSweetTypeKAHN(SweetnessVO sweetnessVO) {
		SweetnessVO sweetVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_SWEETTYPE_KAHN);

			pstmt.setString(1, sweetnessVO.getSweet_num());

			rs = pstmt.executeQuery();

			rs.next();
			sweetVO = new SweetnessVO();
			sweetVO.setSweet_num(rs.getString("SWEET_NUM"));
			sweetVO.setSweet_type(rs.getString("SWEET_TYPE"));
			sweetVO.setStatus(rs.getString("STATUS"));

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
		return sweetVO;
	}
	
	
	
	
//----------Shawn----------
	private static final String GET_SWEET_NAME = 
			"SELECT sweet_num, sto_num, sweet_type, status FROM SWEETNESS WHERE sweet_num=?";


	@Override
	public SweetnessVO getSweetName(String sweet_num) {
		SweetnessVO sweetnessVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_SWEET_NAME);
			
			pstmt.setString(1, sweet_num);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				sweetnessVO = new SweetnessVO();

				sweetnessVO.setSweet_num(rs.getString("sweet_num"));
				sweetnessVO.setSto_num(rs.getString("sto_num"));
				sweetnessVO.setSweet_type(rs.getString("sweet_type"));
				sweetnessVO.setStatus(rs.getString("status"));

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
		return sweetnessVO;
	}
	
//-------end Shawn-------------	
	
	
//-----------Eric--------------
	
	private static final String INSERT_SWEETNESS_ERIC = "INSERT INTO SWEETNESS STO_NUM=?, SWEET_TYPE=?, STATUS=?";
	private static final String UPDATE_ERIC  = "UPDATE SWEETNESS SET STATUS=? WHERE SWEET_NUM=?";
	private static final String GET_SWEETNESS_ERIC  = "SELECT * FROM SWEETNESS WHERE STO_NUM=?";
	private static final String GET_SWEETTYPE = "SELECT * FROM SWEETNESS WHERE SWEET_NUM=?";

	@Override
	public void insert_ERIC(SweetnessVO sweetnessVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_SWEETNESS_ERIC );

			con.setAutoCommit(false);

			pstmt.setString(1, sweetnessVO.getSto_num());
			pstmt.setString(2, sweetnessVO.getSweet_type());
			pstmt.setString(3, sweetnessVO.getStatus());

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
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update_ERIC(SweetnessVO sweetnessVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_ERIC );

			con.setAutoCommit(false);

			pstmt.setString(1, sweetnessVO.getStatus());
			pstmt.setString(2, sweetnessVO.getSweet_num());

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
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public List<SweetnessVO> getSweetness(SweetnessVO sweetnessVO) {
		SweetnessVO sweetVO = null;
		List<SweetnessVO> sweetList = new ArrayList<SweetnessVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_SWEETNESS_ERIC );

			pstmt.setString(1, sweetnessVO.getSto_num());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				sweetVO = new SweetnessVO();
				sweetVO.setSweet_num(rs.getString("SWEET_NUM"));
				sweetVO.setSweet_type(rs.getString("SWEET_TYPE"));
				sweetVO.setStatus(rs.getString("STATUS"));
				sweetList.add(sweetVO);
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
		return sweetList;
	}

	@Override
	public SweetnessVO getSweetType(SweetnessVO sweetnessVO) {
		SweetnessVO sweetVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_SWEETTYPE);

			pstmt.setString(1, sweetnessVO.getSweet_num());

			rs = pstmt.executeQuery();

			rs.next();
			sweetVO = new SweetnessVO();
			sweetVO.setSweet_num(rs.getString("SWEET_NUM"));
			sweetVO.setSweet_type(rs.getString("SWEET_TYPE"));
			sweetVO.setStatus(rs.getString("STATUS"));

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
		return sweetVO;
	}
	
//----------end Eric-----------	
	
	
}