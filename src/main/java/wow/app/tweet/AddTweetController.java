package wow.app.tweet;
 
import java.time.LocalDateTime;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import wow.domain.service.tweet.TweetService;
import wow.domain.service.user.UserService;
import wow.domain.model.Tweet;
 
@Controller
@RequestMapping("addTweet")
public class AddTweetController {
 
    @Autowired
    TweetService tweetService;
    
    @RequestMapping(method = RequestMethod.GET)
    String add(Model model){
    	return "addTweet/addTweet";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    String addTweet(@RequestParam("tweet") String detail,Model model){
    	/*
    	 * LoginUserIDの取り方わからない。。。
    	 */
    	String loginUserId = "admin";
    	LocalDateTime now = LocalDateTime.now();
    	String tweetId = loginUserId + now;
    	Tweet tweet = new Tweet();
    	tweet.setTweetId(tweetId);
    	tweet.setUserId(loginUserId);
    	tweet.setDetail(detail);
    	tweet.setTime(now);
    	tweetService.addTweet(tweet);
    	/*List<Tweet> timeLine = tweetService.findTimeLine(loginUserId);
    	model.addAttribute("timeLine",timeLine);*/
    	return "redirect:/timeLine";
    }
    
    
}