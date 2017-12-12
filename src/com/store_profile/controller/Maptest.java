package com.store_profile.controller;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;

import com.google.gson.Gson;

public class Maptest {

	Map<String,Integer> mapp = new HashMap<String,Integer>();
	
    public void savekey(String key){		
			if(mapp.containsKey(key)){				//如果table已經有相同的key
				mapp.put(key, mapp.get(key)+1);		//把value找出來+1再放入table
			}else{
				mapp.put(key, 1);					//新的key把value設為1放入table
			}
			System.out.println("unsorted map: " + mapp);
	}
    
   
    public void dis2(){
    	
    	List<Map.Entry<String, Integer>> list_Data =
                new ArrayList<Map.Entry<String, Integer>>(mapp.entrySet());
    	Collections.sort(list_Data, new Comparator<Map.Entry<String, Integer>>(){
            public int compare(Map.Entry<String, Integer> entry1,
                               Map.Entry<String, Integer> entry2){
                return (entry2.getValue() - entry1.getValue());
            }
        });
        for (Map.Entry<String, Integer> entry:list_Data) {
           System.out.println(entry.getKey()+","+entry.getValue());
        }
        System.out.println(" map: " + mapp);
        System.out.println(" map: " + list_Data);
        
        Map<String,Integer> mapp2 = new HashMap<String,Integer>();
        
        for(Entry<String, Integer> key : list_Data){
        	mapp2.put(key.getKey(), key.getValue());
        }
    	
        mapp2.put("餘", 15);
        System.out.println(" map2222: " + mapp2);
        
    }
    

    
	
	public void describe(){
	    ValueComparator bvc = new ValueComparator(mapp);
	    Map<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);
System.out.println("unsorted map: " + mapp);
	    sorted_map.putAll(mapp);
System.out.println("sorted map: " + sorted_map);
	    mapp.clear();
System.out.println("sorted map: " + sorted_map);
	    for(String key :sorted_map.keySet()){
	    	mapp.put(key, sorted_map.get(key));
	    }
System.out.println("???: " );	    
	    System.out.println("sorted map: " + mapp);
	}
	
	public class ValueComparator implements Comparator<String> {
	    Map<String, Integer> base;
	    public ValueComparator(Map<String, Integer> base) {
	        this.base = base;
	    }
	    public int compare(String a, String b) {
	        if (base.get(a) >= base.get(b)) {
	            return -1;
	        } else {
	            return 1;
	        }
	    }
	}
	
	public static void main(String[] args) {
	    
		Maptest mt = new Maptest();
		
		mt.savekey("茶");
		mt.savekey("紅茶");
		mt.savekey("綠茶");
		mt.savekey("茶");
		mt.savekey("茶");
		mt.savekey("清新");
		mt.savekey("奶茶");
		mt.savekey("奶茶");
		mt.savekey("50嵐");

		mt.savekey("茶湯");
		mt.savekey("茶");
		mt.savekey("奶茶");
//		
	
//		mt.savtoFile();
//		try {
//			mt.save();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		Hashtable<String,String> mapp2 = new Hashtable<String,String>();
//		mt.getfromfile();
//		
		
//		Map<String,Integer> mp= mt.laod();
//		System.out.println(mp.size());
//		for(String key : mp.keySet()){
//			
//			System.out.println(key+"--");
//		}
//		
//		mt.describe();
		mt.dis2();
		

	}
	


}
