package com.product_type.model;

import java.util.List;

import com.product.model.ProductVO;

public class ProductTypeService {

	private ProductTypeDAO_interface dao;

	public ProductTypeService() {
		dao = new ProductTypeDAO();
	}

	
//---------KHAN-----------------------------------------------
	public List<ProductTypeVO> getProtypes(String sto_num) {
		
		ProductVO productVO = new ProductVO();
		
		productVO.setSto_num(sto_num);
		productVO.setStatus("已上架");

		return dao.getProductType(productVO);
	}
	
//--------end KHAN----------------------------------------------
	
	
//--------Peiiun-------------------------------------------------
	
	public ProductTypeVO add(ProductTypeVO productTypeVO){
		dao.insert(productTypeVO);
		return productTypeVO;
	}
	public List<ProductTypeVO> getAll(){
		return dao.getAll();
	}
	
	public ProductTypeVO getOnePdcT(String pt_num){
		return dao.getOnePdcT(pt_num);
	}
//-------end Peiiun---------------------------------------------
	
//------------Shawn----------------------	
	public ProductTypeVO getProductTypeName(String pt_num){
		return dao.getProductTypeName(pt_num);
	}
//------------Shawn--------------------
	
//----------Eric-------------
//public List<ProductTypeVO> getProtypes(String sto_num) {
//		
//		ProductVO productVO = new ProductVO();
//		
//		productVO.setSto_num(sto_num);
//		productVO.setStatus("已上架");
//
//		return dao.getProductType(productVO);
//	}
//--------end Eric-----------------

	
}
