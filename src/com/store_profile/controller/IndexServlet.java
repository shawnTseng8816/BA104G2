package com.store_profile.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.store_profile.controller.Maptest.ValueComparator;
import com.store_profile.model.*;

@WebServlet("/IndexServlet.do")
public class IndexServlet extends HttpServlet/* implements Runnable*/{
	
	Map<String,Integer> keywordMap = new HashMap<String,Integer>() ;
	List<Map.Entry<String, Integer>> list_KeyData = new ArrayList<Map.Entry<String, Integer>>();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		HttpSession se = req.getSession();
		double curlat;
		double curlng;
		try{
			curlat = (double) se.getAttribute("lat");
			curlng = (double) se.getAttribute("lng");
		}catch(Exception e){
			curlat = 24.971342;
			curlng = 121.194806;
		}

		//地址JSON
		StoreProfileService spSvc = new StoreProfileService();
		List<StoreProfileVO> orgList = spSvc.getAllgeo();//查出上架狀態的店家
		TreeSet<StoreProfileVO> newList = new TreeSet<>();
		for(StoreProfileVO stoVO : orgList){			
			String addr = stoVO.getArea()+stoVO.getAddress();
			
			double[] latlng = Geoget(addr);//轉經緯度
			double lat = latlng[0];
			double lng = latlng[1];

			double distance = Disget(curlat,curlng,lat,lng);//算距離		
			
			stoVO.setAddress(addr);
			stoVO.setLat(lat);
			stoVO.setLng(lng);
			stoVO.setDistance(distance);
			newList.add(stoVO);	//spVO(sto_num,sto_name,area,addr(完整),lat,lng,distance)	
		}


			Gson gson = new Gson();
			String addrList =gson.toJson(newList);	
			
			res.setContentType("application/json ; charset=UTF-8");
		    PrintWriter out = res.getWriter();		    
		    out.println(addrList);
		    out.flush();
		    out.close();

	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//用搜尋關鍵字送

		req.setCharacterEncoding("UTF-8");
		String action =req.getParameter("action");
		
		List<StoreProfileVO> oldList = null;
		ServletContext context = getServletContext();		
		list_KeyData = (List<Map.Entry<String, Integer>>) context.getAttribute("list_KeyData");

		
		for(Entry<String, Integer> Key :list_KeyData){
			keywordMap.put(Key.getKey(), Key.getValue());
		}

