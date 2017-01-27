package wow.app.profile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import wow.domain.service.tweet.TweetService;
import wow.domain.service.user.UserService;
import wow.domain.service.user.WowUserDetails;
import wow.OtherLogic;
import wow.app.tweet.TimeLineComparator;
import wow.domain.model.Block;
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
	String listUser(@Param("userId") String userId, @AuthenticationPrincipal WowUserDetails userDetails, Model model) {
		if (userId == null) {
			userId = "admin";
		}
		// ログインユーザー情報をモデルにセット
		User loginUser = userDetails.getUser();
		model.addAttribute("login_user", loginUser);

		// ユーザー情報をDBから取ってきて、モデルにセット
		User user = userService.loadUserByUserId(userId);
		model.addAttribute("user", user);
		
		// ユーザのトップ画像、ヘッダ画像を取得
		// topPicture を画像へ変換し、変換先URLを topPictureUrl へセット＠南波
		if (user.getTopPicture() != null) {
			// System.out.println("ファイル保存処理" + timeLine.get(i).getDetail());
			String saveFileNameTop = "topPicture/topPicture_" + user.getUserId() + ".jpg";
			OtherLogic.saveImageFromHexString(user.getTopPicture(), saveFileNameTop);
			user.setTopPictureUrl("img/usersPicture/" + saveFileNameTop);
			userService.update(user);
		}
		// headPicture を画像へ変換し、変換先URLを headPictureUrl へセット＠南波
		if (user.getHeadPicture() != null) {
			String saveFileNameHead = "headPicture/headPicture_" + user.getUserId() + ".jpg";
			OtherLogic.saveImageFromHexString(user.getHeadPicture(), saveFileNameHead);
			user.setHeadPictureUrl("img/usersPicture/" + saveFileNameHead);
			userService.update(user);
		}
		
		// ユーザーのツイートをDBから取ってきて、モデルにセット
		List<Tweet> tweet = tweetService.findTimeLine(userId);
		model.addAttribute("tweet", tweet);
		model.addAttribute("count_tweet", tweet.size());
		System.out.println("ツイート数" + tweet.size());
		if(tweet.size() == 0){
			model.addAttribute("tweet_none",true);
		}else{
			model.addAttribute("tweet_none",false);
		}

		// フォローユーザをDBから取ってきて、モデルにセット
		List<Follow> follow = userService.loadFollowUserByUserId(userId);
		List<User> followUser = new ArrayList<User>();
		for (int i = 0; i < follow.size(); i++) {
			followUser.add(userService.loadUserByUserId(follow.get(i).getFollowUserId()));
		}
		model.addAttribute("follow", followUser);
		model.addAttribute("count_follow", followUser.size());
		System.out.println("フォロー数" + followUser.size());
		if(followUser.size() == 0){
			model.addAttribute("follow_none",true);
		}else{
			model.addAttribute("follow_none",false);
		}

		// フォロワーをDBから取ってきて、モデルにセット
		List<Follow> follower = userService.loadFollowerByUserId(userId);
		List<User> followerUser = new ArrayList<User>();
		for (int i = 0; i < follower.size(); i++) {
			followerUser.add(userService.loadUserByUserId(follower.get(i).getUserId()));
		}
		model.addAttribute("follower", followerUser);
		model.addAttribute("count_follower", followerUser.size());
		System.out.println("フォロワー数" + followerUser.size());
		if(follower.size() == 0){
			model.addAttribute("follower_none",true);
		}else{
			model.addAttribute("follower_none",false);
		}

		// いいねしたツイートをDBから取ってきて、モデルにセット
		List<Tweet> favorite = tweetService.findFavoriteTweet(userId);
		Collections.sort(favorite,new TimeLineComparator());
		model.addAttribute("favorite", favorite);
		model.addAttribute("count_favorite", favorite.size());
		System.out.println("お気に入り数" + favorite.size());
		if(favorite.size() == 0){
			model.addAttribute("favorite_none",true);
		}else{
			model.addAttribute("favorite_none",false);
		}

		// 画像付きの自分のツイートをDBから取ってきて、モデルにセット
		List<Tweet> media = tweetService.searchMedia(userId,userId);
		model.addAttribute("media",media);
		model.addAttribute("count_media",media.size());
		System.out.println("画像付きツイート数" +  media.size());
		if(media.size() == 0){
			model.addAttribute("media_none",true);
		}else{
			model.addAttribute("media_none",false);
		}
		
		// ログインユーザーのフォローの有無
		System.out.println("ログインユーザーのフォローの有無" + userDetails.getUser().getUserId());
		List<Follow> loginUserFollow = userService.loadFollowUserByUserId(userDetails.getUser().getUserId());
		/*System.out.println(loginUserFollow.size() + ' ' + follow.size());
		List<User> loginFollowUser = new ArrayList<User>();
		for (int i = 0; i < follow.size(); i++) {
			loginFollowUser.add(userService.loadUserByUserId(loginUserFollow.get(i).getFollowUserId()));
		}
		System.out.println("null?");*/
		
		// ログインユーザーorフォローユーザーorNotフォローユーザーか判断
		System.out.println("プロフィールのユーザーID：" + userId + "　ログインユーザーのユーザーID：" + userDetails.getUser().getUserId());
		if (userId.equals(userDetails.getUser().getUserId())) {
			model.addAttribute("switch","ログイン");
			System.out.println("このユーザーはログインユーザー");
		}else{
			List<String> followId = new ArrayList<String>();
			System.out.println("フォローリスト");
			for (int i = 0; i < loginUserFollow.size(); i++) {
				followId.add(loginUserFollow.get(i).getFollowUserId());
				System.out.println(loginUserFollow.get(i).getFollowUserId() + " " + userId);
			}
			if(followId.contains(userId)){
				model.addAttribute("switch","フォロー済み");
				System.out.println("このユーザーはフォロー済み");
			}else{
				model.addAttribute("switch","未フォロー");
				System.out.println("このユーザーは未フォロー");
			}
		}
		
		// ログインユーザーがブロックされているかどうか
		Block block = userService.searchLoginUserBlocked(userDetails.getUser().getUserId(), userId);
		boolean check;
		if(block == null){
			check = false;
		}else{
			check = true;
		}
		model.addAttribute("check",check);
		
		// ログインユーザーがブロックしているかどうか
		Block block2 = userService.searchLoginUserBlocked(userId, userDetails.getUser().getUserId());
		boolean check2;
		if(block2 == null){
			check2 = false;
		}else{
			check2 = true;
		}
		model.addAttribute("check2",check2);
		
		return "profile/profile";
	}
	
	// プロフィールからpostした時
	@RequestMapping(value = "profile" ,method = RequestMethod.POST)
	String jump(@RequestParam("user_id") String userId ,RedirectAttributes attributes) {
		System.out.println(userId);
		attributes.addAttribute("userId",userId);
		return "redirect:/profile";
	}
	
	// フォローユーザリストからpostした時
	@RequestMapping(value = "follow", method = RequestMethod.POST)
	String followJump(@RequestParam("follow_user_id") String followId, RedirectAttributes attributes) {
		System.out.println(followId);
		attributes.addAttribute("userId", followId);
		return "redirect:/profile";
	}
	//　フォロワーリストからpostした時
	@RequestMapping(value = "follower", method = RequestMethod.POST)
	String followerJump(@RequestParam("follower_user_id") String followerId, RedirectAttributes attributes) {
		System.out.println(followerId);
		attributes.addAttribute("userId", followerId);
		return "redirect:/profile";
	}
	//　お気に入り
	@RequestMapping(value = "/favorite", method = RequestMethod.POST)
	String favorite(@RequestParam("favorite_tweet_id") String favoriteTweetId,
			@AuthenticationPrincipal WowUserDetails userDetails, RedirectAttributes attributes) {
		LocalDateTime now = LocalDateTime.now();
		String userId = userDetails.getUser().getUserId();
		String favoriteId = now + userId;
		Favorite favorite = new Favorite();
		favorite.setFavoriteId(favoriteId);
		favorite.setUserId(userId);
		favorite.setFavoriteTweetId(favoriteTweetId);

		tweetService.favoriteTweet(favorite);
		attributes.addAttribute("userId", userId);

		return "redirect:/profile";
	}
	
	// ログインユーザーの場合
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	String profileEdit(@AuthenticationPrincipal WowUserDetails userDetails){
		return "redirect:/edit";
	}
	// フォローしてるユーザーの場合
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	String followReset(@AuthenticationPrincipal WowUserDetails userDetails,
			@RequestParam("reset_user_id") String resetUserId,RedirectAttributes attributes){
		List<Follow> follow = userService.loadFollowUserByUserId(userDetails.getUser().getUserId());
		for(int i=0;i<follow.size();i++){
			if(follow.get(i).getFollowUserId().equals(resetUserId)){
				userService.deleteFollowByFollowId(follow.get(i).getFollowId());
				break;
			}
		}
		attributes.addAttribute("userId",resetUserId);
		return "redirect:/profile";
	}
	// フォローしてないユーザーの場合
	@RequestMapping(value = "/following", method = RequestMethod.POST)
	String follow(@AuthenticationPrincipal WowUserDetails userDetails,
			@RequestParam("following_user_id") String followingUserId,RedirectAttributes attributes){
		System.out.println("フォロー開始");
		LocalDateTime time = LocalDateTime.now();
		String followId = userDetails.getUser().getUserId() + time;
		Follow follow = new Follow();
		follow.setFollowId(followId);
		follow.setUserId(userDetails.getUser().getUserId());
		follow.setFollowUserId(followingUserId);
		
		userService.following(follow);
		
		attributes.addAttribute("userId",followingUserId);
		
		return "redirect:/profile";
	}
	// ブロック解除
	@RequestMapping(value = "unblock", method = RequestMethod.POST)
	String unblock(@RequestParam("unblock_user_id") String unBlockUserId,RedirectAttributes attributes,
			@AuthenticationPrincipal WowUserDetails userDetails){
		Block block = userService.searchLoginUserBlocked(unBlockUserId,userDetails.getUser().getUserId());
		
		if(block != null){
			System.out.println("ブロック解除");
			userService.unblock(block.getBlockId());
		}else{
			System.out.println("ブロック解除できないよー");
		}
		
		attributes.addAttribute("userId",unBlockUserId);
		return "redirect:/profile";
	}
}
