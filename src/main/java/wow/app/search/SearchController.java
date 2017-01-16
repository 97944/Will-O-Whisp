package wow.app.search;
import java.time.LocalDateTime;
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
    String search(@Param("text") String text,@AuthenticationPrincipal WowUserDetails userDetails,Model model){
    	// アクセスしてきたユーザー情報を取得
    	User user = userDetails.getUser();
    	model.addAttribute("login_user",user);
    	
    	// text内容が含まれるユーザーを検索する
    	List<User> searchUser = userService.searchByUserId(text);
    	model.addAttribute("search_user",searchUser);
    	
    	// text内容が含まれるツイートを検索する
    	List<Tweet> searchTweet = tweetService.searchTweet(text);
    	model.addAttribute("search_tweet",searchTweet);
    	
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
