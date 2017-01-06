package wow.app.profile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import wow.domain.service.user.UserService;
import wow.domain.model.User;

@Controller
@RequestMapping("profile")
public class ProfileController {

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	String listUser(Model model) {

		List<User> user = userService.loadUserByUserId("admin");
		model.addAttribute("loginUser",user);
		model.addAttribute("user", user);
		return "profile/profile";
	}
	@RequestMapping(method = RequestMethod.POST)
	String dispTweet(Model model) {
		List<User> user = userService.loadUserByUserId("admin");
		model.addAttribute("user", user);
		return "redirect:/timeLine";
	}
}
