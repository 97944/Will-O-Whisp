package wow.domain.service.tweet;

import java.util.List;

import wow.domain.model.Favorite;
import wow.domain.model.Tweet;
import wow.domain.repository.tweet.FavoriteTweetRepository;
import wow.domain.repository.tweet.TweetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TweetService {
	
	@Autowired
	TweetRepository tweetRepository;
	
	@Autowired
	FavoriteTweetRepository favoriteTweetRepository;
	
	
	public List<Tweet> findTimeLine(String userId){
		return tweetRepository.findByUserIdContainsOrderByTimeDesc(userId);
	}
	
	public List<Tweet> findFavoriteTweet(String userId){
		List<Favorite> favorite = favoriteTweetRepository.findByUserId(userId);
		List<Tweet> tweet = null;
		for(int i=0;i<favorite.size();i++){
			tweet = tweetRepository.findByUserIdContainsOrderByTimeDesc(favorite.get(i).getUserId());
		}
		return tweet;
	}
	public void addTweet(Tweet tweet){
		tweetRepository.save(tweet);
	}
	
}
