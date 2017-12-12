package com.extra.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.member_profile.model.MemberProfileVO;

public class ExtraDAO implements ExtraDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	
//----------------Peiiun---------------------------	
//	private static final String INSERT_EXTRA = "INSERT INTO EXTRA (EXT_NAME, STO_NUM, EXT_AMOUNT, STATUS) VALUES (?, ?, ?, ?)";
//	private static final String UPDATE = "UPDATE EXTRA SET STATUS=? WHERE EXT_NUM=?";
	private static final String GET_EXTRAS_KAHN = "SELECT * FROM EXTRA WHERE STO_NUM=? AND STATUS=?";
	private static final String GET_EXTRAATTR = "SELECT * FROM EXTRA WHERE EXT_NUM=? AND STATUS=?";

	
	private static final String INSERT_EXTRA =
			"INSERT INTO EXTRA (EXT_NUM, EXT_NAME, STO_NUM, EXT_AMOUNT, STATUS) "
			+ "VALUES ('EX'||LPAD(to_char(SEQ_EXT_NUM.NEXTVAL),10,'0'),?, ?, ?, ?)";
	private static final String UPDATE = 
			"UPDATE EXTRA SET EXT_NAME=?, EXT_AMOUNT=?, STATUS=? WHERE EXT_NUM=?";
	private static final String GET_EXTRAS = 
			"SELECT * FROM EXTRA WHERE STO_NUM=? AND STATUS<>'刪除' ORDER BY EXT_NUM DESC";
	private static final String GET_ONE_EXT =
			"SELECT * FROM EXTRA WHERE EXT_NUM=?";
	
	
	
	@Override
	public String insert(ExtraVO extraVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		String ext_num = null;
		try {
			String[] col = {"ext_num"};
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_EXTRA,col);
			pstmt.setString(1, extraVO.getExt_name());
			pstmt.setString(2, extraVO.getSto_num());
			pstmt.setInt(3, extraVO.getExt_amount());
			pstmt.setString(4, extraVO.getStatus());
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				do {
					for (int i = 1; i <= columnCount; i++) {
						ext_num = rs.getString(i);						
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
		return ext_num;
	}

	@Override
	public void update(ExtraVO extraVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1,extraVO.getExt_name());
			pstmt.setInt(2, extraVO.getExt_amount());
			pstmt.setString(3, extraVO.getStatus());
			pstmt.setString(4, extraVO.getExt_num());
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
	public List<ExtraVO> getExtras(String sto_num) {
		List<ExtraVO> extraList = new ArrayList<ExtraVO>();
		ExtraVO extraVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_EXTRAS);
			pstmt.setString(1,sto_num);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				extraVO = new ExtraVO();
				extraVO.setExt_num(rs.getString("EXT_NUM"));
				extraVO.setExt_name(rs.getString("EXT_NAME"));
				extraVO.setSto_num(sto_num);
				extraVO.setExt_amount(rs.getInt("EXT_AMOUNT"));
				extraVO.setStatus(rs.getString("STATUS"));
				extraList.add(extraVO);
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
		return extraList;
	}

	@Override
	public ExtraVO getOneExtra(String ext_num) {
		ExtraVO extraVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_EXT);			
			pstmt.setString(1, ext_num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				extraVO = new ExtraVO();
				extraVO.setExt_num(rs.getString("ext_num"));
				extraVO.setExt_name(rs.getString("ext_name"));
				extraVO.setSto_num(rs.getString("sto_num"));
				extraVO.setExt_amount(rs.getInt("ext_amount"));
				extraVO.setStatus(rs.getString("status"));			
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
		return extraVO;
	}	
	
//	@Override
//	public void insert(ExtraVO extraVO) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(INSERT_EXTRA);
//
//			con.setAutoCommit(false);
//
//			pstmt.setString(1, extraVO.getExt_name());
//			pstmt.setString(2, extraVO.getSto_num());
//			pstmt.setInt(3, extraVO.getExt_amount());
//			pstmt.setString(4, extraVO.getStatus());
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

//	@Override
//	public void update(ExtraVO extraVO) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(UPDATE);
//
//			con.setAutoCommit(false);
//
//			pstmt.setString(1, extraVO.getStatus());
//			pstmt.setString(2, extraVO.getExt_num());
//
//			pstmt.executeUpdate();
//
//			con.commit();
//
//		} catch (SQLException se) {
//			try {
//				con.rollback();
//				throw new RuntimeException("A database error occured. " + se.getMessage());
//			} catch (SQLException e) {
//				e.printStackTrace();
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
//
//	}

	@Override
	public List<ExtraVO> getExtras(ExtraVO extraVO) {
		List<ExtraVO> extraList = new ArrayList<ExtraVO>();
		ExtraVO exVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_EXTRAS_KAHN);

			pstmt.setString(1, extraVO.getSto_num());
			pstmt.setString(2, extraVO.getStatus());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				exVO = new ExtraVO();
				exVO.setExt_name(rs.getString("EXT_NAME"));
				;
				exVO.setExt_amount(rs.getInt("EXT_AMOUNT"));
				exVO.setExt_num(rs.getString("EXT_NUM"));
				extraList.add(exVO);
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
		return extraList;
	}
	
	@Override
	public ExtraVO getExtraAttr(ExtraVO extraVO) {
		ExtraVO exVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_EXTRAATTR);

			pstmt.setString(1, extraVO.getExt_num());
			pstmt.setString(2, extraVO.getStatus());

			rs = pstmt.executeQuery();

			rs.next();
			exVO = new ExtraVO();
			exVO.setExt_name(rs.getString("EXT_NAME"));
			exVO.setExt_amount(rs.getInt("EXT_AMOUNT"));

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
		return exVO;
	}
