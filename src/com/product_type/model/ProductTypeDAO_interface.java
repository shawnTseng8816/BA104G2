package com.product_type.model;

import java.util.List;

import com.product.model.ProductVO;

public interface ProductTypeDAO_interface {
	
//-------KHAN------------------------------------------------
	public List<ProductTypeVO> getProductType(ProductVO productVO);
//-----end KHAN-----------------------------------------------
	
//-------Peiiun---------------------------------------------------
	public void insert(ProductTypeVO productTypeVO);
	public List<ProductTypeVO> getAll();
	public ProductTypeVO getOnePdcT(String pt_num);
//-------end Peiiun---------------------------------------------
	
//--------Shawn--------------------
	public ProductTypeVO getProductTypeName(String pt_num);
//-------end Shawn-------------------	
	
//--------Eric--------------------
	
//	public List<ProductTypeVO> getProductType(ProductVO productVO);

//------end Eric---------------	
}
