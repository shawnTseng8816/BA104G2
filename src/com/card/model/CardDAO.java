package com.card.model;

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

public class CardDAO implements CardDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	// 新增一張集點卡									
	private static final String NEW_CARD = "INSERT INTO CARD(CARD_KINDS, STO_NUM, POINTS, POINTS_CASH, CARD_DES, EXP_DATE)VALUES('CK'||LPAD(to_char(SEQ_CARD_KINDS.NEXTVAL),3,'0'),?,?,?,?,?)";
										
	// 下架全部集點卡
	private static final String UPDATE_CARD_STATUS_DOWN = "UPDATE CARD SET STATUS='下架' WHERE STO_NUM=?";
	
	// 上架集點卡
	private static final String UPDATE_CARD_KINDS = "UPDATE CARD SET STATUS='上架' WHERE CARD_KINDS=?";

	// 查詢單店家所有集點卡資訊
	private static final String GET_ONE_STO_CARD = "SELECT * FROM CARD WHERE STO_NUM=?";
	
	// 查詢單張集點卡資訊
	private static final String GET_ONE_CARD_INFO_BY_CARD_KINDS = "SELECT * FROM CARD WHERE CARD_KINDS=?";

	private static final String GET_STO_NOW_CARD = "SELECT * FROM CARD WHERE STATUS='上架' AND STO_NUM=? ";

	
	
	
	
	
	
	@Override
	public void insertCard(CardVO cardVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(NEW_CARD);
			pstmt.setString(1, cardVO.getSto_num());
			pstmt.setInt(2, cardVO.getPoints());
			pstmt.setInt(3, cardVO.getPoints_cash());
			pstmt.setString(4, cardVO.getCard_des());
			pstmt.setInt(5, cardVO.getExp_date());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (con != null) {
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
	public void upCard(String sto_num, String card_kinds) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_CARD_STATUS_DOWN);
			pstmt.setString(1, sto_num);
			pstmt.executeUpdate();
			pstmt = con.prepareStatement(UPDATE_CARD_KINDS);
			pstmt.setString(1, card_kinds);
			pstmt.executeUpdate();
			con.commit();

		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public List<CardVO> getall() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CardVO> getCardsBySto_num(String sto_num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CardVO> list = new ArrayList<CardVO>();
		System.out.println("sto" + sto_num);
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STO_CARD);
			pstmt.setString(1, sto_num);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CardVO cardVO = new CardVO();
				cardVO.setCard_kinds(rs.getString("card_kinds"));
				System.out.println(rs.getString("card_kinds"));
				cardVO.setCard_des(rs.getString("card_des"));
				cardVO.setExp_date(rs.getInt("exp_date"));
				cardVO.setPoints(rs.getInt("points"));
				cardVO.setPoints_cash(rs.getInt("points_cash"));
				cardVO.setSto_num(rs.getString("sto_num"));
				cardVO.setStatus(rs.getString("status"));
				list.add(cardVO);

			}

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
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		return list;
	}

	@Override
	public void downCard(String sto_num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		System.out.println("OK?");
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(UPDATE_CARD_STATUS_DOWN);
			pstmt.setString(1, sto_num);
			pstmt.executeUpdate();

		} catch (SQLException se) {

			throw new RuntimeException("A database error occured. " + se.getMessage());
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
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	
	
	@Override
	public CardVO getStoNowCrad(String sto_num) {
		
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CardVO cardVO = new CardVO();
		System.out.println("sto"+sto_num);
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_STO_NOW_CARD);
			pstmt.setString(1, sto_num);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				
				cardVO.setCard_kinds(rs.getString("card_kinds"));
				cardVO.setCard_des(rs.getString("card_des"));
				cardVO.setExp_date(rs.getInt("exp_date"));
				cardVO.setPoints(rs.getInt("points"));
				cardVO.setPoints_cash(rs.getInt("points_cash"));
				cardVO.setSto_num(rs.getString("sto_num"));
				cardVO.setStatus(rs.getString("status"));
				
			}
		}catch(

	SQLException e)
	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
	{
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

	return cardVO;
}

	@Override
	public CardVO getOneCradInfo(String card_kinds) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CardVO cardVO =new CardVO();;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_CARD_INFO_BY_CARD_KINDS);
			pstmt.setString(1, card_kinds);
			rs = pstmt.executeQuery();
			rs.next();
			cardVO.setCard_des(rs.getString("card_des"));
			cardVO.setCard_kinds(rs.getString("card_kinds"));
			cardVO.setExp_date(rs.getInt("exp_date"));
			cardVO.setPoints_cash(rs.getInt("points_cash"));
			cardVO.setPoints(rs.getInt("points"));
			cardVO.setStatus(rs.getString("status"));
			cardVO.setSto_num(rs.getString("sto_num"));
	
			
			
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
		
		
		return cardVO;
	}

}
