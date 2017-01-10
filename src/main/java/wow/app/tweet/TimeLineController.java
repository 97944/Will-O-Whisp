package wow.app.tweet;
 
import java.util.ArrayList;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import wow.domain.service.tweet.TweetService;
import wow.domain.service.user.UserService;
import wow.domain.model.Favorite;
import wow.domain.model.Follow;
import wow.domain.model.Tweet;
import wow.domain.model.User;
 
@Controller
@RequestMapping("timeLine")
public class TimeLineController {
 
    @Autowired
    TweetService tweetService;
    @Autowired
    UserService userService;
 
    @RequestMapping(method = RequestMethod.GET)
    String listTweet(@Param("userId") String userId,Model model){
    	System.out.println("タイムライン" + userId);
    	if(userId == null){
    		userId = "admin";
    	}
    	List<Follow> follow = userService.loadFollowUserByUserId(userId);
    	List<Tweet> timeLine = new ArrayList<Tweet>();
    	List<Tweet> dummyList = null;
    	for(int i=0;i<follow.size();i++){
    		dummyList = (tweetService.findTimeLine(follow.get(i).getFollowUserId()));
    		for(int j=0;j<dummyList.size();j++){
    			timeLine.add(dummyList.get(j));
    		}
    	}
    	model.addAttribute("user",userService.loadUserByUserId(userId));
    	model.addAttribute("timeLine",timeLine);
    	return "timeline/timeLine";
    }
    @RequestMapping(params="profile",method = RequestMethod.POST)
    String dispProfile(Model model){
    	return "redirect:/profile";
    }
    @RequestMapping(params="search",method = RequestMethod.POST)
    String dispSearch(@RequestParam("searchText") String text,RedirectAttributes attributes){
    	attributes.addAttribute("text",text);
    	return "redirect:/search";
    }
    @RequestMapping(params="addTweet",method = RequestMethod.POST)
    String dispAddTweet(Model model){
    	return "redirect:/addTweet";
    }
    
    
}