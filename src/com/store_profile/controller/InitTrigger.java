package com.store_profile.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store_comment.model.StoreCommentService;


public class InitTrigger extends HttpServlet {


	Map<String,Integer> keywordMap = new HashMap<String,Integer>();
	List<Map.Entry<String, Integer>> list_KeyData;
	List<Map.Entry<String, Integer>> list_RankData;
	Timer timer;

  public void doGet(HttpServletRequest req, HttpServletResponse res) 
                               throws ServletException, IOException {                          
  }
  
  public void init() throws ServletException {
	  
	  System.out.println("init Start");
	  FileReader in ;
	  BufferedReader br;
	  String saveDirectory = "/front-end/data";
	  String realPath = getServletContext().getRealPath(saveDirectory);
	    	try {
				in = new FileReader(realPath+"/key.txt");
				System.out.println(realPath);
				br = new BufferedReader(in);
				String str = "";
				while((str = br.readLine())!=null){
					System.out.println(str);
					String[] stuInfo = str.split(",");
					keywordMap.put(stuInfo[0],Integer.parseInt(stuInfo[1]));
				}
				br.close();
				in.close();
				
				list_KeyData = new ArrayList<Map.Entry<String, Integer>>(keywordMap.entrySet());
		    	Collections.sort(list_KeyData, new Comparator<Map.Entry<String, Integer>>(){
		            public int compare(Map.Entry<String, Integer> entry1,
		                               Map.Entry<String, Integer> entry2){
		                return (entry2.getValue() - entry1.getValue());
		            }
		        });
				
			} catch (FileNotFoundException e ) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
	    	
	    	ServletContext context = getServletContext();
		    context.setAttribute("list_KeyData", list_KeyData);   

		    
		    // schedule to get star /////////////////////////////////////////
		    TimerTask task = new TimerTask(){ 
		        public void run() {
		        	
		        	 // Store get Stars /////////////////////////////////////
		        	StoreCommentService scSvc = new StoreCommentService();
				    Map<String, Integer> rankList = scSvc.getStoreStars();
				    
				    list_RankData = new ArrayList<Map.Entry<String, Integer>>(rankList.entrySet());
			    	Collections.sort(list_RankData, new Comparator<Map.Entry<String, Integer>>(){
			            public int compare(Map.Entry<String, Integer> entry1,
			                               Map.Entry<String, Integer> entry2){
			                return (entry2.getValue() - entry1.getValue());
			            }
			        });
			    	System.out.println(list_RankData);
				    context.setAttribute("list_RankData", list_RankData); 
		        	
		        } 
		    };
			timer = new Timer(); 
			Calendar calendar = Calendar.getInstance();
			timer.schedule(task, calendar.getTime(), 1*5*60*1000);	//5分鐘算一次
			System.out.println("已建立排程!");       
	

  }
  
	public void destroy() {
		///////   結束排成器   //////////
		timer.cancel();
		System.out.println("已結束評價排程!");
		  
		///////   儲存關鍵字   //////////
    	ServletContext context = getServletContext();
    	list_KeyData = (List<Entry<String, Integer>>) context.getAttribute("list_KeyData");   

		try {
			saveKeyword();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
  
	//保存關鍵字
	public void saveKeyword() throws IOException{
		FileWriter out = null;
		BufferedWriter br = null;
		String saveDirectory = "/front-end/data";
		String realPath = getServletContext().getRealPath(saveDirectory);
		System.out.println(realPath);
		File fsaveDirectory = new File(realPath);
		
		if (!fsaveDirectory.exists())
			 fsaveDirectory.mkdirs(); // 於 ContextPath 之下,自動建立目地目錄
		try {
			out = new FileWriter( realPath +"/key.txt");
			br = new BufferedWriter(out);
			for (Entry<String, Integer> Key :list_KeyData) {
				br.write(Key.getKey()+","+Key.getValue());
				br.newLine();
				System.out.println(Key.getKey()+","+ Key.getValue());
			}	
			br.flush();
			br.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		System.out.println("KEYWORD SAVED");	 
	}
  

}
