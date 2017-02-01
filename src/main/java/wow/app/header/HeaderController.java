package wow.app.header;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import wow.domain.service.tweet.TweetService;
import wow.domain.service.user.UserService;
import wow.domain.service.user.WowUserDetails;
import wow.OtherLogic;
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

	// ログインユーザーのプロフィール表示
	@RequestMapping(value = "pro", method = RequestMethod.POST)
	String dispProfile(@AuthenticationPrincipal WowUserDetails userDetails, RedirectAttributes attributes) {
		String userId = userDetails.getUser().getUserId();
		attributes.addAttribute("userId", userId);
		return "redirect:/profile";
	}

	// 検索
	@RequestMapping(value = "search", method = RequestMethod.POST)
	String dispSearch(@RequestParam("searchText") String text,@AuthenticationPrincipal WowUserDetails userDetails,
			RedirectAttributes attributes) {
		attributes.addAttribute("text", text);
		attributes.addAttribute("more",1);
		return "redirect:/search";
	}

	// ツイート
	@RequestMapping(value = "add", method = RequestMethod.POST)
	String addTweet(@RequestParam("tweet") String detail,@AuthenticationPrincipal WowUserDetails userDetails,
			@RequestParam("upImg")MultipartFile upImg) {

		LocalDateTime now = LocalDateTime.now();
		String userId = userDetails.getUser().getUserId();
		String tweetId = userId + OtherLogic.nowDateTimeID();
		Tweet tweet = new Tweet();
		tweet.setTweetId(tweetId);
		tweet.setUserId(userId);
		tweet.setDetail(detail);
		tweet.setTime(now);
		// 画像無しツイートした時は IOException になる。しかし動く＠南波
		if(upImg.isEmpty() == false){
			System.out.println("画像あり:" + upImg);
			tweet.setMedia(OtherLogic.multipartFileToHexString(upImg));
			String saveFileName = "tweetPicture/tweetPicture_" + tweetId + ".jpg";
			OtherLogic.saveImageFromHexString(tweet.getMedia(), saveFileName);
			tweet.setMediaUrl("img/usersPicture/" + saveFileName);
		}else{
			System.out.println("画像はありません");
		}
		
		tweetService.addTweet(tweet);
		
		return "redirect:/timeLine";
	}

	// タイムライン表示
	@RequestMapping(value = "timeLine", method = RequestMethod.POST)
	String dispTweet(@AuthenticationPrincipal WowUserDetails userDetails,RedirectAttributes attributes) {
		//attributes.addAttribute("more",1);
		return "redirect:/timeLine";
	}
	
	// プロフィール編集
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	String edit(){
		return "redirect:/edit";
	}
	

}