package wow.app.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping("profile")
public class ProfileController {

	@Autowired
	UserService userService;
	
	@Autowired
	TweetService tweetService;

	@RequestMapping(method = RequestMethod.GET)
	String listUser(@Param("userId") String userId,Model model) {
		if(userId == null){
			userId = "admin";
		}
		// ログインユーザー情報をDBから取ってきて、モデルにセット
		User user = userService.loadUserByUserId(userId);
		model.addAttribute("user", user);
		
		// ログインユーザーのツイートをDBから取ってきて、モデルにセット
		List<Tweet> tweet = tweetService.findTimeLine(userId);
		model.addAttribute("tweet",tweet);
		model.addAttribute("count_tweet",tweet.size());
		System.out.println("ツイート数" + tweet.size());
		
		// フォローユーザをDBから取ってきて、モデルにセット
		List<Follow> follow = userService.loadFollowUserByUserId(userId);
		List<User> followUser = new ArrayList<User>();
		for(int i=0;i<follow.size();i++){
			followUser.add(userService.loadUserByUserId(follow.get(i).getFollowUserId()));
		}
		model.addAttribute("follow",followUser);
		model.addAttribute("count_follow",followUser.size());
		System.out.println("フォロー数" + followUser.size());
		
		// フォロワーをDBから取ってきて、モデルにセット
		List<Follow> follower = userService.loadFollowerByUserId(userId);
		List<User> followerUser = new ArrayList<User>();
		for(int i=0;i<follower.size();i++){
			followerUser.add(userService.loadUserByUserId(follower.get(i).getUserId()));
		}
		model.addAttribute("follower",followerUser);
		model.addAttribute("count_follower",followerUser.size());
		System.out.println("フォロワー数" + followerUser.size());
		
		// いいねしたツイートをDBから取ってきて、モデルにセット
		List<Tweet> favorite = tweetService.findFavoriteTweet(userId);
		model.addAttribute("favorite",favorite);
		model.addAttribute("count_favorite",favorite.size());
		System.out.println("お気に入り数" + favorite.size());
		
		// 画像付きの自分のツイートをDBから取ってきて、モデルにセット
		
		return "profile/profile";
	}
	@RequestMapping(method = RequestMethod.POST)
	String dispTweet(@RequestParam("userId") String userId,RedirectAttributes attributes) {
		System.out.println("プロフィール"+ userId);
		attributes.addAttribute("userId",userId);
		return "redirect:/timeLine";
	}
	@RequestMapping(params="follow_user_id",method = RequestMethod.POST)
	String follow(@RequestParam("follow_user_id") String followId,RedirectAttributes attributes){
		System.out.println(followId);
		attributes.addAttribute("userId",followId);
		return "redirect:/profile";
	}
	@RequestMapping(params="follower_user_id",method = RequestMethod.POST)
	String follower(@RequestParam("follower_user_id") String followerId,RedirectAttributes attributes){
		System.out.println(followerId);
		attributes.addAttribute("userId",followerId);
		return "redirect:/profile";
	}
}