//---------------end Peiiun-------------------------
	
	
//-----------------Shawn----------------------------
	
	private static final String GET_EXT_NAME = 
			"SELECT * FROM EXTRA WHERE ext_num=?";

	@Override
	public ExtraVO getExtName(String ext_num) {
		ExtraVO extraVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_EXT_NAME);
			
			pstmt.setString(1, ext_num);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				extraVO = new ExtraVO();
				extraVO.setExt_num(rs.getString("ext_num"));
				extraVO.setExt_name(rs.getString("ext_name"));
				extraVO.setSto_num(rs.getString("sto_num"));
				extraVO.setExt_amount(rs.getInt("ext_amount"));
				extraVO.setStatus(rs.getString("status"));


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
		return extraVO;
	}	
	
	
//---------------end Shawn-----------------------	
	
	
//-------------Eric-------------------
//	private static final String INSERT_EXTRA = "INSERT INTO EXTRA (EXT_NAME, STO_NUM, EXT_AMOUNT, STATUS) VALUES (?, ?, ?, ?)";
//	private static final String UPDATE = "UPDATE EXTRA SET STATUS=? WHERE EXT_NUM=?";
	private static final String GET_EXTRAS_ERIC = "SELECT * FROM EXTRA WHERE STO_NUM=? AND STATUS=?";
//	private static final String GET_EXTRAATTR = "SELECT * FROM EXTRA WHERE EXT_NUM=? AND STATUS=?";

//	@Override
//	public void insert(ExtraVO extraVO) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(INSERT_EXTRA);
//
//			con.setAutoCommit(false);
//
//			pstmt.setString(1, extraVO.getExt_name());
//			pstmt.setString(2, extraVO.getSto_num());
//			pstmt.setInt(3, extraVO.getExt_amount());
//			pstmt.setString(4, extraVO.getStatus());
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
//	public void update(ExtraVO extraVO) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(UPDATE);
//
//			con.setAutoCommit(false);
//
//			pstmt.setString(1, extraVO.getStatus());
//			pstmt.setString(2, extraVO.getExt_num());
//
//			pstmt.executeUpdate();
//
//			con.commit();
//
//		} catch (SQLException se) {
//			try {
//				con.rollback();
//				throw new RuntimeException("A database error occured. " + se.getMessage());
//			} catch (SQLException e) {
//				e.printStackTrace();
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
//
//	}

	@Override
	public List<ExtraVO> getExtrasEric(ExtraVO extraVO) {
		List<ExtraVO> extraList = new ArrayList<ExtraVO>();
		ExtraVO exVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_EXTRAS);

			pstmt.setString(1, extraVO.getSto_num());
			pstmt.setString(2, extraVO.getStatus());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				exVO = new ExtraVO();
				exVO.setExt_name(rs.getString("EXT_NAME"));
				;
				exVO.setExt_amount(rs.getInt("EXT_AMOUNT"));
				exVO.setExt_num(rs.getString("EXT_NUM"));
				extraList.add(exVO);
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
		return extraList;
	}
	
//	@Override
//	public ExtraVO getExtraAttr(ExtraVO extraVO) {
//		ExtraVO exVO = null;
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_EXTRAATTR);
//
//			pstmt.setString(1, extraVO.getExt_num());
//			pstmt.setString(2, extraVO.getStatus());
//
//			rs = pstmt.executeQuery();
//
//			rs.next();
//			exVO = new ExtraVO();
//			exVO.setExt_name(rs.getString("EXT_NAME"));
//			exVO.setExt_amount(rs.getInt("EXT_AMOUNT"));
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
//		return exVO;
//	}
	
	
//------------end Eric------------------	
}