package wow.domain.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wow.app.account.AccountForm;
import wow.domain.model.Block;
import wow.domain.model.Follow;
import wow.domain.model.Request;
import wow.domain.model.User;
import wow.domain.repository.user.BlockUserRepository;
import wow.domain.repository.user.FollowUserRepository;
import wow.domain.repository.user.RequestRepository;
import wow.domain.repository.user.UserRepository;

@Service

public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FollowUserRepository followRepository;
    @Autowired
    BlockUserRepository blockRepository;
    @Autowired
    RequestRepository requestRepository;

    public User loadUserByUserId(String userId){
    	User user = userRepository.findByUserId(userId);
    	return user;
    }
    
    public Follow checkFollow(String userId,String followUserId){
    	Follow follow = followRepository.findByUserIdAndFollowUserId(userId, followUserId);
    	return follow;
    }

    public List<Follow> loadFollowUserByUserId(String userId){
    	List<Follow> follow = followRepository.findByUserId(userId);
    	return follow;
    }
    public List<Follow> loadFollowerByUserId(String userId){
    	List<Follow> follower = followRepository.findByFollowUserId(userId);
    	return follower;
    }
    public List<Block> loadBlockUserByUserId(String userId){
    	List<Block> block = blockRepository.findByUserId(userId);
    	return block;
    }
    public Block searchLoginUserBlocked(String blockUserId,String userId){
    	Block block = blockRepository.findByBlockUserIdAndUserId(blockUserId, userId);
    	return block;
    }
    public void deleteFollowByFollowId(String followId){
    	followRepository.delete(followId);
    }
    public void following(Follow follow){
    	followRepository.save(follow);
    }
    public void create(User user){
    	userRepository.save(user);
    }
    public void update(User user){
    	userRepository.save(user);
    }
    public void blocking(Block block){
    	blockRepository.save(block);
    }
    public void unblock(String blockId){
    	blockRepository.delete(blockId);
    }
    public List<User> searchByUserId(String text){
    	List<User> user = userRepository.findByUserIdContainingOrderByUserIdAsc(text);
    	return user;
    }
    public List<User> searchByUserName(String text){
    	List<User> user = userRepository.findByUserNameContainingOrderByUserIdAsc(text);
    	return user;
    }
    public List<User> searchUser(String text,String text2){
    	List<User> user = userRepository.findByUserIdContainsOrUserNameContainsOrderByUserIdAsc(text,text2);
    	return user;
    }
    public void request(Request request){
    	requestRepository.save(request);
    }
    public void accept(String requestId){
    	requestRepository.delete(requestId);
    }
    public List<Request> searchRequestList(String requestUserId){
    	List<Request> request = requestRepository.findByRequestUserId(requestUserId);
    	return request;
    }
    public Request checkRequest(String userId,String requestUserId){
    	return requestRepository.findByUserIdAndRequestUserId(userId, requestUserId);
    }
}

