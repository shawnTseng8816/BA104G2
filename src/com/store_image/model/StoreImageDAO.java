package com.store_image.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class StoreImageDAO implements StoreImageDAO_interface {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String GET_ONE_STORE_IMAGE = 
			"SELECT img FROM STORE_IMAGE WHERE sto_num=? AND img_num=?";
	
	
	private static final String GET_STORE_IMAGE_NUM = 
			"SELECT img_num, sto_num FROM STORE_IMAGE WHERE sto_num=?";
	private static final String UPDATE_IMG = "UPDATE STORE_IMAGE SET IMG=? WHERE IMG_NUM=?";
	private static final String GET_ONE_STORE_ALL_IMG = 
			"SELECT img FROM STORE_IMAGE WHERE img_num=?";
	
	private static final String INSERT_IMG = "insert into STORE_IMAGE(IMG_NUM,STO_NUM,IMG)VALUES('SI'||LPAD(to_char(SEQ_IMG_NUM.NEXTVAL),10,'0'),?,?)";

	
	
	
	@Override
	public void updateImg(StoreImageVO storeImageVO) {
		// TODO Auto-generated method stub
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_IMG);
			pstmt.setBytes(1, storeImageVO.getImg());
			pstmt.setString(2, storeImageVO.getImg_num());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public StoreImageVO getStoreImage(String sto_num, String img_num) {
	
		StoreImageVO storeImageVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STORE_IMAGE);
			
			pstmt.setString(1, sto_num);
			pstmt.setString(2, img_num);

			rs = pstmt.executeQuery();
			while (rs.next()) {

				storeImageVO = new StoreImageVO();
				storeImageVO.setImg(rs.getBytes("img"));

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
		
		return storeImageVO;
	}


	@Override
	public List<StoreImageVO> getStoreImageNum(String sto_num) {
		List<StoreImageVO> storeImageList = new ArrayList<StoreImageVO>();	
		StoreImageVO storeImageVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_STORE_IMAGE_NUM);
			
			pstmt.setString(1, sto_num);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				storeImageVO = new StoreImageVO();

				storeImageVO.setImg_num(rs.getString("img_num"));
				storeImageVO.setSto_num(rs.getString("sto_num"));
				
				storeImageList.add(storeImageVO);
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
		
		return storeImageList;
	}


	@Override
	public void insertImg(List<StoreImageVO> list) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		StoreImageVO storeImageVO =null;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			for(int i =0;i<list.size();i++){
			storeImageVO = list.get(i);
			pstmt = con.prepareStatement(INSERT_IMG);
			pstmt.setString(1,storeImageVO.getSto_num());
			pstmt.setBytes(2, storeImageVO.getImg());
			pstmt.executeUpdate();
			}
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				pstmt.close();
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}


	@Override
	public byte[] getStoreImage(String img_num) {
		StoreImageVO storeImageVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		byte[] img = null;
		try {
					
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STORE_ALL_IMG);	
			pstmt.setString(1, img_num);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				img = rs.getBytes("img");
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
		
		
		return img;
	}


}
