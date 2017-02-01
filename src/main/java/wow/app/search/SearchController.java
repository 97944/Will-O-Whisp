package wow.app.search;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import wow.domain.service.tweet.TweetService;
import wow.domain.service.user.UserService;
import wow.domain.service.user.WowUserDetails;
import wow.app.profile.Profile;
import wow.app.tweet.TimeLine;
import wow.app.tweet.TimeLineComparator;
import wow.domain.model.Tweet;
import wow.domain.model.User;
 
@Controller
@RequestMapping("search")
public class SearchController {
 
    @Autowired
    TweetService tweetService;
    @Autowired
    UserService userService;
 
    @RequestMapping(method = RequestMethod.GET)
    String search(@Param("text") String text,@Param("more") int more,
    		@AuthenticationPrincipal WowUserDetails userDetails,Model model){
    	// アクセスしてきたユーザー情報を取得
    	User user = userDetails.getUser();
    	model.addAttribute("login_user",user);
    	
    	// text内容が含まれるユーザーを検索する
    	/*List<User> searchUser = userService.searchByUserId(text);
    	model.addAttribute("search_user",searchUser);*/
    	
    	List<User> searchUser = userService.searchUser(text,text);
    	List<Profile> trueSearchUser = new ArrayList<Profile>();
    	for(int i=0;i<searchUser.size();i++){
    		int check = 0;
    		if(!(user.getUserId().equals(searchUser.get(i).getUserId()))){
	    		if(userService.checkFollow(user.getUserId(),searchUser.get(i).getUserId()) != null){
	    			check = 1;
	    		}else{
	    			if(userService.checkRequest(userDetails.getUser().getUserId(), searchUser.get(i).getUserId()) != null){
	    				check = 3;
	    			}else{
	    				check = 2;
	    			}
	    		}
    		}
    		System.out.println("ユーザー名" + searchUser.get(i).getUserName() + ":" + check);
    		Profile profile = new Profile();
    		profile.setUserId(searchUser.get(i).getUserId());
    		profile.setUserName(searchUser.get(i).getUserName());
    		profile.setPassword(searchUser.get(i).getPassword());
    		profile.setProfile(searchUser.get(i).getProfile());
    		profile.setTopPicture(searchUser.get(i).getTopPicture());
    		profile.setTopPictureUrl(searchUser.get(i).getTopPictureUrl());
    		profile.setHeadPicture(searchUser.get(i).getHeadPicture());
    		profile.setHeadPictureUrl(searchUser.get(i).getHeadPictureUrl());
    		profile.setLock(searchUser.get(i).getLock());
    		profile.setRoleName(searchUser.get(i).getRoleName());
    		profile.setCheck(check);
    		
    		trueSearchUser.add(profile);
    	}
    	List<Profile> su = new ArrayList<Profile>();
		if(trueSearchUser.size() > more*12){
	    	for(int i=0;i<more*12;i++){
	    		su.add(trueSearchUser.get(i));
	    	}
	    	model.addAttribute("more",more);
	    	model.addAttribute("search_user",su);
		}else{
			model.addAttribute("more",0);
			model.addAttribute("search_user",trueSearchUser);
		}
    	
    	if(searchUser.size() == 0){
    		model.addAttribute("user_none",true);
    		System.out.println("ユーザーいないよ");
    	}else{
    		model.addAttribute("user_none",false);
    	}
    	
    	
    	// text内容が含まれるツイートを検索する
    	List<Tweet> searchTweet = tweetService.searchTweet(text);
    	List<TimeLine> trueSearchTweet = new ArrayList<TimeLine>();
		for(int i=0;i<searchTweet.size();i++){
			int countRetweet = 0;
			countRetweet = tweetService.countRetweet(searchTweet.get(i).getTweetId());
			if(tweetService.searchRetweet(searchTweet.get(i).getRetweetId()) != null){
				countRetweet = tweetService.countRetweet(searchTweet.get(i).getRetweetId());
			}
			
			int countFavorite = 0;
			countFavorite = tweetService.countFavorite(searchTweet.get(i).getTweetId());
			
			boolean checkRetweet = false;
			if(tweetService.checkRetweet(userDetails.getUser().getUserId(), searchTweet.get(i).getTweetId()) != null){
				checkRetweet = true;
			}
			
			boolean checkFavorite = false;
			if(tweetService.checkFavorite(userDetails.getUser().getUserId(), searchTweet.get(i).getTweetId()) != null){
				checkFavorite = true;
			}
			
			TimeLine dummy = new TimeLine();
			dummy.setTweetId(searchTweet.get(i).getTweetId());
			dummy.setUserId(searchTweet.get(i).getUserId());
			dummy.setDetail(searchTweet.get(i).getDetail());
			dummy.setTime(searchTweet.get(i).getTime());
			dummy.setMedia(searchTweet.get(i).getMedia());
			dummy.setMediaUrl(searchTweet.get(i).getMediaUrl());
			dummy.setRelation(searchTweet.get(i).getRelation());
			dummy.setReplyId(searchTweet.get(i).getReplyId());
			dummy.setRetweetId(searchTweet.get(i).getRetweetId());
			dummy.setUser(searchTweet.get(i).getUser());
			dummy.setRetweet(searchTweet.get(i).getRetweet());
			dummy.setReply(searchTweet.get(i).getReply());
			dummy.setCountRetweet(countRetweet);
			dummy.setCountFavorite(countFavorite);
			dummy.setCheckRetweet(checkRetweet);
			dummy.setCheckFavorite(checkFavorite);
			
			trueSearchTweet.add(dummy);
		}
		List<TimeLine> st = new ArrayList<TimeLine>();
		if(trueSearchTweet.size() > more*10){
	    	for(int i=0;i<more*10;i++){
	    		st.add(trueSearchTweet.get(i));
	    	}
	    	model.addAttribute("more",more);
	    	Collections.sort(st, new TimeLineComparator());
	    	model.addAttribute("search_tweet",st);
		}else{
			model.addAttribute("more",0);
			Collections.sort(st, new TimeLineComparator());
			model.addAttribute("search_tweet",trueSearchTweet);
		}
		
    	if(searchTweet.size() == 0){
    		model.addAttribute("tweet_none",true);
    		System.out.println("ツイートないよ");
    	}else{
    		model.addAttribute("tweet_none",false);
    	}
    	
    	
    	// 検索文字列をセット
    	model.addAttribute("search_text",text);
    	
    	return "search/search";
    }
    @RequestMapping(method = RequestMethod.POST)
    String dispProfile(@RequestParam("user_id") String userId,Model model,RedirectAttributes attributes){
    	User user = userService.loadUserByUserId(userId);
    	attributes.addAttribute("userId",userId);
    	model.addAttribute("user",user);
    	return "redirect:/profile";
    }
}
