package wow.app.account;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import wow.domain.model.User;
import wow.domain.service.user.UserService;

@Controller
@RequestMapping("account")
public class AccountController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	String account(Model model){
		return "account/account";
	}
	@RequestMapping(method = RequestMethod.POST)
	String create(@RequestParam("user_id") String id,
			@RequestParam("user_name") String name,
			@RequestParam("user_password") String pass,Model model){
	    User user = new User();
	    user.setUserId(id);
	    user.setUserName(name);
	    user.setPassword(pass);
		userService.create(user);
		
		model.addAttribute("user",user);
		
		return "redirect:/profile";
	}
}
