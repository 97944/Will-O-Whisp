package wow.app.tweet;
 
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
@RequestMapping("timeLine")
public class TimeLineController {
 
    @Autowired
    TweetService tweetService;
    @Autowired
    UserService userService;
 
    @RequestMapping(method = RequestMethod.GET)
    String listTweet(Model model){
    	/*
    	 * LoginUserIDの取り方わからない。。。
    	 */
    	String loginUserId = "admin";
    	model.addAttribute("user",userService.loadUserByUserId(loginUserId));
    	List<Tweet> timeLine = tweetService.findTimeLine(loginUserId);
    	model.addAttribute("timeLine",timeLine);
    	return "timeline/timeLine";
    }
    @RequestMapping(params="profile",method = RequestMethod.POST)
    String dispProfile(Model model){
    	return "redirect:/profile";
    }
    @RequestMapping(params="search",method = RequestMethod.POST)
    String dispSearch(Model model){
    	return "redirect:/search";
    }
    @RequestMapping(params="addTweet",method = RequestMethod.POST)
    String dispAddTweet(Model model){
    	return "redirect:/addTweet";
    }
    
    
}