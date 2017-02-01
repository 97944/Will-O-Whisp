package wow.domain.repository.tweet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import wow.domain.model.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, String> {
	public List<Tweet> findByUserIdOrderByTimeDesc(String userId);
	public List<Tweet> findByTweetIdOrderByTimeDesc(String tweetId);
	public List<Tweet> findByDetailContainsOrderByTimeDesc(String text);
	public List<Tweet> findByMediaUrlContainsAndUserIdOrderByTimeDesc(String mediaUrl,String userId);
	public List<Tweet> findByRetweetId(String retweetId);
	public Tweet findByTweetId(String tweetId);
	public Tweet findByUserIdAndRetweetId(String userId,String retweetId);
}
