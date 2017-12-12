package com.android.store.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.member_profile.model.MemberProfileService;
import com.member_profile.model.MemberProfileVO;
import com.store_profile.model.StoreProfileService;
import com.store_profile.model.StoreProfileVO;


public class Store extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doGet(req,res);
	}
	
  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {

	  
	  String action = req.getParameter("action");
	  
	
  

      JSONObject jsonOut = new JSONObject();
      
      System.out.println("12333333");
      if("rank".equals(req.getParameter("storeSearchType"))){
    	  	JSONObject json = new JSONObject();
  	    	StoreProfileService stoProSer = new StoreProfileService();
  	    	
  	    	ServletContext context = getServletContext();
  	    	List<Map.Entry<String, Integer>> list_RankData;
  	    	list_RankData = (List<Map.Entry<String, Integer>>)context.getAttribute("list_RankData");
  	    	
  	    	
//  	    	List<StoreProfileVO> stoList = new ArrayList();
  		  
//  	    	System.out.println("keyyyyyyyyyyyyyyyyy"+list_RankData);
  	    	for(int i=0; i<list_RankData.size(); i++){
  	    		
  	    		Map.Entry<String, Integer> rankMap = list_RankData.get(i);
  	    		StoreProfileVO stoProVO = stoProSer.getOneStoInfo(rankMap.getKey());
  	    	
  	    		json = new JSONObject();
  	    		json.put("name", stoProVO.getSto_name());
  	    		json.put("storeID", stoProVO.getSto_num());
  	    		json.put("address", stoProVO.getArea() + stoProVO.getAddress());
  	    		json.put("phone", stoProVO.getMobile());
  	        
  	    		jsonOut.put("rank"+(i+1), json);
  	    		
  	    	}
  	    	
  	    	
//  	    	for(int i=0; i<stoList.size(); i++){
//  	    		StoreProfileVO stoProVO = stoList.get(i);
//  	    		json = new JSONObject();
//  	  	  
//  	    		json.put("name", stoProVO.getSto_name());
//  	    		json.put("storeID", stoProVO.getSto_num());
//  	    		json.put("address", stoProVO.getAddress());
//  	    		json.put("phone", stoProVO.getMobile());
//  	        
//  	    		jsonOut.put("rank"+(i+1), json);
//  		
//  	        
//  	    	}
      }
    	  
      
      
      
      
      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      res.getWriter().write(jsonOut.toString());
      

  }
  
 
}
