package com.ice_list.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class IceListDAO implements IceListDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	
//--------------KHAN------------------------
	private static final String INSERT_ICELIST_KHAN = "INSERT INTO ICE_LIST STO_NUM=?, ICE_TYPE=?, STATUS=?";
	private static final String UPDATE_KHAN = "UPDATE ICE_LIST SET STATUS=? WHERE ICE_NUM=?";
	private static final String GET_ICELIST_KHAN = "SELECT * FROM ICE_LIST WHERE STO_NUM=? AND STATUS<>'刪除' ORDER BY ICE_NUM DESC";
	private static final String GET_ICETYPE_KHAN = "SELECT * FROM ICE_LIST WHERE ICE_NUM=?";

	@Override
	public void insertKHAN(IceListVO iceListVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_ICELIST_KHAN);

			con.setAutoCommit(false);

			pstmt.setString(1, iceListVO.getSto_num());
			pstmt.setString(2, iceListVO.getIce_type());
			pstmt.setString(3, iceListVO.getStatus());

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
	public void updateKHAN(IceListVO iceListVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_KHAN);

			con.setAutoCommit(false);

			pstmt.setString(1, iceListVO.getStatus());
			pstmt.setString(2, iceListVO.getIce_num());

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
	public List<IceListVO> getIceList(IceListVO iceListVO) {
		IceListVO iceVO = null;
		List<IceListVO> iceList = new ArrayList<IceListVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ICELIST_KHAN);

			pstmt.setString(1, iceListVO.getSto_num());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				iceVO = new IceListVO();
				iceVO.setIce_num(rs.getString("ICE_NUM"));
				iceVO.setIce_type(rs.getString("ICE_TYPE"));
				iceVO.setSto_num(rs.getString("sto_num"));
				iceVO.setStatus(rs.getString("STATUS"));
				iceList.add(iceVO);
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
		return iceList;
	}

	@Override
	public IceListVO getIceType(IceListVO iceListVO) {
		IceListVO iceVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ICETYPE_KHAN);

			pstmt.setString(1, iceListVO.getIce_num());

			rs = pstmt.executeQuery();

			rs.next();
			iceVO = new IceListVO();
			iceVO.setIce_num(rs.getString("ICE_NUM"));
			iceVO.setIce_type(rs.getString("ICE_TYPE"));
			iceVO.setStatus(rs.getString("STATUS"));

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
		return iceVO;
	}

//--------------end KHAN------------------------

