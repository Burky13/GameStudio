package sk.tsystems.gamestudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.User;
import sk.tsystems.gamestudio.service.userService.UserService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {

	@Autowired
	private UserService userService;

	private User loggedUser;
	
	public boolean isLogged() {
		return loggedUser != null;
	}

	@RequestMapping("/login")
	public String login(String username, String password) {
		if (username != null && password != null) {
			loggedUser = userService.login(username, password);
		}
		return "user";
	}

	@RequestMapping("/register")
	public String register(User user, Model model) {
		String message;

		if (user.validatepassword()) {
			if (userService.register(user)) {
				message = "You have been registered. Enjoy playing :";
			} else {
				message = "Problem creating user, probably username exists already."; 
			}
		} else {
			message = "Password does not match";
		}
		model.addAttribute("registerMessage",message);
		return "user";
	}
	
	@RequestMapping("/logout")
	public String logout() {
		loggedUser = null;
		return "redirect:/login";
	}
	
	public String getLoggedUsername() {
		if(loggedUser != null) {
		return loggedUser.getUsername();
		}
		else return "anonymous";
	}
	
}
