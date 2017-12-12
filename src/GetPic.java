


import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

import com.coupon.model.CouponService;
import com.member_profile.model.MemberProfileService;
import com.store_image.model.StoreImageService;
import com.store_profile.model.StoreProfileService;

public class GetPic extends HttpServlet {

	
	
	
	
	

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/jpeg");
		ServletOutputStream out = res.getOutputStream();
		String coupon_num = req.getParameter("coupon_num");
		String mem_num = req.getParameter("mem_num");
		String sto_num = req.getParameter("sto_num");
		String img_num = req.getParameter("img_num");
		// 讀取店圖片
		if(img_num!=null ){
			
		try {
			System.out.println("店家圖片");
			img_num = req.getParameter("img_num");
			StoreImageService stoImgSvc = new StoreImageService();
			 byte[] pic = stoImgSvc.getStoreImage(img_num);
			 out.write(pic);
				
		
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		
		}finally{
			out.close();
		}
	}
		
		
		// 讀取店家LOGO
		else if(sto_num!=null){
			
		try {
			
			sto_num = req.getParameter("sto_num");
			StoreProfileService storeProfileSvc = new StoreProfileService();
			 byte[] pic = storeProfileSvc.getLogo(sto_num);
			 out.write(pic);
				
		
				
		} catch (Exception e) {
			InputStream in = getServletContext().getResourceAsStream("/img/coupon.jpg");
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			in.close();
		}finally{
			out.close();
		}
	}
		
		
		
		// 讀取折價券圖片
		else if(coupon_num!=null){
			
		try {
			
			 coupon_num = req.getParameter("coupon_num");
			 CouponService couponsvc = new CouponService();
			 byte[] pic = couponsvc.getPic(coupon_num);
			 out.write(pic);
				
		
				
		} catch (Exception e) {
			InputStream in = getServletContext().getResourceAsStream("/img/coupon.jpg");
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			in.close();
		}
	}
		
		// 讀取會員大頭貼
		else if(mem_num!=null){
			
		try {
			
			 mem_num = req.getParameter("mem_num");
			 MemberProfileService memberProfileService = new MemberProfileService();
			 byte[] pic = memberProfileService.getPic(mem_num);
			 out.write(pic);
				
		
				
		} catch (Exception e) {
			
			InputStream in = getServletContext().getResourceAsStream("/img/memnopic.png");
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			in.close();
		}
	}

	
	}

}
