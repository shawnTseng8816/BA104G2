package com.getpic.model;


import java.io.*;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.*;
import javax.servlet.http.*;

import com.member_profile.model.MemberProfileService;
import com.member_profile.model.MemberProfileVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.store_image.model.StoreImageService;
import com.store_image.model.StoreImageVO;
import com.store_profile.model.StoreProfileService;
import com.store_profile.model.StoreProfileVO;

public class GetPic extends HttpServlet {

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("text/html; charset=UTF-8");
		res.setContentType("image/jpg");
		InputStream in = null;
		ServletOutputStream out = res.getOutputStream();
		String type = req.getParameter("getType");
		
		
//		System.out.println("getpic" + sto_num + "  " + img_num);
		
		
		if("storeProfile".equals(type)){
			String sto_num = req.getParameter("sto_num");
			StoreProfileService storeProfileService = new StoreProfileService();
			Set<StoreProfileVO> setStoreProfile = storeProfileService.getStoreProfileBySto_num(sto_num);
			Iterator itStoreProfile = setStoreProfile.iterator();
			StoreProfileVO storeProfileVO = (StoreProfileVO) itStoreProfile.next();
			try{
				in = new ByteArrayInputStream(storeProfileVO.getSto_logo());
			}catch(Exception e){
				in = new FileInputStream(getServletContext().getRealPath("/img/earth.jpg"));
			}
		}
		
		
		if("storeImage".equals(type)){
			String sto_num = req.getParameter("sto_num");
			String img_num = req.getParameter("img_num");
			StoreImageService storeImageService = new StoreImageService();
			StoreImageVO storeImageVO = storeImageService.getStoreImage(sto_num, img_num);
				try{
					in = new ByteArrayInputStream(storeImageVO.getImg());
				}catch(Exception e){
					in = new FileInputStream(getServletContext().getRealPath("/img/earth.jpg"));
				}
		}
		
		
		if("product".equals(type)){
			String sto_num = req.getParameter("sto_num");
			String com_num = req.getParameter("com_num");
			ProductService productService = new ProductService();
			ProductVO productVO = productService.getProductImage(sto_num, com_num);
				try{
					in = new ByteArrayInputStream(productVO.getImg());
				}catch(Exception e){
					in = new FileInputStream(getServletContext().getRealPath("/img/earth.jpg"));
				}
		}
		
		
		if("memberProfile".equals(type)){
			String mem_num = req.getParameter("mem_num");
			MemberProfileService memberProfileService = new MemberProfileService();
			MemberProfileVO memberProfileVO = memberProfileService.getMemberProfileByMem_num(mem_num);
			try{
				in = new ByteArrayInputStream(memberProfileVO.getMem_img());
			}catch(Exception e){
				in = new FileInputStream(getServletContext().getRealPath("/img/earth.jpg"));
			}
			
		}
		
		if("storeName".equals(type)){
			String sto_num = req.getParameter("sto_num");
			StoreProfileService storeProfileService = new StoreProfileService();
			Set<StoreProfileVO> setStoreProfile = storeProfileService.getStoreProfileBySto_num(sto_num);
			Iterator itStoreName = setStoreProfile.iterator();
			StoreProfileVO storeProfileVO = (StoreProfileVO) itStoreName.next();
			try{
				in = new ByteArrayInputStream(storeProfileVO.getSto_logo());
			}catch(Exception e){
				in = new FileInputStream(getServletContext().getRealPath("/img/earth.jpg"));
			}
		}
		
		
		if("memberName".equals(type)){
			String mem_num = req.getParameter("mem_num");
			System.out.println(mem_num);
			MemberProfileService memberProfileService = new MemberProfileService();
			Set<MemberProfileVO> setMemberProfile = memberProfileService.getMemberProfileImg(mem_num);
			Iterator itMemberName = setMemberProfile.iterator();
			MemberProfileVO memberProfileVO = (MemberProfileVO) itMemberName.next();
			try{
				in = new ByteArrayInputStream(memberProfileVO.getMem_img());
			}catch(Exception e){
				in = new FileInputStream(getServletContext().getRealPath("/img/earth.jpg"));
			}
		}
		
		if("keepRecord".equals(type)){
			String sto_num = req.getParameter("sto_num");
			String com_num = req.getParameter("com_num");
			ProductService productService = new ProductService();
			ProductVO productVO = productService.getProductImage(sto_num, com_num);
			try{
				in = new ByteArrayInputStream(productVO.getImg());
			}catch(Exception e){
				in = new FileInputStream(getServletContext().getRealPath("/img/earth.jpg"));
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
