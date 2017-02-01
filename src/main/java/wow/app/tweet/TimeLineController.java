package wow.app.tweet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import wow.domain.service.tweet.TweetService;
import wow.domain.service.user.UserService;
import wow.domain.service.user.WowUserDetails;
import wow.OtherLogic;
import wow.domain.model.Block;
import wow.domain.model.Favorite;
import wow.domain.model.Follow;
import wow.domain.model.Reply;
import wow.domain.model.Retweet;
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
	String listTweet(@RequestParam("more") Optional<Integer> more,
			@AuthenticationPrincipal WowUserDetails userDetails, Model model) {
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
		List<Tweet> loginList = tweetService.findTimeLine(userId);
		for(int i = 0;i < loginList.size();i++){
			timeLine.add(loginList.get(i));
		}
		Collections.sort(timeLine, new TimeLineComparator());
		
		List<TimeLine> trueTimeLine = new ArrayList<TimeLine>();
		for(int i=0;i<timeLine.size();i++){
			int countRetweet = 0;
			countRetweet = tweetService.countRetweet(timeLine.get(i).getTweetId());
			if(tweetService.searchRetweet(timeLine.get(i).getRetweetId()) != null){
				countRetweet = tweetService.countRetweet(timeLine.get(i).getRetweetId());
			}
			
			int countFavorite = 0;
			countFavorite = tweetService.countFavorite(timeLine.get(i).getTweetId());
			if(timeLine.get(i).getRetweetId() != null){
				countFavorite = tweetService.countFavorite(timeLine.get(i).getRetweetId());
			}
			
			boolean checkRetweet = false;
			if(timeLine.get(i).getRetweetId() != null){
				if(tweetService.checkRetweet(userId, timeLine.get(i).getRetweetId()) != null){
					checkRetweet = true;
				}
			}else{
				if(tweetService.checkRetweet(userId, timeLine.get(i).getTweetId()) != null){
					checkRetweet = true;
				}
			}
			
			boolean checkFavorite = false;
			if(timeLine.get(i).getRetweetId() != null){
				if(tweetService.checkFavorite(userId, timeLine.get(i).getRetweetId()) != null){
					checkFavorite = true;
				}
			}else{
				if(tweetService.checkFavorite(userId, timeLine.get(i).getTweetId()) != null){
					checkFavorite = true;
				}
			}
			
			TimeLine dummy = new TimeLine();
			dummy.setTweetId(timeLine.get(i).getTweetId());
			dummy.setUserId(timeLine.get(i).getUserId());
			dummy.setDetail(timeLine.get(i).getDetail());
			dummy.setTime(timeLine.get(i).getTime());
			dummy.setMedia(timeLine.get(i).getMedia());
			dummy.setMediaUrl(timeLine.get(i).getMediaUrl());
			dummy.setRelation(timeLine.get(i).getRelation());
			dummy.setReplyId(timeLine.get(i).getReplyId());
			dummy.setRetweetId(timeLine.get(i).getRetweetId());
			dummy.setUser(timeLine.get(i).getUser());
			dummy.setRetweet(timeLine.get(i).getRetweet());
			dummy.setReply(timeLine.get(i).getReply());
			dummy.setCountRetweet(countRetweet);
			dummy.setCountFavorite(countFavorite);
			dummy.setCheckRetweet(checkRetweet);
			dummy.setCheckFavorite(checkFavorite);
			
			trueTimeLine.add(dummy);
		}

		int morePage;
		if(more.isPresent()){
			morePage = more.get();
		}else{
			morePage = 1;
		}
		List<TimeLine> tl = new ArrayList<TimeLine>();
		if(trueTimeLine.size() > morePage*10){
			for(int i=0;i<morePage*10;i++){
				tl.add(trueTimeLine.get(i));
			}
			model.addAttribute("more",morePage);
			Collections.sort(tl, new TimeLineComparator());
			model.addAttribute("timeLine", tl);
		}else{
			model.addAttribute("more",0);
			Collections.sort(tl, new TimeLineComparator());
			model.addAttribute("timeLine", trueTimeLine);
		}
		
		model.addAttribute("login_user", userDetails.getUser());
		
		model.addAttribute("count_follow", follow.size());
		
		if(timeLine.size() == 0){
			model.addAttribute("timeLine_none",true);
		}else{
			model.addAttribute("timeLine_none",false);
		}
		
		// ログインユーザーのツイート数をDBから取ってきて、モデルにセット
		List<Tweet> tweet = tweetService.findTimeLine(userId);
		model.addAttribute("count_tweet", tweet.size());
		System.out.println("ツイート数" + tweet.size());

		// フォロワー数をDBから取ってきて、モデルにセット
		List<Follow> follower = userService.loadFollowerByUserId(userId);
		model.addAttribute("count_follower", follower.size());
		System.out.println("フォロワー数" + follower.size());

		LocalDateTime now = LocalDateTime.now();
		model.addAttribute("now", now);

		return "timeline/timeLine";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	String profile(@RequestParam("timeLine_user_id") String userId, RedirectAttributes attributes) {

		attributes.addAttribute("userId", userId);
		System.out.println("プロフィール表示：" + userId);
		return "redirect:/profile";
	}

	@RequestMapping(value = "/profile2", method = RequestMethod.POST)
	String profile2(@RequestParam("retweet_user_id") String userId, RedirectAttributes attributes) {

		attributes.addAttribute("userId", userId);
		System.out.println("プロフィール表示：" + userId);
		return "redirect:/profile";
	}

	@RequestMapping(value = "/favorite", method = RequestMethod.POST)
	String favorite(@RequestParam("favorite_tweet_id") String favoriteTweetId,
			@AuthenticationPrincipal WowUserDetails userDetails, RedirectAttributes attributes) {

		/*
		 * String time = LocalDateTime.now().toString(); LocalDateTime now =
		 * LocalDateTime.parse(time,
		 * DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss"));
		 * System.out.println(now);
		 */
		Favorite check = tweetService.checkFavorite(userDetails.getUser().getUserId(), favoriteTweetId);
		
		if(check == null){
			LocalDateTime now = LocalDateTime.now();
			String favoriteId = userDetails.getUser().getUserId() + now;
			Favorite favorite = new Favorite();
			favorite.setFavoriteId(favoriteId);
			favorite.setUserId(userDetails.getUser().getUserId());
			favorite.setFavoriteTweetId(favoriteTweetId);
	
			tweetService.favoriteTweet(favorite);
		}
		attributes.addAttribute("userId", userDetails.getUser().getUserId());

		return "redirect:/profile";
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	String reply(@RequestParam("tweet") String detail,@RequestParam("reply_id") String replyId,
			@AuthenticationPrincipal WowUserDetails userDetails,MultipartFile upImg){
		LocalDateTime now = LocalDateTime.now();
		String userId = userDetails.getUser().getUserId();
		String tweetId = userId + OtherLogic.nowDateTimeID();
		Tweet tweet = new Tweet();
		tweet.setTweetId(tweetId);
		tweet.setUserId(userId);
		tweet.setDetail(detail);
		tweet.setTime(now);
		tweet.setRelation(1);
		tweet.setReplyId(replyId);
		// 画像無しツイートした時は IOException になる。しかし動く＠南波
		if(upImg.isEmpty() == false){
			tweet.setMedia(OtherLogic.multipartFileToHexString(upImg));
			tweet.setMedia(OtherLogic.multipartFileToHexString(upImg));
			String saveFileName = "tweetPicture/tweetPicture_" + tweetId + ".jpg";
			OtherLogic.saveImageFromHexString(tweet.getMedia(), saveFileName);
			tweet.setMediaUrl("img/usersPicture/" + saveFileName);
		}
		
		if (tweetService.searchReply(replyId) == null) {
			Reply reply = new Reply();
			reply.setReplyId(replyId);
			Tweet re = tweetService.searchTweetByTweetId(replyId);
			reply.setUserId(re.getUserId());
			reply.setDetail(re.getDetail());
			reply.setTime(re.getTime());
			reply.setRelation(re.getRelation());
			reply.setMediaUrl(re.getMediaUrl());

			tweetService.addReply(reply);
		}
		
		tweetService.addTweet(tweet);
		
		return "redirect:/timeLine";
	}
	@RequestMapping(value = "/retweet", method = RequestMethod.POST)
	String retweet(@RequestParam("retweet_id") String retweetId, @AuthenticationPrincipal WowUserDetails userDetails) {
		/*
		 * String time = LocalDateTime.now().toString(); LocalDateTime now =
		 * LocalDateTime.parse(time,DateTimeFormatter.
		 * ofPattern("yyyy/MM/dd hh:mm:ss"));
		 */
		
		Tweet check = tweetService.checkRetweet(userDetails.getUser().getUserId(), retweetId);
		if(check == null){
			System.out.println(retweetId);
			
			LocalDateTime now = LocalDateTime.now();
			Tweet tweet = new Tweet();
			String tweetId = userDetails.getUser().getUserId() + now;
			tweet.setTweetId(tweetId);
			tweet.setUserId(userDetails.getUser().getUserId());
			tweet.setTime(now);
			tweet.setRetweetId(retweetId);
			tweetService.addTweet(tweet);
			if (tweetService.searchRetweet(retweetId) == null) {
			
				Retweet retweet = new Retweet();
				retweet.setRetweetId(retweetId);
				Tweet re = tweetService.searchTweetByTweetId(retweetId);
				
				retweet.setUserId(re.getUserId());
				retweet.setDetail(re.getDetail());
				retweet.setTime(re.getTime());
				retweet.setMediaUrl(re.getMediaUrl());
	
				tweetService.addRetweet(retweet);
			}
		}
		return "redirect:/timeLine";
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	String remove(@RequestParam("remove_id") String removeId, @AuthenticationPrincipal WowUserDetails userDetails) {
		System.out.println(removeId);
		tweetService.remove(removeId);

		return "redirect:/timeLine";
	}

	@RequestMapping(value = "/block", method = RequestMethod.POST)
	String block(@RequestParam("block_user_id") String blockUserId,
			@AuthenticationPrincipal WowUserDetails userDetails) {
		
		Block check = userService.searchLoginUserBlocked(blockUserId, userDetails.getUser().getUserId());
		if(check == null){
			LocalDateTime now = LocalDateTime.now();
			String blockId = userDetails.getUser().getUserId() + now;
			Block block = new Block();
			block.setBlockId(blockId);
			block.setUserId(userDetails.getUser().getUserId());
			block.setBlockUserId(blockUserId);
			userService.blocking(block);
		}
		return "redirect:/timeLine";
	}
}