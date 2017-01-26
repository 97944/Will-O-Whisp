package wow.domain.model;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import wow.domain.model.converter.LocalDateTimeConverter;
 
@Entity(name="reply_record")
public class Reply implements Serializable{
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    private String replyId;
    private String userId;
    private String detail;
    @Column(nullable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime time;
    private String media;
    private String mediaUrl;
    private int relation;
    
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @MapsId("userId")
    private User user;
    
    public String getReplyId() {
        return replyId;
    }
 
    public void setReplyId(String replyId) {
        this.replyId = replyId;
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
    
    public String getMedia(){
    	return media;
    }
    
    public void setMedia(String media){
    	this.media = media;
    }
    
    public String getMediaUrl(){
    	return mediaUrl;
    }
    
    public void setMediaUrl(String mediaUrl){
    	this.mediaUrl = mediaUrl;
    }
    
    public int getRelation(){
    	return relation;
    }
    
    public void setRelation(int relation){
    	this.relation = relation;
    }
  
    public User getUser(){
    	return user;
    }
    
    public void setUser(User user){
    	this.user = user;
    }
    
}
