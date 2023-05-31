package pmf.ris.glassnet.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pmf.ris.glassnet.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	private static final Map<String, String> sortNames = Map.of(
			"Name", "name",
			"Price", "price",
			"Release date", "creationTime"
			);
	
	private String searchTarget;
	private String sort;
	private boolean desc;
	
	@GetMapping("/products")
	public String products(Model model, @RequestParam(defaultValue = "0") Integer page) {
		
		model.addAttribute("products", productService.findSearchResults(page, searchTarget, sort, desc));
		
		return "/products";
	}
	
	@PostMapping("/search")
	public String search(Model model, @RequestParam("sort") String sort, @RequestParam("name") String name, @RequestParam(name = "desc", defaultValue = "false") boolean desc) {
		this.searchTarget = name;
		this.desc = desc;
		this.sort = sortNames.get(sort);
		return "redirect:/products";
	}
	
	@PostMapping("/puttocart")
	public String putToCart(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") Long id,
			@RequestParam(name = "amount", defaultValue = "1") Integer amount) {
		
		if(amount > 0) {
			productService.putToCart(request, response, id, amount);			
		}
		
		return "redirect:/products";
	}
}
