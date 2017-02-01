package wow.app.tweet;

import wow.domain.model.Tweet;

public class TimeLine extends Tweet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int countRetweet;
	private int countFavorite;
	private boolean checkRetweet;
	private boolean checkFavorite;
	
	public int getCountRetweet(){
		return countRetweet;
	}
	
	public void setCountRetweet(int countRetweet){
		this.countRetweet = countRetweet;
	}
	
	public int getCountFavorite(){
		return countFavorite;
	}
	
	public void setCountFavorite(int countFavorite){
		this.countFavorite = countFavorite;
	}
	
	public boolean getCheckRetweet(){
		return checkRetweet;
	}
	
	public void setCheckRetweet(boolean checkRetweet){
		this.checkRetweet = checkRetweet;
	}
	
	public boolean getCheckFavorite(){
		return checkFavorite;
	}
	
	public void setCheckFavorite(boolean checkFavorite){
		this.checkFavorite = checkFavorite;
	}
}
