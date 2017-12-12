package com.shop_ad.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;

public class ShopAdService {

	private ShopAdDAO_interface dao;

	public ShopAdService() {
		dao = new ShopAdDAO();
	}
	
	public ShopAdVO addShopAd(String sto_num,    String sa_title,   String sa_text, 
							  byte[] sa_img,        Integer sa_views,  Date sa_apptime,
							  Date sa_addtime,       Date sa_preofftime,   String ab_no,
							  String sa_addmode) throws IOException {

		ShopAdVO shopadVO = new ShopAdVO();
		
		shopadVO.setSto_num(sto_num);
		shopadVO.setSa_title(sa_title);
		shopadVO.setSa_text(sa_text);
		shopadVO.setSa_img(sa_img);
//		if (getFileNameFromPart(sa_img) != null) {
//		shopadVO.setSa_img(getPictureByteArray(sa_img.getInputStream()));
//	} else {
//		shopadVO.setSa_img(null);
//	}
		shopadVO.setSa_views(sa_views);
		shopadVO.setSa_apptime(sa_apptime);
		shopadVO.setSa_addtime(sa_addtime);
		shopadVO.setSa_preofftime(sa_preofftime);
		shopadVO.setAb_no(ab_no);
		shopadVO.setSa_addmode(sa_addmode);
		
		dao.insert(shopadVO);
		return shopadVO;
	}
	
	public ShopAdVO updateShopAd(String sa_no, String sa_title, String sa_text, byte[] sa_img,
			Date sa_addtime, Date sa_preofftime, String ab_no) {

		ShopAdVO shopadVO = new ShopAdVO();

		shopadVO.setSa_no(sa_no);
		shopadVO.setSa_title(sa_title);
		shopadVO.setSa_text(sa_text);
		shopadVO.setSa_img(sa_img);
		shopadVO.setSa_addtime(sa_addtime);
		shopadVO.setSa_preofftime(sa_preofftime);
		shopadVO.setAb_no(ab_no);
		

		dao.update(shopadVO);

		return shopadVO;
	}
	
	public void updateShopAd(ShopAdVO shopadVO){
		dao.update(shopadVO);
	}
	
	
	public static byte[] getPictureByteArray(InputStream in) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int i;
		while ((i = in.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		in.close();

		return baos.toByteArray();
	}

	public List<ShopAdVO> findBySa_no(String sa_no) {
		return dao.findBySa_no(sa_no);
	}
	
	public ShopAdVO getOneFuncList(String sa_no) {
		return dao.findByPrimaryKey(sa_no);
	}

	public List<ShopAdVO> getAllBySto_num(String sto_num) {
		return dao.getAllBySto_num(sto_num);
	}
	
	public List<ShopAdVO> getAllBySa_addmode(String sa_addmode) {
		return dao.getAllBySa_addmode(sa_addmode);
	}
	
	public List<ShopAdVO> getAll() {
		return dao.getAll();
	}
	
	public List<ShopAdVO> getAllExceptEdit() {
		return dao.getAllExceptEdit();
	}
}
