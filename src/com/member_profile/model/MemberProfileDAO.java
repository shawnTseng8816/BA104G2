package com.member_profile.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemberProfileDAO implements MemberProfileDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

//-------------------------KAHN---------------------------------	
	private static final String GET_MEMBERPROFILE = "SELECT * FROM MEMBER_PROFILE WHERE MEM_NUM=?";
	private static final String UPDATE = "UPDATE MEMBER_PROFILE SET MEM_NAME=NVL(?, MEM_NAME), MEM_PWD=NVL(?, MEM_PWD), MOBILE=NVL(?, MOBILE), EMAIL=NVL(?, EMAIL), ADDRESS=NVL(?, ADDRESS), MEM_IMG=NVL(?, MEM_IMG), REM_POINT=NVL(?, REM_POINT) WHERE MEM_NUM=?";

	@Override
	public void update(MemberProfileVO memberProfileVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(UPDATE);

			Blob blob = con.createBlob();

			con.setAutoCommit(false);

			pstmt.setString(1, memberProfileVO.getMem_name());
			pstmt.setString(2, memberProfileVO.getMem_pwd());
			pstmt.setString(3, memberProfileVO.getMobile());
			pstmt.setString(4, memberProfileVO.getEmail());
			pstmt.setString(5, memberProfileVO.getAddress());

			if (memberProfileVO.getMem_img() != null) {
				blob.setBytes(1, memberProfileVO.getMem_img());
			} else {
				blob = null;
			}

			pstmt.setBlob(6, blob);

			// pstmt.setBytes(6, memberProfileVO.getMem_pic());
			
			if (memberProfileVO.getRem_point() != null) {
				pstmt.setInt(7, memberProfileVO.getRem_point());
			} else {
				pstmt.setNull(7, java.sql.Types.INTEGER);
			}
			
			pstmt.setString(8, memberProfileVO.getMem_num());

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
	public MemberProfileVO getMemProfile(MemberProfileVO memberProfileVO) {
		MemberProfileVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEMBERPROFILE);

			pstmt.setString(1, memberProfileVO.getMem_num());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memVO = new MemberProfileVO();
				memVO.setMem_num(rs.getString("MEM_NUM"));
				memVO.setMem_acc(rs.getString("MEM_ACC"));
				memVO.setMem_pwd(rs.getString("MEM_PWD"));
				memVO.setMem_name(rs.getString("MEM_NAME"));
				memVO.setSex(rs.getString("SEX"));
				memVO.setAge(rs.getInt("AGE"));
				memVO.setMobile(rs.getString("MOBILE"));
				memVO.setCek_mobile(rs.getString("CEK_MOBILE"));
				memVO.setEmail(rs.getString("EMAIL"));
				memVO.setAddress(rs.getString("ADDRESS"));
				memVO.setRem_point(rs.getInt("REM_POINT"));
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
		return memVO;
	}
	

//-------------------------end KAHN---------------------------------	
	
	
	
	
	
