package wow.app.profile;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import wow.domain.service.user.UserService;
import wow.domain.model.User;

@Controller
@RequestMapping("profile")
public class ProfileController {

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	String listUser(@Param("userId") String userId,Model model) {
		if(userId == null){
			userId = "admin";
		}
		User user = userService.loadUserByUserId(userId);
		model.addAttribute("loginUser",user);
		model.addAttribute("user", user);
		return "profile/profile";
	}
	@RequestMapping(method = RequestMethod.POST)
	String dispTweet(@RequestParam("userId") String userId,RedirectAttributes attributes) {
		System.out.println("プロフィール"+ userId);
		attributes.addAttribute("userId",userId);
		return "redirect:/timeLine";
	}
}