//------------------Peiiun--------------------------
	
	private static final String INSERT_ICELIST = 
			"INSERT INTO ICE_LIST (ICE_NUM,STO_NUM,ICE_TYPE,STATUS)"
			+ " VALUES ('IC'||LPAD(to_char(SEQ_ICE_NUM.NEXTVAL),10,'0'),?,?,?)";
	private static final String UPDATE =
			"UPDATE ICE_LIST SET ICE_TYPE=?,STATUS=? WHERE ICE_NUM=?";
	private static final String GET_ICELIST = 
			"SELECT * FROM ICE_LIST WHERE STO_NUM=? AND STATUS<>'刪除' ORDER BY ICE_NUM DESC";
	private static final String GET_ONE_ICE=
			"SELECT * FROM ICE_LIST WHERE ICE_NUM=?";
	@Override
	public String insert(IceListVO iceListVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		String ice_num = null;
		try {
			String[] col = {"ice_num"};
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_ICELIST,col);				

			pstmt.setString(1, iceListVO.getSto_num());
			pstmt.setString(2, iceListVO.getIce_type());
			pstmt.setString(3, iceListVO.getStatus());

			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				do {
					for (int i = 1; i <= columnCount; i++) {
						ice_num = rs.getString(i);						
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
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return ice_num;
	}

	@Override
	public void update(IceListVO iceListVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, iceListVO.getIce_type());
			pstmt.setString(2, iceListVO.getStatus());
			pstmt.setString(3, iceListVO.getIce_num());

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
	public List<IceListVO> getIceList(String sto_num) {
		IceListVO iceVO = null;
		List<IceListVO> iceList = new ArrayList<IceListVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ICELIST);
			pstmt.setString(1, sto_num);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				iceVO = new IceListVO();
				iceVO.setIce_num(rs.getString("ICE_NUM"));
				iceVO.setSto_num(sto_num);
				iceVO.setIce_type(rs.getString("ICE_TYPE"));
				iceVO.setStatus(rs.getString("STATUS"));
				iceList.add(iceVO);
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
		return iceList;
	}

	@Override
	public IceListVO getOneIce(String ice_num) {
		IceListVO iceVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_ICE);
			pstmt.setString(1, ice_num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				iceVO = new IceListVO();
				iceVO.setIce_num(rs.getString("ice_num"));
				iceVO.setSto_num(rs.getString("sto_num"));
				iceVO.setIce_type(rs.getString("ice_type"));
				iceVO.setStatus(rs.getString("status"));
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
		
		return iceVO;
	}
	
	
//------------------end Peiiun--------------------------

	
//--------------------Shawn------------------------------
	
	private static final String GET_ICE_NAME = 
			"SELECT ice_num, sto_num, ice_type, status FROM ICE_LIST WHERE ice_num=?";

	@Override
	public IceListVO getIceName(String ice_num) {
		IceListVO iceListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ICE_NAME);
			
			pstmt.setString(1, ice_num);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				iceListVO = new IceListVO();

				iceListVO.setIce_num(rs.getString("ice_num"));
				iceListVO.setSto_num(rs.getString("sto_num"));
				iceListVO.setIce_type(rs.getString("ice_type"));
				iceListVO.setStatus(rs.getString("status"));

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
		return iceListVO;
	}

//----------------end Shawn----------------------------	
	
//------------Eric------------------------
//	private static final String INSERT_ICELIST = "INSERT INTO ICE_LIST STO_NUM=?, ICE_TYPE=?, STATUS=?";
//	private static final String UPDATE = "UPDATE ICE_LIST SET STATUS=? WHERE ICE_NUM=?";
//	private static final String GET_ICELIST = "SELECT * FROM ICE_LIST WHERE STO_NUM=?";
//	private static final String GET_ICETYPE = "SELECT * FROM ICE_LIST WHERE ICE_NUM=?";
//
//	@Override
//	public void insert(IceListVO iceListVO) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(INSERT_ICELIST);
//
//			con.setAutoCommit(false);
//
//			pstmt.setString(1, iceListVO.getSto_num());
//			pstmt.setString(2, iceListVO.getIce_type());
//			pstmt.setString(3, iceListVO.getStatus());
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
//	public void update(IceListVO iceListVO) {
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
//			pstmt.setString(1, iceListVO.getStatus());
//			pstmt.setString(2, iceListVO.getIce_num());
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
//	public List<IceListVO> getIceList(IceListVO iceListVO) {
//		IceListVO iceVO = null;
//		List<IceListVO> iceList = new ArrayList<IceListVO>();
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_ICELIST);
//
//			pstmt.setString(1, iceListVO.getSto_num());
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				iceVO = new IceListVO();
//				iceVO.setIce_num(rs.getString("ICE_NUM"));
//				iceVO.setIce_type(rs.getString("ICE_TYPE"));
//				iceVO.setStatus(rs.getString("STATUS"));
//				iceList.add(iceVO);
//			}
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
//		return iceList;
//	}
//
//	@Override
//	public IceListVO getIceType(IceListVO iceListVO) {
//		IceListVO iceVO = null;
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_ICETYPE);
//
//			pstmt.setString(1, iceListVO.getIce_num());
//
//			rs = pstmt.executeQuery();
//
//			rs.next();
//			iceVO = new IceListVO();
//			iceVO.setIce_num(rs.getString("ICE_NUM"));
//			iceVO.setIce_type(rs.getString("ICE_TYPE"));
//			iceVO.setStatus(rs.getString("STATUS"));
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
//		return iceVO;
//	}
	
	
//--------------end Eric---------------------	
}