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

import pmf.ris.glassnet.exception.EmailAlreadyInUseException;
import pmf.ris.glassnet.model.dto.RegistrationRequest;
import pmf.ris.glassnet.service.UserService;

@Controller
public class RegistrationController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/register")
	public String registration(Model model) {
		model.addAttribute("user", new RegistrationRequest());		
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user") RegistrationRequest user,
			HttpServletResponse response) {
		String token = userService.registerUser(user);
		response.addHeader("Set-Cookie", "_jwt=" + token + "; HttpOnly; Secure; SameSite=Lax;");
		return "redirect:/index";
	}
	
	@ExceptionHandler(BindException.class)
	public String handleIncorrectRequests(BindException ex, Model model) {
	    model.addAttribute("error", "Registration failed");
	    model.addAttribute("cause", "Please fill every mandatory field!");
	    return "error";
	}
	
	@ExceptionHandler(EmailAlreadyInUseException.class)
	public String handleEmailAlreadyInUse(Model model) {
		model.addAttribute("error", "Registration failed");
		model.addAttribute("cause", "The given email is already being used! Please try with a different one!");
		return "error";
	}
}
