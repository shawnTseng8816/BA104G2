package com.merged_commodity.model;

import java.util.List;

public interface MergedCommodityDAO_interface {
	public void insert(MergedCommodityVO mergedCommodityVO);
	
	
	public String insert(List<String> list);
	public void delete(String mercom_num);
	List<MergedCommodityVO> getMerList(String mercom_num);
	
//----------Eric----------	
//	public void insert(MergedCommodityVO mergedCommodityVO);
//--------end Eric-------------	
}
