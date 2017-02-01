package wow.domain.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import wow.domain.model.Request;

public interface RequestRepository extends JpaRepository<Request,String>{
	public List<Request> findByRequestUserId(String requestUserId);
	public Request findByUserIdAndRequestUserId(String userId,String requestUserId);
}