//------------------------Chi------------------------------------
	//新增，註冊用
		private static final String INSERT_STMT = 
			"INSERT INTO member_profile (mem_num,mem_acc,mem_pwd,mem_name,sex,age,mobile,cek_mobile,email,address,rem_point,mem_img,status) VALUES ('MB'||LPAD(to_char(SEQ_MEM_NUM.nextval), 10, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		private static final String GET_MEMBERPROFILE_CHI = "SELECT * FROM MEMBER_PROFILE WHERE MEM_NUM=?";
		private static final String UPDATE_CHI = "UPDATE MEMBER_PROFILE SET MEM_NAME=NVL(?, MEM_NAME), MEM_PWD=NVL(?, MEM_PWD), MOBILE=NVL(?, MOBILE), EMAIL=NVL(?, EMAIL), ADDRESS=NVL(?, ADDRESS), MEM_PIC=NVL(?, MEM_PIC) WHERE MEM_NUM=?";

		private static final String GET_MEM_STMT = "SELECT * FROM MEMBER_PROFILE WHERE MEM_ACC=?";
		
		private static final String GET_ALL_ACCOUNT = "SELECT MEM_ACC FROM MEMBER_PROFILE";
		
		@Override
		public List<String> getAllAccount(){
			List<String> accountList = new ArrayList<>();
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_ACCOUNT);
				
				rs = pstmt.executeQuery();
				while(rs.next()){
					String account = rs.getString("mem_acc");
					accountList.add(account);
				}
				
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
			return accountList;
		}
		
		
		@Override
		public void insert(MemberProfileVO memberprofileVO) {
			
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);
				
				Blob blob = con.createBlob();

				con.setAutoCommit(false);
			
				pstmt.setString(1, memberprofileVO.getMem_acc());
				pstmt.setString(2, memberprofileVO.getMem_pwd());
				pstmt.setString(3, memberprofileVO.getMem_name());
				pstmt.setString(4, memberprofileVO.getSex());
				pstmt.setInt(5, memberprofileVO.getAge());
				pstmt.setString(6, memberprofileVO.getMobile());
				pstmt.setString(7, memberprofileVO.getCek_mobile());
				pstmt.setString(8, memberprofileVO.getEmail());
				pstmt.setString(9, memberprofileVO.getAddress());
				pstmt.setInt(10, memberprofileVO.getRem_point());
				
				if (memberprofileVO.getMem_img() != null) {
					blob.setBytes(1, memberprofileVO.getMem_img());
				} else {
					blob = null;
				}
				pstmt.setBlob(11, blob);

				pstmt.setString(12, memberprofileVO.getStatus());

				pstmt.executeUpdate();  // insert也是嗎??

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
		public void updateChi(MemberProfileVO memberProfileVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();

				pstmt = con.prepareStatement(UPDATE_CHI);

				Blob blob = con.createBlob();

				con.setAutoCommit(false);

				pstmt.setString(1, memberProfileVO.getMem_name());
				pstmt.setString(2, memberProfileVO.getMem_pwd());
				pstmt.setString(3, memberProfileVO.getMobile());
				pstmt.setString(4, memberProfileVO.getEmail());
				pstmt.setString(5, memberProfileVO.getAddress());

				if (memberProfileVO.getMem_img() != null) {
					blob.setBytes(1, memberProfileVO.getMem_img());
				} else {
					blob = null;
				}

				pstmt.setBlob(6, blob);

				pstmt.setString(7, memberProfileVO.getMem_num());

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
		public MemberProfileVO getMemProfile(String mem_num) {
			MemberProfileVO memVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_MEMBERPROFILE_CHI);
			
				pstmt.setString(1, mem_num);
				
				rs = pstmt.executeQuery();
				System.out.println("dwdwddddddddddda");
				while (rs.next()) {
					memVO = new MemberProfileVO();
					memVO.setMem_num(rs.getString("MEM_NUM"));
					memVO.setMem_acc(rs.getString("MEM_ACC"));
					System.out.println(rs.getString("MEM_ACC"));
					memVO.setMem_pwd(rs.getString("MEM_PWD"));
					memVO.setMem_name(rs.getString("MEM_NAME"));
					memVO.setSex(rs.getString("SEX"));
					memVO.setAge(rs.getInt("AGE"));
					memVO.setMobile(rs.getString("MOBILE"));
					memVO.setCek_mobile(rs.getString("CEK_MOBILE"));
					memVO.setEmail(rs.getString("EMAIL"));
					memVO.setAddress(rs.getString("ADDRESS"));
					memVO.setRem_point(rs.getInt("REM_POINT"));
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
			return memVO;
		}

		@Override
		public MemberProfileVO checkLogin(String mem_acc) {
			MemberProfileVO memVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement( GET_MEM_STMT);	
				pstmt.setString(1, mem_acc);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					memVO = new MemberProfileVO();
					memVO.setMem_num(rs.getString("MEM_NUM"));
					memVO.setMem_acc(rs.getString("MEM_ACC"));
					memVO.setMem_pwd(rs.getString("MEM_PWD"));
					memVO.setMem_name(rs.getString("MEM_NAME"));
					memVO.setSex(rs.getString("SEX"));
					memVO.setAge(rs.getInt("AGE"));
					memVO.setMobile(rs.getString("MOBILE"));
					memVO.setCek_mobile(rs.getString("CEK_MOBILE"));
					memVO.setEmail(rs.getString("EMAIL"));
					memVO.setAddress(rs.getString("ADDRESS"));
					memVO.setRem_point(rs.getInt("REM_POINT"));
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
			return memVO;
		}
	
	
	
//----------------------end Chi-----------------------------------	
		
		
		
//--------------------Shawn---------------------------------------
		
		
		private static final String GET_MEMBER_PROFILE = 
				"SELECT mem_num, mem_acc, mem_pwd, mem_name, sex, age, mobile, cek_mobile, email, address, "
				+ "rem_point, mem_img, status FROM MEMBER_PROFILE WHERE mem_num=?";
		private static final String GET_MEMBER_PROFILE_ALL_NAME = 
				"SELECT mem_num, mem_acc, mem_pwd, mem_name, sex, age, mobile, cek_mobile, email, address, "
				+ "rem_point, mem_img, status FROM MEMBER_PROFILE WHERE mem_name=?";
		private static final String UPDATE_MEMBER_PROFILE = 
				"UPDATE MEMBER_PROFILE set status=? WHERE mem_num=?";
		private static final String GET_ALL = "SELECT * FROM MEMBER_PROFILE";
		private static final String GET_MEMBER_PROFILE_IMAGE = 
				"SELECT mem_img FROM MEMBER_PROFILE WHERE mem_num=?";
		
		
		@Override
		public MemberProfileVO getMemberProfileByMem_num(String mem_num) {	
			MemberProfileVO memberProfileVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_MEMBER_PROFILE);
				pstmt.setString(1, mem_num);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					memberProfileVO = new MemberProfileVO();
					memberProfileVO.setMem_num(rs.getString("mem_num"));
					memberProfileVO.setMem_acc(rs.getString("mem_acc"));
					memberProfileVO.setMem_pwd(rs.getString("mem_pwd"));
					memberProfileVO.setMem_name(rs.getString("mem_name"));
					memberProfileVO.setSex(rs.getString("sex"));
					memberProfileVO.setAge(rs.getInt("age"));
					memberProfileVO.setMobile(rs.getString("mobile"));
					memberProfileVO.setCek_mobile(rs.getString("cek_mobile"));
					memberProfileVO.setEmail(rs.getString("email"));
					memberProfileVO.setAddress(rs.getString("address"));
					memberProfileVO.setRem_point(rs.getInt("rem_point"));
					memberProfileVO.setMem_img(rs.getBytes("mem_img"));
					memberProfileVO.setStatus(rs.getString("status"));
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
			return memberProfileVO;
		}

		@Override
		public Set<MemberProfileVO> getMemberProfileAllName(String mem_name) {
			Set<MemberProfileVO> set = new LinkedHashSet<MemberProfileVO>();		
			MemberProfileVO memberProfileVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_MEMBER_PROFILE_ALL_NAME);
				pstmt.setString(1, mem_name);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					memberProfileVO = new MemberProfileVO();
					memberProfileVO.setMem_num(rs.getString("mem_num"));
					memberProfileVO.setMem_acc(rs.getString("mem_acc"));
					memberProfileVO.setMem_pwd(rs.getString("mem_pwd"));
					memberProfileVO.setMem_name(rs.getString("mem_name"));
					memberProfileVO.setSex(rs.getString("sex"));
					memberProfileVO.setAge(rs.getInt("age"));
					memberProfileVO.setMobile(rs.getString("mobile"));
					memberProfileVO.setCek_mobile(rs.getString("cek_mobile"));
					memberProfileVO.setEmail(rs.getString("email"));
					memberProfileVO.setAddress(rs.getString("address"));
					memberProfileVO.setRem_point(rs.getInt("rem_point"));
					memberProfileVO.setMem_img(rs.getBytes("mem_img"));
					memberProfileVO.setStatus(rs.getString("status"));
					set.add(memberProfileVO);

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
			return set;
		}
		

		@Override
		public void updateMemberProfile(MemberProfileVO memberProfileVO) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_MEMBER_PROFILE);

				pstmt.setString(1, memberProfileVO.getStatus());
				pstmt.setString(2, memberProfileVO.getMem_num());

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
		public List<MemberProfileVO> getAll() {
			List<MemberProfileVO> list = new ArrayList<MemberProfileVO>();
			MemberProfileVO memberProfileVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					memberProfileVO = new MemberProfileVO();
					memberProfileVO.setMem_num(rs.getString("mem_num"));
					memberProfileVO.setMem_acc(rs.getString("mem_acc"));
					memberProfileVO.setMem_pwd(rs.getString("mem_pwd"));
					memberProfileVO.setMem_name(rs.getString("mem_name"));
					memberProfileVO.setSex(rs.getString("sex"));
					memberProfileVO.setAge(rs.getInt("age"));
					memberProfileVO.setMobile(rs.getString("mobile"));
					memberProfileVO.setCek_mobile(rs.getString("cek_mobile"));
					memberProfileVO.setEmail(rs.getString("email"));
					memberProfileVO.setAddress(rs.getString("address"));
					memberProfileVO.setRem_point(rs.getInt("rem_point"));
					memberProfileVO.setMem_img(rs.getBytes("mem_img"));
					memberProfileVO.setStatus(rs.getString("status"));
					list.add(memberProfileVO); // Store the row in the list
				}

				// Handle any SQL errors
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
		public Set<MemberProfileVO> getMemberProfileImg(String mem_num) {
			Set<MemberProfileVO> setMemberProfile = new LinkedHashSet<MemberProfileVO>();		
			MemberProfileVO memberProfileVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_MEMBER_PROFILE_IMAGE);
				pstmt.setString(1, mem_num);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					memberProfileVO = new MemberProfileVO();
					memberProfileVO.setMem_img(rs.getBytes("mem_img"));
					setMemberProfile.add(memberProfileVO);
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
			return setMemberProfile;
		}
