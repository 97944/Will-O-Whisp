package wow.domain.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import wow.domain.model.Block;
import wow.domain.model.Follow;

@Repository
public interface BlockUserRepository extends JpaRepository<Block, String> {
	public List<Block> findByUserId(String userId);
	public Block findByBlockUserIdAndUserId(String blockUserId,String userId);
}
