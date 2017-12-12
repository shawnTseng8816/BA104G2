package com.store_profile.model;

import java.sql.Blob;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.product.model.ProductVO;
import com.store_comment.model.StoreCommentVO;



public class StoreProfileDAO implements StoreProfileDAO_interface{
	
	private static DataSource ds = null;
	static{
		
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	
	
//-----------------Peiiun------------------------	
	private static final String GET_ADDR = 
			"SELECT STO_NUM, STO_NAME, AREA, ADDRESS ,STO_LOGO FROM STORE_PROFILE WHERE STO_STATUS = '已上架'";

	private static final String KEYWORD_SEARCH =
			"SELECT SP.STO_NUM, SP.STO_NAME , AREA, ADDRESS FROM STORE_PROFILE SP "
			+ " RIGHT JOIN PRODUCT P ON P.STO_NUM = SP.STO_NUM"
			+ " LEFT JOIN PRODUCT_TYPE PT ON P.PT_NUM = PT.PT_NUM"
			+ " WHERE (SP.STO_NAME LIKE ? OR P.COM_NAME LIKE ? OR PT.PT_NAME LIKE ? ) AND STO_STATUS='已上架'"
			+ " GROUP BY SP.STO_NUM, SP.STO_NAME , AREA, ADDRESS ";
	private static final String N_KEYWORD_SEARCH =
			"SELECT SP.STO_NUM, SP.STO_NAME , AREA, ADDRESS FROM STORE_PROFILE SP "
			+ " RIGHT JOIN PRODUCT P ON P.STO_NUM = SP.STO_NUM"
			+ " LEFT JOIN PRODUCT_TYPE PT ON P.PT_NUM = PT.PT_NUM"
			+ " WHERE STO_STATUS='已上架'"
			+ " GROUP BY SP.STO_NUM, SP.STO_NAME , AREA, ADDRESS";
	private static final String GET_ONE_STONAME = 
			"SELECT sto_num , sto_name FROM STORE_PROFILE WHERE sto_num=?";
	@Override
	public List<StoreProfileVO> getAllgeo() {
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<StoreProfileVO> list = new ArrayList<StoreProfileVO>();
		StoreProfileVO sto_info = null;
		try{
			con = ds.getConnection();	
			pstmt = con.prepareStatement(GET_ADDR);			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				sto_info = new StoreProfileVO();	
				sto_info.setSto_num(rs.getString("sto_num"));
				sto_info.setSto_name(rs.getString("sto_name"));
				sto_info.setArea(rs.getString("area"));
				sto_info.setAddress(rs.getString("address"));
				list.add(sto_info);
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
	public List<StoreProfileVO> search(String keyword){
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<StoreProfileVO> list = new ArrayList<StoreProfileVO>();
		StoreProfileVO sto_info = null;
		try{
			con = ds.getConnection();	
			pstmt = con.prepareStatement(KEYWORD_SEARCH);
			pstmt.setString(1,"%" +keyword+"%");
			pstmt.setString(2,"%" +keyword+"%");
			pstmt.setString(3,"%" +keyword+"%");
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				sto_info = new StoreProfileVO();	
				sto_info.setSto_num(rs.getString("sto_num"));
				sto_info.setSto_name(rs.getString("sto_name"));
				sto_info.setArea(rs.getString("area"));
				sto_info.setAddress(rs.getString("address"));
				list.add(sto_info);
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
		return list;		
	}

	@Override
	public List<StoreProfileVO> search() {
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<StoreProfileVO> list = new ArrayList<StoreProfileVO>();
		StoreProfileVO sto_info = null;
		try{
			con = ds.getConnection();	
			pstmt = con.prepareStatement(KEYWORD_SEARCH);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				sto_info = new StoreProfileVO();	
				sto_info.setSto_num(rs.getString("sto_num"));
				sto_info.setSto_name(rs.getString("sto_name"));
				sto_info.setArea(rs.getString("area"));
				sto_info.setAddress(rs.getString("address"));
				list.add(sto_info);
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
		return list;	
	}

	@Override
	public StoreProfileVO getOneByPrimary(String sto_num) {
		StoreProfileVO spVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STONAME);
			pstmt.setString(1, sto_num);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				spVO = new StoreProfileVO();
				spVO.setSto_num(rs.getString("sto_num"));
				spVO.setSto_name(rs.getString("sto_name"));				;	
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
		return spVO;
	}
//-----------------end Peiiun------------------------
	
	
	
//-----------------Chi------------------------------
	//新增，註冊用 SEQ_STO_NUM
		//INSERT INTO store_profile (sto_num,sto_acc,sto_pwd,sto_name,guy,uni_num,mobile,area,address,set_time,
		//                           sto_logo,sto_introduce,sto_status,rem_point) 
		//VALUES ('ST'||LPAD(to_char(SEQ_STO_NUM.NEXTVAL), 10, '0'), 'shop8', 'store8', '格鬥飲', '陸奧',76452389, '05-7658473',
		//        '桃園市桃園區', '新生北路80號',to_date(to_char(sysdate,'MMDDYYYY HH24:MI:SS'),'MMDDYYYY HH24:MI:SS'),null, '未知領域', '未上架', 1200);
		private static final String INSERT_STMT = 
			"INSERT INTO store_profile (sto_num,sto_acc,sto_pwd,sto_name,guy,uni_num,mobile,area,address,email,set_time,sto_logo,sto_introduce,sto_status,rem_point) VALUES ('ST'||LPAD(to_char(SEQ_STO_NUM.NEXTVAL), 10, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		//帳密
		private static final String GET_STOREPROFILE = "SELECT * FROM STORE_PROFILE WHERE STO_NUM=?";
		
		// ALL~
		private static final String GET_ALL_STMT = 
				"SELECT sto_num,sto_acc,sto_pwd,sto_name,guy,uni_num,mobile,area,address,email,set_time,sto_logo,sto_introduce,sto_status,rem_point FROM store_profile order by sto_num desc";
		
		private static final String UPDATE = "UPDATE STORE_PROFILE SET STO_NAME=NVL(?, STO_NAME), STO_PWD=NVL(?, STO_PWD), MOBILE=NVL(?, MOBILE), ADDRESS=NVL(?, ADDRESS), EMAIL=NVL(?, EMAIL), STO_LOGO=NVL(?, STO_LOGO), STO_INTRODUCE=NVL(?, STO_INTRODUCE), STO_STATUS=NVL(?, STO_STATUS) WHERE STO_NUM=?";
		
		private static final String GET_STO_STMT = "SELECT * FROM STORE_PROFILE WHERE STO_ACC=?";
		
		private static final String GET_STO_CHECK = "SELECT * FROM STORE_PROFILE WHERE STO_STATUS=?";
		
		private static final String CHANGE_STATUS = "UPDATE STORE_PROFILE SET STO_STATUS=? WHERE STO_NUM=?";
		
		private static final String GET_PIC = "SELECT * FROM store_profile WHERE sto_num = ?";

		@Override
		public void insert(StoreProfileVO storeprofileVO) {
			
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);
				
				Blob blob = con.createBlob();

//				con.setAutoCommit(false);
			
				pstmt.setString(1, storeprofileVO.getSto_acc());
				pstmt.setString(2, storeprofileVO.getSto_pwd());
				pstmt.setString(3, storeprofileVO.getSto_name());
				pstmt.setString(4, storeprofileVO.getGuy());
				pstmt.setInt(5, storeprofileVO.getUni_num());
				pstmt.setString(6, storeprofileVO.getMobile());
				pstmt.setString(7, storeprofileVO.getArea());
				pstmt.setString(8, storeprofileVO.getAddress());
				pstmt.setString(9, storeprofileVO.getEmail());
				

				java.sql.Date sqlDate=new java.sql.Date(storeprofileVO.getSet_time().getTime()); 
			
				pstmt.setDate(10, sqlDate);
				pstmt.setBytes(11, storeprofileVO.getSto_logo());
				pstmt.setString(12, storeprofileVO.getSto_introduce());
				pstmt.setString(13, storeprofileVO.getSto_status());
				pstmt.setInt(14, storeprofileVO.getRem_point());

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
		public void update(StoreProfileVO storeprofileVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE);

				con.setAutoCommit(false);

				pstmt.setString(1, storeprofileVO.getSto_name());
				pstmt.setString(2, storeprofileVO.getSto_pwd());
				pstmt.setString(3, storeprofileVO.getMobile());
				pstmt.setString(4, storeprofileVO.getAddress());
				pstmt.setString(5, storeprofileVO.getEmail());
				pstmt.setBytes(6, storeprofileVO.getSto_logo());
				pstmt.setString(7, storeprofileVO.getSto_introduce());
				pstmt.setString(8, storeprofileVO.getSto_status());
				
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
		public StoreProfileVO getStoreProfile(String sto_num) {
			StoreProfileVO stoVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_STOREPROFILE);

				Blob blob = con.createBlob();

				con.setAutoCommit(false);
			
				pstmt.setString(1, sto_num);
				
				rs = pstmt.executeQuery();
				//System.out.println("dwdwddddddddddda");
				while (rs.next()) {
					stoVO = new StoreProfileVO();
					
					stoVO.setSto_acc(rs.getString("STO_ACC"));
					stoVO.setSto_pwd(rs.getString("STO_PWD"));
					System.out.println(rs.getString("STO_PWD"));
					stoVO.setSto_name(rs.getString("STO_NAME"));
					stoVO.setGuy(rs.getString("GUY"));
					stoVO.setUni_num(rs.getInt("UNI_NUM"));
					stoVO.setMobile(rs.getString("MOBILE"));
					stoVO.setArea(rs.getString("AREA"));
					stoVO.setAddress(rs.getString("ADDRESS"));
					stoVO.setEmail(rs.getString("EMAIL"));
					stoVO.setSet_time(rs.getDate("SET_TIME"));
					stoVO.setSto_logo(rs.getBytes("STO_LOGO"));
					stoVO.setSto_introduce(rs.getString("STO_INTRODUCE"));
					stoVO.setSto_status(rs.getString("STO_STATUS"));
					stoVO.setRem_point(rs.getInt("REM_POINT"));
					// memVO.setMem_pic(rs.getBytes(12));
					// memVO.setStatus(rs.getString("STATUS"));
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
			return stoVO;
		}
		
		@Override
		public StoreProfileVO checkLogin(String sto_acc) {
			StoreProfileVO stoVO = null;
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_STO_STMT);	
				pstmt.setString(1, sto_acc);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					stoVO = new StoreProfileVO();
					stoVO.setSto_num(rs.getString("STO_NUM"));
				System.out.println(rs.getString("STO_NUM"));
					stoVO.setSto_acc(rs.getString("STO_ACC"));
					stoVO.setSto_pwd(rs.getString("STO_PWD"));
				System.out.println(rs.getString("STO_PWD"));
					stoVO.setSto_name(rs.getString("STO_NAME"));
					stoVO.setGuy(rs.getString("GUY"));
					stoVO.setUni_num(rs.getInt("UNI_NUM"));
					stoVO.setMobile(rs.getString("MOBILE"));
					stoVO.setArea(rs.getString("AREA"));
					stoVO.setAddress(rs.getString("ADDRESS"));
					stoVO.setEmail(rs.getString("EMAIL"));
					stoVO.setSet_time(rs.getDate("SET_TIME"));
					stoVO.setSto_logo(rs.getBytes("STO_LOGO"));
					stoVO.setSto_introduce(rs.getString("STO_INTRODUCE"));
					stoVO.setSto_status(rs.getString("STO_STATUS"));
					stoVO.setRem_point(rs.getInt("REM_POINT"));
					// memVO.setMem_pic(rs.getBytes(12));
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
			return stoVO;
		}
		
		@Override
		public List<StoreProfileVO> getStatus(String sto_status) {
			
			List<StoreProfileVO> list = new ArrayList<StoreProfileVO>(); // Arraylist、add
			StoreProfileVO stoVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_STO_CHECK);

//				con.setAutoCommit(false);
			
				pstmt.setString(1, sto_status);
				
				rs = pstmt.executeQuery();
				System.out.println("dwdwddddddddddda");
				
				while (rs.next()) {
					stoVO = new StoreProfileVO();
					
					stoVO.setSto_num(rs.getString("STO_NUM"));
					stoVO.setSto_acc(rs.getString("STO_ACC"));
					stoVO.setSto_pwd(rs.getString("STO_PWD"));
					System.out.println(rs.getString("STO_PWD"));
					stoVO.setSto_name(rs.getString("STO_NAME"));
					stoVO.setGuy(rs.getString("GUY"));
					stoVO.setUni_num(rs.getInt("UNI_NUM"));
					stoVO.setMobile(rs.getString("MOBILE"));
					stoVO.setArea(rs.getString("AREA"));
					stoVO.setAddress(rs.getString("ADDRESS"));
					stoVO.setEmail(rs.getString("EMAIL"));
					stoVO.setSet_time(rs.getDate("SET_TIME"));
					stoVO.setSto_logo(rs.getBytes("STO_LOGO"));
					stoVO.setSto_introduce(rs.getString("STO_INTRODUCE"));
					stoVO.setSto_status(rs.getString("STO_STATUS"));
					stoVO.setRem_point(rs.getInt("REM_POINT"));
					list.add(stoVO);
					// memVO.setMem_pic(rs.getBytes(12));
					// memVO.setStatus(rs.getString("STATUS"));
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
		public List<StoreProfileVO> getAll() {
			List<StoreProfileVO> list = new ArrayList<StoreProfileVO>();
			StoreProfileVO storeprofileVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVO 也稱為 Domain objects
					storeprofileVO = new StoreProfileVO();
					storeprofileVO.setSto_num(rs.getString("sto_num"));
					storeprofileVO.setSto_acc(rs.getString("sto_acc"));
					storeprofileVO.setSto_pwd(rs.getString("sto_pwd"));
					storeprofileVO.setSto_name(rs.getString("sto_name"));
					storeprofileVO.setGuy(rs.getString("guy"));
					storeprofileVO.setUni_num(rs.getInt("uni_num"));
					storeprofileVO.setMobile(rs.getString("mobile"));
					storeprofileVO.setArea(rs.getString("area"));
					storeprofileVO.setAddress(rs.getString("address"));
					storeprofileVO.setEmail(rs.getString("email"));
					storeprofileVO.setSet_time(rs.getDate("set_time"));
					storeprofileVO.setSto_logo(rs.getBytes("sto_logo"));
					storeprofileVO.setSto_introduce(rs.getString("sto_introduce"));
					storeprofileVO.setSto_status(rs.getString("sto_status"));
					storeprofileVO.setRem_point(rs.getInt("rem_point"));				
					list.add(storeprofileVO); // Store the row in the list
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
		public void changestatus(StoreProfileVO storeprofileVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(CHANGE_STATUS);

				Blob blob = con.createBlob();

				con.setAutoCommit(false);

				pstmt.setString(1, storeprofileVO.getSto_status());
				
				pstmt.setString(2, storeprofileVO.getSto_num());

				pstmt.executeUpdate();

				con.commit();

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
//		public StoreProfileVO getPic(String sto_logo) {
//			StoreProfileVO storeprofileVO = null;
//			
//			Connection con = null;
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
	//
//			try {
	//
//				con = ds.getConnection();
//				pstmt = con.prepareStatement(GET_PIC);	
//				pstmt.setString(1, sto_logo);
//				rs = pstmt.executeQuery();
//				
//				while (rs.next()) {
//					storeprofileVO = new StoreProfileVO();
//					storeprofileVO.setSto_num(rs.getString("STO_NUM"));
//				System.out.println(rs.getString("STO_NUM"));;
//					storeprofileVO.setSto_logo(rs.getBytes("STO_LOGO"));
//				}
	//
//			} catch (SQLException se) {
//				throw new RuntimeException("A database error occured. " + se.getMessage());
//			} finally {
//				if (rs != null) {
//					try {
//						rs.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (Exception e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
//			return storeprofileVO;
//		}
	
	
//----------------end Chi---------------------------	
	
//-----------------Shawn----------------------
		
		private static final String GET_STORE_PROFILE = 
				"SELECT sto_num, sto_acc, sto_pwd, sto_name, guy, uni_num, mobile, area, address, "
				+ "set_time, sto_logo, sto_introduce, sto_status,rem_point FROM STORE_PROFILE WHERE sto_num=?";
		private static final String GET_STORE_PROFILE_ALL_NAME = 
				"SELECT sto_num, sto_acc, sto_pwd, sto_name, guy, uni_num, mobile, area, address, "
				+ "set_time, sto_logo, sto_introduce, sto_status,rem_point FROM STORE_PROFILE WHERE sto_name=?";
		private static final String GET_STORE_PROFILE_IMAGE = 
				"SELECT sto_logo FROM STORE_PROFILE WHERE sto_num=?";
		private static final String UPDATE_STORE_PROFILE = 
				"UPDATE STORE_PROFILE set sto_status=? WHERE sto_num=?";
		private static final String GET_ALL = "SELECT * FROM STORE_PROFILE";
		private static final String GET_STORE_PROFILE_ONE = 
				"SELECT * FROM STORE_PROFILE WHERE sto_num=?";
		
		@Override
		public Set<StoreProfileVO> getStoreProfileImage(String sto_num) {
			
			Set<StoreProfileVO> setStoreProfile = new LinkedHashSet<StoreProfileVO>();		
			StoreProfileVO storeProfileVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_STORE_PROFILE_IMAGE);
				pstmt.setString(1, sto_num);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					storeProfileVO = new StoreProfileVO();
					storeProfileVO.setSto_logo(rs.getBytes("sto_logo"));
					setStoreProfile.add(storeProfileVO);
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
			return setStoreProfile;

		}
		

		@Override
		public Set<StoreProfileVO> getStoreProfileBySto_num(String sto_num) {
			
			Set<StoreProfileVO> setStoreProfile = new LinkedHashSet<StoreProfileVO>();		
			StoreProfileVO storeProfileVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_STORE_PROFILE);
				pstmt.setString(1, sto_num);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					storeProfileVO = new StoreProfileVO();
					storeProfileVO.setSto_num(rs.getString("sto_num"));
					storeProfileVO.setSto_acc(rs.getString("sto_acc"));
					storeProfileVO.setSto_pwd(rs.getString("sto_pwd"));
					storeProfileVO.setSto_name(rs.getString("sto_name"));
					storeProfileVO.setGuy(rs.getString("guy"));
					storeProfileVO.setUni_num(rs.getInt("uni_num"));
					storeProfileVO.setMobile(rs.getString("mobile"));
					storeProfileVO.setArea(rs.getString("area"));
					storeProfileVO.setAddress(rs.getString("address"));
					storeProfileVO.setSet_time(rs.getDate("set_time"));
					storeProfileVO.setSto_logo(rs.getBytes("sto_logo"));
					storeProfileVO.setSto_introduce(rs.getString("sto_introduce"));
					storeProfileVO.setSto_status(rs.getString("sto_status"));
					storeProfileVO.setRem_point(rs.getInt("rem_point"));
					setStoreProfile.add(storeProfileVO);

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
			return setStoreProfile;

		}

		@Override
		public Set<StoreProfileVO> getStoreProfileAllName(String sto_name) {
			Set<StoreProfileVO> setStoreProfile = new LinkedHashSet<StoreProfileVO>();		
			StoreProfileVO storeProfileVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_STORE_PROFILE_ALL_NAME);
				pstmt.setString(1, sto_name);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					storeProfileVO = new StoreProfileVO();
					storeProfileVO.setSto_num(rs.getString("sto_num"));
					storeProfileVO.setSto_acc(rs.getString("sto_acc"));
					storeProfileVO.setSto_pwd(rs.getString("sto_pwd"));
					storeProfileVO.setSto_name(rs.getString("sto_name"));
					storeProfileVO.setGuy(rs.getString("guy"));
					storeProfileVO.setUni_num(rs.getInt("uni_num"));
					storeProfileVO.setMobile(rs.getString("mobile"));
					storeProfileVO.setArea(rs.getString("area"));
					storeProfileVO.setAddress(rs.getString("address"));
					storeProfileVO.setSet_time(rs.getDate("set_time"));
					storeProfileVO.setSto_logo(rs.getBytes("sto_logo"));
					storeProfileVO.setSto_introduce(rs.getString("sto_introduce"));
					storeProfileVO.setSto_status(rs.getString("sto_status"));
					storeProfileVO.setRem_point(rs.getInt("rem_point"));
					setStoreProfile.add(storeProfileVO);

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
			return setStoreProfile;
		}

		@Override
		public void updateStoreProfile(StoreProfileVO storeProfileVO) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_STORE_PROFILE);
				pstmt.setString(1, storeProfileVO.getSto_status());
				pstmt.setString(2, storeProfileVO.getSto_num());

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
//		public List<StoreProfileVO> getAll() {
//			List<StoreProfileVO> list = new ArrayList<StoreProfileVO>();
//			StoreProfileVO storeProfileVO = null;
//
//			Connection con = null;
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//
//			try {
//
//				con = ds.getConnection();
//				pstmt = con.prepareStatement(GET_ALL);
//				rs = pstmt.executeQuery();
//
//				while (rs.next()) {
//					storeProfileVO = new StoreProfileVO();
//					storeProfileVO.setSto_num(rs.getString("sto_num"));
//					storeProfileVO.setSto_acc(rs.getString("sto_acc"));
//					storeProfileVO.setSto_pwd(rs.getString("sto_pwd"));
//					storeProfileVO.setSto_name(rs.getString("sto_name"));
//					storeProfileVO.setGuy(rs.getString("guy"));
//					storeProfileVO.setUni_num(rs.getInt("uni_num"));
//					storeProfileVO.setMobile(rs.getString("mobile"));
//					storeProfileVO.setArea(rs.getString("area"));
//					storeProfileVO.setAddress(rs.getString("address"));
//					storeProfileVO.setSet_time(rs.getDate("set_time"));
//					storeProfileVO.setSto_logo(rs.getBytes("sto_logo"));
//					storeProfileVO.setSto_introduce(rs.getString("sto_introduce"));
//					storeProfileVO.setSto_status(rs.getString("sto_status"));
//					storeProfileVO.setRem_point(rs.getInt("rem_point"));
//					list.add(storeProfileVO); // Store the row in the list
//				}
//
//				// Handle any SQL errors
//			} catch (SQLException se) {
//				throw new RuntimeException("A database error occured. "
//						+ se.getMessage());
//			} finally {
//				if (rs != null) {
//					try {
//						rs.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (Exception e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
//			return list;
//		
//		}


//		@Override
//		public StoreProfileVO getStoreProfile(String sto_num) {
//			StoreProfileVO storeProfileVO = null;
//			Connection con = null;
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//			
//			try {
//
//				con = ds.getConnection();
//				pstmt = con.prepareStatement(GET_STORE_PROFILE_ONE);
//				pstmt.setString(1, sto_num);
//				rs = pstmt.executeQuery();
//
//				while (rs.next()) {
//					storeProfileVO = new StoreProfileVO();
//					storeProfileVO.setSto_num(rs.getString("sto_num"));
//					storeProfileVO.setSto_name(rs.getString("sto_name"));
//					storeProfileVO.setSto_status(rs.getString("sto_status"));
//				}
//
//				// Handle any driver errors
//			} catch (SQLException se) {
//				throw new RuntimeException("A database error occured. "
//						+ se.getMessage());
//				// Clean up JDBC resources
//			} finally {
//				if (rs != null) {
//					try {
//						rs.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (Exception e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
//			return storeProfileVO;
//		}
//		
		
//---------------- end Shawn-------------------		
	
//----------------Eric-------------------------
		
		private static final String UPDATE_STATUS ="UPDATE STORE_PROFILE SET STO_STATUS=? WHERE STO_NUM=?";
		private static final String GET_STO_LOGO = "SELECT STO_LOGO FROM STORE_PROFILE WHERE STO_NUM=?";
		
		private static final String GET_ONE_STO_INFO = "SELECT * FROM STORE_PROFILE WHERE STO_NUM=?";
		
		private static final String GET_STO_COM_BY_STONUM = "SELECT * FROM STORE_COMMENT WHERE STO_NUM=? order by com_time desc";
		
		private static final String UPDATE_STO_INFO = "UPDATE STORE_PROFILE SET GUY=?,MOBILE=?,STO_LOGO=?,Sto_introduce=?,sto_pwd=? WHERE STO_NUM=?";
		
		@Override
		public StoreProfileVO getOneStoInfo(String sto_num) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs =null;
			StoreProfileVO storeProfileVO = null;
			
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STO_INFO);
				pstmt.setString(1, sto_num);
				rs =pstmt.executeQuery();
				if(rs.next()){
					storeProfileVO  = new StoreProfileVO(); 
					storeProfileVO.setSto_num(rs.getString("sto_num"));
					storeProfileVO.setArea(rs.getString("area"));
					storeProfileVO.setGuy(rs.getString("guy"));
					storeProfileVO.setMobile(rs.getString("mobile"));
					storeProfileVO.setSet_time(rs.getDate("set_time"));
					storeProfileVO.setSto_name(rs.getString("sto_name"));
					storeProfileVO.setAddress(rs.getString("address"));
					storeProfileVO.setRem_point(rs.getInt("rem_point"));
					storeProfileVO.setSto_introduce(rs.getString("sto_introduce"));
					storeProfileVO.setSto_status(rs.getString("sto_status"));
					storeProfileVO.setSto_acc(rs.getString("sto_acc"));
					storeProfileVO.setSto_pwd(rs.getString("sto_pwd"));
					storeProfileVO.setUni_num(rs.getInt("uni_num"));
					storeProfileVO.setSto_logo(rs.getBytes("sto_logo"));
					storeProfileVO.setEmail(rs.getString("email"));
				}else{
					storeProfileVO = null;
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(rs!=null){
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(pstmt!=null){
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(con!=null){
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
			
			return storeProfileVO;
			// TODO Auto-generated method stub
			
		}

		@Override
		public void updateStoStatus(String sto_num,String status) {
			Connection con  = null;
			PreparedStatement pstmt = null;
			try {
				con=ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_STATUS);
				pstmt.setString(1, status);
				pstmt.setString(2, sto_num);			
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(pstmt!=null){
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(con!=null){
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
		}

		@Override
		public List<StoreCommentVO> getStoComment(String sto_num) {
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				StoreCommentVO storeCommentVO ;
				List<StoreCommentVO> list = new ArrayList<StoreCommentVO>();
				try {
					con = ds.getConnection();
					pstmt = con.prepareStatement(GET_STO_COM_BY_STONUM);
					pstmt.setString(1, sto_num);
					rs = pstmt.executeQuery();
					while(rs.next()){
						 storeCommentVO  = new  StoreCommentVO();
						 storeCommentVO.setCom_num(rs.getString("com_num"));
						 storeCommentVO.setCom_time(rs.getDate("com_time"));
						 storeCommentVO.setCom_title(rs.getString("com_title"));
						 storeCommentVO.setCommentt(rs.getString("commentt"));
						 storeCommentVO.setMem_num(rs.getString("mem_num"));
						 storeCommentVO.setStars(rs.getInt("stars"));
						 storeCommentVO.setSto_num(rs.getString("sto_num"));
						 list.add(storeCommentVO);
					}
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if(rs!=null){
						try {
							rs.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(pstmt!=null){
						try {
							pstmt.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(con!=null){
						try {
							con.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			return list;
		}

		@Override
		public byte[] getLogo(String sto_num) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			byte[] logo = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_STO_LOGO);
				pstmt.setString(1, sto_num);
				rs =pstmt.executeQuery();
				rs.next();
				logo= rs.getBytes(1);
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					pstmt.close();
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
			return logo;
			
			
		}

		@Override
		public void updateStoInfo(StoreProfileVO storeProfileVO) {
			Connection con= null;
			PreparedStatement pstmt = null;
			
			try {
				con=ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_STO_INFO);
				pstmt.setString(1, storeProfileVO.getGuy());
				pstmt.setString(2, storeProfileVO.getMobile());
				pstmt.setBytes(3, storeProfileVO.getSto_logo());
				pstmt.setString(4, storeProfileVO.getSto_introduce());
				pstmt.setString(5, storeProfileVO.getSto_pwd());
				pstmt.setString(6, storeProfileVO.getSto_num());
				pstmt.executeUpdate();
		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(pstmt!=null){
					try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				if(con!=null){
					try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
			
			
		}
		
		
//---------------end Eric-------------------------------		
}