//--------------end Shawn----------------------------------------		
		
		
//------------Eric------------------------------
		private static final String GET_ONE_MEM = "SELECT * FROM MEMBER_PROFILE WHERE MEM_NUM=?";
		
		private static final String GET_MEM_PIC = "SELECT MEM_IMG FROM MEMBER_PROFILE WHERE MEM_NUM=?";
		
		@Override
		public MemberProfileVO getOneMemInfo(String mem_num) {
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				MemberProfileVO memberProfileVO = new MemberProfileVO();
				try {
					con = ds.getConnection();
					pstmt = con.prepareStatement(GET_ONE_MEM);
					pstmt.setString(1, mem_num);
					rs = pstmt.executeQuery();
					if(rs.next()){
						memberProfileVO.setAddress(rs.getString("address"));
						memberProfileVO.setAge(rs.getInt("age"));
						memberProfileVO.setCek_mobile(rs.getString("cek_mobile"));
						memberProfileVO.setEmail(rs.getString("email"));
						memberProfileVO.setMem_acc(rs.getString("mem_acc"));
						memberProfileVO.setMem_name(rs.getString("mem_name"));
						memberProfileVO.setMem_num(rs.getString("mem_num"));
						memberProfileVO.setMem_pwd(rs.getString("mem_pwd"));
						memberProfileVO.setMobile(rs.getString("mobile"));
						memberProfileVO.setRem_point(rs.getInt("rem_point"));
						memberProfileVO.setSex(rs.getString("sex"));
						memberProfileVO.setStatus(rs.getString("status"));
					}else{
						
						memberProfileVO = null;
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
				

			return  memberProfileVO;
		}

		@Override
		public byte[] getPic(String mem_num) {
			
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				byte[] pic = null;
				
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_MEM_PIC);
				pstmt.setString(1, mem_num);
				rs = pstmt.executeQuery();
				rs.next();
				pic = rs.getBytes(1);

				// Handle any driver errors
			}catch(

			SQLException se)
			{
				throw new RuntimeException("A database error occured. " + se.getMessage());
				// Clean up JDBC resources
			}finally
			{
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

			}return pic;

			}
		
		
//-----------end Eric-----------------------------		
}