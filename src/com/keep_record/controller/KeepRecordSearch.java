package com.keep_record.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.extra.model.ExtraService;
import com.extra.model.ExtraVO;
import com.extra_list.model.ExtraListService;
import com.extra_list.model.ExtraListVO;
import com.ice_list.model.IceListService;
import com.ice_list.model.IceListVO;
import com.keep_record.model.KeepRecordService;
import com.keep_record.model.KeepRecordVO;
import com.order_detail.model.OrderDetailService;
import com.order_detail.model.OrderDetailVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.store_profile.model.StoreProfileService;
import com.store_profile.model.StoreProfileVO;
import com.sweetness.model.SweetnessService;
import com.sweetness.model.SweetnessVO;


@WebServlet("/KeepRecordS")
public class KeepRecordSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		
		HttpSession session = req.getSession();
		String session_mem_num = (String) session.getAttribute("mem_num");
		String action = req.getParameter("action");
		String KeepRecordStyle = req.getParameter("KeepRecordStyle");
		
		if("keep_record_change".equals(action)){
	    	String keep_num = req.getParameter("keep_num");
	    	KeepRecordService keepRecordUpdate = new KeepRecordService();
	    	keepRecordUpdate.updateKeepRecord("申請中", keep_num);
	    	
	    }
		
		
	
		
		List<KeepRecordDetail> keepList = new ArrayList<>();
		KeepRecordService keepRecordService = new KeepRecordService();
		Set<KeepRecordVO> setKeepRecord=null;


		
		String KeepRecordStyleChinese = null;
		String statusString = null;
		if(KeepRecordStyle!=null){
			KeepRecordStyleChinese = new String(KeepRecordStyle.getBytes("ISO-8859-1"),"UTF-8");
		}
		
			
		String statusStringAgainChinese = null;
	    String statusStringAgain = req.getParameter("statusStringAgain");
	    if(statusStringAgain!=null){
	    	statusStringAgainChinese = new String(statusStringAgain.getBytes("ISO-8859-1"),"UTF-8");
	    }
	    
	    if("未領取".equals(statusStringAgainChinese)){
			statusString = statusStringAgainChinese;
			KeepRecordStyleChinese = statusString;
	    }
			

		if("未領取".equals(KeepRecordStyleChinese)){
			statusString = KeepRecordStyleChinese;
			setKeepRecord = keepRecordService.getKeepRecordByStatus(session_mem_num, "未領取");
		}else if("申請中".equals(KeepRecordStyleChinese)){
			statusString = KeepRecordStyleChinese;
			setKeepRecord = keepRecordService.getKeepRecordByStatus(session_mem_num, "申請中");
		}else if("審核通過".equals(KeepRecordStyleChinese)){
			statusString = KeepRecordStyleChinese;
			setKeepRecord = keepRecordService.getKeepRecordByStatus(session_mem_num, "審核通過");
		}else if("已領取".equals(KeepRecordStyleChinese)){
			statusString = KeepRecordStyleChinese;
			setKeepRecord = keepRecordService.getKeepRecordByStatus(session_mem_num, "已領取");
		}else if("全部".equals(KeepRecordStyleChinese)){
			statusString = KeepRecordStyleChinese;
			setKeepRecord = keepRecordService.getKeepRecordByMem_num(session_mem_num);
		}else{
			setKeepRecord = keepRecordService.getKeepRecordByMem_num(session_mem_num);
		}
		req.setAttribute("statusString", statusString);
		
	    
	    Iterator itKeepRecordVO = setKeepRecord.iterator();
	    OrderDetailService orderDetailService = null;
	    StoreProfileService storeProfileService = null;
	    IceListService iceListService = null;
	    SweetnessService sweetnessService = null;
	    ExtraService extraService = null;
	    ExtraListService extraListService = null;
	    ProductService productService = null;
	    while(itKeepRecordVO.hasNext()){
		    KeepRecordDetail keep = new KeepRecordDetail();
	    	KeepRecordVO keepRecordVO = (KeepRecordVO) itKeepRecordVO.next();
			String ordet_num = keepRecordVO.getOrdet_num();
			orderDetailService = new OrderDetailService();
			storeProfileService = new StoreProfileService();
			String sto_num = keepRecordVO.getSto_num();
			Set<StoreProfileVO> setStoreProfile = storeProfileService.getStoreProfileBySto_num(sto_num);
			Iterator itSetStoreProfileVO = setStoreProfile.iterator();
			StoreProfileVO storeProfileVO = null;
			while(itSetStoreProfileVO.hasNext()){
				storeProfileVO = (StoreProfileVO)itSetStoreProfileVO.next();
				keep.setSto_name(storeProfileVO.getSto_name());
			}
			OrderDetailVO orderDetailVO = orderDetailService.getOrderDetailByOrdet_num(ordet_num);
			String ice_num = orderDetailVO.getIce_num();
			String sweet_num = orderDetailVO.getSweet_num();
			iceListService = new IceListService();
			IceListVO iceListVO = iceListService.getIceName(ice_num); 
			sweetnessService = new SweetnessService();
			SweetnessVO sweetnessVO = sweetnessService.getSweetName(sweet_num);
			extraListService = new ExtraListService();
			Set<ExtraListVO> setExtraListVO = extraListService.getExtNum(ordet_num);
			Iterator itSetExtraListVO = setExtraListVO.iterator();
			ExtraVO extraVO = null;
			String totalExtName="";
			while(itSetExtraListVO.hasNext()){
				ExtraListVO extraListVO = (ExtraListVO) itSetExtraListVO.next();
				String ext_num = extraListVO.getExt_num();
				extraService = new ExtraService();
				extraVO = extraService.getExtName(ext_num);
				String ext_name = extraVO.getExt_name();
				totalExtName = totalExtName + "  " +ext_name;
			}
			keep.setExt_name(totalExtName);;
			String com_num = keepRecordVO.getCom_num();
			productService = new ProductService();
			ProductVO productVO = productService.getProductImage(sto_num, com_num);
			keep.setCom_name(productVO.getCom_name());
			keep.setKeep_num(keepRecordVO.getKeep_num());
			keep.setCom_num(keepRecordVO.getCom_num());
			keep.setSto_num(keepRecordVO.getSto_num());
			keep.setKeep_time(keepRecordVO.getKeep_time());
			keep.setRec_time(keepRecordVO.getRec_time());
			keep.setKeep_status(keepRecordVO.getKeep_status());
			keep.setCom_size(orderDetailVO.getCom_size());
			keep.setIce_type(iceListVO.getIce_type());
			keep.setSweet_type(sweetnessVO.getSweet_type());
			keepList.add(keep);
		}
	    
	    

	    req.setAttribute("keepList", keepList);
	    String url = "/front-end/KeepRecord/KeepRecord.jsp";
	    
	    
	    
	    RequestDispatcher successView = req.getRequestDispatcher(url); 
		successView.forward(req, res);
	}
       
    

}
