package com.getpic.controller;


import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.*;
import javax.servlet.http.*;

import com.shop_ad.model.ShopAdService;
import com.shop_ad.model.ShopAdVO;
import com.store_profile.model.StoreProfileService;
import com.store_profile.model.StoreProfileVO;

public class GetPicServlet extends HttpServlet {

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("text/html; charset=UTF-8");
		res.setContentType("image/jpg");
		InputStream in = null;
		ServletOutputStream out = res.getOutputStream();
		String type = req.getParameter("getType");
		
		
//		System.out.println("getpic" + sto_num + "  " + img_num);
		
		
		if("storeProfile".equals(type)){
			String sto_num = req.getParameter("sto_num");
//		System.out.println("sto_num："+sto_num);
			StoreProfileService storeProfileService = new StoreProfileService();
			StoreProfileVO storeProfileVO = storeProfileService.getMyProfile(sto_num);
//		System.out.println("storeProfileVO："+storeProfileVO);
			try{
				in = new ByteArrayInputStream(storeProfileVO.getSto_logo());
//		System.out.println("in："+in);
			}catch(Exception e){
				in = new FileInputStream(getServletContext().getRealPath("/img/tomcat.gif"));
			}
		}else if("shopad".equals(type)){
			String sa_no = req.getParameter("sa_no");
//		System.out.println("sa_no："+sa_no);
			ShopAdService shopadService = new ShopAdService();
			ShopAdVO shopadVO = shopadService.getOneFuncList(sa_no);
//		System.out.println("shopadVO："+shopadVO);
			try{
				in = new ByteArrayInputStream(shopadVO.getSa_img());
//		System.out.println("in："+in);
			}catch(Exception e){
				in = new FileInputStream(getServletContext().getRealPath("/img/tomcat.gif"));
			}
		}
		
			if(in != null){
				
				try{
					
					byte[] buffer = new byte[in.available()];
					int len = 0;
					
					try{
						while((len = in.read(buffer))!=-1){
							out.write(buffer, 0, len);
						}
							out.close();
							return;
						
						}catch(IOException e){
							e.printStackTrace();
						}
					}catch (Exception e) {
					
						byte[] propic = new byte[in.available()];
						in.read(propic);
						out.write(propic);
						in.close();
				    }
			}
		}
	
			
}
