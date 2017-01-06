package wow.domain.repository.tweet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import wow.domain.model.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, String> {
	public List<Tweet> findByUserIdContainsOrderByTimeDesc(String userId);
}
