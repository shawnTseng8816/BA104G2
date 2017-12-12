package com.favorite_store.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.store_profile.model.StoreProfileVO;

public class FavoriteStoreDAO implements Favorite_StoreDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
//----------------KAHN------------------------
	private static final String UPDATE = "UPDATE FAVORITE_STORE SET IS_FAVO=? WHERE MEM_NUM=? AND STO_NUM=?";
	private static final String GET_FAVORITESTORES = "SELECT STO_NUM, STO_NAME, MOBILE, AREA, ADDRESS, STO_STATUS FROM STORE_PROFILE WHERE STO_NUM IN (SELECT DISTINCT STO_NUM FROM FAVORITE_STORE WHERE MEM_NUM=? AND IS_FAVO=?)";

	@Override
	public void update(FavoriteStoreVO favoritestoreVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			con.setAutoCommit(false);

			pstmt.setString(1, favoritestoreVO.getIs_favo());
			pstmt.setString(2, favoritestoreVO.getMem_num());
			pstmt.setString(3, favoritestoreVO.getSto_num());

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
	public List<StoreProfileVO> getFavoriteStore(FavoriteStoreVO favoritestoreVO) {
		List<StoreProfileVO> list = new ArrayList<StoreProfileVO>();
		StoreProfileVO storeProfileVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_FAVORITESTORES);

			pstmt.setString(1, favoritestoreVO.getMem_num());
			pstmt.setString(2, favoritestoreVO.getIs_favo());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				storeProfileVO = new StoreProfileVO();
				storeProfileVO.setSto_num(rs.getString("STO_NUM"));
				storeProfileVO.setSto_name(rs.getString("STO_NAME"));
				storeProfileVO.setMobile(rs.getString("MOBILE"));
				storeProfileVO.setArea(rs.getString("AREA"));
				storeProfileVO.setAddress(rs.getString("ADDRESS"));
				storeProfileVO.setSto_status(rs.getString("STO_STATUS"));
				list.add(storeProfileVO);
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
//----------------end KAHN------------------------	
	
	
//-----------------Shawn--------------------------
	
	private static final String GET_ONE_FAVORITE_STORE = 
			"SELECT mem_num, sto_num, is_favo,to_char(chang_time,'yyyy-mm-dd')chang_time FROM FAVORITE_STORE WHERE mem_num=? AND sto_num=?";
	private static final String INSERT_FAVORITE_STORE = 
			"INSERT INTO FAVORITE_STORE (mem_num,sto_num,is_favo,chang_time) VALUES (?, ?, ?, ?)";
	private static final String UPDATE_FAVORITE_STORE = 
			"UPDATE FAVORITE_STORE set is_favo=? ,chang_time=? WHERE mem_num=? AND sto_num=?";
	
	
	
	@Override
	public FavoriteStoreVO getOneFavoriteStore(String mem_num, String sto_num) {
		
		FavoriteStoreVO favoriteStoreVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_FAVORITE_STORE);
			
			pstmt.setString(1, mem_num);
			pstmt.setString(2, sto_num);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				favoriteStoreVO = new FavoriteStoreVO();

				favoriteStoreVO.setMem_num(rs.getString("mem_num"));
				favoriteStoreVO.setSto_num(rs.getString("sto_num"));
				favoriteStoreVO.setIs_favo(rs.getString("is_favo"));
				favoriteStoreVO.setChang_time(rs.getDate("chang_time"));

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
		return favoriteStoreVO;
		
	}

	
	
	
	
	@Override
	public void insertFavoriteStore(FavoriteStoreVO favoriteStoreVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_FAVORITE_STORE);

			pstmt.setString(1, favoriteStoreVO.getMem_num());
			pstmt.setString(2, favoriteStoreVO.getSto_num());
			pstmt.setString(3, favoriteStoreVO.getIs_favo());
			pstmt.setTimestamp(4, new java.sql.Timestamp( favoriteStoreVO.getChang_time().getTime()));
			

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void updateFavoriteStore(FavoriteStoreVO favoriteStoreVO) {
		
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_FAVORITE_STORE);

			pstmt.setString(1, favoriteStoreVO.getIs_favo());
			pstmt.setTimestamp(2, new java.sql.Timestamp(favoriteStoreVO.getChang_time().getTime()));
			pstmt.setString(3, favoriteStoreVO.getMem_num());
			pstmt.setString(4, favoriteStoreVO.getSto_num());
	

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	
//-----------------end Shawn-----------------------	
	
	
}