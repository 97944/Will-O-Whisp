package wow.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
 
@Entity(name="block_record")
public class Block {
 
    @Id
    private String blockId;
    private String userId;
    private String blockUserId;
   
 
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
    
    public String getBlockUserId(){
    	return blockUserId;
    }
    
    public void setBlockUserId(String blockUserId){
    	this.blockUserId = blockUserId;
    }
    
}