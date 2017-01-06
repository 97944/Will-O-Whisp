package wow.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
 
@Entity(name="follow_record")
public class Follow {
 
    @Id
    private String followId;
    private String userId;
    private String followUserId;
   
 
    public String getFollowId() {
        return followId;
    }
 
    public void setFollowId(String followId) {
        this.followId = followId;
    }
  
    public String getUserId() {
        return userId;
    }
 
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getFollowUserId(){
    	return followUserId;
    }
    
    public void setFollowUserId(String followUserId){
    	this.followUserId = followUserId;
    }
    
}