package wow.app.header;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import wow.domain.service.tweet.TweetService;
import wow.domain.service.user.UserService;
import wow.domain.model.Tweet;

@Controller
@RequestMapping("header")
public class HeaderController {

	@Autowired
	TweetService tweetService;
	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	String header() {
		return "header/header";
	}

	// ヘッダーで使用するメソッド
	@RequestMapping(value = "pro", method = RequestMethod.POST)
	String dispProfile(@RequestParam("user_id") String userId, RedirectAttributes attributes) {
		attributes.addAttribute("userId", userId);
		return "redirect:/profile";
	}

	// ヘッダーで使用するメソッド
	@RequestMapping(value = "search", method = RequestMethod.POST)
	String dispSearch(@RequestParam("searchText") String text, @RequestParam("search_user_id") String userId,
			RedirectAttributes attributes) {
		attributes.addAttribute("text", text);
		attributes.addAttribute("userId", userId);
		return "redirect:/search";
	}

	// ヘッダーで使用するメソッド
	@RequestMapping(value = "add", method = RequestMethod.POST)
	String addTweet(@RequestParam("tweet") String detail, @RequestParam("tweet_user_id") String userId,
			RedirectAttributes attributes) {

		LocalDateTime now = LocalDateTime.now();
		String tweetId = userId + now;
		Tweet tweet = new Tweet();
		tweet.setTweetId(tweetId);
		tweet.setUserId(userId);
		tweet.setDetail(detail);
		tweet.setTime(now);
		tweetService.addTweet(tweet);
		attributes.addAttribute("userId", userId);

		return "redirect:/timeLine";
	}

	// ヘッダーで使用するメソッド
	@RequestMapping(value = "timeLine", method = RequestMethod.POST)
	String dispTweet(@RequestParam("timeLine") String userId, RedirectAttributes attributes) {
		System.out.println("プロフィール" + userId);
		attributes.addAttribute("userId", userId);
		return "redirect:/timeLine";
	}

}