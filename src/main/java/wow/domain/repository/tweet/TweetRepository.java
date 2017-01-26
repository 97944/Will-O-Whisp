package wow.domain.repository.tweet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import wow.domain.model.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, String> {
	public List<Tweet> findByUserIdContainsOrderByTimeDesc(String userId);
	public List<Tweet> findByTweetIdContainsOrderByTimeDesc(String tweetId);
	public List<Tweet> findByDetailContainsOrderByTimeDesc(String text);
	public List<Tweet> findByMediaUrlContainsOrderByTimeDesc(String mediaUrl);
	public Tweet findByTweetId(String tweetId);
}
