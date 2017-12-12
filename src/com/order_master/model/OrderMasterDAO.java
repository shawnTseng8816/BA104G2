package com.order_master.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.card.model.CardVO;
import com.card_list.model.CardListVO;

public class OrderMasterDAO implements OrderMasterDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_ORDERMASTER = "INSERT INTO ORDER_MASTER (OR_NUM, MEM_NUM, STO_NUM, OR_STANUM, OR_AMOUNT, MEROR_NUM) VALUES ('OM'||TRIM(TO_CHAR(SEQ_OR_NUM.NEXTVAL, '0000000000')), ?, ?, ?, ?, ?)";
	private static final String UPDATE_ORDERMASTER = "UPDATE ORDER_MASTER SET OR_TIME=NVL(?, OR_TIME), OR_STANUM=NVL(?, OR_STANUM), RECE=NVL(?, RECE), PAY_MEN=NVL(?, PAY_MEN), OR_AMOUNT=NVL(?, OR_AMOUNT), MEROR_NUM=NVL(?, MEROR_NUM), ADDRESS=NVL(?, ADDRESS) WHERE OR_NUM=?";
	private static final String GET_ORDERMASTER = "SELECT * FROM ORDER_MASTER WHERE OR_NUM=NVL(?, null) AND OR_STANUM=NVL(?, null)";
	private static final String GET_ORDERMASTERS_WITHPAYMENT = "SELECT * FROM ORDER_MASTER WHERE MEROR_NUM=NVL(?, null) AND PAY_MEN IS NOT NULL";
	private static final String GET_ORDERMASTERS_WITHOUTPAYMENT = "SELECT * FROM ORDER_MASTER WHERE MEROR_NUM=NVL(?, null)";

	@Override
	public String insertOrderMaster(OrderMasterVO orderMaster) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String key = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			String[] cols = {"OR_NUM"}; // 或 int cols[] = {1};
			pstmt = con.prepareStatement(INSERT_ORDERMASTER, cols);

			con.setAutoCommit(false);

			pstmt.setString(1, orderMaster.getMem_num());
			pstmt.setString(2, orderMaster.getSto_num());
			pstmt.setString(3, orderMaster.getOr_stanum());
			pstmt.setInt(4, orderMaster.getOr_amount());
			pstmt.setString(5, orderMaster.getMeror_num());

			pstmt.executeUpdate();

			con.commit();

			rs = pstmt.getGeneratedKeys();
			rs.next();
			key = rs.getString(1);

		} catch (SQLException se) {
			try {
				con.rollback();
				System.out.println(se);
				throw new RuntimeException("A database error occured. " + se.getMessage());
			} catch (SQLException see) {
				see.printStackTrace();
			}
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
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return key;
	}

	@Override
	public void updateOrderMaster(OrderMasterVO orderMaster) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_ORDERMASTER);

			con.setAutoCommit(false);

			pstmt.setTimestamp(1, new java.sql.Timestamp(new Date().getTime()));
			pstmt.setString(2, orderMaster.getOr_stanum());
			pstmt.setString(3, orderMaster.getRece());
			pstmt.setString(4, orderMaster.getPay_men());
			// pstmt.setTimestamp(5, new
			// java.sql.Timestamp(orderMaster.getPre_rece().getTime()));

			if (orderMaster.getOr_amount() != null) {
				pstmt.setInt(5, orderMaster.getOr_amount());
			} else {
				pstmt.setNull(5, java.sql.Types.INTEGER);
			}
			pstmt.setString(6, orderMaster.getMeror_num());
			pstmt.setString(7, orderMaster.getAddress());
			pstmt.setString(8, orderMaster.getOr_num());

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
	public OrderMasterVO getEditOrderMaster(OrderMasterVO orderMaster) {
		OrderMasterVO orderMasterVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDERMASTER);

			pstmt.setString(1, orderMaster.getOr_num());
			pstmt.setString(2, orderMaster.getOr_stanum());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				orderMasterVO = new OrderMasterVO();
				orderMasterVO.setOr_num(rs.getString("OR_NUM"));
				orderMasterVO.setMem_num(rs.getString("MEM_NUM"));
				orderMasterVO.setSto_num(rs.getString("STO_NUM"));
				orderMasterVO.setOr_time(rs.getDate("OR_TIME"));
				orderMasterVO.setOr_stanum(rs.getString("OR_STANUM"));
				orderMasterVO.setOr_amount(rs.getInt("OR_AMOUNT"));
				orderMasterVO.setMeror_num(rs.getString("MEROR_NUM"));
				orderMasterVO.setAddress(rs.getString("ADDRESS"));
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
		return orderMasterVO;
	}

	@Override
	public List<OrderMasterVO> getGroupBuyOrderMaster(String meror_num, String or_stanum) {
		OrderMasterVO orderMasterVO = null;
		List<OrderMasterVO> groupBuyOrderMasters = new ArrayList<OrderMasterVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDERMASTERS_WITHPAYMENT);

			pstmt.setString(1, meror_num);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderMasterVO = new OrderMasterVO();
				orderMasterVO.setOr_num(rs.getString("OR_NUM"));
				orderMasterVO.setMem_num(rs.getString("MEM_NUM"));
				orderMasterVO.setSto_num(rs.getString("STO_NUM"));
				orderMasterVO.setOr_time(rs.getDate("OR_TIME"));
				orderMasterVO.setOr_stanum(rs.getString("OR_STANUM"));
				orderMasterVO.setOr_amount(rs.getInt("OR_AMOUNT"));
				orderMasterVO.setMeror_num(rs.getString("MEROR_NUM"));
				orderMasterVO.setPay_men(rs.getString("PAY_MEN"));

				groupBuyOrderMasters.add(orderMasterVO);
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
		return groupBuyOrderMasters;
	}

	@Override
	public List<OrderMasterVO> getGroupBuyOrderMasterWithoutPayMent(String meror_num) {
		OrderMasterVO orderMasterVO = null;
		List<OrderMasterVO> groupBuyOrderMasters = new ArrayList<OrderMasterVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDERMASTERS_WITHOUTPAYMENT);

			pstmt.setString(1, meror_num);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderMasterVO = new OrderMasterVO();
				orderMasterVO.setOr_num(rs.getString("OR_NUM"));
				orderMasterVO.setMem_num(rs.getString("MEM_NUM"));
				orderMasterVO.setSto_num(rs.getString("STO_NUM"));
				orderMasterVO.setOr_time(rs.getDate("OR_TIME"));
				orderMasterVO.setOr_stanum(rs.getString("OR_STANUM"));
				orderMasterVO.setOr_amount(rs.getInt("OR_AMOUNT"));
				orderMasterVO.setMeror_num(rs.getString("MEROR_NUM"));
				orderMasterVO.setPay_men(rs.getString("PAY_MEN"));

				groupBuyOrderMasters.add(orderMasterVO);
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
		return groupBuyOrderMasters;
	}

	
