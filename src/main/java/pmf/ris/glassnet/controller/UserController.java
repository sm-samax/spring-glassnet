package pmf.ris.glassnet.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pmf.ris.glassnet.model.dto.RegistrationRequest;
import pmf.ris.glassnet.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		model.addAttribute("user", userService.getAuthenticatedUserDTO());
		model.addAttribute("avatar", userService.getAuthenticatedUser().getAvatar());
		return "dashboard";
	}
	
	@PostMapping("/updateUser")
	public String updateUser(@Valid @ModelAttribute("user") RegistrationRequest user) {
		userService.updateUser(user);
		return "redirect:/dashboard";
	}
	
	@ExceptionHandler(BindException.class)
	public String handleIncorrectRequests(BindException ex, Model model) {
	    model.addAttribute("error", "Profile update failed");
	    model.addAttribute("cause", "Please fill every field properly!");
	    return "error";
	}
}
