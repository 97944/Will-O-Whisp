package wow.domain.service.tweet;

import java.util.ArrayList;
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
		List<Tweet> tweet = new ArrayList<Tweet>();
		List<Tweet> dummyList = null;
		for(int i=0;i<favorite.size();i++){
			dummyList = tweetRepository.findByTweetIdContainsOrderByTimeDesc(favorite.get(i).getFavoriteTweet());
			for(int j=0;j<dummyList.size();j++){
				tweet.add(dummyList.get(j));
			}
		}
		return tweet;
	}
	
	public int countFavorite(String favoriteTweet){
		List<Favorite> favorite = favoriteTweetRepository.findByFavoriteTweet(favoriteTweet);
		
		return favorite.size();
	}
	
	public void addTweet(Tweet tweet){
		tweetRepository.save(tweet);
	}
	
}
