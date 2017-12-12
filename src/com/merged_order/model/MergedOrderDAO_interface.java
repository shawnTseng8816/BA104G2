package com.merged_order.model;

public interface MergedOrderDAO_interface {
	
	public String insert(MergedOrderVO mergedOrderVO);
	
	public void update(MergedOrderVO mergedOrderVO);

	public MergedOrderVO getMergedOrder(MergedOrderVO mergedOrderVO);
	
//---------Eric--------------
	public void insertEric(MergedOrderVO mergedOrderVO);

//	public MergedOrderVO getMergedOrder(MergedOrderVO mergedOrderVO);
	
//--------end Eric-------------	
}
