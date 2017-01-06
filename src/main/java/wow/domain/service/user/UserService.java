package wow.domain.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wow.app.account.AccountForm;
import wow.domain.model.User;
import wow.domain.repository.user.UserRepository;

@Service

public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> loadUserByUserId(String userName){
    	List<User> user = userRepository.findByUserId("admin");
    	return user;
    }
    public User create(User user){
    	User list = userRepository.save(user);
    	return list;
    }
}

