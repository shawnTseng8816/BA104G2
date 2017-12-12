package com.card_list.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CardListDAO implements CardListDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	// 查詢單個會員所有集點卡清單
	private static final String GET_MEM_CARDLIST = "SELECT * FROM CARD_LIST WHERE MEM_NUM=?";

	// 查詢所有集點卡清單
	private static final String GET_ALL = "SELECT * FROM CARD_LIST";

	private static final String GET_MYCARD = "SELECT * FROM CARD_LIST WHERE MEM_NUM=? AND STO_NUM=? AND STATUS=? AND USED_DATE IS NULL AND EXP_DATE >= SYSTIMESTAMP";

	private static final String GET_CARDINFO = "SELECT * FROM CARD_LIST WHERE CARD_NUM=?";

	private static final String UPDATE_MYCARD = "UPDATE CARD_LIST SET USED_DATE=?, OR_NUM=? WHERE CARD_NUM=?";

	// 查詢單個會員所有集點卡清單
	@Override
	public List<CardListVO> getCardListByMem_num(String mem_num) {

		List<CardListVO> list = new ArrayList<CardListVO>();
		CardListVO cardlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_MEM_CARDLIST);
			pstmt.setString(1, mem_num);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				cardlistVO = new CardListVO();
				cardlistVO.setCard_num(rs.getString("card_num"));
				cardlistVO.setSto_num(rs.getString("sto_num"));
				cardlistVO.setCard_kinds(rs.getString("card_kinds"));
				cardlistVO.setValue(rs.getInt("value"));
				cardlistVO.setStatus(rs.getString("status"));
				cardlistVO.setOr_num(rs.getString("or_num"));
				cardlistVO.setExp_date(rs.getTimestamp("exp_date"));
				cardlistVO.setUsed_date(rs.getTimestamp("used_date"));
				list.add(cardlistVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public List<CardListVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void newCard() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CardListVO> getCardListByMem_numAndSto_num(CardListVO cardListVO) {
		List<CardListVO> list = new ArrayList<CardListVO>();
		CardListVO cardlistVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_MYCARD);
			pstmt.setString(1, cardListVO.getMem_num());
			pstmt.setString(2, cardListVO.getSto_num());
			pstmt.setString(3, cardListVO.getStatus());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				cardlistVO = new CardListVO();
				cardlistVO.setCard_num(rs.getString("CARD_NUM"));
				cardlistVO.setSto_num(rs.getString("STO_NUM"));
				cardlistVO.setCard_kinds(rs.getString("CARD_KINDS"));
				cardlistVO.setValue(rs.getInt("VALUE"));
				cardlistVO.setStatus(rs.getString("STATUS"));
				cardlistVO.setOr_num(rs.getString("OR_NUM"));
				cardlistVO.setExp_date(rs.getTimestamp("EXP_DATE"));
				cardlistVO.setUsed_date(rs.getTimestamp("USED_DATE"));
				list.add(cardlistVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void updateMyCard(String card_num, String or_num) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();

			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(UPDATE_MYCARD);
			pstmt.setTimestamp(1, new java.sql.Timestamp(new Date().getTime()));
			pstmt.setString(3, card_num);
			pstmt.setString(2, or_num);
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
	public CardListVO getCardInfo(String card_num) {
		CardListVO cardlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_CARDINFO);
			pstmt.setString(1, card_num);

			rs = pstmt.executeQuery();

			rs.next();
			cardlistVO = new CardListVO();
			cardlistVO.setCard_num(rs.getString("card_num"));
			cardlistVO.setSto_num(rs.getString("sto_num"));
			cardlistVO.setCard_kinds(rs.getString("card_kinds"));
			cardlistVO.setValue(rs.getInt("value"));
			cardlistVO.setStatus(rs.getString("status"));
			cardlistVO.setOr_num(rs.getString("or_num"));
			cardlistVO.setExp_date(rs.getTimestamp("exp_date"));
			cardlistVO.setUsed_date(rs.getTimestamp("used_date"));

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return cardlistVO;
	}
	
	
//-----------Eric----------------
	//查詢單個會員所有集點卡清單
//		private static final String GET_MEM_CARDLIST ="SELECT * FROM CARD_LIST WHERE MEM_NUM=?";
		
		
		//查詢所有集點卡清單
//		private static final String GET_ALL ="SELECT * FROM CARD_LIST";
			
		private static final String GET_CARD_CASH = "SELECT C.POINTS_CASH FROM CARD C JOIN CARD_LIST CL"
													+" ON C.CARD_KINDS = CL.CARD_KINDS AND CL.OR_NUM =? ";
		
		
		
		
			@Override
			public int getCardCash(String or_num) {
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				int cash = 0;
				try {
					
					con = ds.getConnection();
					
					pstmt = con.prepareStatement(GET_CARD_CASH);
					pstmt.setString(1, or_num);
			
					rs = pstmt.executeQuery();

					if(rs.next()) {
						
					cash = rs.getInt("POINTS_CASH");
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
				return cash;
			}
			
		
		//查詢單個會員所有集點卡清單
//		@Override
//		public List<CardListVO> getCardListByMem_num(String mem_num) {
//			
//			List<CardListVO> list = new ArrayList<CardListVO>();
//			CardListVO cardlistVO = null;
//			Connection con = null;
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//
//			try {
//				
//				con = ds.getConnection();
//				
//				pstmt = con.prepareStatement(GET_MEM_CARDLIST);
//				pstmt.setString(1, mem_num);
//		
//				rs = pstmt.executeQuery();
//
//				while (rs.next()) {
//					
//					cardlistVO = new CardListVO();			
//					cardlistVO.setCard_num(rs.getString("card_num"));
//					cardlistVO.setSto_num(rs.getString("sto_num"));	
//					cardlistVO.setCard_kinds(rs.getString("card_kinds"));
//					cardlistVO.setValue(rs.getInt("value"));
//					cardlistVO.setStatus(rs.getString("status"));
//					cardlistVO.setOr_num(rs.getInt("or_num"));
//					cardlistVO.setExp_date(rs.getTimestamp("exp_date"));
//					cardlistVO.setUsed_date(rs.getTimestamp("used_date"));
//					list.add(cardlistVO); // Store the row in the list
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
//			return list;
//		}


		


//		@Override
//		public List<CardListVO> getAll() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//
//
//
//
//		@Override
//		public void newCard() {
//			// TODO Auto-generated method stub
//			
//		}
//		
//----------end Eric---------------------		
}
