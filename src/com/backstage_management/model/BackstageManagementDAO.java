package com.backstage_management.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BackstageManagementDAO implements BackstageManagementDAO_interface{
	
	
		private static DataSource ds = null;
		static {
			try {
				Context ctx = new javax.naming.InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
			} catch (NamingException e) {
				e.printStackTrace(System.err);
			}
		}
		
//-----------------Peiiun-------------------------------------------
		private static final String INSERT_STMT = 
				"INSERT INTO BACKSTAGE_MANAGEMENT (bm_no,bm_name,bm_number,bm_mail,bm_banknum,bm_num,bm_pwd,bm_jstatus,bm_img)"
				+ "VALUES ('BM'||LPAD(to_char(SEQ_BM_NO.NEXTVAL),10,'0'),?,?,?,?,?,?,?,?)";
		private static final String UPDATE_STMT = 
				"UPDATE BACKSTAGE_MANAGEMENT SET "
				+ " bm_name=?,bm_number=?,bm_mail=?,bm_banknum=?,bm_num=?,bm_pwd=?,bm_jstatus=?,bm_img=? WHERE bm_no=?";
		private static final String GET_ONE_STMT = 
				"SELECT * FROM BACKSTAGE_MANAGEMENT WHERE bm_no=?";
		private static final String GET_ALL_STMT = 
				"SELECT * FROM BACKSTAGE_MANAGEMENT ORDER BY bm_no DESC";
		private static final String CHECK_BM_NUM = "SELECT BM_NUM FROM BACKSTAGE_MANAGEMENT WHERE BM_NUM = ?";
	
	@Override
	public String insert(BackstageManagementVO bmVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String bm_no = null;
		try {
			
			con = ds.getConnection();
			String[] col = {"bm_no"};
			pstmt = con.prepareStatement(INSERT_STMT,col);
			
			
			pstmt.setString(1, bmVO.getBm_name());
			pstmt.setString(2, bmVO.getBm_number());
			pstmt.setString(3, bmVO.getBm_mail());
			pstmt.setString(4, bmVO.getBm_banknum());
			pstmt.setString(5, bmVO.getBm_num());
			pstmt.setString(6, bmVO.getBm_pwd());
			pstmt.setString(7, bmVO.getBm_jstatus());
			pstmt.setBytes(8, bmVO.getBm_img());
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				do {
					for (int i = 1; i <= columnCount; i++) {
						bm_no = rs.getString(i);						
					}
				} while (rs.next());
			}			
			rs.close();
			
		} catch (SQLException se) {	//SQL error
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally{ 			
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
		return bm_no;
	}

	@Override
	public void update(BackstageManagementVO bmVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
						
			pstmt.setString(1, bmVO.getBm_name());
			pstmt.setString(2, bmVO.getBm_number());
			pstmt.setString(3, bmVO.getBm_mail());
			pstmt.setString(4, bmVO.getBm_banknum());
			pstmt.setString(5, bmVO.getBm_num());
			pstmt.setString(6, bmVO.getBm_pwd());
			pstmt.setString(7, bmVO.getBm_jstatus());
			pstmt.setBytes(8, bmVO.getBm_img());
			pstmt.setString(9, bmVO.getBm_no());
			
			pstmt.executeUpdate();
			
			
		} catch (SQLException se) {	//SQL error
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally{ 			
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
	public BackstageManagementVO findbyPrimaryKey(String bm_no) {
		
		BackstageManagementVO bmVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
			try {
				
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STMT);
				
				pstmt.setString(1, bm_no);
				
				rs = pstmt.executeQuery();
				while(rs.next()){
					bmVO = new BackstageManagementVO();
					bmVO.setBm_no(rs.getString("bm_no"));
					bmVO.setBm_name(rs.getString("bm_name"));
					bmVO.setBm_number(rs.getString("bm_number"));
					bmVO.setBm_mail(rs.getString("bm_mail"));
					bmVO.setBm_banknum(rs.getString("bm_banknum"));
					bmVO.setBm_num(rs.getString("bm_num"));
					bmVO.setBm_pwd(rs.getString("bm_pwd"));
					bmVO.setBm_jstatus(rs.getString("bm_jstatus"));	
					bmVO.setBm_img(rs.getBytes("bm_img"));
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
		return bmVO;
	}

	@Override
	public List<BackstageManagementVO> getAll() {
		List<BackstageManagementVO> list = new ArrayList<BackstageManagementVO>();
		BackstageManagementVO bmVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {				
				bmVO = new BackstageManagementVO();
				bmVO.setBm_no(rs.getString("bm_no"));
				bmVO.setBm_name(rs.getString("bm_name"));
				bmVO.setBm_number(rs.getString("bm_number"));
				bmVO.setBm_mail(rs.getString("bm_mail"));
				bmVO.setBm_banknum(rs.getString("bm_banknum"));
				bmVO.setBm_num(rs.getString("bm_num"));
				bmVO.setBm_pwd(rs.getString("bm_pwd"));
				bmVO.setBm_jstatus(rs.getString("bm_jstatus"));
				list.add(bmVO);
			}
			// Handle any SQL errors
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
	public int checkBm_num(String bm_num){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int existBm_num=0;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECK_BM_NUM);
			
			pstmt.setString(1, bm_num);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				existBm_num+=1;
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
		
		return existBm_num;		
	}
	
	
//---------------------end Peiiun----------------------------------------
	
	
	
//----------------------Chi----------------------------------------------
	
	private static final String INSERT_STMT_CHI = 
			"INSERT INTO backstage_management (bm_no,bm_name,bm_number,bm_mail,bm_banknum,bm_img,bm_num,bm_pwd,bm_jstatus) VALUES ('MB'||LPAD(to_char(SEQ_BM_NO.NEXTVAL), 10, '0'), ?, ?, ?, ?, ?, ?, ?, ?)";
		private static final String GET_ALL_STMT_CHI = 
			"SELECT bm_no,bm_name,bm_number,bm_mail,bm_banknum,bm_img,bm_num,bm_pwd,bm_jstatus FROM backstage_management order by bm_no";
		private static final String GET_ONE_STMT_CHI = 
			"SELECT bm_no,bm_name,bm_number,bm_mail,bm_banknum,bm_img,bm_num,bm_pwd,bm_jstatus FROM backstage_management where bm_no = ?";
		private static final String GET_BM_STMT = "SELECT * FROM BACKSTAGE_MANAGEMENT WHERE BM_NUM=?";
		
		//	private static final String DELETE = 
//			"DELETE FROM emp2 where Bm_no = ?";
//		private static final String UPDATE = 
//			"UPDATE backstage_management set bm_number=?, bm_mail=?, bm_banknum=?, bm_img=?, bm_num=?, bm_pwd=? bm_jstatus=? where bm_no = ?";

		@Override
		public void insertChi(BackstageManagementVO backstagemanagementVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {
					con = ds.getConnection();
					pstmt = con.prepareStatement(INSERT_STMT_CHI);
				
					pstmt.setString(1, backstagemanagementVO.getBm_name());
					pstmt.setString(2, backstagemanagementVO.getBm_number());
					pstmt.setString(3, backstagemanagementVO.getBm_mail());
					pstmt.setString(4, backstagemanagementVO.getBm_banknum());
					pstmt.setBytes(5, backstagemanagementVO.getBm_img());
					pstmt.setString(6, backstagemanagementVO.getBm_num());
					pstmt.setString(7, backstagemanagementVO.getBm_pwd());
					pstmt.setString(8, backstagemanagementVO.getBm_jstatus());
				
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

//		@Override
//		public void update(BackstageManagementVO backstagemanagementVO) {
	//
//		    Connection con = null;
//		    PreparedStatement pstmt = null;
	//
//			try {
	//
//				con = ds.getConnection();
//				pstmt = con.prepareStatement(UPDATE);
//				
//				pstmt.setString(1, backstagemanagementVO.getBm_number());
//				pstmt.setString(2, backstagemanagementVO.getBm_mail());
//				pstmt.setString(3, backstagemanagementVO.getBm_banknum());
//				pstmt.setBytes(4, backstagemanagementVO.getBm_img());
//				pstmt.setString(5, backstagemanagementVO.getBm_num());
//				pstmt.setString(6, backstagemanagementVO.getBm_pwd());
//				pstmt.setString(7, backstagemanagementVO.getBm_jstatus());
//				pstmt.setString(8, backstagemanagementVO.getBm_no());
	//
//				pstmt.executeUpdate();
	//
				// Handle any driver errors
			//} catch (SQLException se) {
			//	throw new RuntimeException("A database error occured. "
			//			+ se.getMessage());
			//	// Clean up JDBC resources
			//} finally {
			//	if (pstmt != null) {
			//		try {
			//			pstmt.close();
			//		} catch (SQLException se) {
			//			se.printStackTrace(System.err);
			//		}
			//	}
			//	if (con != null) {
			//		try {
			//			con.close();
			//		} catch (Exception e) {
			//			e.printStackTrace(System.err);
			//		}
			//	}
			//}
			//
			//}


		@Override
		public BackstageManagementVO findByPrimaryKey(String bm_no) {

			BackstageManagementVO backstagemanagementVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STMT_CHI);

				pstmt.setString(1, bm_no);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVo 也稱為 Domain objects
					backstagemanagementVO = new BackstageManagementVO();
					backstagemanagementVO.setBm_no(rs.getString("bm_no"));
					backstagemanagementVO.setBm_name(rs.getString("bm_name"));
					backstagemanagementVO.setBm_number(rs.getString("bm_number"));
					backstagemanagementVO.setBm_mail(rs.getString("bm_mail"));
					backstagemanagementVO.setBm_banknum(rs.getString("bm_banknum"));
					backstagemanagementVO.setBm_img(rs.getBytes("bm_img"));
					backstagemanagementVO.setBm_num(rs.getString("bm_num"));
					backstagemanagementVO.setBm_pwd(rs.getString("bm_pwd"));
					backstagemanagementVO.setBm_jstatus(rs.getString("bm_jstatus"));
					

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
			return backstagemanagementVO;
		}
		
		@Override
		public List<BackstageManagementVO> getAllChi() {
			
			List<BackstageManagementVO> list = new ArrayList<BackstageManagementVO>();
			BackstageManagementVO backstagemanagementVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT_CHI);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVO 也稱為 Domain objects
					backstagemanagementVO = new BackstageManagementVO();
					backstagemanagementVO.setBm_no(rs.getString("bm_no"));
					backstagemanagementVO.setBm_name(rs.getString("bm_name"));
					backstagemanagementVO.setBm_number(rs.getString("bm_number"));
					backstagemanagementVO.setBm_mail(rs.getString("bm_mail"));
					backstagemanagementVO.setBm_banknum(rs.getString("bm_banknum"));
					backstagemanagementVO.setBm_img(rs.getBytes("bm_img"));
					backstagemanagementVO.setBm_num(rs.getString("bm_num"));
					backstagemanagementVO.setBm_pwd(rs.getString("bm_pwd"));
					backstagemanagementVO.setBm_jstatus(rs.getString("bm_jstatus"));
					list.add(backstagemanagementVO); // Store the row in the list
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
		public BackstageManagementVO checkLogin(String bm_num) {
			BackstageManagementVO backstagemanagementVO = null;
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_BM_STMT);	
				pstmt.setString(1, bm_num);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					backstagemanagementVO = new BackstageManagementVO();
					backstagemanagementVO.setBm_no(rs.getString("bm_no"));
					backstagemanagementVO.setBm_name(rs.getString("BM_NAME"));
					backstagemanagementVO.setBm_number(rs.getString("BM_NUMBER"));
					backstagemanagementVO.setBm_mail(rs.getString("BM_MAIL"));
					backstagemanagementVO.setBm_banknum(rs.getString("BM_BANKNUM"));
					backstagemanagementVO.setBm_img(rs.getBytes("BM_IMG"));
					backstagemanagementVO.setBm_num(rs.getString("BM_NUM"));
//					System.out.println(rs.getString("BM_NUM"));
					backstagemanagementVO.setBm_pwd(rs.getString("BM_PWD"));
//					System.out.println(rs.getString("BM_PWD"));
					backstagemanagementVO.setBm_jstatus(rs.getString("BM_JSTATUS"));
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
			return backstagemanagementVO;
		}
	
//--------------------end Chi--------------------------------------------
	
	
}
