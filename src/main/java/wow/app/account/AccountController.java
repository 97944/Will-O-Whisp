package wow.app.account;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
	@RequestMapping(params = "cancel" , method = RequestMethod.POST)
	String cancel(){
		return "login/loginForm";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	String create(@RequestParam("user_id") String id,
			@RequestParam("user_name") String name,
			@RequestParam("user_password") String pass,
			RedirectAttributes attributes,Model model){
		
		System.out.println("アカウント作成開始");
		int count = 0;
		if(id.length() < 1 || 15 < id.length() || !id.matches("[0-9a-zA-Z_]+")){
			model.addAttribute("id_error",true);
			count++;
		}else{
			User dummy = userService.loadUserByUserId(id);
			if(dummy != null){
				model.addAttribute("not_unique",true);
				count++;
			}
		}
		if(name.length() < 1 || 20 < name.length()){
			model.addAttribute("name_error",true);
			count++;
		}
		if(pass.length() < 4 || 20 < pass.length() || !pass.matches("[0-9a-zA-Z]+")){
			model.addAttribute("pass_error",true);
			count++;
		}
		if(count != 0 ){
			model.addAttribute("id",id);
			model.addAttribute("name",name);
			model.addAttribute("pass",pass);
			return "account/createForm";
		}
		
		
	    // パスワードを暗号化する
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordHash = encoder.encode(pass);
	    
        User user = new User();
	    user.setUserId(id);
	    user.setUserName(name);
	    user.setPassword(passwordHash);
	    user.setHeadPictureUrl("img/NoImage.png");
	    user.setTopPictureUrl("img/NoImage.png");
	    
		userService.create(user);
		System.out.println("アカウント作成完了");
		System.out.println("ハッシュ化前:" + pass);
		System.out.println("ハッシュ化後：" + user.getPassword());
		attributes.addFlashAttribute("user",user);
        return "redirect:/account/create?finish";
	}
	
	@RequestMapping(value = "create", params = "finish", method = RequestMethod.GET)
    String createFinish() {
        return "account/createFinish";
    }
}
