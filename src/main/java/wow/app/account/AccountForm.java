package wow.app.account;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AccountForm implements Serializable {
	@Size(min = 1, max = 20)
	@NotNull(message = "必須です")
	private String userName;
	
	@Size(min = 1, max = 15)
	@NotNull(message = "必須です")
	private String userId;
	
	@Size(min = 4, max = 20)
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
