package com.product.model;

import java.util.List;

public interface ProductDAO_interface {

//-----------KAHN--------------	
	public List<ProductVO> getProducts(ProductVO productVO);
	
	public ProductVO getProductName(ProductVO productVO);
//-----------end KAHN--------------	
	
//----------Peiiun----------	
	public String insert(ProductVO productVO);
	public void update(ProductVO productVO);
	public List<ProductVO> getAll();
	public ProductVO findByPrimaryKey(String com_num);
	public List<ProductVO> stoFindbyProductName(String com_name , String sto_num);
	public List<ProductVO> stoGetAll(String sto_num);
//----------end Peiiun----------	
	
	
//------------------------Shawn------------
public List<ProductVO> getProductBySto_num(String sto_num);
	
	public ProductVO getProductImage(String sto_num,String com_num);
	
	public List<ProductVO> getProductBySto_numStatus(String sto_num, String status);
		
	
//---------------end Shawn-------------------	
	
//-------------Eric-----------------
//public List<ProductVO> getProducts(ProductVO productVO);
//	
//	public ProductVO getProductName(ProductVO productVO);
	
//-------end Eric-------------------	
	
}
