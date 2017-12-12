package account;

public class AccService {

	private AccDAOInterface dao;

	public AccService() {
		dao = new AccDAO();
	}

	public AccVO addValue(String account, int cash , int point) {
		AccVO accVO = new AccVO();

		accVO.setAccount(account);
		accVO.setPoint(point);
		dao.addvalue(accVO, cash);
		return accVO;
	}
	
	

	public AccVO remBack(String account, int point) {
		AccVO accVO = new AccVO();

		accVO.setAccount(account);
		accVO.setPoint(point);
		dao.remBack(accVO);
		return accVO;
	}
	
	
	public AccVO remValue(String account, int remcash,int rem_acc,int point) {
		AccVO accVO = new AccVO();

		accVO.setAccount(account);
		accVO.setPoint(point);
		dao.remvalue(accVO, remcash, rem_acc);
		return accVO;
	}
	
	
	public int getPoint(String acc) {
		return dao.getpoint(acc);
	}

}
