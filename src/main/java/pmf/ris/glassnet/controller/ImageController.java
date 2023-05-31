package pmf.ris.glassnet.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import pmf.ris.glassnet.service.ImageService;

@MultipartConfig(
		maxFileSize=8388608L,
		maxRequestSize=10485760
		)
@Controller
public class ImageController {

	@Autowired
	private ImageService imageService;
	
	@PostMapping("/uploadAvatar")
	public String uploadAvatar(@RequestParam("file") MultipartFile multipartFile) throws IOException, SQLException {
		
		if(multipartFile.getSize() > 0) {
			imageService.saveAvatar(multipartFile.getOriginalFilename(), multipartFile);			
		}
		
		return "redirect:/dashboard";
	}
	
	@ExceptionHandler({IOException.class, SQLException.class})
	public String handleFileUploadException(Model model) {
		model.addAttribute("error", "File upload error");
		model.addAttribute("cause", "The uploaded file was not proper!");
		return "error";
	}
}
