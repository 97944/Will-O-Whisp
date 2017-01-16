package wow.domain.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import wow.domain.model.User;
import wow.domain.repository.user.UserRepository;

@Service
@Transactional
public class WowUserDetailsService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO 自動生成されたメソッド・スタブ
		User user = userRepository.findOne(username);
		if (user == null) {
			throw new UsernameNotFoundException(username + " is not found");
		}
		return new WowUserDetails(user);
	}
}
