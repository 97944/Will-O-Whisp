package wow.domain.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wow.app.account.AccountForm;
import wow.domain.model.Follow;
import wow.domain.model.User;
import wow.domain.repository.user.FollowUserRepository;
import wow.domain.repository.user.UserRepository;

@Service

public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FollowUserRepository followRepository;

    public User loadUserByUserId(String userId){
    	User user = userRepository.findByUserId(userId);
    	return user;
    }
    public List<Follow> loadFollowUserByUserId(String userId){
    	List<Follow> follow = followRepository.findByUserId(userId);
    	return follow;
    }
    public List<Follow> loadFollowerByUserId(String userId){
    	List<Follow> follower = followRepository.findByFollowUserId(userId);
    	return follower;
    }
    public User create(User user){
    	User list = userRepository.save(user);
    	return list;
    }
    public List<User> searchByUserId(String text){
    	List<User> user = userRepository.findByUserIdContaining(text);
    	return user;
    }
}

