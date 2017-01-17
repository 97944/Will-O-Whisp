package wow.app.edit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import wow.domain.service.tweet.TweetService;
import wow.domain.service.user.UserService;
import wow.domain.service.user.WowUserDetails;
import wow.domain.model.Block;
import wow.domain.model.Favorite;
import wow.domain.model.Follow;
import wow.domain.model.Tweet;
import wow.domain.model.User;

@Controller
@RequestMapping("edit")
public class EditController {

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	String editDisp(@AuthenticationPrincipal WowUserDetails userDetails,Model model){
		User user = userDetails.getUser();
		model.addAttribute("login_user",user);
		System.out.println(user.getUserName() + " " + user.getUserId() + " " + user.getProfile());
		
		List<Block> block = userService.loadBlockUserByUserId(user.getUserId());
		List<User> blockUser = new ArrayList<User>();
		for(int i=0;i<block.size();i++){
			blockUser.add(userService.loadUserByUserId(block.get(i).getBlockUserId()));
		}
		model.addAttribute("block_user",blockUser);
		
		return "edit/edit";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	String editSave(@AuthenticationPrincipal WowUserDetails userDetails,
			@RequestParam("user_name") String userName,
			@RequestParam("profile") String profile,
			@RequestParam("old_password") String oldPassword,
			@RequestParam("new_password") String newPassword,
			@RequestParam("lock") int lock,RedirectAttributes attributes){
		
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        if(encoder.matches(oldPassword,userDetails.getUser().getPassword())){
        	String newPasswordHash = encoder.encode(newPassword);
        	User user = userDetails.getUser();
        	user.setUserName(userName);
        	user.setProfile(profile);
        	user.setPassword(newPasswordHash);
        	user.setLock(lock);
        	
        	userService.update(user);
        }else{
        	System.out.println("パスワード確認失敗");
        }
        attributes.addAttribute("userId",userDetails.getUser().getUserId());
        return "redirect:/profile";
	}
}
	