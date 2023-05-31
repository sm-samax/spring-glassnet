package pmf.ris.glassnet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pmf.ris.glassnet.service.MessageService;
import pmf.ris.glassnet.service.UserService;

@Controller
public class MessageController {

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/messages")
	public String messages(Model model) {
		model.addAttribute("messages", userService.getAuthenticatedUser().getMessages());
		return "messages";
	}
	
	@PostMapping("/deleteMessage")
	public String deleteMessage(@RequestParam Long id) {
		messageService.deleteMessage(id);
		return "redirect:/messages";
	}
}
