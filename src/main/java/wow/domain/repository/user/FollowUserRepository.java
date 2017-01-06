package wow.domain.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import wow.domain.model.Follow;

@Repository
public interface FollowUserRepository extends JpaRepository<Follow, String> {
	public List<Follow> findByFollowId(String userId);
}
