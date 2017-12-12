package account;

import java.util.List;



public interface AccDAOInterface {
	public void addvalue(AccVO accVO, int cash);
	public void remBack(AccVO accVO);
	public void remvalue(AccVO accVO, int remcash,int rem_acc);
	public int getpoint(String acc);
	

}
