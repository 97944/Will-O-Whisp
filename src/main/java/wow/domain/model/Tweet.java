package wow.domain.model;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

import wow.domain.model.converter.LocalDateTimeConverter;
 
@Entity(name="tweet_record")
public class Tweet implements Serializable{
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    private String tweetId;
    private String userId;
    private String detail;
    @Column(nullable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime time;
    private File media;
    private int relation;
    private String relationId;
    private String retweetId;
 
    public String getTweetId() {
        return tweetId;
    }
 
    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }
  
    public String getUserId() {
        return userId;
    }
 
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getDetail(){
    	return detail;
    }
    
    public void setDetail(String detail){
    	this.detail = detail;
    }
    
    public LocalDateTime getTime(){
    	return time;
    }
    
    public void setTime(LocalDateTime time){
    	this.time = time;
    }
    
    public File getMedia(){
    	return media;
    }
    
    public void setMedia(File media){
    	this.media = media;
    }
    
    public int getRelation(){
    	return relation;
    }
    
    public void setRelation(int relation){
    	this.relation = relation;
    }
    
    public String getRelationId(){
    	return relationId;
    }
    
    public void setRelationId(String relationId){
    	this.relationId = relationId;
    }
    
    public String getRetweetId(){
    	return retweetId;
    }
    
    public void setRetweetId(String retweetId){
    	this.retweetId = retweetId;
    }
}
