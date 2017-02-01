package wow.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
 
@Entity(name="request_record")
public class Request {
 
    @Id
    private String requestId;
    private String userId;
    private String requestUserId;
   
 
    public String getRequestId() {
        return requestId;
    }
 
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
  
    public String getUserId() {
        return userId;
    }
 
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getRequestUserId(){
    	return requestUserId;
    }
    
    public void setRequestUserId(String requestUserId){
    	this.requestUserId = requestUserId;
    }
    
}