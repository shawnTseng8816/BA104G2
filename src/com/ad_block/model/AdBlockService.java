package com.ad_block.model;

import java.util.List;

public class AdBlockService {

	private AdBlockDAO_interface dao;

	public AdBlockService() {
		dao = new AdBlockDAO();
	}

	public AdBlockVO getOneAdBlock(String ab_no) {
		return dao.findByPrimaryKey(ab_no);
	}

	public List<AdBlockVO> getAll() {
		return dao.getAll();
	}
}
