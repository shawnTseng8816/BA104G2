package com.store_detail.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.favorite_store.model.FavoriteStoreService;
import com.favorite_store.model.FavoriteStoreVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.report_member.model.ReportMemberService;
import com.report_store.model.ReportStoreService;
import com.store_comment.model.StoreCommentService;
import com.store_comment.model.StoreCommentVO;
import com.store_image.model.StoreImageService;
import com.store_image.model.StoreImageVO;
import com.store_profile.model.StoreProfileService;
import com.store_profile.model.StoreProfileVO;

public class StoreDetailServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		String mem_num = (String) session.getAttribute("mem_num");
		String session_sto_num = (String) session.getAttribute("sto_num");
		
	
		
		String section = req.getParameter("section");
		
		if(section!=null){
			req.setAttribute("section", section);
		}
		
		
		/***************************1.�����ШD�Ѽ�****************************************/
		String sto_num = req.getParameter("sto_num");//��request��sto_num�Mmem_num

		Date date = new Date();
//		HttpSession session = req.getSession();//��session��sto_num�Mmem_num
//		session.getAttribute();
		/***************************2.�}�l�d�߸��****************************************/
		StoreProfileService storeProfileService = new StoreProfileService();//�d�ߩ��a��T
		Set<StoreProfileVO> setStoreProfile = storeProfileService.getStoreProfileBySto_num(sto_num);
		Iterator itStoreProfile = setStoreProfile.iterator();
		StoreProfileVO storeProfileVO = (StoreProfileVO) itStoreProfile.next();
		/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
		req.setAttribute("storeProfileVO", storeProfileVO); // ��Ʈw���X��storeProfileVO����,�s�Jreq
				
		
		
		
		
		
		ProductService productService = new ProductService();	
		List<ProductVO> productList = productService.getProductBySto_numStatus(sto_num,"已上架");
		Iterator itProduct = productList.iterator();
		List<ProductVO> getProductImageList = null;
		while(itProduct.hasNext()){
			ProductVO productVO = (ProductVO) itProduct.next();
			getProductImageList = new ArrayList<ProductVO>();	
			getProductImageList.add(productVO);	
//			System.out.println(productVO.getCom_num() + "   " + productVO.getSto_num());
		}
		req.setAttribute("productList", productList); 
		req.setAttribute("getProductImageList", getProductImageList);
		
		
		
		
		
		
		
		StoreCommentService storeCommentService = new StoreCommentService();	
		List<StoreCommentVO> storeCommentList = storeCommentService.geStoreCommentBySto_numStatus(sto_num,"一般");
	
		req.setAttribute("storeCommentList", storeCommentList); 

		
		
		
		
		
		
		StoreImageService storeImageService = new StoreImageService();
		List<StoreImageVO> storeImageList = storeImageService.getStoreImageNum(sto_num);
		Iterator itStoreImage = storeImageList.iterator();
		List<StoreImageVO> getStoreImageList = null;
		while(itStoreImage.hasNext()){
			StoreImageVO storeImageVO = (StoreImageVO) itStoreImage.next();
			getStoreImageList = new ArrayList<StoreImageVO>();	
			getStoreImageList.add(storeImageVO);	
//			System.out.println(storeImageVO.getImg_num() + "   " + storeImageVO.getSto_num());
		}
//		System.out.println(getStoreImageList.size());
		req.setAttribute("storeImageList", storeImageList);
		req.setAttribute("getStoreImageList", getStoreImageList);
		
		
		
		
		
		
		FavoriteStoreService favoriteStoreService = new FavoriteStoreService();
		FavoriteStoreVO favoriteStoreVO = null;
		favoriteStoreVO = favoriteStoreService.getOneFavoriteStore(mem_num, sto_num);
		if(favoriteStoreVO==null){
			if("add".equals(action)){
				favoriteStoreVO = favoriteStoreService.addFavoriteStore(mem_num, sto_num, "Y", date);
			}
			
		}else{
			if("add".equals(action)){
				favoriteStoreVO = favoriteStoreService.updateFavoriteStore("Y", date, mem_num, sto_num);
			}
			else if("cancel".equals(action)){
					favoriteStoreVO = favoriteStoreService.updateFavoriteStore("N", date, mem_num, sto_num);
			}
		}
		req.setAttribute("favoriteStoreVO", favoriteStoreVO);
		req.setAttribute("mem_num", mem_num);
		req.setAttribute("sto_num", sto_num);
		
		
		
		
		
		ReportStoreService reportStoreService = new ReportStoreService();
		ReportMemberService reportMemberService = new ReportMemberService();
		String com_num = req.getParameter("com_num");
		String requestURL = req.getParameter("requestURL");
		if("sto_add".equals(action)){
			String sto_comment_text = req.getParameter("sto_comment_text");
			String sto_comment_text1 = new String(sto_comment_text.getBytes("ISO-8859-1"),"UTF-8");
			reportStoreService.addReportStoreComment(session_sto_num, com_num, date, "待處理", sto_comment_text1);
			String url = requestURL;
			RequestDispatcher successView = req.getRequestDispatcher(url);   // �ק令�\��,���^�e�X�ק諸�ӷ�����
			successView.forward(req, res);
			return;
		}else if("mem_add".equals(action)){
			String mem_comment_text = req.getParameter("mem_comment_text");
			
			String mem_comment_text1 = new String(mem_comment_text.getBytes("ISO-8859-1"),"UTF-8");
			reportMemberService.addReportMemberComment(mem_num, com_num, date, "待處理", mem_comment_text1);
			String url = requestURL;
//		System.out.println("uriiiiiiiiiiii"+requestURL);
//			res.sendRedirect(req.getRequestURI());
			
			RequestDispatcher successView = req.getRequestDispatcher(url);   // �ק令�\��,���^�e�X�ק諸�ӷ�����
			successView.forward(req, res);
			return;
		}
		
		String url = "/front-end/StoreDetail/StoreDetail.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���StoreDetail.jsp
		successView.forward(req, res);	
		
		
		
		
	}


}
