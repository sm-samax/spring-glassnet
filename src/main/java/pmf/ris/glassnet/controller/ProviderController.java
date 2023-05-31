package pmf.ris.glassnet.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pmf.ris.glassnet.model.Address;
import pmf.ris.glassnet.model.Product;
import pmf.ris.glassnet.model.Provider;
import pmf.ris.glassnet.model.dto.JasperProduct;
import pmf.ris.glassnet.model.dto.ProductDTO;
import pmf.ris.glassnet.model.mapper.JasperProductMapper;
import pmf.ris.glassnet.service.ProductService;
import pmf.ris.glassnet.service.ProviderService;
import pmf.ris.glassnet.service.ShoppingCartService;
import pmf.ris.glassnet.service.UserService;

@Controller
public class ProviderController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private JasperProductMapper jasperProductMapper;
	
	private Long recentId;
	
	@GetMapping("/provider-menu")
	public String providerMenu(Model model) {
		Provider provider = new Provider();
		provider.setAddress(new Address());
		
		List<Product> products = productService.findAllByProvider(
				userService.getAuthenticatedUser().getProvider());
		
		model.addAttribute("isProvider", userService.hasAuthority("PROVIDER"));
		model.addAttribute("provider", provider);
		model.addAttribute("product", new ProductDTO());
		model.addAttribute("products", products);		
		return "provider-menu";
	}
	
	@PostMapping("/becomeProvider")
	public String becomeProvider(@ModelAttribute Provider provider) {
		providerService.saveProvider(provider);
		return "redirect:/provider-menu";
	}
	
	@PostMapping("/addProduct")
	public String addProduct(@RequestParam("image") MultipartFile image, @Valid @ModelAttribute ProductDTO product) throws IOException {
		productService.addProduct(image, product);
		return "redirect:/provider-menu";
	}
	
	@GetMapping("/updateProduct")
	public String updateProduct(Model model, @RequestParam("id") Long id) {
		Provider provider = userService.getAuthenticatedUser().getProvider();
		Product product = productService.findById(id);
		
		if(provider == null || !product.getProvider().equals(provider)) {
			throw new AccessDeniedException("");			
		}
		
		ProductDTO dto = productService.dto(product);
		
		this.recentId = id;
		
		model.addAttribute("product", dto);
		return "update-product";
	}
	
	@PostMapping("/updateProduct")
	public String updateProduct(@RequestParam("image") MultipartFile image, @Valid @ModelAttribute ProductDTO product) throws IOException {
		productService.updateProduct(recentId, image, product);
		return "redirect:/provider-menu";
	}
	
	@GetMapping("/deleteProduct")
	public String deleteProduct(@RequestParam Long id) {
		Provider provider = userService.getAuthenticatedUser().getProvider();
		Product product = productService.findById(id);
		
		if(provider == null || !product.getProvider().equals(provider)) {
			throw new AccessDeniedException("");			
		}
		
		productService.deleteProduct(id);
		return "redirect:/provider-menu";
	}
	
	@PostMapping("salesReport")
	public void salesReport(HttpServletRequest request, HttpServletResponse response) throws JRException, IOException {
		
		List<Product> products = userService.getAuthenticatedUser().getProvider().getProducts();
		
		BigDecimal sum = products
				.stream()
				.map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getSoldCopies())))
				.reduce(BigDecimal.ZERO ,BigDecimal::add);
		
		int totalAmount = products.stream().mapToInt(Product::getSoldCopies).sum();

		String company = userService.getAuthenticatedUser().getProvider().getCompanyName();
		
		Map<String, Object> params = new HashMap<>();
		params.put("company", company != null ? company : "Not specified");
		params.put("provider", userService.getAuthenticatedUser().getUsername());
		params.put("sum", sum);
		params.put("totalAmount", totalAmount);
		
		List<JasperProduct> list = products.stream().map(p ->
			jasperProductMapper.toJasperProduct(p, p.getSoldCopies(), p.getPrice().multiply(BigDecimal.valueOf(p.getSoldCopies())))).toList();
		
		JRDataSource source = new JRBeanCollectionDataSource(list);
		InputStream inputStream = getClass().getResourceAsStream("/jasper/sales_report.jrxml");
		JasperReport report = JasperCompileManager.compileReport(inputStream);
		JasperPrint print = JasperFillManager.fillReport(report, params, source);
		
		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=purchase.pdf");
		shoppingCartService.emptyShoppingCart(request, response);
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(print, out);
	}
	
	
	@ExceptionHandler({BindException.class, IOException.class})
	public String handleIncorrectRequests(Model model) {
	    model.addAttribute("error", "Product creation failed");
	    model.addAttribute("cause", "Fill every mandatory field and make sure images are bellow 10MB!");
	    return "error";
	}
	
	@ExceptionHandler({AccessDeniedException.class, NoSuchElementException.class})
	public String handleAccessDenied(Model model) {
	    model.addAttribute("error", "Permission denied");
	    model.addAttribute("cause", "The given resource is not available!");
	    return "error";
	}
}
