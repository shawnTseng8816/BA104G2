package com.merged_order.model;

public class MergedOrderService {

	private MergedOrderDAO_interface dao;

	public MergedOrderService() {
		dao = new MergedOrderDAO();
	}

	public String addMergedOrder(String mem_num) {

		MergedOrderVO mergedOrderVO = new MergedOrderVO();

		mergedOrderVO.setMem_num(mem_num);
		mergedOrderVO.setTol_amount(0);

		return dao.insert(mergedOrderVO);
	}
	
	public void updateMergedOrder(String meror_num, String mem_num, Integer tol_amount) {

		MergedOrderVO mergedOrderVO = new MergedOrderVO();

		mergedOrderVO.setMem_num(mem_num);
		mergedOrderVO.setMeror_num(meror_num);
		mergedOrderVO.setTol_amount(tol_amount);

		dao.update(mergedOrderVO);
	}

	public MergedOrderVO getMergedOrder(String meror_num) {

		MergedOrderVO mergedOrderVO = new MergedOrderVO();

		mergedOrderVO.setMeror_num(meror_num);

		return dao.getMergedOrder(mergedOrderVO);
	}
	
	
//-----------Eric------------
	public MergedOrderVO addMergedOrder(String mem_num, Integer tol_amount) {

		MergedOrderVO mergedOrderVO = new MergedOrderVO();

		mergedOrderVO.setMem_num(mem_num);
		mergedOrderVO.setTol_amount(tol_amount);

		dao.insertEric(mergedOrderVO);

		return mergedOrderVO;
	}

//	public MergedOrderVO getMergedOrder(String meror_num) {
//
//		MergedOrderVO mergedOrderVO = new MergedOrderVO();
//
//		mergedOrderVO.setMeror_num(meror_num);
//
//		return dao.getMergedOrder(mergedOrderVO);
//	}
	
//--------------end Eric---------------------	
}
