package com.store_comment.model;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.store_profile.model.StoreProfileVO;

public class StoreCommentDAO implements StoreCommentDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

//----------KAHN--------------------------	
	private static final String UPDATE = "UPDATE STORE_COMMENT SET COM_TITLE=NVL(?, COM_TITLE), STARS=NVL(?, STARS), COMMENTT=NVL(?, COMMENTT), STATUS=NVL(?, STATUS) WHERE COM_NUM=?";
	private static final String GET_MYCOMMENTS = "SELECT * FROM (SELECT T.*, ROWNUM R FROM (SELECT COM_NUM ,COM_TITLE, STARS, COMMENTT, COM_TIME, STO_NAME, SC.STO_NUM FROM STORE_COMMENT SC FULL OUTER JOIN STORE_PROFILE SP ON SC.STO_NUM = SP.STO_NUM WHERE MEM_NUM=? AND STATUS=? ORDER BY COM_TIME DESC) T ) WHERE R BETWEEN ? AND ?";

	private static final String GET_STORE_COMMENT = 
			"SELECT * FROM STORE_COMMENT WHERE sto_num=? ORDER BY COM_TIME DESC";
	
	private static final String GET_STORE_STAR = 
			"SELECT SP.sto_num , SP.sto_name ,AVG(STARS)FROM STORE_PROFILE SP "
			+ " LEFT JOIN STORE_COMMENT SC ON SP.STO_NUM = SC.STO_NUM WHERE SP.STO_STATUS = '已上架' AND SC.STATUS = '一般'  "
			+ " GROUP BY SP.STO_NUM, SP.STO_NAME ORDER BY AVG(STARS) DESC";
	
	
	
	@Override
	public List<StoreCommentVO> geStoreCommentBySto_num(String sto_num) {
		
		List<StoreCommentVO> storeCommentList = new ArrayList<StoreCommentVO>();
		StoreCommentVO storeCommentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_STORE_COMMENT);
			pstmt.setString(1, sto_num);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				storeCommentVO = new StoreCommentVO();
				storeCommentVO.setCom_num(rs.getString("com_num"));
				storeCommentVO.setCom_title(rs.getString("com_title"));
				storeCommentVO.setSto_num(rs.getString("sto_num"));
				storeCommentVO.setMem_num(rs.getString("mem_num"));
				storeCommentVO.setStars(rs.getInt("stars"));
				storeCommentVO.setCommentt(rs.getString("commentt"));
				storeCommentVO.setCom_time(rs.getDate("com_time"));
				storeCommentVO.setStatus(rs.getString("status"));
				storeCommentList.add(storeCommentVO);
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
		return storeCommentList;
	}
	
	public Map<String, Integer> getStoreStars() {
		Map<String, Integer> storeCommentList = new HashMap<String, Integer>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_STORE_STAR);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				storeCommentList.put(rs.getString("STO_Num"),rs.getInt("AVG(STARS)"));
				
			}
			System.out.println(storeCommentList);
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
		return storeCommentList;
	}
	
	
	@Override
	public void update(StoreCommentVO storeCommentVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			con.setAutoCommit(false);

			pstmt.setString(1, storeCommentVO.getCom_title());
			if (storeCommentVO.getStars() == null) {
				pstmt.setString(2, null);
			} else {
				pstmt.setInt(2, storeCommentVO.getStars());
			}
			pstmt.setString(3, storeCommentVO.getCommentt());
			pstmt.setString(4, storeCommentVO.getStatus());
			pstmt.setString(5, storeCommentVO.getCom_num());

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
	public List<List> getMyComment(StoreCommentVO storeCommentVO, int rows) {
		StoreCommentVO stoCommentVO = null;
		StoreProfileVO stoProfileVO = null;
		List<List> commentsList = new ArrayList<List>();
		List<Object> myComment = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MYCOMMENTS);

			pstmt.setString(1, storeCommentVO.getMem_num());
			pstmt.setString(2, storeCommentVO.getStatus());
			pstmt.setInt(3, rows);
			pstmt.setInt(4, (rows + 1));

			rs = pstmt.executeQuery();

			while (rs.next()) {
				stoCommentVO = new StoreCommentVO();
				stoProfileVO = new StoreProfileVO();
				myComment = new ArrayList<Object>();

				stoCommentVO.setCom_num(rs.getString("COM_NUM"));
				stoCommentVO.setCom_title(rs.getString("COM_TITLE"));
				stoCommentVO.setCommentt(rs.getString("COMMENTT"));
				stoCommentVO.setStars(rs.getInt("STARS"));
				stoCommentVO.setCom_time(rs.getTimestamp("COM_TIME"));
				stoProfileVO.setSto_name(rs.getString("STO_NAME"));
				stoProfileVO.setSto_num(rs.getString("STO_NUM"));
				
				myComment.add(stoCommentVO);
				myComment.add(stoProfileVO);
				commentsList.add(myComment);
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
		return commentsList;
	}
	
//-------------end KAHN--------------------
	
	
//--------------Shawn------------------------
	
//	private static final String GET_STORE_COMMENT = 
//			"SELECT * FROM STORE_COMMENT WHERE sto_num=? ORDER BY COM_TIME DESC";
	private static final String GET_COMMENT_CONTENT = 
			"SELECT * FROM STORE_COMMENT WHERE com_num=? ORDER BY COM_TIME DESC";
	
	private static final String GET_STORE_COMMENT_BY_STO_STATUS = 
			"SELECT * FROM STORE_COMMENT WHERE STO_NUM=? AND STATUS=? ORDER BY COM_TIME DESC";
//	@Override
//	public List<StoreCommentVO> geStoreCommentBySto_num(String sto_num) {
//		
//		List<StoreCommentVO> storeCommentList = new ArrayList<StoreCommentVO>();
//		StoreCommentVO storeCommentVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_STORE_COMMENT);
//			pstmt.setString(1, sto_num);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				storeCommentVO = new StoreCommentVO();
//				storeCommentVO.setCom_num(rs.getString("com_num"));
//				storeCommentVO.setCom_title(rs.getString("com_title"));
//				storeCommentVO.setSto_num(rs.getString("sto_num"));
//				storeCommentVO.setMem_num(rs.getString("mem_num"));
//				storeCommentVO.setStars(rs.getInt("stars"));
//				storeCommentVO.setCommentt(rs.getString("commentt"));
//				storeCommentVO.setCom_time(rs.getDate("com_time"));
//				storeCommentVO.setStatus(rs.getString("status"));
//				storeCommentList.add(storeCommentVO);
//			}
//
//			// Handle any driver errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
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
//		return storeCommentList;
//	}

	@Override
	public List<StoreCommentVO> geStoreCommentBySto_numStatus(String sto_num, String status){
		
		List<StoreCommentVO> storeCommentList = new ArrayList<StoreCommentVO>();
		StoreCommentVO storeCommentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_STORE_COMMENT_BY_STO_STATUS);
			pstmt.setString(1, sto_num);
			pstmt.setString(2, status);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				storeCommentVO = new StoreCommentVO();
				storeCommentVO.setCom_num(rs.getString("com_num"));
				storeCommentVO.setCom_title(rs.getString("com_title"));
				storeCommentVO.setSto_num(rs.getString("sto_num"));
				storeCommentVO.setMem_num(rs.getString("mem_num"));
				storeCommentVO.setStars(rs.getInt("stars"));
				storeCommentVO.setCommentt(rs.getString("commentt"));
				storeCommentVO.setCom_time(rs.getDate("com_time"));
				storeCommentVO.setStatus(rs.getString("status"));
				storeCommentList.add(storeCommentVO);
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
		return storeCommentList;
	}
	
	@Override
	public StoreCommentVO getStoreComment(String sto_num) {
		StoreCommentVO storeCommentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_COMMENT_CONTENT);
			pstmt.setString(1, sto_num);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				storeCommentVO = new StoreCommentVO();
				storeCommentVO.setCom_title(rs.getString("com_title"));
				storeCommentVO.setCommentt(rs.getString("commentt"));
				storeCommentVO.setStatus(rs.getString("status"));
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
		return storeCommentVO;
	}
	
	@Override
	public void updateCommentProfile(StoreCommentVO storeCommentVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STORE_COMMENT);

			pstmt.setString(1, storeCommentVO.getStatus());
			pstmt.setString(2, storeCommentVO.getCom_num());

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
	
