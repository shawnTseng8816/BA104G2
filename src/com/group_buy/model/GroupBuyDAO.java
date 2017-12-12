package com.group_buy.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.member_profile.model.MemberProfileVO;

public class GroupBuyDAO implements GroupBuyDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT = "INSERT INTO GROUP_BUY (INV_MEM_NUM, INVD_MEM_NUM, MEROR_NUM, ISACCEPT) VALUES (?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE GROUP_BUY SET ISACCEPT=? WHERE INVD_MEM_NUM=? AND MEROR_NUM=?";
	private static final String GET_GROUPBUYMEMS = "SELECT * FROM GROUP_BUY WHERE MEROR_NUM=? AND INV_MEM_NUM=?";
	private static final String GET_MYGROUPBUYINVITE = "SELECT * FROM GROUP_BUY WHERE INVD_MEM_NUM=? AND ISACCEPT=? order by inv_time desc";
	private static final String GET_INVITER = "SELECT MEM_NUM, MEM_NAME FROM MEMBER_PROFILE WHERE MEM_NUM IN (SELECT INV_MEM_NUM FROM GROUP_BUY WHERE MEROR_NUM=?)";

	@Override
	public void insert(GroupBuyVO groupBuyVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(INSERT);

			con.setAutoCommit(false);

			pstmt.setString(1, groupBuyVO.getInv_mem_num());
			pstmt.setString(2, groupBuyVO.getInvd_mem_num());
			pstmt.setString(3, groupBuyVO.getMeror_num());
			pstmt.setString(4, groupBuyVO.getIsaccept());

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
	public void update(GroupBuyVO groupBuyVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(UPDATE);

			con.setAutoCommit(false);

			pstmt.setString(1, groupBuyVO.getIsaccept());
			pstmt.setString(2, groupBuyVO.getInvd_mem_num());
			pstmt.setString(3, groupBuyVO.getMeror_num());

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
	public List<GroupBuyVO> getGroupBuyMem(GroupBuyVO groupBuyVO) {
		GroupBuyVO grouBuyVO = null;
		List<GroupBuyVO> groupBuyMems = new ArrayList<GroupBuyVO>();;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_GROUPBUYMEMS);

			pstmt.setString(1, groupBuyVO.getMeror_num());
			pstmt.setString(2, groupBuyVO.getInv_mem_num());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				grouBuyVO = new GroupBuyVO();
				grouBuyVO.setIsaccept(rs.getString("ISACCEPT"));
				grouBuyVO.setInvd_mem_num(rs.getString("INVD_MEM_NUM"));
				
				groupBuyMems.add(grouBuyVO);
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
		return groupBuyMems;
	}

	@Override
	public MemberProfileVO getInviter(GroupBuyVO groupBuyVO) {
		MemberProfileVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_INVITER);

			pstmt.setString(1, groupBuyVO.getMeror_num());

			rs = pstmt.executeQuery();

			rs.next();
			memVO = new MemberProfileVO();
			memVO.setMem_num(rs.getString("MEM_NUM"));
			memVO.setMem_name(rs.getString("MEM_NAME"));

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
	public List<GroupBuyVO> getMyGroupBuyInvite(GroupBuyVO groupBuyVO) {
		GroupBuyVO grouBuyVO = null;
		List<GroupBuyVO> groupBuyMems = new ArrayList<GroupBuyVO>();;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MYGROUPBUYINVITE);

			pstmt.setString(1, groupBuyVO.getInvd_mem_num());
			pstmt.setString(2, groupBuyVO.getIsaccept());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				grouBuyVO = new GroupBuyVO();
				grouBuyVO.setInv_mem_num(rs.getString("INV_MEM_NUM"));
				grouBuyVO.setInv_time(rs.getTimestamp("INV_TIME"));
				grouBuyVO.setIsaccept(rs.getString("ISACCEPT"));
				grouBuyVO.setMeror_num(rs.getString("MEROR_NUM"));
				grouBuyVO.setInvd_mem_num(rs.getString("INVD_MEM_NUM"));
				
				groupBuyMems.add(grouBuyVO);
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
		return groupBuyMems;
	}

}