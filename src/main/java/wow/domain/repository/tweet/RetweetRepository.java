package wow.domain.repository.tweet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import wow.domain.model.Retweet;

@Repository
public interface RetweetRepository extends JpaRepository<Retweet, String> {
	public Retweet findByRetweetId(String retweetId);
}