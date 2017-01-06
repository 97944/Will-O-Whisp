package wow.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
 
@Entity(name="block_record")
public class Block {
 
    @Id
    private String blockId;
    private String userId;
    private String blockUser;
   
 
    public String getBlockId() {
        return blockId;
    }
 
    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }
  
    public String getUserId() {
        return userId;
    }
 
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getBlockUser(){
    	return blockUser;
    }
    
    public void setBlockUser(String blockUser){
    	this.blockUser = blockUser;
    }
    
}