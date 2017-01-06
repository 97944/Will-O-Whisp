package wow.app.tweet;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class TweetForm implements Serializable {
	@NotNull(message = "必須です")
	private String detail;
	
	public String getDetail(){
    	return detail;
    }
    
    public void setDetail(String detail){
    	this.detail = detail;
    }
    
}
	
	