		if("search".equals(action)){
			HttpSession se = req.getSession();
			double curlat;
			double curlng;
			try{
				curlat = (double) se.getAttribute("lat");
				curlng = (double) se.getAttribute("lng");
			}catch(Exception e){
				curlat = 24.971342;
				curlng = 121.194806;
			}
			String keywordorg= req.getParameter("keyword");
			try{
				String keyword = keywordorg;
				String str_reg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]+$";
				for(String retval: keyword.split(" ")){
		        	System.out.println("be:"+retval);
		        	if(retval.length()!=0 && retval.matches(str_reg)){
			            System.out.println("af:"+retval);
			            keyword=retval;
			            
						//把關鍵字存在map
						if(keywordMap.containsKey(keyword)){
							keywordMap.put(keyword, keywordMap.get(keyword)+1);
						}else{
							keywordMap.put(keyword, 1);
						}
			            break;
		        	}else{
		        		keyword="X";
		        	}
		        }
				
				StoreProfileService spSvc = new StoreProfileService();
	
				if (keyword.equals("X")){
					oldList = spSvc.getAllgeo();	
					System.out.println("不符合正則 list-size:"+oldList.size());
				}else{
					System.out.println();
					oldList = spSvc.getByKeyword(keyword);//查出有關鍵字且上架狀態的店家
					System.out.println(keyword+"有符合正則 list-size:"+oldList.size());
				}
				
			} catch (Exception e){	
				StoreProfileService spSvc = new StoreProfileService();	
				oldList = spSvc.getAllgeo();	
				
			} finally {

			TreeSet<StoreProfileVO> newList = new TreeSet<>();
			for(StoreProfileVO stoVO : oldList){			
				String addr = stoVO.getArea()+stoVO.getAddress();
				
				double[] latlng = Geoget(addr);//轉經緯度
				double lat = latlng[0];
				double lng = latlng[1];

				double distance = Disget(curlat,curlng,lat,lng);//算距離		
				//	System.out.println(distance);
				stoVO.setAddress(addr);
				stoVO.setLat(lat);
				stoVO.setLng(lng);
				stoVO.setDistance(distance);
				newList.add(stoVO);	//spVO(sto_num,sto_name,area,addr(完整),lat,lng,distance)	
			}	
			
			list_KeyData = new ArrayList<Map.Entry<String, Integer>>(keywordMap.entrySet());			
	    	Collections.sort(list_KeyData, new Comparator<Map.Entry<String, Integer>>(){
	            public int compare(Map.Entry<String, Integer> entry1,
	                               Map.Entry<String, Integer> entry2){
	                return (entry2.getValue() - entry1.getValue());
	            }
	        });
			
	    	context.setAttribute("list_KeyData", list_KeyData);  	    
			req.setAttribute("stoList", newList);
			req.setAttribute("keywordorg", keywordorg);
			
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/storeList.jsp"); 
			successView.forward(req, res);
			}
		}
		
		if("position".equals(action)){
			
			double lat = Double.parseDouble(req.getParameter("lat"));
			double lng = Double.parseDouble(req.getParameter("lng"));			
			HttpSession se = req.getSession();
			se.setAttribute("lat", lat);
			se.setAttribute("lng", lng);
			System.out.println("position:"+lat+","+lng);
		}
		
		
	}

	//用地址轉出經緯度
	private double[] Geoget(String addr){	
		double[] latlng= new double[2];
		URL url = null;		
		try {
			url = new URL("http://maps.googleapis.com/maps/api/geocode/xml?address="+java.net.URLEncoder.encode(addr,"UTF-8")+"&sensor=false&language=zh-TW"); // 建立URL物件url , 以 中文台北市(之地址換算經緯度為例)
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
		try {
			// 以URL物件建立URLConnection物件
			URLConnection urlConn = url.openConnection();
			// 以URLConnection物件取得輸入資料流
			InputStream ins = urlConn.getInputStream();	
			// 建立URL資料流
			BufferedReader br = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
			String data;
			while ((data = br.readLine()) != null) {
				if(data.contains("<location>")){
					data = br.readLine();
					if (data.contains("<lat>")) {
						latlng[0]=Double.parseDouble((data.substring(data.indexOf("<lat>") + 5, data.indexOf("</lat>"))));
					}
					data = br.readLine();
					if (data.contains("<lng>")) {
						latlng[1]=Double.parseDouble((data.substring(data.indexOf("<lng>") + 5, data.indexOf("</lng>"))));
					}
					break;
				}
			}	
			br.close();
			ins.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return latlng;		
	}
	
	//兩點距離轉換
	private double Disget(double curlat , double curlng , double lat , double lng ){
		double distance= 0;
		URL url = null;	
		try {
			url = new URL("http://maps.googleapis.com/maps/api/distancematrix/xml?units=imperial&origins="+curlat+","+curlng+"&destinations="+lat+","+lng+"&sensor=false&language=zh-TW");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}	
		try {
			// 以URL物件建立URLConnection物件
			URLConnection urlConn = url.openConnection();
			// 以URLConnection物件取得輸入資料流
			InputStream ins = urlConn.getInputStream();	
			// 建立URL資料流
			BufferedReader in = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
			String data;
			while ((data = in.readLine()) != null) {
				if(data.contains("<distance>")){
					data = in.readLine();
					if (data.contains("<value>")) {
						distance=Double.parseDouble((data.substring(data.indexOf("<value>") + 7, data.indexOf("</value>"))))/1000;
					}					
					break;
				}
			}			
		}catch (IOException e) {
			e.printStackTrace();
		}
		return distance;
	}


}
