package com.backstage_management.controller;

import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

public class BmGifReader extends HttpServlet {

	Connection con;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		try {
			req.setCharacterEncoding("UTF-8");
			Statement stmt = con.createStatement();
			String bm_no = req.getParameter("bm_no");
			String bm_no2 = new String(bm_no.getBytes("ISO-8859-1"),"UTF-8");
			ResultSet rs = stmt.executeQuery(
				"SELECT BM_IMG FROM BACKSTAGE_MANAGEMENT WHERE BM_NO ='"+ bm_no2 +"'");

			if (rs.next()) {
				BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream(1));
				byte[] buf = new byte[4 * 1024]; // 4K buffer
				int len;
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				in.close();
			} else {
				//若檔案名稱不對，改顯示圖片
				InputStream in = getServletContext().getResourceAsStream("/img/LOGO_150x150.png");
				byte[] b = new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			//空值NullPointerException，改顯示圖片
			InputStream in = getServletContext().getResourceAsStream("/img/LOGO_150x150.png");
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			in.close();
		}
	}

	public void init() throws ServletException {
		try {
			Context ctx = new javax.naming.InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA104G2");
			con = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	}

	public void destroy() {
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
