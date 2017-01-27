package wow.domain.repository.tweet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import wow.domain.model.Favorite;
import wow.domain.model.Tweet;

@Repository
public interface FavoriteTweetRepository extends JpaRepository<Favorite, String> {
	public List<Favorite> findByUserId(String userId);
	public List<Favorite> findByFavoriteTweetId(String favoriteTweetId);
	public Favorite findByUserIdAndFavoriteTweetId(String userId,String favoriteTweetId);
}