//-----------end Shawn----------------------	
	
//---------Eric---------------------
//	private static final String UPDATE = "UPDATE STORE_COMMENT SET COM_TITLE=NVL(?, COM_TITLE), STARS=NVL(?, STARS), COMMENTT=NVL(?, COMMENTT), STATUS=NVL(?, STATUS) WHERE COM_NUM=?";
//	
//	private static final String GET_MYCOMMENTS = "SELECT * FROM (SELECT T.*, ROWNUM R FROM (SELECT COM_NUM ,COM_TITLE, STARS, COMMENTT, COM_TIME, STO_NAME, SC.STO_NUM FROM STORE_COMMENT SC FULL OUTER JOIN STORE_PROFILE SP ON SC.STO_NUM = SP.STO_NUM WHERE MEM_NUM=? AND STATUS=? ORDER BY COM_TIME DESC) T ) WHERE R BETWEEN ? AND ?";
	
	private static final String INSERT ="INSERT INTO STORE_COMMENT(COM_NUM, COM_TITLE, STO_NUM, MEM_NUM,OR_NUM, STARS, COMMENTT, STATUS)"+	
	"VALUES ('CM'||LPAD(to_char(SEQ_COM_NUM.NEXTVAL),13,'0'), ?, ?,?, ?, ?, ?, '一般')";
	
	private static final String GET_COMMENT = "SELECT * FROM STORE_COMMENT WHERE OR_NUM =?";
	
	private static final String UPDATE_STORE_COMMENT = 
			"UPDATE STORE_COMMENT set status=? WHERE com_num=?";
	
	
	@Override
	public String checkComment(String or_num){
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		String com_num = null;; 
		try {
			con = ds.getConnection();
			pstmt= con.prepareStatement(GET_COMMENT);
			pstmt.setString(1, or_num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				com_num = rs.getString("com_num");			
				
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
		
		return com_num;
		
		
		
	}
	
	
	
//	@Override
//	public void update(StoreCommentVO storeCommentVO) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(UPDATE);
//
//			con.setAutoCommit(false);
//
//			pstmt.setString(1, storeCommentVO.getCom_title());
//			if (storeCommentVO.getStars() == null) {
//				pstmt.setString(2, null);
//			} else {
//				pstmt.setInt(2, storeCommentVO.getStars());
//			}
//			pstmt.setString(3, storeCommentVO.getCommentt());
//			pstmt.setString(4, storeCommentVO.getStatus());
//			pstmt.setString(5, storeCommentVO.getCom_num());
//
//			pstmt.executeUpdate();
//
//			con.commit();
//
//		} catch (SQLException se) {
//			try {
//				con.rollback();
//				throw new RuntimeException("A database error occured. " + se.getMessage());
//			} catch (SQLException see) {
//				see.printStackTrace();
//			}
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.setAutoCommit(true);
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//	}
//
//	@Override
//	public List<List> getMyComment(StoreCommentVO storeCommentVO, int rows) {
//		StoreCommentVO stoCommentVO = null;
//		StoreProfileVO stoProfileVO = null;
//		List<List> commentsList = new ArrayList<List>();
//		List<Object> myComment = null;
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_MYCOMMENTS);
//
//			pstmt.setString(1, storeCommentVO.getMem_num());
//			pstmt.setString(2, storeCommentVO.getStatus());
//			pstmt.setInt(3, rows);
//			pstmt.setInt(4, (rows + 1));
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				stoCommentVO = new StoreCommentVO();
//				stoProfileVO = new StoreProfileVO();
//				myComment = new ArrayList<Object>();
//
//				stoCommentVO.setCom_num(rs.getString("COM_NUM"));
//				stoCommentVO.setCom_title(rs.getString("COM_TITLE"));
//				stoCommentVO.setCommentt(rs.getString("COMMENTT"));
//				stoCommentVO.setStars(rs.getInt("STARS"));
//				stoCommentVO.setCom_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getTimestamp("COM_TIME").toString()));
//				stoProfileVO.setSto_name(rs.getString("STO_NAME"));
//				stoProfileVO.setSto_num(rs.getString("STO_NUM"));
//				
//				myComment.add(stoCommentVO);
//				myComment.add(stoProfileVO);
//				commentsList.add(myComment);
//			}
//
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
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
//		return commentsList;
//	}

	@Override
	public void insert(StoreCommentVO storeCommentVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			pstmt.setString(1, storeCommentVO.getCom_title());
			pstmt.setString(2, storeCommentVO.getSto_num());
			pstmt.setString(3, storeCommentVO.getMem_num());
			pstmt.setString(4, storeCommentVO.getOr_num());
			pstmt.setInt(5, storeCommentVO.getStars());
			pstmt.setString(6, storeCommentVO.getCommentt());
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
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}
	
//--------end Eric------------------------	
	
}