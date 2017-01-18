package wow.domain.service.tweet;

import java.util.ArrayList;
import java.util.List;

import wow.domain.model.Favorite;
import wow.domain.model.Retweet;
import wow.domain.model.Tweet;
import wow.domain.repository.tweet.FavoriteTweetRepository;
import wow.domain.repository.tweet.RetweetRepository;
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
	
	@Autowired
	RetweetRepository retweetRepository;
	
	public List<Tweet> findTimeLine(String userId){
		return tweetRepository.findByUserIdContainsOrderByTimeDesc(userId);
	}
	
	public List<Tweet> findFavoriteTweet(String userId){
		List<Favorite> favorite = favoriteTweetRepository.findByUserId(userId);
		List<Tweet> tweet = new ArrayList<Tweet>();
		List<Tweet> dummyList = null;
		for(int i=0;i<favorite.size();i++){
			dummyList = tweetRepository.findByTweetIdContainsOrderByTimeDesc(favorite.get(i).getFavoriteTweetId());
			for(int j=0;j<dummyList.size();j++){
				tweet.add(dummyList.get(j));
			}
		}
		return tweet;
	}
	
	public List<Tweet> searchTweet(String text){
		return tweetRepository.findByDetailContainsOrderByTimeDesc(text);
	}
	public Tweet searchTweetByTweetId(String tweetId){
		return tweetRepository.findByTweetId(tweetId);
	}
	
	public void favoriteTweet(Favorite favorite){
		favoriteTweetRepository.save(favorite);
	}
	
	public int countFavorite(String favoriteTweetId){
		List<Favorite> favorite = favoriteTweetRepository.findByFavoriteTweetId(favoriteTweetId);
		
		return favorite.size();
	}
	
	public void addTweet(Tweet tweet){
		tweetRepository.save(tweet);
	}
	
	public void addRetweet(Retweet retweet){
		retweetRepository.save(retweet);
	}
	public Retweet searchRetweet(String retweetId){
		return retweetRepository.findByRetweetId(retweetId);
	}
	public void remove(String tweetId){
		tweetRepository.delete(tweetId);
	}
}
