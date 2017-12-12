package com.product_type.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.product.model.ProductVO;

public class ProductTypeDAO implements ProductTypeDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	
//-------KHAN------------------------------------------------
	private static final String GET_PRODUCTTYPE = "SELECT * FROM PRODUCT_TYPE WHERE PT_NUM IN (SELECT PT_NUM FROM PRODUCT WHERE STO_NUM=? AND STATUS=?) ORDER BY PT_NUM";
	
	@Override
	public List<ProductTypeVO> getProductType(ProductVO productVO) {
		ProductTypeVO proTypeVO = null;
		List<ProductTypeVO> proTypes = new ArrayList<ProductTypeVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PRODUCTTYPE);
			
			pstmt.setString(1, productVO.getSto_num());
			pstmt.setString(2, productVO.getStatus());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				proTypeVO = new ProductTypeVO();

				proTypeVO.setPt_num(rs.getString("PT_NUM"));
				proTypeVO.setPt_name(rs.getString("PT_NAME"));
				
				proTypes.add(proTypeVO);
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
		return proTypes;
	}
	
//-----end KHAN-----------------------------------------------

	
	
//-------Peiiun---------------------------------------------------

	private static final String INSERT = 
			"INSERT INTO PRODUCT_TYPE (PT_NUM,PT_NAME) "
			+ " VALUES ('PT'||LPAD(to_char(SEQ_RPT_SNUM.NEXTVAL),3,'0'),?)";
	private static final String GET_ALL_STMT="SELECT * FROM PRODUCT_TYPE";
	private static final String GET_ONE_STMT = 
			"SELECT * FROM PRODUCT_TYPE WHERE PT_NUM=?";
	@Override
	public void insert(ProductTypeVO productTypeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setString(1, productTypeVO.getPt_name());
			pstmt.executeUpdate();
	
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}				
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}		
	}
	@Override
	public List<ProductTypeVO> getAll() {
		List<ProductTypeVO> list = new ArrayList<ProductTypeVO>();
		ProductTypeVO pdcTypeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				pdcTypeVO = new ProductTypeVO();
				pdcTypeVO.setPt_num(rs.getString("pt_num"));
				pdcTypeVO.setPt_name(rs.getString("pt_name"));
				list.add(pdcTypeVO);
			}
		} catch (SQLException se) {
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
		return list;
	}

	@Override
	public ProductTypeVO getOnePdcT(String pt_num) {
		ProductTypeVO productTypeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, pt_num);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				productTypeVO = new ProductTypeVO();
				productTypeVO.setPt_num(rs.getString("pt_num"));
				productTypeVO.setPt_name(rs.getString("pt_name"));
			}
			
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
		return productTypeVO;
	}
	
//-------end Peiiun---------------------------------------------

	
//------------Shawn------------------------------------------
	private static final String GET_PRODUCT_TYPE = 
			"SELECT pt_num, pt_name FROM PRODUCT_TYPE WHERE pt_num=?";

	@Override
	public ProductTypeVO getProductTypeName(String pt_num) {
		ProductTypeVO productTypeVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PRODUCT_TYPE);
			
			pstmt.setString(1, pt_num);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				productTypeVO = new ProductTypeVO();

				productTypeVO.setPt_num(rs.getString("pt_num"));
				productTypeVO.setPt_name(rs.getString("pt_name"));

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
		return productTypeVO;
		
	}
//-----------end Shawn-----------------------------	
	
//-----------Eric--------------------------
//private static final String GET_PRODUCTTYPE = "SELECT * FROM PRODUCT_TYPE WHERE PT_NUM IN (SELECT PT_NUM FROM PRODUCT WHERE STO_NUM=? AND STATUS=?) ORDER BY PT_NUM";
//	
//	@Override
//	public List<ProductTypeVO> getProductType(ProductVO productVO) {
//		ProductTypeVO proTypeVO = null;
//		List<ProductTypeVO> proTypes = new ArrayList<ProductTypeVO>();
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_PRODUCTTYPE);
//			
//			pstmt.setString(1, productVO.getSto_num());
//			pstmt.setString(2, productVO.getStatus());
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				proTypeVO = new ProductTypeVO();
//
//				proTypeVO.setPt_num(rs.getString("PT_NUM"));
//				proTypeVO.setPt_name(rs.getString("PT_NAME"));
//				
//				proTypes.add(proTypeVO);
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
//		return proTypes;
//	}
//--------end Eric--------------------------	
	
}