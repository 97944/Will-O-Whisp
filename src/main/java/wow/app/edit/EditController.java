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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import wow.domain.service.tweet.TweetService;
import wow.domain.service.user.UserService;
import wow.domain.service.user.WowUserDetails;
import wow.OtherLogic;
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
	@RequestMapping(params = "unblock_btn" ,method = RequestMethod.POST)
	String unBlock(@AuthenticationPrincipal WowUserDetails userDetails,
			@RequestParam("unblock_user_id") String[] unBlockUserId,
			@RequestParam("unblock_btn") int index){
		System.out.println(userDetails.getUser().getUserId() + " " + unBlockUserId);
		System.out.println(index);
		Block block = userService.searchLoginUserBlocked(unBlockUserId[index],userDetails.getUser().getUserId());
		
		if(block != null){
			System.out.println("ブロック解除");
			userService.unblock(block.getBlockId());
		}else{
			System.out.println("ブロック解除できないよー");
		}
		
		return "redirect:/edit";
	}
	@RequestMapping(params = "cancel",method = RequestMethod.POST)
	String cancel(){
		return "redirect:/timeLine";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	String editSave(@AuthenticationPrincipal WowUserDetails userDetails,
			@RequestParam("user_name") String userName,
			@RequestParam("profile") String profile,
			@RequestParam("old_password") String oldPassword,
			@RequestParam("new_password") String newPassword,
			@RequestParam("new_password2") String newPassword2,
			@RequestParam("lock") int lock,
			@RequestParam("topPicture")MultipartFile topPicture,
			@RequestParam("headPicture")MultipartFile headPicture,
			RedirectAttributes attributes,Model model){
		
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = userDetails.getUser();
        
        int count = 0;
        
        if(encoder.matches(oldPassword,userDetails.getUser().getPassword())){
        	if(newPassword.length() < 4 || 20 < newPassword.length() || !newPassword.matches("[0-9a-zA-Z]+")){
    			model.addAttribute("pass_error",true);
    			count++;
    		}
        	if(!(newPassword.equals(newPassword2))){
        		model.addAttribute("check_error",true);
        		count++;
        	}
        	if(count != 0){
        		model.addAttribute("login_user",user);
        		return "edit/edit";
        	}
        	String newPasswordHash = encoder.encode(newPassword);
        	
        	user.setPassword(newPasswordHash);
        	
        	userService.update(user);
        }else{
        	System.out.println("パスワード確認失敗");
        }
        if(userName.length() < 1 || 20 < userName.length()){
        	model.addAttribute("name_error",true);
        	count++;
        }
        if(160 < profile.length()){
        	model.addAttribute("profile_error",true);
        	count++;
        }
        if(count != 0){
        	model.addAttribute("login_user",user);
        	return "edit/edit";
        }
        user.setUserName(userName);
        user.setProfile(profile);
        user.setLock(lock);
        user.setTopPicture(OtherLogic.multipartFileToHexString(topPicture));
        user.setHeadPicture(OtherLogic.multipartFileToHexString(headPicture));
        
        userService.update(user);
        
        attributes.addAttribute("userId",userDetails.getUser().getUserId());
        return "redirect:/profile";
	}
}
	