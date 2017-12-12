package com.rem_record.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import account.AccService;
import account.AccVO;




public class RemRecordDAO implements RemRecordDAO_interface {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	//修改匯款狀態
	private static final String UPDATE_STATUS ="UPDATE REM_RECORD SET REM_STATUS =?,REM_DATE=? WHERE REM_NUM = ?";
	//取得目前狀態為省核中的匯款資料
	private static final String GET_REM_STATUS ="SELECT * FROM REM_RECORD WHERE REM_STATUS='審核中'";
	//取得一個店家匯款明細
	private static final String GET_ONE_STO_REM_NUM_DETAILS ="SELECT * FROM REM_RECORD WHERE STO_NUM = ? order by rem_num DESC";
	
	//取得一個匯款編號明細
	private static final String GET_ONE_REM_NUM_DETAILS ="SELECT * FROM REM_RECORD WHERE REM_NUM = ?";
	//取得一個店家編號明細
	private static final String GET_ONE_STO_DETAILS ="SELECT * FROM STORE_PROFILE WHERE STO_NUM = ?";
	//更新店家的剩餘點數
	private static final String UPDATE_STO_REM_POINT = "UPDATE STORE_PROFILE SET REM_POINT=REM_POINT+? WHERE STO_NUM = ?";
	
	private static final String GET_REM_APPLY ="SELECT * FROM REM_RECORD WHERE REM_STATUS='審核中'";
	
	private static final String GET_REM_FINISH ="SELECT * FROM REM_RECORD WHERE REM_STATUS NOT LIKE '審核中'";
	
	private static final String INSERT_REM_RECORD ="INSERT INTO REM_RECORD(REM_NUM, STO_NUM, REM_ACCOUNT, REM_CASH)VALUES('RR'||LPAD(to_char(SEQ_REM_NUM.NEXTVAL),10,'0'),?,?,?)";
	
	//查詢申請中的匯款紀錄
		
	@Override
	public List<RemRecordVO> getApply() {
		
		List<RemRecordVO> list = new ArrayList<RemRecordVO>();
		RemRecordVO remVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_REM_APPLY);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				remVO = new RemRecordVO();
				remVO.setRem_num(rs.getString("rem_num"));
				remVO.setSto_num(rs.getString("sto_num"));
				remVO.setRem_account(rs.getString("rem_account"));
				remVO.setRem_cash(rs.getInt("rem_cash"));
				remVO.setApply_date(rs.getTimestamp("apply_date"));	
				//remVO.setApply_date(rs.getDate("apply_date"));
				remVO.setRem_status(rs.getString("rem_status"));
				list.add(remVO); // Store the row in the list
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

	
	//取得店家剩餘點數
	public Integer getStoRemPoint(String sto_num){
			
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer rem_point;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STO_DETAILS);
			pstmt.setString(1, sto_num);
			rs = pstmt.executeQuery();
			rs.next();
			rem_point = rs.getInt("rem_point");
			System.out.println(rem_point);

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
		return rem_point;
	
	}

	
	//匯款省核通過
	@Override
	public void pass(RemRecordVO remVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {
				//新增一筆資料到 匯款紀錄裡面
				long t = System.currentTimeMillis();
				Timestamp ts = new Timestamp(t);
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_STATUS);
				pstmt.setString(1, remVO.getRem_status());
				pstmt.setString(3, remVO.getRem_num());
				pstmt.setTimestamp(2, ts);
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
	
	//匯款省核失敗
@Override
	public void noPass(RemRecordVO remVO) {


		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			//更新匯款紀錄狀態 為失敗
			long t = System.currentTimeMillis();
			Timestamp ts = new Timestamp(t);
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_STATUS);
			pstmt.setString(1, remVO.getRem_status());
			pstmt.setString(3, remVO.getRem_num());
			pstmt.setTimestamp(2, ts);
			pstmt.executeUpdate();
			
			
			//將金額返回至店家帳號內
			pstmt = con.prepareStatement(UPDATE_STO_REM_POINT);			
			pstmt.setInt(1,  remVO.getRem_cash());
			pstmt.setString(2, remVO.getSto_num());
			pstmt.executeUpdate();
			con.commit();
			// Handle any SQL errors
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	//取得所有省核完成清單
	@Override
	public List<RemRecordVO> getFinish() {
		List<RemRecordVO> list = new ArrayList<RemRecordVO>();
		RemRecordVO remVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_REM_FINISH);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				remVO = new RemRecordVO();
				remVO.setRem_num(rs.getString("rem_num"));
				remVO.setSto_num(rs.getString("sto_num"));
				remVO.setRem_account(rs.getString("Rem_account"));
				remVO.setRem_cash(rs.getInt("rem_cash"));
				remVO.setApply_date(rs.getTimestamp("apply_date"));
				remVO.setRem_status(rs.getString("rem_status"));
				remVO.setRem_date(rs.getTimestamp("Rem_date"));
				list.add(remVO); // Store the row in the list
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

	//新增一筆申請匯款紀錄
	public void insertRem(RemRecordVO remVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_REM_RECORD);
			pstmt.setString(1, remVO.getSto_num());
			pstmt.setString(2, remVO.getRem_account());
			pstmt.setInt(3, remVO.getRem_cash());
			pstmt.executeUpdate();
			
			//從店家帳號中扣除提款的金額
			pstmt = con.prepareStatement(UPDATE_STO_REM_POINT);
			pstmt.setInt(1,  -remVO.getRem_cash());
			pstmt.setString(2, remVO.getSto_num());
			pstmt.executeUpdate();
			con.commit();
			// Handle any SQL errors
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}


	@Override
	public List<RemRecordVO> getOneStoRemInfo(String sto_num) {
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RemRecordVO remRecordVO = null;
		List<RemRecordVO> list = new ArrayList<RemRecordVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STO_REM_NUM_DETAILS);
			pstmt.setString(1, sto_num);
			rs =pstmt.executeQuery();
			while(rs.next()){
				remRecordVO = new RemRecordVO();
				remRecordVO.setApply_date(rs.getTimestamp("apply_date"));
				remRecordVO.setRem_account(rs.getString("rem_account"));
				remRecordVO.setRem_cash(rs.getInt("rem_cash"));
				remRecordVO.setRem_num(rs.getString("rem_num"));
				remRecordVO.setRem_date(rs.getTimestamp("rem_date"));
				remRecordVO.setRem_status(rs.getString("rem_status"));
				list.add(remRecordVO);				
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	finally {
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

}
