package com.keep_record.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.order_master.model.OrderDetailToolVO;

public class KeepRecordDAO implements KeepRecordDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	
//-------------------KAHN--------------------------------	
	private static final String INSERT_KEEP_RECORD = "INSERT INTO KEEP_RECORD (KEEP_NUM, MEM_NUM, STO_NUM, COM_NUM, ORDET_NUM, KEEP_STATUS) VALUES('KN'||TRIM(TO_CHAR(SEQ_KEEP_NUM.NEXTVAL, '0000000000')), ?, ?, ?, ?, ?)";
	private static final String GET_KEEP_RECORD = "SELECT KEEP_NUM, MEM_NUM, STO_NUM, COM_NUM, ORDET_NUM, keep_time, rec_time, KEEP_STATUS, fail_reason FROM KEEP_RECORD WHERE mem_num=?";
	private static final String GET_KEEP_RECORD_FROM_ORDETNUM = "SELECT * FROM KEEP_RECORD WHERE ORDET_NUM=?";
	private static final String DELETE_KEEP_RECORD = "DELETE FROM KEEP_RECORD WHERE KEEP_NUM=?";

	@Override
	public Set<KeepRecordVO> getKeepRecordByMem_num(String mem_num) {
		Set<KeepRecordVO> set = new LinkedHashSet<KeepRecordVO>();
		KeepRecordVO keepRecordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_KEEP_RECORD);
			pstmt.setString(1, mem_num);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				keepRecordVO = new KeepRecordVO();
				keepRecordVO.setKeep_num(rs.getString("keep_num"));
				keepRecordVO.setMem_num(rs.getString("mem_num"));
				keepRecordVO.setSto_num(rs.getString("sto_num"));
				keepRecordVO.setCom_num(rs.getString("com_num"));
				keepRecordVO.setOrdet_num(rs.getString("ordet_num"));
				keepRecordVO.setKeep_time(rs.getDate("keep_time"));
				keepRecordVO.setRec_time(rs.getDate("rec_time"));
				keepRecordVO.setKeep_status(rs.getString("keep_status"));
				keepRecordVO.setFail_reason(rs.getString("fail_reason"));
				set.add(keepRecordVO);

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
		return set;
	}

	@Override
	public String insertKeepRecord(KeepRecordVO keeprecordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String key = null;

		try {

			con = ds.getConnection();

			String[] cols = { "KEEP_NUM" }; // 或 int cols[] = {1};
			pstmt = con.prepareStatement(INSERT_KEEP_RECORD, cols);

			con.setAutoCommit(false);

			pstmt.setString(1, keeprecordVO.getMem_num());
			pstmt.setString(2, keeprecordVO.getSto_num());
			pstmt.setString(3, keeprecordVO.getCom_num());
			pstmt.setString(4, keeprecordVO.getOrdet_num());
			pstmt.setString(5, keeprecordVO.getKeep_status());

			pstmt.executeUpdate();

			con.commit();

			rs = pstmt.getGeneratedKeys();
			rs.next();
			key = rs.getString(1);

		} catch (SQLException se) {
			try {
				con.rollback();
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
	public void deleteKeepRecord(KeepRecordVO keeprecordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_KEEP_RECORD);

			con.setAutoCommit(false);

			pstmt.setString(1, keeprecordVO.getKeep_num());

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
	public KeepRecordVO getKeepRecordByOrdetNum(String OrdetNum) {
		KeepRecordVO keepRecordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_KEEP_RECORD_FROM_ORDETNUM);
			pstmt.setString(1, OrdetNum);
			rs = pstmt.executeQuery();

			rs.next();
			keepRecordVO = new KeepRecordVO();
			keepRecordVO.setKeep_num(rs.getString("keep_num"));
			keepRecordVO.setMem_num(rs.getString("mem_num"));
			keepRecordVO.setSto_num(rs.getString("sto_num"));
			keepRecordVO.setCom_num(rs.getString("com_num"));
			keepRecordVO.setOrdet_num(rs.getString("ordet_num"));
			keepRecordVO.setKeep_time(rs.getDate("keep_time"));
			keepRecordVO.setRec_time(rs.getDate("rec_time"));
			keepRecordVO.setKeep_status(rs.getString("keep_status"));
			keepRecordVO.setFail_reason(rs.getString("fail_reason"));

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
		return keepRecordVO;
	}
//-------------------end KAHN--------------------------------	

	
	
//-------------------Shawn---------------------------------
	
//	private static final String GET_KEEP_RECORD = 
//			"SELECT * FROM KEEP_RECORD WHERE mem_num=? ORDER BY STO_NUM";
	private static final String GET_KEEP_RECORD_STATUS = 
			"SELECT * FROM KEEP_RECORD WHERE mem_num=? AND keep_status=? ORDER BY STO_NUM";
	private static final String UPDATE_KEEP_RECORD = 
			"UPDATE KEEP_RECORD SET keep_status=? WHERE keep_num=?";
	

//	@Override
//	public Set<KeepRecordVO> getKeepRecordByMem_num(String mem_num) {
//		Set<KeepRecordVO> set = new LinkedHashSet<KeepRecordVO>();		
//		KeepRecordVO keepRecordVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_KEEP_RECORD);
//			pstmt.setString(1, mem_num);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				keepRecordVO = new KeepRecordVO();
//				keepRecordVO.setKeep_num(rs.getString("keep_num"));
//				keepRecordVO.setMem_num(rs.getString("mem_num"));
//				keepRecordVO.setSto_num(rs.getString("sto_num"));
//				keepRecordVO.setCom_num(rs.getString("com_num"));
//				keepRecordVO.setOrdet_num(rs.getString("ordet_num"));
//				keepRecordVO.setKeep_time(rs.getDate("keep_time"));
//				keepRecordVO.setRec_time(rs.getDate("rec_time"));
//				keepRecordVO.setKeep_status(rs.getString("keep_status"));
//				keepRecordVO.setFail_reason(rs.getString("fail_reason"));
//				set.add(keepRecordVO);
//
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
//		return set;
//
//	}

	@Override
	public void updateKeepRecord(KeepRecordVO keepRecordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_KEEP_RECORD);

			pstmt.setString(1, keepRecordVO.getKeep_status());
			pstmt.setString(2, keepRecordVO.getKeep_num());
	

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public Set<KeepRecordVO> getKeepRecordByStatus(String mem_num, String keep_status) {
		Set<KeepRecordVO> set = new LinkedHashSet<KeepRecordVO>();		
		KeepRecordVO keepRecordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_KEEP_RECORD_STATUS);
			pstmt.setString(1, mem_num);
			pstmt.setString(2, keep_status);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				keepRecordVO = new KeepRecordVO();
				keepRecordVO.setKeep_num(rs.getString("keep_num"));
				keepRecordVO.setMem_num(rs.getString("mem_num"));
				keepRecordVO.setSto_num(rs.getString("sto_num"));
				keepRecordVO.setCom_num(rs.getString("com_num"));
				keepRecordVO.setOrdet_num(rs.getString("ordet_num"));
				keepRecordVO.setKeep_time(rs.getDate("keep_time"));
				keepRecordVO.setRec_time(rs.getDate("rec_time"));
				keepRecordVO.setKeep_status(rs.getString("keep_status"));
				keepRecordVO.setFail_reason(rs.getString("fail_reason"));
				set.add(keepRecordVO);

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
	
//------------------end Shawn-------------------------------	
	
	
//-----------------Eric------------------------
	private static final String FINISH_KEEP_STATUS = "UPDATE KEEP_RECORD SET KEEP_STATUS=?,REC_TIME=SYSDATE WHERE KEEP_NUM=?";
	private static final String UPDATE_KEEP_STATUS = "UPDATE KEEP_RECORD SET KEEP_STATUS=? WHERE KEEP_NUM=?";
	private static final String GET_ONE_STO_KEEPRECORD_NOFINISH ="SELECT * FROM KEEP_RECORD WHERE STO_NUM=? AND KEEP_STATUS!='已領取' ORDER BY substr(KEEP_STATUS,-2) DESC";
	private static final String GET_ONE_STO_KEEPRECORD_FINISH ="SELECT * FROM KEEP_RECORD WHERE STO_NUM=? AND KEEP_STATUS='已領取'";
	private static final String GET_EXTRA = "SELECT E.ext_name FROM EXTRA_LIST EL JOIN EXTRA E"
			+" ON EL.EXT_NUM=E.EXT_NUM AND EL.ordet_num =?";

	//ORDET_NUM =?
	private static final String GET_ORDET =
	"SELECT OD.ORDET_NUM,PRO.COM_NAME,SWE.SWEET_TYPE,ICE.ICE_TYPE,OD.COM_SIZE,OD.OD_PRICE ,COUNT(*) AS AMOUNT "+
	" FROM ORDER_DETAIL OD JOIN PRODUCT PRO ON OD.COM_NUM = PRO.COM_NUM JOIN SWEETNESS SWE "+
	" ON OD.SWEET_NUM = SWE.SWEET_NUM JOIN ICE_LIST ICE ON OD.ICE_NUM = ICE.ICE_NUM AND OD.ORDET_NUM =?"+
	"Group By PRO.COM_NAME,SWE.SWEET_TYPE,ICE.ICE_TYPE,OD.COM_SIZE,OD.OD_PRICE,OD.ORDET_NUM";
	
	@Override
	public List<OrderDetailToolVO> getOrderDetailTool(String ordet_num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderDetailToolVO orderDetailToolVO = null;
		List<OrderDetailToolVO> list = new ArrayList<OrderDetailToolVO>();

		List<String> extras = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDET);
			pstmt.setString(1, ordet_num);
			rs = pstmt.executeQuery();
			while (rs.next()) {	
				orderDetailToolVO = new OrderDetailToolVO();
				orderDetailToolVO.setCom_name(rs.getString("COM_NAME"));
				orderDetailToolVO.setSweet_type(rs.getString("SWEET_TYPE"));
				orderDetailToolVO.setIce_type(rs.getString("ICE_TYPE"));			
				orderDetailToolVO.setCom_size(rs.getString("COM_SIZE"));	
				orderDetailToolVO.setOd_price(rs.getInt("OD_PRICE"));
				orderDetailToolVO.setAmount(rs.getInt("AMOUNT"));	
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
	public List<KeepRecordVO> getKeepInfo(Map<String, String[]> map) {
		// TODO Auto-generated method stub
		List<KeepRecordVO>  list = new ArrayList<KeepRecordVO>();
		KeepRecordVO keepRecordVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			con = ds.getConnection();
			String SQL = "SELECT * FROM KEEP_RECORD " 
			+ CompositeQueryKeepRecord.get_WhereCondition(map)
			+ " ORDER BY KEEP_NUM";
			
			System.out.println(" ***SQL*** :" + SQL);
			pstmt = con.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while(rs.next()){
				 keepRecordVO = new  KeepRecordVO();
				 keepRecordVO.setCom_num(rs.getString("com_num"));
				 keepRecordVO.setSto_num(rs.getString("sto_num"));
				 keepRecordVO.setKeep_status(rs.getString("keep_status"));
				 keepRecordVO.setRec_time(rs.getDate("rec_time"));
				 keepRecordVO.setMem_num(rs.getString("mem_num"));
				 keepRecordVO.setKeep_time(rs.getDate("keep_time"));
				 keepRecordVO.setOrdet_num(rs.getString("ordet_num"));
				 keepRecordVO.setFail_reason(rs.getString("fail_reason"));
				 keepRecordVO.setKeep_num(rs.getString("keep_num"));
				 list.add(keepRecordVO);
				
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
	public List<KeepRecordVO> getKeepInfoByMobile(String mobile,String sto_num) {
		// TODO Auto-generated method stub
		List<KeepRecordVO>  list = new ArrayList<KeepRecordVO>();
		KeepRecordVO keepRecordVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			con = ds.getConnection();
//			String SQL = "SELECT * FROM KEEP_RECORD " 
//			+ CompositeQueryKeepRecord.get_WhereCondition(map)
//			+ " ORDER BY KEEP_NUM";
			String SQL = "SELECT KR.* FROM KEEP_RECORD KR"+
					" JOIN MEMBER_PROFILE MP ON Kr.Mem_Num=Mp.Mem_Num" +
					" WHERE substr(MOBILE,-3)=? AND STO_NUM=? AND (KEEP_STATUS='審核通過' or KEEP_STATUS='申請中')";
			System.out.println(" ***SQL*** :" + SQL);
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(1,mobile);
			pstmt.setString(2,sto_num);
			rs = pstmt.executeQuery();
			while(rs.next()){
				 keepRecordVO = new  KeepRecordVO();
				 keepRecordVO.setCom_num(rs.getString("com_num"));
				 keepRecordVO.setSto_num(rs.getString("sto_num"));
				 keepRecordVO.setKeep_status(rs.getString("keep_status"));
				 keepRecordVO.setRec_time(rs.getDate("rec_time"));
				 keepRecordVO.setMem_num(rs.getString("mem_num"));
				 keepRecordVO.setKeep_time(rs.getDate("keep_time"));
				 keepRecordVO.setOrdet_num(rs.getString("ordet_num"));
				 keepRecordVO.setFail_reason(rs.getString("fail_reason"));
				 keepRecordVO.setKeep_num(rs.getString("keep_num"));
				 list.add(keepRecordVO);
				
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
	public List<KeepRecordVO> getOneStoKeepRecordNoFinish(String sto_num) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			KeepRecordVO keepRecordVO = null;
			List list = new ArrayList<KeepRecordVO>();
			try {
				con = ds.getConnection();
				pstmt =con.prepareStatement(GET_ONE_STO_KEEPRECORD_NOFINISH);
				pstmt.setString(1, sto_num);
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					 keepRecordVO = new  KeepRecordVO();
					 keepRecordVO.setCom_num(rs.getString("com_num"));
					 keepRecordVO.setSto_num(rs.getString("sto_num"));
					 keepRecordVO.setKeep_status(rs.getString("keep_status"));
					 keepRecordVO.setRec_time(rs.getDate("rec_time"));
					 keepRecordVO.setMem_num(rs.getString("mem_num"));
					 keepRecordVO.setKeep_time(rs.getDate("keep_time"));
					 keepRecordVO.setOrdet_num(rs.getString("ordet_num"));
					 keepRecordVO.setFail_reason(rs.getString("fail_reason"));
					 keepRecordVO.setKeep_num(rs.getString("keep_num"));
					list.add(keepRecordVO);
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
	public List<KeepRecordVO> getOneStoKeepRecordFinish(String sto_num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		KeepRecordVO keepRecordVO = null;
		List list = new ArrayList<KeepRecordVO>();
		try {
			con = ds.getConnection();
			pstmt =con.prepareStatement(GET_ONE_STO_KEEPRECORD_FINISH);
			pstmt.setString(1, sto_num);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				 keepRecordVO = new  KeepRecordVO();
				 keepRecordVO.setCom_num(rs.getString("com_num"));
				 keepRecordVO.setSto_num(rs.getString("sto_num"));
				 keepRecordVO.setKeep_status(rs.getString("keep_status"));
				 keepRecordVO.setRec_time(rs.getDate("rec_time"));
				 keepRecordVO.setMem_num(rs.getString("mem_num"));
				 keepRecordVO.setKeep_time(rs.getDate("keep_time"));
				 keepRecordVO.setOrdet_num(rs.getString("ordet_num"));
				 keepRecordVO.setFail_reason(rs.getString("fail_reason"));
				 keepRecordVO.setKeep_num(rs.getString("keep_num"));
				list.add(keepRecordVO);
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
	public void UpdateKeepRecord(String keep_num, String keep_status) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = ds.getConnection();
				if(keep_status.equals("已領取")){
					pstmt = con.prepareStatement(FINISH_KEEP_STATUS );
					pstmt.setString(1, keep_status);
					pstmt.setString(2, keep_num);
				}else{			
				pstmt = con.prepareStatement(UPDATE_KEEP_STATUS);
				pstmt.setString(1, keep_status);
				pstmt.setString(2, keep_num);
				}
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

	
//----------------end Eric----------------------	
}
