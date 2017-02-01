package wow.app.profile;

import wow.domain.model.User;

public class Profile extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int check;
	
	public int getCheck(){
		return check;
	}
	
	public void setCheck(int check){
		this.check = check;
	}

}
