package com.ad_block.model;

import java.util.*;

public interface AdBlockDAO_interface {
          public AdBlockVO findByPrimaryKey(String ab_no);
          public List<AdBlockVO> getAll();
          //萬用複合查詢(傳入參數型態Map)(回傳 List)
          //public List<AdBlockVO> getAll(Map<String, String[]> map); 
}