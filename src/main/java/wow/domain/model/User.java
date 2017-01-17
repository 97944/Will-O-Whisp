package wow.domain.model;

import java.io.File;
import java.io.Serializable;

import javax.persistence.*;

@Entity(name="user_master")
public class User implements Serializable{
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    private String userId;
    private String userName;
    private String password;
    private String profile;
    private File topPicture;
    private File headPicture;
    private int lock;
    
    @Enumerated(EnumType.STRING)
    private RoleName roleName;
    
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
    
    public String getProfile(){
    	return profile;
    }
    
    public void setProfile(String profile){
    	this.profile = profile;
    }
    
    public File getTopPicture(){
    	return topPicture;
    }
    
    public void setTopPicture(File topPicture){
    	this.topPicture = topPicture;
    }
    
    public File getHeadPicture(){
    	return headPicture;
    }
    
    public void setHeadPicture(File headPicture){
    	this.headPicture = headPicture;
    }
    
    public int getLock(){
    	return lock;
    }
    
    public void setLock(int lock){
    	this.lock = lock;
    }
    
    public RoleName getRoleName(){
    	return roleName;
    }
    
    public void setRoleName(RoleName roleName){
    	this.roleName = roleName;
    }
}
