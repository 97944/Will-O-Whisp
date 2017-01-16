package wow.app.account;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import wow.domain.model.RoleName;
import wow.domain.model.User;
import wow.domain.service.user.UserService;

@Controller
@RequestMapping("account")
public class AccountController {
	
	@Autowired
	UserService userService;
	
	@ModelAttribute
    AccountForm setupForm() {
        return new AccountForm();
    }
	
	@RequestMapping(value = "create", params = "form", method = RequestMethod.GET)
    String createForm() {
		System.out.println("新規作成開始");
        return "account/createForm";
    }
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	String create(@RequestParam("user_id") String id,
			@RequestParam("user_name") String name,
			@RequestParam("user_password") String pass,RedirectAttributes attributes){
		
		System.out.println("アカウント作成開始");
		
	    // パスワードを暗号化する
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordHash = encoder.encode(pass);
	    
        User user = new User();
	    user.setUserId(id);
	    user.setUserName(name);
	    user.setPassword(passwordHash);
	    
		userService.create(user);
		
		attributes.addFlashAttribute("user",user);
        return "redirect:/account/create?finish";
	}
	
	@RequestMapping(value = "create", params = "finish", method = RequestMethod.GET)
    String createFinish() {
        return "account/createFinish";
    }
}
