package com.product.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.merged_commodity.model.MergedCommodityService;
import com.product.model.*;

@WebServlet("/ProductServlet")
@MultipartConfig()
public class ProductServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		req.setCharacterEncoding("UTF-8");
		String action =req.getParameter("action");
		HttpSession se = req.getSession();

		if("insert".equals(action)){

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
			try {
				/************ 1.接收請求參數 -輸入格式處理  ******************/
				String sto_num = req.getParameter("sto_num");
				String com_name = req.getParameter("com_name");
				String com_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_\r\t\n\f-)]{2,20}$";
				if (com_name == null || com_name.trim().length() == 0){
					errorMsgs.put("com_name","商品名稱：請勿空白");
				} else if (!com_name.trim().matches(com_nameReg)) {
					errorMsgs.put("com_name","商品名稱：只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
				}
				
				String price_nameReg = "^[0-9]{1,10}+$";
				Integer m_price = null;
				Integer l_price = null;				
				String m_price2 =req.getParameter("m_price");
				String l_price2 =req.getParameter("l_price");
				if (m_price2.trim().matches(price_nameReg)){
					try{
					m_price = new Integer(Integer.parseInt(m_price2));	
					} catch(NumberFormatException e) {
						errorMsgs.put("em_price","小杯值錯誤");
					}
				}else{
					m_price = new Integer(0);
					errorMsgs.put("m_price","小杯價錢：請輸入1-10位數數字");
				}				
				if (l_price2.trim().matches(price_nameReg)){
					try{
						l_price = new Integer( Integer.parseInt(l_price2));
					} catch(NumberFormatException e) {
						errorMsgs.put("el_price","大杯值錯誤");
					}
				}else{
					l_price = new Integer(0);
					errorMsgs.put("l_price","大杯價錢：請輸入1-10位數數字");
				}							
				if( errorMsgs.containsKey("em_price") && errorMsgs.containsKey("el_price")){
					errorMsgs.remove("em_price");
					errorMsgs.remove("el_price");
					errorMsgs.put("price","價錢請勿都為空白");				
				}
				if(l_price==0 && m_price==0){
					errorMsgs.put("price","價錢請勿都為0");	
				}
				if(m_price>l_price && l_price!=0){
					errorMsgs.put("price","小杯價格比大杯貴喔O_O!");	
				}
								
				String discribe = req.getParameter("discribe");
				if (discribe == null || discribe.trim().length() == 0){
					discribe = "無商品敘述";
				}
				
				Part imgs= req.getPart("img");
				byte[] img = getPictureByteArray(imgs); 
				
				String pt_num = req.getParameter("pt_num");
				if ( pt_num == null || pt_num.trim().length() == 0 ){
					errorMsgs.put("pt_num","商品類別：請勿空白");
				}				
				String status = req.getParameter("status");				
				
				
				System.out.println(errorMsgs.toString());
				ProductVO productVO = new ProductVO();
				productVO.setSto_num(sto_num);
				productVO.setCom_name(com_name);
				productVO.setM_price(m_price);
				productVO.setL_price(l_price);
				productVO.setDiscribe(discribe);
				productVO.setImg(img);
				productVO.setPt_num(pt_num);
				productVO.setStatus(status);
				
				
				//send back if errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/store-end/pdc_mng/addProduct.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/************ 2.開始加入資料   ****************************/
				ProductService pdcSvc = new ProductService();
				productVO = pdcSvc.addProduct(productVO);
				String com_num = productVO.getCom_num();
				/************ 3.加入完成,準備轉交(Send the Success view)**/	
				se.removeAttribute("addform");				//把通行證拿掉防止f5重送表單
				String url = "/store-end/pdc_mng/addProduct.jsp?com_num="+com_num;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交stolistAllProduct.jsp
				successView.forward(req, res);
				
				/*********** 其他錯誤  ***************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store-end/pdc_mng/addProduct.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
		if ( "getOne_For_Update".equals(action) ){
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
			try{
				/************ 1.接收請求參數 -輸入格式處理  ******************/
				String com_num = req.getParameter("com_num");
				
				/************ 2.開始查詢資料   ****************************/
				ProductService pdcSvc = new ProductService();
				ProductVO productVO = pdcSvc.getOneProduct(com_num);
				
				/************ 3.查詢完成,準備轉交(Send the Success view)**/				
				req.setAttribute("productVO", productVO);
				req.setAttribute("update_pdc_input", "update_pdc_input");
				String url = "/store-end/pdc_mng/update_pdc_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_pdc_input.jsp
				successView.forward(req, res);
				
				/************ 其他錯誤處理  ******************************/					
			} catch (Exception e) {
				errorMsgs.put("修改資料失敗:",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store-end/pdc_mng/stolistAllProduct.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ( "getName_For_Display".equals(action) ){
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
			try{
				/************ 1.接收請求參數 -輸入格式處理  ******************/
				String sto_num = req.getParameter("sto_num");
				String com_name = req.getParameter("com_name");
				if (com_name == null || com_name.trim().length() == 0){
					errorMsgs.put("com_name","請輸入關鍵字");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/store-end/pdc_mng/store_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				/************ 2.開始查詢資料   ****************************/
				ProductService pdcSvc = new ProductService();
				List<ProductVO> list = pdcSvc.stoFindProductbyName(com_name, sto_num);
				if(list.size()==0){
					errorMsgs.put("com_name","查無資料");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/store-end/pdc_mng/store_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				/************ 3.查詢完成,準備轉交(Send the Success view)**/				
				req.setAttribute("stolistAllProduct2", list);
				
				String url = "/store-end/pdc_mng/store_select_page.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 stoListAllProduct2.jsp
				successView.forward(req, res);
				
				/************ 其他錯誤處理  ******************************/					
			} catch (Exception e) {
				errorMsgs.put("查詢失敗:",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store-end/pdc_mng/store_select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("update".equals(action)){  // 來自update_pdc_input.jsp的請求
		
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
			try{
				/************ 1.接收請求參數 -輸入格式處理  ******************/				
				String sto_num = req.getParameter("sto_num");
				String com_num = req.getParameter("com_num");
				
				ProductService pdcSvc2 = new ProductService();		
				ProductVO productVOORG = pdcSvc2.getOneProduct(com_num);//把商品查出來
				
				String com_name = req.getParameter("com_name");
				System.out.println("update com_name:"+com_name);
				String com_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_\r\t\n\f-)]{2,20}$";
				if (com_name == null || com_name.trim().length() == 0){
					errorMsgs.put("com_name","商品名稱：請勿空白");
				} else if (!com_name.trim().matches(com_nameReg)) {
					errorMsgs.put("com_name","商品名稱：只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
				}				
				
				String price_nameReg = "^[0-9]{1,10}+$";
				Integer m_price = null;
				Integer l_price = null;				
				String m_price2 =req.getParameter("m_price");
				String l_price2 =req.getParameter("l_price");
				if (m_price2.trim().matches(price_nameReg)){
					try{
					m_price = new Integer(Integer.parseInt(m_price2));
					} catch(NumberFormatException e) {
						errorMsgs.put("em_price","小杯值錯誤");
					}
				}else{
					m_price = new Integer(0);
					errorMsgs.put("m_price","小杯價錢：請輸入1-10位數數字");
				}				
				if (l_price2.trim().matches(price_nameReg)){
					try{
						l_price = new Integer( Integer.parseInt(l_price2));
					} catch(NumberFormatException e) {
						errorMsgs.put("el_price","大杯值錯誤");
					}
				}else{
					l_price = new Integer(0);
					errorMsgs.put("l_price","大杯價錢：請輸入1-10位數數字");
				}							
				if( errorMsgs.containsKey("em_price") && errorMsgs.containsKey("el_price")){
					errorMsgs.remove("em_price");
					errorMsgs.remove("el_price");
					errorMsgs.put("price","價錢請勿都為空白");				
				}
				if(l_price==0 && m_price==0){
					errorMsgs.put("price","價錢請勿都為0");	
				}
				if(m_price>l_price && l_price!=0){
					errorMsgs.put("price","小杯價格比大杯貴喔O_O!");	
				}
				
				String discribe = req.getParameter("discribe");
				if (discribe == null || discribe.trim().length() == 0){
					discribe = "無商品敘述";
				}
				
				Part imgs= req.getPart("img");
				byte[] img = null;
				if(imgs.getSize()!=0){
					System.out.println("new img");
					img = getPictureByteArray(imgs); 
				} else {
					System.out.println("orgin img");
					img = productVOORG.getImg();
				}
				
				String pt_num = req.getParameter("pt_num");
				if ( pt_num == null || pt_num.trim().length() == 0 ){
					errorMsgs.put("pt_num","商品類別：請勿空白");
				}
				
				String status = req.getParameter("status");				
				
				String mercom_num = req.getParameter("mercom_num");
				
				System.out.println(errorMsgs.toString());
				
				ProductVO productVO = new ProductVO();
				productVO.setSto_num(sto_num);
				productVO.setCom_num(com_num);
				productVO.setCom_name(com_name);
				productVO.setM_price(m_price);
				productVO.setL_price(l_price);
				productVO.setDiscribe(discribe);
				productVO.setImg(img);
				productVO.setPt_num(pt_num);
				productVO.setStatus(status);
				productVO.setMercom_num(mercom_num);
				
				//send back if errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/store-end/pdc_mng/update_pdc_input.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/************ 2.開始修改資料   ****************************/
				ProductService pdcSvc = new ProductService();
				productVO = pdcSvc.updateProduct(productVO);
				
				/************ 3.加入完成,準備轉交(Send the Success view)**/	
				req.setAttribute("productVO", productVO); // 資料庫update成功後,正確的VO物件
				String url = "/store-end/pdc_mng/store_select_page.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
				
				/************ 其他錯誤處理  ******************************/
			} catch (Exception e){
				errorMsgs.put("修改資料失敗:",e.getMessage());				
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store-end/pdc_mng/update_pdc_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("insert_merge".equals(action)){
			if(se.getAttribute("addform")!="permit" ){
				String url = "/store-end/pdc_mng/store_select_page.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); //回去listAll頁面
				successView.forward(req, res);
				return;
			} 
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
			try {
				/************ 1.接收請求參數 -輸入格式處理  ******************/
				String sto_num = req.getParameter("sto_num");
				String com_name = req.getParameter("com_name");
				String com_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_\r\t\n\f)]{2,20}$";
				if (com_name == null || com_name.trim().length() == 0){
					errorMsgs.put("com_name","商品名稱：請勿空白");
				} else if (!com_name.trim().matches(com_nameReg)) {
					errorMsgs.put("com_name","商品名稱：只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
				}
				
				String price_nameReg = "^[0-9]{1,10}+$";
				Integer m_price = null;
				Integer l_price = null;				
				String m_price2 =req.getParameter("m_price");
				String l_price2 =req.getParameter("l_price");
				if (m_price2.trim().matches(price_nameReg)){
					try{
					m_price = new Integer(Integer.parseInt(m_price2));	
					} catch(NumberFormatException e) {
						errorMsgs.put("em_price","小杯值錯誤");
					}
				}else{
					m_price = new Integer(0);
					errorMsgs.put("m_price","小杯價錢：請輸入1-10位數數字");
				}				
				if (l_price2.trim().matches(price_nameReg)){
					try{
						l_price = new Integer( Integer.parseInt(l_price2));
					} catch(NumberFormatException e) {
						errorMsgs.put("el_price","大杯值錯誤");
					}
				}else{
					l_price = new Integer(0);
					errorMsgs.put("l_price","大杯價錢：請輸入1-10位數數字");
				}							
				if( errorMsgs.containsKey("em_price") && errorMsgs.containsKey("el_price")){
					errorMsgs.remove("em_price");
					errorMsgs.remove("el_price");
					errorMsgs.put("price","價錢請勿都為空白");				
				}
				if(l_price==0 && m_price==0){
					errorMsgs.put("price","價錢請勿都為0");	
				}
				if(m_price>l_price && l_price!=0){
					errorMsgs.put("price","小杯價格比大杯貴喔O_O!");	
				}
								
				String discribe = req.getParameter("discribe");
				if (discribe == null || discribe.trim().length() == 0){
					discribe = "無商品敘述";
				}
				
				Part imgs= req.getPart("img");
				byte[] img = getPictureByteArray(imgs); 
				
				String pt_num = req.getParameter("pt_num");
				if ( pt_num == null || pt_num.trim().length() == 0 ){
					errorMsgs.put("pt_num","商品類別：請勿空白");
				}				
				String status = req.getParameter("status");
				
				System.out.println(errorMsgs.toString());
				ProductVO productVO = new ProductVO();
				productVO.setSto_num(sto_num);
				productVO.setCom_name(com_name);
				productVO.setM_price(m_price);
				productVO.setL_price(l_price);
				productVO.setDiscribe(discribe);
				productVO.setImg(img);
				productVO.setPt_num(pt_num);
				productVO.setStatus(status);
				
				
				String[] checkbox = req.getParameterValues("checkbox");
				//轉List
				List<String> ckList = new ArrayList<String>();
				for(int i =0; i<checkbox.length;i++){
					ckList.add(checkbox[i]);
				}

				req.setAttribute("ckList",ckList);
				//send back if errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO);

					RequestDispatcher failureView = req
							.getRequestDispatcher("/store-end/pdc_mng/addMerge.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/************ 2.開始加入資料   ****************************/
				//新增商品
				ProductService pdcSvc = new ProductService();
				productVO = pdcSvc.addProduct(productVO);
				String com_num = productVO.getCom_num();//取到新增合併商品的商品編號

				//新增合併商品
				MergedCommodityService mcSvc = new MergedCommodityService();
				String mercom_num = mcSvc.addMergedCommodity(ckList);//取到合併商品編號
				productVO.setCom_num(com_num);
				productVO.setMercom_num(mercom_num);
			
				//新增合併商品編號到剛新增的商品
				pdcSvc.updateProduct(productVO);				
				
				/************ 3.加入完成,準備轉交(Send the Success view)**/	
				se.removeAttribute("addform");				//把通行證拿掉防止f5重送表單
				String url = "/store-end/pdc_mng/store_select_page.jsp?com_num="+com_num;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交stolistAllProduct.jsp
				successView.forward(req, res);
				
				/*********** 其他錯誤  ***************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/store-end/pdc_mng/addMerge.jsp");
				failureView.forward(req, res);
			}
			return;
		}
		
		if ("delete".equals(action)){		//來自allProduct or allProduct2 的請求
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
			String requestURL = req.getParameter("requestURL");
			System.out.println(requestURL);
			try{
				/************ 1.接收請求參數 -輸入格式處理  ******************/
				String com_num = req.getParameter("com_num");	
								
				/************ 2.開始修改資料   ****************************/
				ProductService pdcSvc = new ProductService();
				ProductVO productVO = pdcSvc.getOneProduct(com_num);
				productVO.setStatus("刪除");
				productVO = pdcSvc.updateProduct(productVO);
				
				/************ 3.修改完成，準備轉交   ***********************/
				req.setAttribute("productVOu", productVO);
				String url = requestURL; // 送出修改的來源網頁(listAllSweet)和修改的是哪一筆
				RequestDispatcher successView = req.getRequestDispatcher(url);   // 修改成功後,轉交回送出修改的來源網頁
				successView.forward(req, res);
				return;
				/************ 其他錯誤處理  ******************************/	
			} catch (Exception e){
				errorMsgs.put("刪除資料失敗:",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
			
		}
		
		if("getAllPdc".equals(action)){	
		System.out.println("---");
			String sto_num = req.getParameter("sto_num");
			ProductService pdcSvc = new ProductService();			
			List<ProductVO> getAllPdc = pdcSvc.stoFindAllProduct(sto_num);	
		System.out.println("---"+getAllPdc.size());
			req.setAttribute("getAllPdc", getAllPdc);			
			RequestDispatcher successView = req.getRequestDispatcher("/store-end/pdc_mng/store_select_page.jsp");
			successView.forward(req, res);
		}
		
		


			
	}
	
	public static byte[] getPictureByteArray(Part part) throws IOException {
		
		InputStream in =  part.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];	//自定緩衝區
		int i;
		while ((i = in.read(buffer)) != -1) {
			baos.write(buffer, 0, i); //(哪個陣列,開始索引值,緩衝區資料大小)
		}		
		in.close();
		return baos.toByteArray();
	}

}
