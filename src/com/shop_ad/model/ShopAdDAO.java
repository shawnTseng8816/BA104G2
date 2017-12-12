package com.shop_ad.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ShopAdDAO implements ShopAdDAO_interface {

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = 
	"INSERT INTO shop_ad (sa_no,sto_num,sa_title,sa_text,sa_img,sa_views,sa_apptime,sa_addtime,sa_preofftime,ab_no,sa_addmode) VALUES ('AD'||LPAD(to_char(SEQ_AD_NO.NEXTVAL), 10, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
	private static final String GET_ALL_STMT = 
	"SELECT sa_no,sto_num,sa_title,sa_text,sa_img,sa_views,sa_apptime,sa_addtime,sa_preofftime,ab_no,sa_addmode,sa_paytime FROM shop_ad order by sa_no";
	
	private static final String GET_ALL_BY_STO_NUM = 
			"SELECT sa_no,sto_num,sa_title,sa_text,sa_img,sa_views,sa_apptime,sa_addtime,sa_preofftime,ab_no,sa_addmode,sa_paytime FROM shop_ad where sto_num = ?";
   
	private static final String GET_ONE_STMT = 
	"SELECT sa_no,sto_num,sa_title,sa_text,sa_img,sa_views,sa_apptime,sa_addtime,sa_preofftime,ab_no,sa_addmode,sa_paytime FROM shop_ad where sa_no = ?";
	
	private static final String GET_ALL_BY_SA_ADDMODE = 
			"SELECT sa_no,sto_num,sa_title,sa_text,sa_img,sa_views,sa_apptime,sa_addtime,sa_preofftime,ab_no,sa_addmode,sa_paytime FROM shop_ad where sa_addmode = ?";

//  private static final String DELETE = 
//	"DELETE FROM shop_ad where sa_no = ?";

    private static final String UPDATE = 
	"UPDATE shop_ad set sa_title=? ,sa_text=? ,sa_img=? ,sa_addtime=? ,sa_preofftime=? ,ab_no=?, sa_addmode=nvl(?, sa_addmode) where sa_no = ?";

    private static final String GET_ALL_EXCEPT_EDIT = "SELECT * FROM SHOP_AD WHERE SA_ADDMODE!='廣告編輯中'";
	
    
	@Override
	public ShopAdVO insert(ShopAdVO shopadVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			String[] col = {"sa_no"};
			pstmt = con.prepareStatement(INSERT_STMT, col);
			
			pstmt.setString(1, shopadVO.getSto_num());
			pstmt.setString(2, shopadVO.getSa_title());
			pstmt.setString(3, shopadVO.getSa_text());
			pstmt.setBytes(4, shopadVO.getSa_img());
			pstmt.setInt(5, shopadVO.getSa_views());
			pstmt.setDate(6, shopadVO.getSa_apptime());
			pstmt.setDate(7, shopadVO.getSa_addtime());
            pstmt.setDate(8, shopadVO.getSa_preofftime());
            pstmt.setString(9, shopadVO.getAb_no());
            pstmt.setString(10, shopadVO.getSa_addmode());
            
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			
			String sa_no = rs.getString(1);
			
			shopadVO.setSa_no(sa_no); 

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
		return shopadVO;

	}

	@Override
	public void update(ShopAdVO shopadVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			// UPDATE shop_ad set sa_title,sa_text,sa_img,sa_addtime,sa_preofftime,ab_no where sa_no = ?
			pstmt.setString(1, shopadVO.getSa_title());
			pstmt.setString(2, shopadVO.getSa_text());
			pstmt.setBytes(3, shopadVO.getSa_img());
			pstmt.setDate(4, shopadVO.getSa_addtime());
			pstmt.setDate(5, shopadVO.getSa_preofftime());
			pstmt.setString(6, shopadVO.getAb_no());
			
			pstmt.setString(7, shopadVO.getSa_addmode());
			pstmt.setString(8, shopadVO.getSa_no());
			
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

//	@Override
//	public void delete(String sa_no) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(DELETE);
//
//			pstmt.setInt(1, sa_no);
//
//			pstmt.executeUpdate();
//
//			// Handle any driver errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
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

	
	@Override
	public List<ShopAdVO> getAllExceptEdit() {
		List<ShopAdVO> list = new ArrayList<ShopAdVO>();
		ShopAdVO shopadVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_EXCEPT_EDIT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				shopadVO = new ShopAdVO();
				shopadVO.setSa_no(rs.getString("sa_no"));
				shopadVO.setSto_num(rs.getString("sto_num"));
				shopadVO.setSa_title(rs.getString("sa_title"));
				shopadVO.setSa_text(rs.getString("sa_text"));
				shopadVO.setSa_img(rs.getBytes("sa_img"));
				shopadVO.setSa_views(rs.getInt("sa_views"));
				shopadVO.setSa_apptime(rs.getDate("sa_apptime"));
				shopadVO.setSa_addtime(rs.getDate("sa_addtime"));
				shopadVO.setSa_preofftime(rs.getDate("sa_preofftime"));
				shopadVO.setAb_no(rs.getString("ab_no"));
				shopadVO.setSa_addmode(rs.getString("sa_addmode"));
				shopadVO.setSa_paytime(rs.getDate("sa_paytime"));
				list.add(shopadVO); // Store the row in the list
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
		return list;
	}
	
	
	@Override
	public ShopAdVO findByPrimaryKey(String sa_no) {

		ShopAdVO shopadVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, sa_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				shopadVO = new ShopAdVO();
				shopadVO.setSa_no(rs.getString("sa_no"));
				shopadVO.setSto_num(rs.getString("sto_num"));
				shopadVO.setSa_title(rs.getString("sa_title"));
				shopadVO.setSa_text(rs.getString("sa_text"));
				shopadVO.setSa_img(rs.getBytes("sa_img"));
				shopadVO.setSa_views(rs.getInt("sa_views"));
				shopadVO.setSa_apptime(rs.getDate("sa_apptime"));
				shopadVO.setSa_addtime(rs.getDate("sa_addtime"));
				shopadVO.setSa_preofftime(rs.getDate("sa_preofftime"));
				shopadVO.setAb_no(rs.getString("ab_no"));
				shopadVO.setSa_addmode(rs.getString("sa_addmode"));
				shopadVO.setSa_paytime(rs.getDate("sa_paytime"));
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
		return shopadVO;
	}

	@Override
	public List<ShopAdVO> findBySa_no(String sa_no) {
		List<ShopAdVO> list = new ArrayList<ShopAdVO>();
		ShopAdVO shopadVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, sa_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				shopadVO = new ShopAdVO();
				shopadVO.setSa_no(rs.getString("sa_no"));
				shopadVO.setSto_num(rs.getString("sto_num"));
				shopadVO.setSa_title(rs.getString("sa_title"));
				shopadVO.setSa_text(rs.getString("sa_text"));
				shopadVO.setSa_img(rs.getBytes("sa_img"));
				shopadVO.setSa_views(rs.getInt("sa_views"));
				shopadVO.setSa_apptime(rs.getDate("sa_apptime"));
				shopadVO.setSa_addtime(rs.getDate("sa_addtime"));
				shopadVO.setSa_preofftime(rs.getDate("sa_preofftime"));
				shopadVO.setAb_no(rs.getString("ab_no"));
				shopadVO.setSa_addmode(rs.getString("sa_addmode"));
				shopadVO.setSa_paytime(rs.getDate("sa_paytime"));
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
		return list;
	}
	
	@Override
	public List<ShopAdVO> getAll() {
		List<ShopAdVO> list = new ArrayList<ShopAdVO>();
		ShopAdVO shopadVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				shopadVO = new ShopAdVO();
				shopadVO.setSa_no(rs.getString("sa_no"));
				shopadVO.setSto_num(rs.getString("sto_num"));
				shopadVO.setSa_title(rs.getString("sa_title"));
				shopadVO.setSa_text(rs.getString("sa_text"));
				shopadVO.setSa_img(rs.getBytes("sa_img"));
				shopadVO.setSa_views(rs.getInt("sa_views"));
				shopadVO.setSa_apptime(rs.getDate("sa_apptime"));
				shopadVO.setSa_addtime(rs.getDate("sa_addtime"));
				shopadVO.setSa_preofftime(rs.getDate("sa_preofftime"));
				shopadVO.setAb_no(rs.getString("ab_no"));
				shopadVO.setSa_addmode(rs.getString("sa_addmode"));
				shopadVO.setSa_paytime(rs.getDate("sa_paytime"));
				list.add(shopadVO); // Store the row in the list
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
		return list;
	}
	
	@Override
	public List<ShopAdVO> getAllBySto_num(String sto_num) {
		List<ShopAdVO> list = new ArrayList<ShopAdVO>();
		ShopAdVO shopadVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_STO_NUM); 
			pstmt.setString(1,sto_num);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVO 也稱為 Domain objects
				shopadVO = new ShopAdVO();
				shopadVO.setSa_no(rs.getString("sa_no"));
				shopadVO.setSto_num(rs.getString("sto_num"));
				shopadVO.setSa_title(rs.getString("sa_title"));
				shopadVO.setSa_text(rs.getString("sa_text"));
				shopadVO.setSa_img(rs.getBytes("sa_img"));
				shopadVO.setSa_views(rs.getInt("sa_views"));
				shopadVO.setSa_apptime(rs.getDate("sa_apptime"));
				shopadVO.setSa_addtime(rs.getDate("sa_addtime"));
				shopadVO.setSa_preofftime(rs.getDate("sa_preofftime"));
				shopadVO.setAb_no(rs.getString("ab_no"));
				shopadVO.setSa_addmode(rs.getString("sa_addmode"));
				shopadVO.setSa_paytime(rs.getDate("sa_paytime"));
				list.add(shopadVO); // Store the row in the list
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
		return list;
	}

	@Override
	public List<ShopAdVO> getAllBySa_addmode(String sa_addmode) {
		List<ShopAdVO> list = new ArrayList<ShopAdVO>();
		ShopAdVO shopadVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_SA_ADDMODE); 
			
			pstmt.setString(1, sa_addmode);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVO 也稱為 Domain objects
				shopadVO = new ShopAdVO();
				shopadVO.setSa_no(rs.getString("sa_no"));
				shopadVO.setSto_num(rs.getString("sto_num"));
				shopadVO.setSa_title(rs.getString("sa_title"));
				shopadVO.setSa_text(rs.getString("sa_text"));
				shopadVO.setSa_img(rs.getBytes("sa_img"));
				shopadVO.setSa_views(rs.getInt("sa_views"));
				shopadVO.setSa_apptime(rs.getDate("sa_apptime"));
				shopadVO.setSa_addtime(rs.getDate("sa_addtime"));
				shopadVO.setSa_preofftime(rs.getDate("sa_preofftime"));
				shopadVO.setAb_no(rs.getString("ab_no"));
				shopadVO.setSa_addmode(rs.getString("sa_addmode"));
				shopadVO.setSa_paytime(rs.getDate("sa_paytime"));
				list.add(shopadVO); // Store the row in the list
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
		return list;
	}


}