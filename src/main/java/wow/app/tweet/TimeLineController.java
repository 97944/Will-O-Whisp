package wow.app.tweet;

import java.time.LocalDateTime;
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
	String listTweet(@Param("userId") String userId, Model model) {
		System.out.println("タイムライン" + userId);
		if (userId == null) {
			userId = "admin";
		}
		List<Follow> follow = userService.loadFollowUserByUserId(userId);
		// フォローユーザをDBから取ってきて、モデルにセット
		System.out.println("フォロー数" + follow.size());
		List<Tweet> timeLine = new ArrayList<Tweet>();
		List<Tweet> dummyList = null;
		for (int i = 0; i < follow.size(); i++) {
			dummyList = (tweetService.findTimeLine(follow.get(i).getFollowUserId()));
			for (int j = 0; j < dummyList.size(); j++) {
				timeLine.add(dummyList.get(j));
			}
		}
		model.addAttribute("user", userService.loadUserByUserId(userId));
		model.addAttribute("timeLine", timeLine);
		model.addAttribute("count_follow",follow.size());

		// ログインユーザーのツイート数をDBから取ってきて、モデルにセット
		List<Tweet> tweet = tweetService.findTimeLine(userId);
		model.addAttribute("count_tweet", tweet.size());
		System.out.println("ツイート数" + tweet.size());

		// フォロワー数をDBから取ってきて、モデルにセット
		List<Follow> follower = userService.loadFollowerByUserId(userId);
		model.addAttribute("count_follower", follower.size());
		System.out.println("フォロワー数" + follower.size());

		return "timeline/timeLine";
	}

	@RequestMapping(value="pro", method = RequestMethod.POST)
	String dispProfile(@RequestParam("user_id") String userId,RedirectAttributes attributes) {
		attributes.addAttribute("userId",userId);
		return "redirect:/profile";
	}

	@RequestMapping(params = "search", method = RequestMethod.POST)
	String dispSearch(@RequestParam("searchText") String text, RedirectAttributes attributes) {
		attributes.addAttribute("text", text);
		return "redirect:/search";
	}
	@RequestMapping(value="add", method = RequestMethod.POST)
	String addTweet(@RequestParam("tweet") String detail,@RequestParam("tweet_user_id") String userId,RedirectAttributes attributes){
    	
    	LocalDateTime now = LocalDateTime.now();
    	String tweetId = userId + now;
    	Tweet tweet = new Tweet();
    	tweet.setTweetId(tweetId);
    	tweet.setUserId(userId);
    	tweet.setDetail(detail);
    	tweet.setTime(now);
    	tweetService.addTweet(tweet);
    	attributes.addAttribute("userId",userId);
    	
    	return "redirect:/timeLine";
    }    
	@RequestMapping(value="/profile", method = RequestMethod.POST)
	String profile(@RequestParam("timeLine_user_id") String userId ,RedirectAttributes attributes) {
		attributes.addAttribute("userId",userId);
		return "redirect:/profile";
	}

}