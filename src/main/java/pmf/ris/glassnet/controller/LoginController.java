package pmf.ris.glassnet.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pmf.ris.glassnet.exception.IncorrectLoginException;
import pmf.ris.glassnet.model.dto.LoginRequest;
import pmf.ris.glassnet.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String loginpage(Model model) {
		model.addAttribute("user", new LoginRequest());		
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("user") LoginRequest user,
			HttpServletResponse response) {
	
		String token = userService.loginUser(user);
		
		response.addHeader("Set-Cookie", "_jwt=" + token + "; HttpOnly; Secure; SameSite=Lax;");
		return "redirect:/index";
	}
	
	@ExceptionHandler({BindException.class, IncorrectLoginException.class})
	public String handleIncorrectLogin(Model model) {
		model.addAttribute("error", "Login failed");
		model.addAttribute("cause", "The email or password was incorrect!");
		return "error";
	}
}
