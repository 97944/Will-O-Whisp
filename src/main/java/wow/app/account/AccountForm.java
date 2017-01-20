package wow.app.account;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AccountForm implements Serializable {
	
	@NotNull(groups={AccountOrder1.class},message = "必須です")
	@Size(min = 1, max = 20, message = "名前は{min}文字以上{max}文字以下です。")
	private String userName;
	
	@NotNull(groups={AccountOrder1.class},message = "必須です")
	@Size(min = 1, max = 15, message = "IDは{min}文字以上{max}文字以下です。")
	@Pattern(regexp="[a-zA-Z0-9]*",groups={AccountOrder2.class},message="IDは英数である必要があります。")
	private String userId;
	
	@NotNull(groups={AccountOrder1.class},message = "必須です")
	@Size(min = 4, max = 20, message = "パスワードは{min}文字以上{max}文字以下です。")
	@Pattern(regexp="[a-zA-Z0-9]*",groups={AccountOrder2.class},message="パスワードは英数である必要があります。")
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
