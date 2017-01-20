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
    private String topPicture;
    private String headPicture;
    private String topPictureUrl;
    private String headPictureUrl;
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
    
    public String getTopPicture(){
    	return topPicture;
    }
    
    public void setTopPicture(String topPicture){
    	this.topPicture = topPicture;
    }
    
    public String getHeadPicture(){
    	return headPicture;
    }
    
    public void setHeadPicture(String headPicture){
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
    
    public String getTopPictureUrl(){
    	return topPictureUrl;
    }
    
    public void setTopPictureUrl(String topPictureUrl){
    	this.topPictureUrl = topPictureUrl;
    }
    
    public String getHeadPictureUrl(){
    	return headPictureUrl;
    }
    
    public void setHeadPictureUrl(String headPictureUrl){
    	this.headPictureUrl = headPictureUrl;
    }
}
