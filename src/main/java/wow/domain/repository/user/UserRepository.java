package wow.domain.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import wow.app.account.AccountForm;
import wow.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	public User findByUserId(String userId);
	public List<User> findByUserIdContaining(String text);
}
