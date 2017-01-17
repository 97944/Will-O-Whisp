package wow.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
 
@Entity(name="favorite_record")
public class Favorite {
 
    @Id
    private String favoriteId;
    private String userId;
    private String favoriteTweetId;
   
 
    public String getFavoriteId() {
        return favoriteId;
    }
 
    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
    }
  
    public String getUserId() {
        return userId;
    }
 
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getFavoriteTweetId(){
    	return favoriteTweetId;
    }
    
    public void setFavoriteTweetId(String favoriteTweetId){
    	this.favoriteTweetId = favoriteTweetId;
    }
    
}