package wow.app.header;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import wow.domain.service.tweet.TweetService;
import wow.domain.service.user.UserService;
import wow.domain.service.user.WowUserDetails;
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
	String dispProfile(@AuthenticationPrincipal WowUserDetails userDetails, RedirectAttributes attributes) {
		String userId = userDetails.getUser().getUserId();
		attributes.addAttribute("userId", userId);
		return "redirect:/profile";
	}

	// ヘッダーで使用するメソッド
	@RequestMapping(value = "search", method = RequestMethod.POST)
	String dispSearch(@RequestParam("searchText") String text,@AuthenticationPrincipal WowUserDetails userDetails,
			RedirectAttributes attributes) {
		attributes.addAttribute("text", text);
		return "redirect:/search";
	}

	// ヘッダーで使用するメソッド
	@RequestMapping(value = "add", method = RequestMethod.POST)
	String addTweet(@RequestParam("tweet") String detail,@AuthenticationPrincipal WowUserDetails userDetails) {

		LocalDateTime now = LocalDateTime.now();
		String userId = userDetails.getUser().getUserId();
		String tweetId = userId + now;
		Tweet tweet = new Tweet();
		tweet.setTweetId(tweetId);
		tweet.setUserId(userId);
		tweet.setDetail(detail);
		tweet.setTime(now);
		tweetService.addTweet(tweet);
		
		return "redirect:/timeLine";
	}

	// ヘッダーで使用するメソッド
	@RequestMapping(value = "timeLine", method = RequestMethod.POST)
	String dispTweet(@AuthenticationPrincipal WowUserDetails userDetails) {
		return "redirect:/timeLine";
	}

}