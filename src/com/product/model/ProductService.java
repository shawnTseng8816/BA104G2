package com.product.model;

import java.util.List;

public class ProductService {

	private ProductDAO_interface dao;

	public ProductService() {
		dao = new ProductDAO();
	}

	
//-------------------KHAN-------------------------------------------------------------------
	public List<ProductVO> getProducts(String sto_num, String pt_num) {
		
		ProductVO productVO = new ProductVO();
		
		productVO.setSto_num(sto_num);
		productVO.setPt_num(pt_num);
		productVO.setStatus("已上架");
		
		return dao.getProducts(productVO);
	}
	
	public ProductVO getProductName(String com_num) {
		
		ProductVO productVO = new ProductVO();
		
		productVO.setCom_num(com_num);
		productVO.setStatus("已上架");
		
		return dao.getProductName(productVO);
	}
	
	
	public ProductVO addProduct(ProductVO productVO){
		String com_num = dao.insert(productVO);
		productVO.setCom_num(com_num);
		return productVO;	
	}
	
	public ProductVO updateProduct(ProductVO productVO){
		dao.update(productVO);
		return productVO;
	}
	
	public List<ProductVO> stoFindProductbyName(String com_name, String sto_num){
		return dao.stoFindbyProductName(com_name, sto_num);
	}
	
	public List<ProductVO> stoFindAllProduct(String sto_num){
		return dao.stoGetAll(sto_num);		
	}
	
	public ProductVO getOneProduct(String com_num){
		return dao.findByPrimaryKey(com_num);
	}
	
	public List<ProductVO> getAll(){
		return dao.getAll();
	}
//----------end KHAN-------------------------------------------
	
//---------Shawn----------------------------------
	
	public List<ProductVO> getProductBySto_num(String sto_num) {
		return dao.getProductBySto_num(sto_num);
	}
	
	public ProductVO getProductImage(String sto_num, String com_num){
		return dao.getProductImage(sto_num, com_num);
	}
	
	public List<ProductVO> getProductBySto_numStatus(String sto_num, String status){
		return dao.getProductBySto_numStatus(sto_num,status);
	}
	
//-------end Shawn--------------------------------	
	
//--------Eric----------------------
//public List<ProductVO> getProducts(String sto_num, String pt_num) {
//		
//		ProductVO productVO = new ProductVO();
//		
//		productVO.setSto_num(sto_num);
//		productVO.setPt_num(pt_num);
//		productVO.setStatus("已上架");
//		
//		return dao.getProducts(productVO);
//	}
//	
//	public ProductVO getProductName(String com_num) {
//		
//		ProductVO productVO = new ProductVO();
//		
//		productVO.setCom_num(com_num);
//		productVO.setStatus("已上架");
//		
//		return dao.getProductName(productVO);
//	}
	
//--------end Eric---------------------------	
	
}
