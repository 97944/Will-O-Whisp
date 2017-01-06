package wow.app.profile;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class ProfileForm implements Serializable {
	@NotNull(message = "必須です")
	private String userName;

	@NotNull(message = "必須です")
	private String userId;
	
	@NotNull(message = "必須です")
	private String password;
	
	 public String getUserId() {
        return userId;
    }
 
    public void setUserId(String userId) {
        this.userId = userId;
    }
  
    public String getUserName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassword(){
    	return password;
    }
    
    public void setPassword(String password){
    	this.password = password;
    }
}