//----------------Eric----------------------
	// 會員是否抽獎過
		private static final String GET_IS_PLAY_GAME = "SELECT GAME FROM ORDER_MASTER WHERE OR_NUM=?";
		
		// 會員是否使用點數
		private static final String GET_IS_USE_POINTS = "SELECT PAY_MEN FROM ORDER_MASTER WHERE OR_NUM=?";

		// 取得店家交易完成獲得點數紀錄
		private static final String GET_STO_ORDER_VALUE_RECORD = "SELECT * FROM ORDER_MASTER WHERE STO_NUM=? AND OR_STANUM ='交易完成' AND PAY_MEN='點數'";

		// 取得訂單飲料總杯數
		private static final String GET_ONE_ORDER_AMOUNT = "SELECT COUNT(*) FROM ORDER_DETAIL WHERE OR_NUM =?";

		// 查詢此會員是否有此店家的集點卡 或 未集滿的集點卡
		private static final String SEARCH_MEM_CARD = "SELECT CL.Card_num, CL.Value, C.POINTS FROM CARD C JOIN CARD_LIST CL ON C.CARD_KINDS = CL.CARD_KINDS AND CL.STATUS='尚未集滿' AND CL.MEM_NUM=? AND CL.STO_NUM =?";

		// 新增一張滿的集點卡
		private static final String NEW_FULL_MEM_CARD = "INSERT INTO CARD_LIST(CARD_NUM,MEM_NUM,STO_NUM,CARD_KINDS,VALUE,STATUS,EXP_DATE)VALUES"
				+ "('CN'||LPAD(to_char(SEQ_CARD_NUM.NEXTVAL),6,'0'),?,?,?,?,'可使用',?)";

		// 查詢此會員是否有使用集點卡
		private static final String GET_IS_USE_CARD = "SELECT OR_NUM FROM CARD_LIST WHERE OR_NUM=?";

		// 使用集點卡完成交易
		private static final String UPDATE_USE_CARD = "UPDATE CARD_LIST SET STATUS='已使用',USED_DATE=SYSDATE WHERE OR_NUM=?";

		// 使用集點卡交易失敗
		private static final String UPDATE_USE_CARD_CANCEL = "UPDATE CARD_LIST OR_NUM=NULL WHERE OR_NUM=?";

		// 查詢目前店家集點卡編號
		private static final String GET_STO_NOW_CARD = "SELECT * FROM CARD WHERE STATUS='上架' AND STO_NUM=? ";

		// 新增一張集點卡
		private static final String NEW_MEM_CARD = "INSERT INTO CARD_LIST(CARD_NUM,MEM_NUM,STO_NUM,CARD_KINDS,VALUE,STATUS,EXP_DATE)VALUES"
				+ "('CN'||LPAD(to_char(SEQ_CARD_NUM.NEXTVAL),6,'0'),?,?,?,?,'尚未集滿',?)";

		// 更新集點卡點數
		private static final String UPDATE_MEM_CARD = "UPDATE CARD_LIST SET VALUE=?,STATUS=? WHERE CARD_NUM=?";
		
		// 查詢是否使用折價券
		private static final String GET_IS_USE_COUPON = "SELECT OR_NUM FROM COUPON_LIST WHERE OR_NUM=?";
		
		// 使用折價券交易失敗
		private static final String UPDATE_USE_COUPON_CANCEL = "UPDATE COUPON_LIST SET OR_NUM=NULL,USED_DATE=NULL WHERE OR_NUM=?";
		
		// 更新訂單狀態
		private static final String UPDATE_ORDER_STATUS = "UPDATE ORDER_MASTER SET OR_STANUM =?, PRE_RECE =SYSDATE WHERE OR_NUM =?";
		
		//更新團購訂單狀態MEROR_NUM
		private static final String UPDATE_GROUP_ORDER_STATUS = "UPDATE ORDER_MASTER SET OR_STANUM =?, PRE_RECE =SYSDATE WHERE MEROR_NUM =?";
		
		// 修改店家點數
		private static final String UPDATE_STO_POINTS = "UPDATE STORE_PROFILE SET REM_POINT=REM_POINT+? WHERE STO_NUM=?";

		// 修改會員點數
		private static final String UPDATE_MEM_POINTS = "UPDATE MEMBER_PROFILE SET REM_POINT=REM_POINT+? WHERE MEM_NUM=?";

		// 新增新集點卡點數
		private static final String INSERT_NEW_CARD_POINT_RECORD = "INSERT INTO CARD_RECORD(CARD_NUM,OR_NUM,ADD_VALUE)VALUES(?,?,?)";

		//取得會員未完成訂單 
		private static final String GET_MEM_NO_FINISH_ORDER = "SELECT * FROM ORDER_MASTER WHERE MEM_NUM=? AND OR_STANUM!='交易完成' order by or_time desc";
		//取得會員完成訂單
		private static final String GET_MEM_FINISH_ORDER = "SELECT * FROM ORDER_MASTER WHERE MEM_NUM=? AND OR_STANUM='交易完成' order by or_time desc";
		//取得會員點數付款
		private static final String GET_MEM_POINT_ORDER = "SELECT * FROM ORDER_MASTER WHERE MEM_NUM=? AND OR_STANUM='交易完成' AND PAY_MEN='點數' order by or_time desc";
				
		//更新寄杯狀態 用訂單編號
		private static final String UPDATE_KEEP_STATUS = "Update (SELECT KEEP_STATUS FROM KEEP_RECORD KR JOIN ORDER_DETAIL OD" 
															+" ON KR.ORDET_NUM = OD.ORDET_NUM AND OD.OR_NUM=?)"+"Set KEEP_STATUS = '未領取'";
		//查詢 單張訂單所有明細
		private static final String GET_ORDER_DETAIL_FOR_TOOL = "SELECT PRO.COM_NAME,SWE.SWEET_TYPE,ICE.ICE_TYPE,OD.COM_SIZE,OD.OD_PRICE,OD.ORDET_NUM  "+
																" FROM ORDER_DETAIL OD JOIN PRODUCT PRO ON OD.COM_NUM = PRO.COM_NUM JOIN SWEETNESS SWE ON OD.SWEET_NUM = SWE.SWEET_NUM JOIN ICE_LIST ICE "+
																" ON OD.ICE_NUM = ICE.ICE_NUM AND OD.OR_NUM =?";
		
		//查詢 單張訂單寄杯明細
		private static final String GET_KEEP_FOR_TOOL = "SELECT PRO.COM_NAME,SWE.SWEET_TYPE,ICE.ICE_TYPE,OD.COM_SIZE,OD.OD_PRICE,OD.ORDET_NUM  "+
														" FROM ORDER_DETAIL OD JOIN PRODUCT PRO ON OD.COM_NUM = PRO.COM_NUM  JOIN KEEP_RECORD KR ON KR.ORDET_NUM = OD.ORDET_NUM  JOIN SWEETNESS SWE ON OD.SWEET_NUM = SWE.SWEET_NUM JOIN ICE_LIST ICE "+
														" ON OD.ICE_NUM = ICE.ICE_NUM AND OD.OR_NUM =?";
			
			
		//查詢 團購訂單明細  使用 團購編號
		private static final String GET_GROUP_ORDER_DETAIL_FOR_TOOL ="SELECT PRO.COM_NAME,SWE.SWEET_TYPE,ICE.ICE_TYPE,OD.COM_SIZE,OD.OD_PRICE,OD.ORDET_NUM "
																	+" FROM ORDER_DETAIL OD JOIN PRODUCT PRO ON OD.COM_NUM = PRO.COM_NUM "
																	+" JOIN SWEETNESS SWE ON OD.SWEET_NUM = SWE.SWEET_NUM JOIN ICE_LIST ICE " 
																	+" ON OD.ICE_NUM = ICE.ICE_NUM JOIN ORDER_MASTER ORM ON ORM.OR_NUM = OD.OR_NUM "
																	+" AND ORM.MEROR_NUM =? ";

		//查詢 團購寄杯明細  使用 團購編號
		private static final String GET_GROUP_KEEP_FOR_TOOL ="SELECT PRO.COM_NAME,SWE.SWEET_TYPE,ICE.ICE_TYPE,OD.COM_SIZE,OD.OD_PRICE,OD.ORDET_NUM "
																		+" FROM ORDER_DETAIL OD JOIN PRODUCT PRO ON OD.COM_NUM = PRO.COM_NUM "
																		+" JOIN SWEETNESS SWE ON OD.SWEET_NUM = SWE.SWEET_NUM JOIN ICE_LIST ICE " 
																		+" ON OD.ICE_NUM = ICE.ICE_NUM JOIN KEEP_RECORD KR ON KR.ORDET_NUM = OD.ORDET_NUM JOIN ORDER_MASTER ORM ON ORM.OR_NUM = OD.OR_NUM "
																		+" AND ORM.MEROR_NUM =? ";

		
		
		private static final String GET_GROUP_ORDER_POINT_FOR_TOOL =
		"SELECT SUM(DECODE(PAY_MEN,'點數',OR_AMOUNT,0)) POINT,MO.TOL_AMOUNT FROM ORDER_MASTER OM JOIN MERGED_ORDER MO "+
		"ON OM.MEROR_NUM = MO.MEROR_NUM  AND OM.MEROR_NUM=? Group By MO.TOL_AMOUNT" ;
		

		
		
		//用訂單查詢是否為團購訂單 
		private static final String IS_GROUP_ORDER = "SELECT MEROR_NUM FROM ORDER_MASTER WHERE OR_NUM=?";
		
		
		//查詢團購發起人   會員編號 OR_NUM =?
		private static final String GROUP_ORDER_MASTER = "SELECT MO.MEM_NUM FROM ORDER_MASTER OM JOIN MERGED_ORDER MO"+
															" ON OM.MEROR_NUM = MO.MEROR_NUM WHERE OM.OR_NUM =?";
		
		
		//用店家 編號查詢所有非團購待審核訂單
		private static final String GET_NOT_GROUP_BY_STO_NUM_WAIT_MODIF = "SELECT * FROM ORDER_MASTER WHERE MEROR_NUM IS NULL AND STO_NUM =?  AND OR_STANUM='待審核' order by or_time desc";
		
		//店家用 查詢團購待審核訂單 (只顯示發起人)
		private static final String GET_GROUP_BY_STO_NUM_WAIT_MODIF ="SELECT OM.* FROM ORDER_MASTER OM JOIN MERGED_ORDER MO ON OM.MEROR_NUM = MO.MEROR_NUM"+
																" AND MO.MEM_NUM = OM.MEM_NUM WHERE OM.STO_NUM =? AND OR_STANUM='待審核' order by or_time desc";
		
		// 查詢某訂單編號詳細內容
		private static final String GET_ONE_ORDER_BY_OR_NUM = "SELECT * FROM ORDER_MASTER WHERE OR_NUM=?";

		// 查詢某店家所有訂單
		private static final String GET_ONE_STO_ALL_ORDER = "SELECT * FROM ORDER_MASTER WHERE STO_NUM=? order by or_time desc";

		
		// 查詢某店家所有待審核訂單  STO_NUM=?
		private static final String GET_ONE_STO_WAIT_MODIF = "SELECT * FROM ORDER_MASTER WHERE STO_NUM=? AND OR_STANUM='待審核' order by or_time desc";

		
		// 查詢某店家所有處理中訂單   STO_NUM=?
		private static final String GET_ONE_STO_PASS_ORDER = "SELECT * FROM ORDER_MASTER WHERE STO_NUM=? AND( OR_STANUM= '已交貨' or OR_STANUM='處理中' or OR_STANUM= '外送中' or OR_STANUM= '待取貨') order by or_time desc";
		
		// 查詢某店家已完成訂單   STO_NUM=?
		private static final String GET_ONE_STO_FINISH_ORDER = "SELECT * FROM ORDER_MASTER WHERE STO_NUM=? AND OR_STANUM='交易完成' order by or_time desc";
			
		
		// 查詢某店家所有** 非團購  **處理中訂單   STO_NUM=?
		private static final String GET_NOT_GROUP_BY_STO_NUM_PASS_ORDER  = "SELECT * FROM ORDER_MASTER WHERE MEROR_NUM IS NULL AND STO_NUM=? AND ( OR_STANUM= '已交貨' or OR_STANUM='處理中' or OR_STANUM= '外送中' or OR_STANUM= '待取貨') order by or_time desc";
		
		
		// 查詢某店家所有** 團購 **處理中訂單  STO_NUM=?
		private static final String GET_ONE_STO_GROUP_PASS_ORDER = "SELECT OM.* FROM ORDER_MASTER OM JOIN MERGED_ORDER MO ON OM.MEROR_NUM = MO.MEROR_NUM"+			
													" AND MO.MEM_NUM = OM.MEM_NUM WHERE OM.STO_NUM =? AND(OR_STANUM= '已交貨' or OR_STANUM='處理中' or OR_STANUM= '外送中' or OR_STANUM= '待取貨') order by or_time desc";
		
		//查詢團購編號內的訂單編號
		private static final String  GET_GROUP_ALL_ORDER_NUM  = "SELECT * FROM ORDER_MASTER WHERE MEROR_NUM=?";
		
		
		private static final String UPDATE_GAME = "UPDATE ORDER_MASTER SET GAME=? WHERE or_num=? ";
		
		private static final String GET_EXTRA = "SELECT E.ext_name FROM EXTRA_LIST EL JOIN EXTRA E"
													+" ON EL.EXT_NUM=E.EXT_NUM AND EL.ordet_num =?";
		
		
		
		
		public void upDateGame(String or_num,Integer value_cash){
			
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = ds.getConnection();
				pstmt= con.prepareStatement(UPDATE_GAME);
				pstmt.setInt(1, value_cash);
				pstmt.setString(2, or_num);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {

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
		
		
		
		public Integer getPlay(String or_num){
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Integer game = null;
			try {
				con =ds.getConnection();
				pstmt =con.prepareStatement(GET_IS_PLAY_GAME);
				pstmt.setString(1, or_num);
				rs = pstmt.executeQuery();	
				
				if(rs.next()){
					game =rs.getInt("GAME");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if (rs!= null) {
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
						con.setAutoCommit(true);
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}

			}

			return game;
			
			}
		
		
		public String getGroupMaster(String or_num){
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String mem_num = null;
			try {
				con =ds.getConnection();
				pstmt =con.prepareStatement(GROUP_ORDER_MASTER);
				pstmt.setString(1, or_num);
				rs = pstmt.executeQuery();	
				
				if(rs.next()){
					mem_num =rs.getString("mem_num");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if (rs!= null) {
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
						con.setAutoCommit(true);
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}

			}

			return mem_num;
			
			}
		
		
		
		// 訂單完成 店家沒有集點卡
		public void finishNoCard(List<Map> all) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			Map map =null;
			int totalPoints =0;
			String sto_num = null;
			try {
				con = ds.getConnection();
				con.setAutoCommit(false);
				
				for(int i=0;i<all.size();i++){
					map = all.get(i);
				
				OrderMasterVO orderMasterVO = (OrderMasterVO) map.get("OrderMasterVO");
				System.out.println( "OR_NUM" +orderMasterVO.getOr_num());
				int usePoints = (int) map.get("usePoints");
				pstmt = con.prepareStatement(UPDATE_ORDER_STATUS);
				pstmt.setString(1, "交易完成");
				pstmt.setString(2, orderMasterVO.getOr_num());
				pstmt.addBatch();

				if (usePoints == 1) {
					
					totalPoints += orderMasterVO.getOr_amount();
					sto_num = orderMasterVO.getSto_num();
				}		
				// 檢查是否使用 集點卡
				pstmt = con.prepareStatement(GET_IS_USE_CARD);
				pstmt.setString(1, orderMasterVO.getOr_num());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					pstmt = con.prepareStatement(UPDATE_USE_CARD);
					pstmt.setString(1, orderMasterVO.getOr_num());
					pstmt.addBatch();
				}
				
				//更新寄杯狀態
				pstmt = con.prepareStatement(UPDATE_KEEP_STATUS);
				pstmt.setString(1, orderMasterVO.getOr_num());

				pstmt.executeUpdate();
				}
				
				pstmt = con.prepareStatement(UPDATE_STO_POINTS);
				pstmt.setInt(1, totalPoints);
				pstmt.setString(2, sto_num);
				pstmt.executeUpdate();
				con.commit();
				System.out.println("訂單完成");
			} catch (SQLException e) {

				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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

		}

		// 訂單完成
		 public void finish(List<Map> all) {
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int totalPoints =0;
			String sto_num = null;
			
			try {
				con = ds.getConnection();
				con.setAutoCommit(false);
				
				for(int i=0;i<all.size();i++){
					Map map = all.get(i);
			
			OrderMasterVO orderMasterVO = (OrderMasterVO) map.get("OrderMasterVO");
					
			Timestamp newCardExpDate = null;

			CardVO cardVO = (CardVO) map.get("CardVO");
			int howManyFullNewCards = 0;
			int newCardPoints = 0;
			int updateMemCard = 0;	
			int addpoints = 0;	
			
			long t = System.currentTimeMillis();	
	System.out.println("finish1111111111111");	
			if (cardVO.getExp_date() != null) {
				long t2 = t + cardVO.getExp_date() * 1000 * 60 * 60 * 24 * 30L;
				newCardExpDate = new Timestamp(t2);
			}
	System.out.println("finish22222222222222");	
			if(map.get("noCrad") ==null){
				System.out.print("noCrad ");
				 cardVO = (CardVO) map.get("CardVO");
				 howManyFullNewCards = (int) map.get("howManyFullNewCards");
				 newCardPoints = (int) map.get("newCardPoints");
				 updateMemCard = (int) map.get("updateMemCard");	
				
			}
			System.out.print("訂單編號 : "+ orderMasterVO.getOr_num());
			if (map.get("addpoints") != null) {
				addpoints = (int) map.get("addpoints");
			}
			
			int usePoints = (int) map.get("usePoints");

			
				// 將訂單狀態改為 交易完成 及更新訂單時間
				pstmt = con.prepareStatement(UPDATE_ORDER_STATUS);
				pstmt.setString(1, "交易完成");
				pstmt.setString(2, orderMasterVO.getOr_num());
				pstmt.executeUpdate();

				// 如果使用點數付款 需幫店家點數餘額增加
				if (usePoints == 1) {
					
					totalPoints += orderMasterVO.getOr_amount();
					sto_num = orderMasterVO.getSto_num();
				}

				// 更新舊的集點卡
				if (updateMemCard > 0) {
					pstmt = con.prepareStatement(UPDATE_MEM_CARD);
					pstmt.setInt(1, (int) map.get("value"));
					pstmt.setString(2, (String) map.get("status"));
					pstmt.setString(3, (String) map.get("card_num"));
					pstmt.executeUpdate();
					// 新增集點卡 集點 記錄
					System.out.println("// 更新舊的集點卡    // 新增集點卡 集點 記錄");
					pstmt = con.prepareStatement(INSERT_NEW_CARD_POINT_RECORD);
					pstmt.setString(1, (String) map.get("card_num"));
					pstmt.setString(2, orderMasterVO.getOr_num());
					pstmt.setInt(3, addpoints);
					pstmt.executeUpdate();
					
				}
				
				// 有需要新增滿的集點卡
				while (howManyFullNewCards > 0) {
					System.out.println("// 有需要新增滿的集點卡 ");
					int cols[] = { 1 };
					pstmt = con.prepareStatement(NEW_FULL_MEM_CARD, cols);
					pstmt.setString(1, orderMasterVO.getMem_num());
					pstmt.setString(2, orderMasterVO.getSto_num());
					pstmt.setString(3, cardVO.getCard_kinds());
					pstmt.setInt(4, cardVO.getPoints());
					pstmt.setTimestamp(5, newCardExpDate);
					pstmt.executeUpdate();

					ResultSet rsKeys = pstmt.getGeneratedKeys();

					rsKeys.next();
					String card_num = rsKeys.getString(1);
					System.out.println("/card_num = rsKeys.getString(1) = " +card_num);
					// 新增集點卡 集點 記錄
					pstmt = con.prepareStatement(INSERT_NEW_CARD_POINT_RECORD);
					// 卡片號碼
					pstmt.setString(1, card_num);
					pstmt.setString(2, orderMasterVO.getOr_num());
					pstmt.setInt(3, cardVO.getPoints());
					pstmt.executeUpdate();
					howManyFullNewCards--;
				}
				// 新增一張集點卡
			
				if (0 < newCardPoints) {
				System.out.println("進入新增一張集點卡 !!!");

					int cols[] = { 1 };
					pstmt = con.prepareStatement(NEW_MEM_CARD, cols);
					pstmt.setString(1, orderMasterVO.getMem_num());
					pstmt.setString(2, orderMasterVO.getSto_num());
					pstmt.setString(3, cardVO.getCard_kinds());
					pstmt.setInt(4, newCardPoints);
					pstmt.setTimestamp(5, newCardExpDate);
					pstmt.executeUpdate();

					ResultSet rsKeys = pstmt.getGeneratedKeys();

					rsKeys.next();
					String card_num = rsKeys.getString(1);

					// 新增集點卡 集點 記錄
					pstmt = con.prepareStatement(INSERT_NEW_CARD_POINT_RECORD);
					// 卡片號碼
					pstmt.setString(1, card_num);
					pstmt.setString(2, orderMasterVO.getOr_num());
					pstmt.setInt(3, newCardPoints);
					pstmt.executeUpdate();

				}
			
				// 檢查是否使用 集點卡
				pstmt = con.prepareStatement(GET_IS_USE_CARD);
				pstmt.setString(1, orderMasterVO.getOr_num());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					pstmt = con.prepareStatement(UPDATE_USE_CARD);
					pstmt.setString(1, orderMasterVO.getOr_num());
					pstmt.executeUpdate();
				}
				
				//更新寄杯狀態
				pstmt = con.prepareStatement(UPDATE_KEEP_STATUS);
				pstmt.setString(1, orderMasterVO.getOr_num());
				pstmt.executeUpdate();		
		
				
			}
				
				pstmt = con.prepareStatement(UPDATE_STO_POINTS);
				pstmt.setInt(1, totalPoints);
				pstmt.setString(2, sto_num);
				pstmt.executeUpdate();	
				

				con.commit();
				System.out.println("交易完成 !");
			} catch (SQLException e) {
				try {
				System.out.println("ERROR " +e.getMessage());
					con.rollback();
				} catch (SQLException se) {
					// TODO Auto-generated catch block
					se.printStackTrace();
				}
				System.out.println(e.getMessage());
				throw new RuntimeException("A database error occured. " + e.getMessage());
				// Clean up JDBC resources
			}finally {

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

		// 訂單取消
		@Override
		public void orderCancel(OrderMasterVO orderMasterVO) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
		
			try {
				con = ds.getConnection();
				con.setAutoCommit(false);
				long t = System.currentTimeMillis();
				Timestamp ts = new Timestamp(t);
		
				// 修改訂單狀態
				pstmt = con.prepareStatement(UPDATE_ORDER_STATUS);
				pstmt.setString(1, "修改中");
				pstmt.setString(2, orderMasterVO.getOr_num());
				pstmt.executeUpdate();
		
				// 檢查是否使用 集點卡
				pstmt = con.prepareStatement(GET_IS_USE_CARD);
				pstmt.setString(1, orderMasterVO.getOr_num());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					pstmt = con.prepareStatement(UPDATE_USE_CARD_CANCEL);
					pstmt.setString(1, orderMasterVO.getOr_num());
					pstmt.executeUpdate();
				}
		
				// 檢查是否使用 折價券
				pstmt = con.prepareStatement(GET_IS_USE_COUPON);
				pstmt.setString(1, orderMasterVO.getOr_num());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					pstmt = con.prepareStatement(UPDATE_USE_COUPON_CANCEL);
					pstmt.setString(1, orderMasterVO.getOr_num());
					pstmt.executeUpdate();
				}
		
				// 檢查是否使用 點數
				pstmt = con.prepareStatement(GET_IS_USE_POINTS);
				pstmt.setString(1, orderMasterVO.getOr_num());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					pstmt = con.prepareStatement(UPDATE_MEM_POINTS);
					pstmt.setInt(1, orderMasterVO.getOr_amount());
					pstmt.setString(2, orderMasterVO.getMem_num());
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

		// 訂單通過審核
		@Override
		public void orderPass(String or_num) {
		
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_ORDER_STATUS);
				pstmt.setString(1, "處理中");
				pstmt.setString(2, or_num);
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

		// 出貨
		@Override
		public void orderAlready(String or_num, String orsta_num) {
		
		
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_ORDER_STATUS);
				pstmt.setString(1, orsta_num);
				pstmt.setString(2, or_num);
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

		// 團購訂單通過審核
		@Override
		public void groupOrderPass(String meror_num) {
		
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_GROUP_ORDER_STATUS);
				pstmt.setString(1, "處理中");
				pstmt.setString(2, meror_num);
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

		// 查詢此會員是否有此店家的集點卡 或 未集滿的集點卡 已集此集點卡需要收集多少點
		public Map searchMemCard(String sto_num, String mem_num) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Map map = new HashMap();
			CardListVO cardlistVO = new CardListVO();
			CardVO cardVO = new CardVO();
		
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(SEARCH_MEM_CARD);
				pstmt.setString(2, sto_num);
				pstmt.setString(1, mem_num);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					map.put("card_num", rs.getString("card_num"));
					map.put("value", rs.getInt("value"));
					map.put("points", rs.getInt("points"));
				} else {
					map = null;
				}
		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				map = null;
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
		
			return map;
		
		}

		public int howManyCups(OrderMasterVO orderMasterVO) {
		
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int total = 0;
			try {
		
				con = ds.getConnection();
		
				// 查詢有多少杯飲料
				pstmt = con.prepareStatement(GET_ONE_ORDER_AMOUNT);
				pstmt.setString(1, orderMasterVO.getOr_num());
				rs = pstmt.executeQuery();
				rs.next();
				total = rs.getInt(1);
		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			return total;
		
		}

		public List<OrderMasterVO> getStoPointOrder(String sto_num) {
		
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<OrderMasterVO> list = new ArrayList<OrderMasterVO>();
		
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_STO_ORDER_VALUE_RECORD);
				pstmt.setString(1, sto_num);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					OrderMasterVO orderMasterVO = new OrderMasterVO();
					orderMasterVO.setMem_num(rs.getString("mem_num"));
					orderMasterVO.setOr_num(rs.getString("or_num"));
					orderMasterVO.setOr_amount(rs.getInt("or_amount"));
					orderMasterVO.setSto_num(rs.getString("sto_num"));
					orderMasterVO.setOr_time(rs.getDate("or_time"));
					orderMasterVO.setPre_rece(rs.getTimestamp("pre_rece"));
					orderMasterVO.setRece(rs.getString("rece"));
					list.add(orderMasterVO);
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
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}
		
			return list;
		
		}


		public List<OrderMasterVO> getOneStoAllOrder(String sto_num) {

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<OrderMasterVO> list = new ArrayList<OrderMasterVO>();

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STO_ALL_ORDER);
				pstmt.setString(1, sto_num);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					OrderMasterVO orderMasterVO = new OrderMasterVO();
					orderMasterVO.setMem_num(rs.getString("mem_num"));
					orderMasterVO.setOr_num(rs.getString("or_num"));
					orderMasterVO.setOr_stanum(rs.getString("or_stanum"));
					orderMasterVO.setSto_num(rs.getString("sto_num"));
					orderMasterVO.setOr_time(rs.getTimestamp("or_time"));
					orderMasterVO.setPre_rece(rs.getTimestamp("pre_rece"));
					orderMasterVO.setPay_men(rs.getString("pay_men"));
					orderMasterVO.setOr_amount(rs.getInt("or_amount"));
					orderMasterVO.setRece(rs.getString("rece"));
					list.add(orderMasterVO);
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
		
		public List<OrderMasterVO> getOneStoFinishOrder(String sto_num) {

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<OrderMasterVO> list = new ArrayList<OrderMasterVO>();
			OrderMasterVO orderMasterVO = null;
			try {
				con = ds.getConnection();
				
			
				pstmt = con.prepareStatement(GET_ONE_STO_FINISH_ORDER);
				pstmt.setString(1, sto_num);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					orderMasterVO = new OrderMasterVO();
					orderMasterVO.setMem_num(rs.getString("mem_num"));
					orderMasterVO.setOr_num(rs.getString("or_num"));
					orderMasterVO.setOr_stanum(rs.getString("or_stanum"));
					orderMasterVO.setSto_num(rs.getString("sto_num"));
					orderMasterVO.setOr_time(rs.getTimestamp("or_time"));
					orderMasterVO.setPre_rece(rs.getTimestamp("pre_rece"));
					orderMasterVO.setPay_men(rs.getString("pay_men"));
					orderMasterVO.setOr_amount(rs.getInt("or_amount"));
					orderMasterVO.setRece(rs.getString("rece"));
					orderMasterVO.setAddress(rs.getString("address"));
					list.add(orderMasterVO);
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

		
		public List<OrderMasterVO> getOneStoWaitModif(String sto_num) {

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<OrderMasterVO> list = new ArrayList<OrderMasterVO>();
			OrderMasterVO orderMasterVO = null;
			try {
				con = ds.getConnection();
				
				//取得非團購待審核名單
				pstmt = con.prepareStatement(GET_NOT_GROUP_BY_STO_NUM_WAIT_MODIF);
				pstmt.setString(1, sto_num);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					orderMasterVO = new OrderMasterVO();
					orderMasterVO.setMem_num(rs.getString("mem_num"));
					orderMasterVO.setOr_num(rs.getString("or_num"));
					orderMasterVO.setOr_stanum(rs.getString("or_stanum"));
					orderMasterVO.setSto_num(rs.getString("sto_num"));
					orderMasterVO.setOr_time(rs.getTimestamp("or_time"));
					orderMasterVO.setPre_rece(rs.getTimestamp("pre_rece"));
					orderMasterVO.setPay_men(rs.getString("pay_men"));
					orderMasterVO.setOr_amount(rs.getInt("or_amount"));
					orderMasterVO.setRece(rs.getString("rece"));
					orderMasterVO.setAddress(rs.getString("address"));
					list.add(orderMasterVO);
				}
				//取得團購待審核名單(只顯示發起人)
				pstmt = con.prepareStatement(GET_GROUP_BY_STO_NUM_WAIT_MODIF);
				pstmt.setString(1, sto_num);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					orderMasterVO = new OrderMasterVO();
					orderMasterVO.setMem_num(rs.getString("mem_num"));
					orderMasterVO.setOr_num(rs.getString("or_num"));
					orderMasterVO.setOr_stanum(rs.getString("or_stanum"));
					orderMasterVO.setSto_num(rs.getString("sto_num"));
					orderMasterVO.setOr_time(rs.getTimestamp("or_time"));
					orderMasterVO.setPre_rece(rs.getTimestamp("pre_rece"));
					orderMasterVO.setPay_men(rs.getString("pay_men"));
					orderMasterVO.setOr_amount(rs.getInt("or_amount"));
					orderMasterVO.setMeror_num(rs.getString("meror_num"));
					orderMasterVO.setRece(rs.getString("rece"));
					orderMasterVO.setAddress(rs.getString("address"));
					list.add(orderMasterVO);
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


		public OrderMasterVO getOneOrder(String or_num) {

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<OrderMasterVO> list = new ArrayList<OrderMasterVO>();
			OrderMasterVO orderMasterVO = new OrderMasterVO();
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_ORDER_BY_OR_NUM);
				pstmt.setString(1, or_num);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					orderMasterVO.setMem_num(rs.getString("mem_num"));
					orderMasterVO.setOr_num(rs.getString("or_num"));
					orderMasterVO.setOr_stanum(rs.getString("or_stanum"));
					orderMasterVO.setSto_num(rs.getString("sto_num"));
					orderMasterVO.setOr_time(rs.getTimestamp("or_time"));
					orderMasterVO.setPre_rece(rs.getTimestamp("pre_rece"));
					orderMasterVO.setPay_men(rs.getString("pay_men"));
					orderMasterVO.setOr_amount(rs.getInt("or_amount"));
					orderMasterVO.setRece(rs.getString("rece"));
					orderMasterVO.setMeror_num(rs.getString("meror_num"));
					orderMasterVO.setAddress(rs.getString("address"));
					list.add(orderMasterVO);
				} else {
					orderMasterVO = null;
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

			return orderMasterVO;

		}

		// 取得團購所有訂單 資訊
		public List<OrderMasterVO> getGroupAllOrderNumByMerorNum(String meror_num) {
		
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<OrderMasterVO> list = new ArrayList<OrderMasterVO>();
		
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_GROUP_ALL_ORDER_NUM);
				pstmt.setString(1, meror_num);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					OrderMasterVO orderMasterVO = new OrderMasterVO();
					orderMasterVO.setMem_num(rs.getString("mem_num"));
					orderMasterVO.setOr_num(rs.getString("or_num"));
					orderMasterVO.setOr_stanum(rs.getString("or_stanum"));
					orderMasterVO.setSto_num(rs.getString("sto_num"));
					orderMasterVO.setOr_time(rs.getTimestamp("or_time"));
					orderMasterVO.setPre_rece(rs.getTimestamp("pre_rece"));
					orderMasterVO.setPay_men(rs.getString("pay_men"));
					orderMasterVO.setOr_amount(rs.getInt("or_amount"));
					orderMasterVO.setRece(rs.getString("rece"));
					list.add(orderMasterVO);
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
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}
		
			return list;
		
		}


		// 取得目前店家卡片編號 跟 需集滿多少點 和 到期日期
		public CardVO getStoNowCard(String sto_num) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			CardVO cardVo = new CardVO();
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_STO_NOW_CARD);
				pstmt.setString(1, sto_num);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					cardVo.setCard_kinds(rs.getString("card_kinds"));
					cardVo.setPoints(rs.getInt("points"));
					cardVo.setExp_date(rs.getInt("exp_date"));
				} else {
					cardVo = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				cardVo = null;
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
		
			return cardVo;
		
		}

		// 團購出貨
			@Override
		public void groupOrderAlready(String meror_num, String orsta_num) {


				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try {
					con = ds.getConnection();
					pstmt = con.prepareStatement(UPDATE_GROUP_ORDER_STATUS);
					pstmt.setString(1, orsta_num);
					pstmt.setString(2, meror_num);
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
		public List<OrderMasterVO> getOneStoPassOrder(String sto_num) {

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<OrderMasterVO> list = new ArrayList<OrderMasterVO>();
			
			try {
				con = ds.getConnection();
				//取得非團購清單
				pstmt = con.prepareStatement(GET_NOT_GROUP_BY_STO_NUM_PASS_ORDER);
				pstmt.setString(1, sto_num);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					OrderMasterVO orderMasterVO = new OrderMasterVO();
					orderMasterVO.setMem_num(rs.getString("mem_num"));
					orderMasterVO.setOr_num(rs.getString("or_num"));
					orderMasterVO.setOr_stanum(rs.getString("or_stanum"));
					orderMasterVO.setSto_num(rs.getString("sto_num"));
					orderMasterVO.setOr_time(rs.getTimestamp("or_time"));
					orderMasterVO.setPre_rece(rs.getTimestamp("pre_rece"));
					orderMasterVO.setPay_men(rs.getString("pay_men"));
					orderMasterVO.setOr_amount(rs.getInt("or_amount"));
					orderMasterVO.setRece(rs.getString("rece"));
					orderMasterVO.setAddress(rs.getString("address"));
					list.add(orderMasterVO);
				}
				//取得團購清單
				pstmt = con.prepareStatement(GET_ONE_STO_GROUP_PASS_ORDER);
				pstmt.setString(1, sto_num);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					OrderMasterVO orderMasterVO = new OrderMasterVO();
					orderMasterVO.setMem_num(rs.getString("mem_num"));
					orderMasterVO.setOr_num(rs.getString("or_num"));
					orderMasterVO.setOr_stanum(rs.getString("or_stanum"));
					orderMasterVO.setSto_num(rs.getString("sto_num"));
					orderMasterVO.setOr_time(rs.getTimestamp("or_time"));
					orderMasterVO.setPre_rece(rs.getTimestamp("pre_rece"));
					orderMasterVO.setPay_men(rs.getString("pay_men"));
					orderMasterVO.setOr_amount(rs.getInt("or_amount"));
					orderMasterVO.setMeror_num(rs.getString("meror_num"));
					orderMasterVO.setRece(rs.getString("rece"));
					orderMasterVO.setAddress(rs.getString("address"));
					list.add(orderMasterVO);
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
		public List<OrderMasterVO> getOneMemFinishOrder(String mem_num) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<OrderMasterVO> list = new ArrayList<OrderMasterVO>();

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_MEM_FINISH_ORDER);
				pstmt.setString(1, mem_num);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					OrderMasterVO orderMasterVO = new OrderMasterVO();
					orderMasterVO.setMem_num(rs.getString("mem_num"));
					orderMasterVO.setOr_num(rs.getString("or_num"));
					orderMasterVO.setOr_stanum(rs.getString("or_stanum"));
					orderMasterVO.setSto_num(rs.getString("sto_num"));
					orderMasterVO.setOr_time(rs.getTimestamp("or_time"));
					orderMasterVO.setPre_rece(rs.getTimestamp("pre_rece"));
					orderMasterVO.setPay_men(rs.getString("pay_men"));
					orderMasterVO.setOr_amount(rs.getInt("or_amount"));
					orderMasterVO.setRece(rs.getString("rece"));
					orderMasterVO.setAddress(rs.getString("address"));
					orderMasterVO.setMeror_num(rs.getString("meror_num"));
					list.add(orderMasterVO);
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
		public List<OrderMasterVO> getOneMemNoFinishOrder(String mem_num) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<OrderMasterVO> list = new ArrayList<OrderMasterVO>();

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_MEM_NO_FINISH_ORDER);
				pstmt.setString(1, mem_num);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					OrderMasterVO orderMasterVO = new OrderMasterVO();
					orderMasterVO.setMem_num(rs.getString("mem_num"));
					orderMasterVO.setOr_num(rs.getString("or_num"));
					orderMasterVO.setOr_stanum(rs.getString("or_stanum"));
					orderMasterVO.setSto_num(rs.getString("sto_num"));
					orderMasterVO.setOr_time(rs.getTimestamp("or_time"));
					orderMasterVO.setPre_rece(rs.getTimestamp("pre_rece"));
					orderMasterVO.setPay_men(rs.getString("pay_men"));
					orderMasterVO.setOr_amount(rs.getInt("or_amount"));
					orderMasterVO.setRece(rs.getString("rece"));
					orderMasterVO.setAddress(rs.getString("address"));
					orderMasterVO.setMeror_num(rs.getString("meror_num"));
					list.add(orderMasterVO);
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
		
		
		//單個訂單 所有明細(含寄杯但無顯示)
		@Override
		public List<OrderDetailToolVO> getOrderDetailTool(String or_num) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			OrderDetailToolVO orderDetailToolVO = null;
			List<OrderDetailToolVO> list = new ArrayList<OrderDetailToolVO>();
			List<String> extras = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ORDER_DETAIL_FOR_TOOL);
				pstmt.setString(1, or_num);
				rs = pstmt.executeQuery();
			
				while (rs.next()) {	
					orderDetailToolVO = new OrderDetailToolVO();
					orderDetailToolVO.setCom_name(rs.getString("COM_NAME"));
					orderDetailToolVO.setSweet_type(rs.getString("SWEET_TYPE"));
					orderDetailToolVO.setIce_type(rs.getString("ICE_TYPE"));			
					orderDetailToolVO.setCom_size(rs.getString("COM_SIZE"));	
					orderDetailToolVO.setOd_price(rs.getInt("OD_PRICE"));
					orderDetailToolVO.setAmount(1);	
					orderDetailToolVO.setOrdet_num(rs.getString("ordet_num"));
					
					list.add(orderDetailToolVO);
				
				}
				
				for(int i =0;i<list.size();i++){
					extras = new ArrayList<String>();
					//查詢是否加料
					pstmt = con.prepareStatement(GET_EXTRA);
					pstmt.setString(1, list.get(i).getOrdet_num());
					rs = pstmt.executeQuery();
					//如果有將加料放入 extras List
					while(rs.next()){						
						extras.add(rs.getString("EXT_NAME"));				
					}
					if(!extras.isEmpty()){
						OrderDetailToolVO modif = list.get(i);
						modif.setExtras(extras);
					}
						
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		
		//單個訂單 寄杯
		@Override
		public List<OrderDetailToolVO> getKeepDetailTool(String or_num) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			OrderDetailToolVO orderDetailToolVO = null;
			List<OrderDetailToolVO> list = new ArrayList<OrderDetailToolVO>();
			List<String> extras = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_KEEP_FOR_TOOL);
				pstmt.setString(1, or_num);
				rs = pstmt.executeQuery();
				while (rs.next()) {	
					orderDetailToolVO = new OrderDetailToolVO();
					orderDetailToolVO.setCom_name(rs.getString("COM_NAME"));
					orderDetailToolVO.setSweet_type(rs.getString("SWEET_TYPE"));
					orderDetailToolVO.setIce_type(rs.getString("ICE_TYPE"));			
					orderDetailToolVO.setCom_size(rs.getString("COM_SIZE"));	
					orderDetailToolVO.setOd_price(rs.getInt("OD_PRICE"));
					orderDetailToolVO.setAmount(1);	
					orderDetailToolVO.setOrdet_num(rs.getString("ordet_num"));			
					list.add(orderDetailToolVO);		
				}
			
				for(int i =0;i<list.size();i++){
					extras = new ArrayList<String>();
					//查詢是否加料
					pstmt = con.prepareStatement(GET_EXTRA);
					pstmt.setString(1, list.get(i).getOrdet_num());
					rs = pstmt.executeQuery();
					//如果有將加料放入 extras List
					while(rs.next()){
					
						extras.add(rs.getString("EXT_NAME"));				
					}
				
					if(!extras.isEmpty()){
						OrderDetailToolVO modif = list.get(i);
						modif.setExtras(extras);
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
						con.setAutoCommit(true);
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}

			return list;
		}
		
		
		//取得團購 所有明細(含寄杯但無顯示)
		@Override
		public List<OrderDetailToolVO> getGroupOrderDetailTool(String meror_num) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			OrderDetailToolVO orderDetailToolVO = null;
			List<OrderDetailToolVO> list = new ArrayList<OrderDetailToolVO>();
			List<String> extras = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_GROUP_ORDER_DETAIL_FOR_TOOL);
				pstmt.setString(1, meror_num);
				rs = pstmt.executeQuery();
				while (rs.next()) {	
					orderDetailToolVO = new OrderDetailToolVO();
					orderDetailToolVO.setCom_name(rs.getString("COM_NAME"));
					orderDetailToolVO.setSweet_type(rs.getString("SWEET_TYPE"));
					orderDetailToolVO.setIce_type(rs.getString("ICE_TYPE"));			
					orderDetailToolVO.setCom_size(rs.getString("COM_SIZE"));	
					orderDetailToolVO.setOd_price(rs.getInt("OD_PRICE"));
					orderDetailToolVO.setAmount(1);	
					orderDetailToolVO.setOrdet_num(rs.getString("ordet_num"));
					
					list.add(orderDetailToolVO);
				
				}
				
				for(int i =0;i<list.size();i++){
					extras = new ArrayList<String>();
					//查詢是否加料
					pstmt = con.prepareStatement(GET_EXTRA);
					pstmt.setString(1, list.get(i).getOrdet_num());
					rs = pstmt.executeQuery();
					//如果有將加料放入 extras List
					while(rs.next()){						
						extras.add(rs.getString("EXT_NAME"));				
					}
					if(!extras.isEmpty()){
						OrderDetailToolVO modif = list.get(i);
						modif.setExtras(extras);
					}
						
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
						con.setAutoCommit(true);
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}

			return list;
		}

		
		//取得團購 寄杯 所有明細
		@Override
		public List<OrderDetailToolVO> getGroupKeepDetailTool(String meror_num) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			OrderDetailToolVO orderDetailToolVO = null;
			List<OrderDetailToolVO> list = new ArrayList<OrderDetailToolVO>();
			List<String> extras = null;
			try {
				con = ds.getConnection();
				
				pstmt = con.prepareStatement(GET_GROUP_KEEP_FOR_TOOL);
				pstmt.setString(1, meror_num);
				rs = pstmt.executeQuery();
				while (rs.next()) {	
					orderDetailToolVO = new OrderDetailToolVO();
					orderDetailToolVO.setCom_name(rs.getString("COM_NAME"));
					orderDetailToolVO.setSweet_type(rs.getString("SWEET_TYPE"));
					orderDetailToolVO.setIce_type(rs.getString("ICE_TYPE"));			
					orderDetailToolVO.setCom_size(rs.getString("COM_SIZE"));	
					orderDetailToolVO.setOd_price(rs.getInt("OD_PRICE"));
					orderDetailToolVO.setAmount(1);	
					orderDetailToolVO.setOrdet_num(rs.getString("ordet_num"));
					
					list.add(orderDetailToolVO);
				
				}
				
				for(int i =0;i<list.size();i++){
					extras = new ArrayList<String>();
					//查詢是否加料
					pstmt = con.prepareStatement(GET_EXTRA);
					pstmt.setString(1, list.get(i).getOrdet_num());
					rs = pstmt.executeQuery();
					//如果有將加料放入 extras List
					while(rs.next()){						
						extras.add(rs.getString("EXT_NAME"));				
					}
					if(!extras.isEmpty()){
						OrderDetailToolVO modif = list.get(i);
						modif.setExtras(extras);
					}
						
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		public OrderDetailToolVO getGroupOrderPoint(String meror_num) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			OrderDetailToolVO orderDetailToolVO = null;
			

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_GROUP_ORDER_POINT_FOR_TOOL);
				pstmt.setString(1, meror_num);
				rs = pstmt.executeQuery();
				if (rs.next()) {	
					orderDetailToolVO = new OrderDetailToolVO();
					orderDetailToolVO.setPoint(rs.getInt("point"));
					orderDetailToolVO.setTol_amount(rs.getInt("tol_amount"));
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
						con.setAutoCommit(true);
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}

			return orderDetailToolVO;
		}



		@Override
		public List<OrderMasterVO> getOrderInfo(Map<String, String[]> map) {
			// TODO Auto-generated method stub
			List<OrderMasterVO>  list = new ArrayList<OrderMasterVO>();
			OrderMasterVO orderMasterVO = null;
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			
			try {
				con = ds.getConnection();
				String SQL = "SELECT * FROM ORDER_MASTER " 
				+ CompositeQueryOrderMaster.get_WhereCondition(map)
				+ " ORDER BY OR_NUM";
				System.out.println(" ***SQL*** :" + SQL);
				pstmt = con.prepareStatement(SQL);
				rs = pstmt.executeQuery();
				while(rs.next()){
					orderMasterVO = new OrderMasterVO();
					orderMasterVO.setMem_num(rs.getString("mem_num"));
					orderMasterVO.setOr_num(rs.getString("or_num"));
					orderMasterVO.setOr_stanum(rs.getString("or_stanum"));
					orderMasterVO.setSto_num(rs.getString("sto_num"));
					orderMasterVO.setOr_time(rs.getTimestamp("or_time"));
					orderMasterVO.setPre_rece(rs.getTimestamp("pre_rece"));
					orderMasterVO.setPay_men(rs.getString("pay_men"));
					orderMasterVO.setOr_amount(rs.getInt("or_amount"));
					orderMasterVO.setRece(rs.getString("rece"));
					orderMasterVO.setAddress(rs.getString("address"));
					orderMasterVO.setMeror_num(rs.getString("meror_num"));
					list.add(orderMasterVO);
					
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
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
		public List<OrderMasterVO> getPointOrder(String mem_num) {
			// TODO Auto-generated method stub
			
			List<OrderMasterVO>  list = new ArrayList<OrderMasterVO>();
			OrderMasterVO orderMasterVO = null;
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;	
			
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_MEM_POINT_ORDER);
				pstmt.setString(1, mem_num);
				rs = pstmt.executeQuery();
				while(rs.next()){
					orderMasterVO = new OrderMasterVO();
					orderMasterVO.setMem_num(rs.getString("mem_num"));
					orderMasterVO.setOr_num(rs.getString("or_num"));
					orderMasterVO.setOr_stanum(rs.getString("or_stanum"));
					orderMasterVO.setSto_num(rs.getString("sto_num"));
					orderMasterVO.setOr_time(rs.getTimestamp("or_time"));
					orderMasterVO.setPre_rece(rs.getTimestamp("pre_rece"));
					orderMasterVO.setPay_men(rs.getString("pay_men"));
					orderMasterVO.setOr_amount(rs.getInt("or_amount"));
					orderMasterVO.setRece(rs.getString("rece"));
					orderMasterVO.setAddress(rs.getString("address"));
					orderMasterVO.setMeror_num(rs.getString("meror_num"));
					list.add(orderMasterVO);
					
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
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
		
//-------end Eric-----------------------------------		
	
}
