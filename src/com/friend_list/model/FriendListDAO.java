package com.friend_list.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.member_profile.model.MemberProfileVO;

public class FriendListDAO implements FriendListDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

//--------------------KAHN----------------------------	
	private static final String INSERT_STMT = "INSERT INTO FRIEND_LIST (INV_MEM_NUM,INVD_MEM_NUM,ISFRIEND) VALUES (?, ?, ?)";
	private static final String UPDATE = "UPDATE FRIEND_LIST SET ISFRIEND=? WHERE INV_MEM_NUM=? AND INVD_MEM_NUM=?";
	private static final String GET_MEMBERS1 = "SELECT * FROM MEMBER_PROFILE WHERE MEM_NUM IN (SELECT DISTINCT INVD_MEM_NUM FROM FRIEND_LIST WHERE INV_MEM_NUM=? AND ISFRIEND=?)";
	private static final String GET_MEMBERS2 = "SELECT * FROM MEMBER_PROFILE WHERE MEM_NUM IN (SELECT DISTINCT INV_MEM_NUM FROM FRIEND_LIST WHERE INVD_MEM_NUM=? AND ISFRIEND=?)";
	private static final String GET_MEMBERS3 = "SELECT * FROM MEMBER_PROFILE WHERE MEM_NAME LIKE ? AND MEM_NUM NOT IN (SELECT DISTINCT INV_MEM_NUM FROM FRIEND_LIST WHERE INVD_MEM_NUM=? AND ISFRIEND=?) AND MEM_NUM NOT IN (SELECT DISTINCT INVD_MEM_NUM FROM FRIEND_LIST WHERE INV_MEM_NUM=? AND ISFRIEND=?) AND MEM_NUM<>?";

	@Override
	public void insert(FriendListVO friendlistVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			con.setAutoCommit(false);

			pstmt.setString(1, friendlistVO.getInv_mem_num());
			pstmt.setString(2, friendlistVO.getInvd_mem_num());
			pstmt.setString(3, friendlistVO.getIsfriend());

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
	public void update(FriendListVO friendlistVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			con.setAutoCommit(false);

			pstmt.setString(1, friendlistVO.getIsfriend());
			pstmt.setString(2, friendlistVO.getInv_mem_num());
			pstmt.setString(3, friendlistVO.getInvd_mem_num());

			pstmt.executeUpdate();

			con.commit();

		} catch (SQLException se) {
			try {
				con.rollback();
				throw new RuntimeException("A database error occured. " + se.getMessage());
			} catch (SQLException e) {
				e.printStackTrace();
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
	public List<MemberProfileVO> getFromInv(FriendListVO friendlistVO) {
		List<MemberProfileVO> list = new ArrayList<MemberProfileVO>();
		MemberProfileVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEMBERS1);

			pstmt.setString(1, friendlistVO.getInv_mem_num());
			pstmt.setString(2, friendlistVO.getIsfriend());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memVO = new MemberProfileVO();
				memVO.setMem_num(rs.getString("MEM_NUM"));
				// memVO.setMem_acc(rs1.getString("MEM_ACC"));
				// memVO.setMem_pwd(rs1.getString("MEM_PWD"));
				memVO.setMem_name(rs.getString("MEM_NAME"));
				memVO.setSex(rs.getString("SEX"));
				// memVO.setAge(rs1.getInt("AGE"));
				// memVO.setMobile(rs1.getString("MOBILE"));
				// memVO.setCek_mobile(rs1.getString("CEK_MOBILE"));
				memVO.setEmail(rs.getString("EMAIL"));
				// memVO.setAddress(rs1.getString("ADDRESS"));
				// memVO.setRem_point(rs1.getInt("REM_POINT"));
				// memVO.setMem_pic(rs1.getBytes(0));
				list.add(memVO);
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

	@Override
	public List<MemberProfileVO> getFromInvd(FriendListVO friendlistVO) {
		List<MemberProfileVO> list = new ArrayList<MemberProfileVO>();
		MemberProfileVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEMBERS2);

			pstmt.setString(1, friendlistVO.getInv_mem_num());
			pstmt.setString(2, friendlistVO.getIsfriend());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memVO = new MemberProfileVO();
				memVO.setMem_num(rs.getString("MEM_NUM"));
				// memVO.setMem_acc(rs1.getString("MEM_ACC"));
				// memVO.setMem_pwd(rs1.getString("MEM_PWD"));
				memVO.setMem_name(rs.getString("MEM_NAME"));
				memVO.setSex(rs.getString("SEX"));
				// memVO.setAge(rs1.getInt("AGE"));
				// memVO.setMobile(rs1.getString("MOBILE"));
				// memVO.setCek_mobile(rs1.getString("CEK_MOBILE"));
				memVO.setEmail(rs.getString("EMAIL"));
				// memVO.setAddress(rs1.getString("ADDRESS"));
				// memVO.setRem_point(rs1.getInt("REM_POINT"));
				// memVO.setMem_pic(rs1.getBytes(0));
				list.add(memVO);
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

	@Override
	public List<MemberProfileVO> getMembers(String name, FriendListVO friendlistVO) {
		List<MemberProfileVO> list = new ArrayList<MemberProfileVO>();
		MemberProfileVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEMBERS3);

			pstmt.setString(1, "%" + name + "%");
			pstmt.setString(2, friendlistVO.getInv_mem_num());
			pstmt.setString(3, friendlistVO.getIsfriend());
			pstmt.setString(4, friendlistVO.getInv_mem_num());
			pstmt.setString(5, friendlistVO.getIsfriend());
			pstmt.setString(6, friendlistVO.getInv_mem_num());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memVO = new MemberProfileVO();
				memVO.setMem_num(rs.getString("MEM_NUM"));
				// memVO.setMem_acc(rs1.getString("MEM_ACC"));
				// memVO.setMem_pwd(rs1.getString("MEM_PWD"));
				memVO.setMem_name(rs.getString("MEM_NAME"));
				memVO.setSex(rs.getString("SEX"));
				// memVO.setAge(rs1.getInt("AGE"));
				// memVO.setMobile(rs1.getString("MOBILE"));
				// memVO.setCek_mobile(rs1.getString("CEK_MOBILE"));
				// memVO.setEmail(rs.getString("EMAIL"));
				// memVO.setAddress(rs1.getString("ADDRESS"));
				// memVO.setRem_point(rs1.getInt("REM_POINT"));
				// memVO.setMem_pic(rs1.getBytes(0));
				list.add(memVO);
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
	
//--------------------end KAHN----------------------------		

	
	
//--------------------Shawn-------------------------------
	private static final String GET_ONE_FRIEND_LIST = 
			"SELECT inv_mem_num, invd_mem_num, to_char(join_time,'yyyy-mm-dd')join_time, isfriend FROM FRIEND_LIST WHERE inv_mem_num=? AND invd_mem_num=?";
	private static final String INSERT_FRIEND_LIST = 
			"INSERT INTO FRIEND_LIST (inv_mem_num, invd_mem_num, join_time, isfriend) VALUES (?, ?, ?, ?)";
	private static final String UPDATE_FRIEND_LIST = 
			"UPDATE FRIEND_LIST set join_time=? ,isfriend=? WHERE inv_mem_num=? AND invd_mem_num=?";

	@Override
	public FriendListVO getIsFriend(String inv_mem_num, String invd_mem_num) {
		FriendListVO friendListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_FRIEND_LIST);
			
			pstmt.setString(1, inv_mem_num);
			pstmt.setString(2, invd_mem_num);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				friendListVO = new FriendListVO();

				friendListVO.setInv_mem_num(rs.getString("inv_mem_num"));
				friendListVO.setInvd_mem_num(rs.getString("invd_mem_num"));
				friendListVO.setJoin_time(rs.getDate("join_time"));
				friendListVO.setIsfriend(rs.getString("isfriend"));

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
		return friendListVO;
	}

	@Override
	public void insertFriendList(FriendListVO friendListVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_FRIEND_LIST);

			pstmt.setString(1, friendListVO.getInv_mem_num());
			pstmt.setString(2, friendListVO.getInvd_mem_num());
			pstmt.setTimestamp(3, new java.sql.Timestamp( friendListVO.getJoin_time().getTime()));
			pstmt.setString(4, friendListVO.getIsfriend());

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
	public void updateFriendList(FriendListVO friendListVO) {
		
		
	}
	
	
	
//--------------------end Shawn-------------------------------	
}