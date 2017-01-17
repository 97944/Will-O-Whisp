package wow.app.tweet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import wow.domain.service.tweet.TweetService;
import wow.domain.service.user.UserService;
import wow.domain.service.user.WowUserDetails;
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
	String listTweet(@AuthenticationPrincipal WowUserDetails userDetails, Model model) {
		System.out.println("タイムライン" + userDetails.getUser().getUserName());
		
		String userId = userDetails.getUser().getUserId();
		
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
		Collections.sort(timeLine,new TimeLineComparator());
		
		model.addAttribute("login_user",userDetails.getUser());
		model.addAttribute("timeLine", timeLine);
		model.addAttribute("count_follow", follow.size());

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

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	String profile(@RequestParam("timeLine_user_id") String userId, RedirectAttributes attributes) {
		
		attributes.addAttribute("userId", userId);
		System.out.println("プロフィール表示：" + userId);
		return "redirect:/profile";
	}
	@RequestMapping(value = "/favorite", method = RequestMethod.POST)
	String favorite(@RequestParam("favorite_tweet_id") String favoriteTweetId, @RequestParam("favorite_user_id") String userId,RedirectAttributes attributes){
		
		String time = LocalDateTime.now().toString();
		LocalDateTime now = LocalDateTime.parse(time,DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss"));
		System.out.println(now);
		String favoriteId = now + userId;
		Favorite favorite = new Favorite();
		favorite.setFavoriteId(favoriteId);
		favorite.setUserId(userId);
		favorite.setFavoriteTweetId(favoriteTweetId);
		
		tweetService.favoriteTweet(favorite);
		attributes.addAttribute("userId",userId);
		
		return "redirect:/profile";
	}
}