package wow.domain.repository.tweet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import wow.domain.model.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, String> {
	public Reply findByReplyId(String replyId);
}
