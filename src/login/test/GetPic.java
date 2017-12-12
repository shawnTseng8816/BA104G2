package login.test;

import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

import oracle.sql.BLOB;

public class GetPic extends HttpServlet {

	Connection con;

	private static final String GET_MEMBERPIC = "SELECT MEM_IMG FROM MEMBER_PROFILE WHERE MEM_NUM=?";
	private static final String GET_STOREPIC = "SELECT STO_LOGO FROM STORE_PROFILE WHERE STO_NUM=?";
	private static final String GET_PRODUCTPIC = "SELECT IMG FROM PRODUCT WHERE COM_NUM=?";

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/jpeg");
		ServletOutputStream out = res.getOutputStream();
		PreparedStatement pstmt = null;

		try {

			if (req.getParameter("mem_num") != null) {
				String mem_num = req.getParameter("mem_num");
				mem_num = new String(mem_num.getBytes("ISO-8859-1"), "UTF-8");
				pstmt = con.prepareStatement(GET_MEMBERPIC);
				pstmt.setString(1, mem_num);
			} else if (req.getParameter("sto_num") != null) {
				String sto_num = req.getParameter("sto_num");
				sto_num = new String(sto_num.getBytes("ISO-8859-1"), "UTF-8");
				pstmt = con.prepareStatement(GET_STOREPIC);
				pstmt.setString(1, sto_num);
			} else if (req.getParameter("com_num") != null){
				String com_num = req.getParameter("com_num");
				com_num = new String(com_num.getBytes("ISO-8859-1"), "UTF-8");
				pstmt = con.prepareStatement(GET_PRODUCTPIC);
				pstmt.setString(1, com_num);
			}

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				
				
				Blob blob = rs.getBlob(1);
				InputStream is = blob.getBinaryStream();
				int i;
				while ((i = is.read()) != -1) {
					out.write(i);
				}
				
//				byte[] pic = rs.getBytes(1);
//				out.write(pic);

			} else {
				InputStream in = req.getServletContext().getResourceAsStream("/img/errimg.jpg");
				byte[] b = new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			InputStream in = getServletContext().getResourceAsStream("/img/errimg.jpg");
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			in.close();
		}
	}

	public void init() throws ServletException {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
			con = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	
	

}
