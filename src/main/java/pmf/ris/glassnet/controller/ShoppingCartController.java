package pmf.ris.glassnet.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pmf.ris.glassnet.model.Product;
import pmf.ris.glassnet.model.User;
import pmf.ris.glassnet.model.dto.JasperProduct;
import pmf.ris.glassnet.model.mapper.JasperProductMapper;
import pmf.ris.glassnet.service.MessageService;
import pmf.ris.glassnet.service.ShoppingCartService;
import pmf.ris.glassnet.service.UserService;

@Controller
public class ShoppingCartController {
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JasperProductMapper jasperProductMapper;
	
	@GetMapping("/shopping-cart")
	public String getShoppingCart(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<Product, Integer> products = shoppingCartService.getProducts(request);
		
		BigDecimal sum = products.entrySet()
				.stream()
				.map(e -> {
					BigDecimal pricePerProduct = e.getKey().getPrice();
					Integer amount = e.getValue();
					return pricePerProduct.multiply(BigDecimal.valueOf(amount));
				}).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
		
		Integer productNumber = products.values().stream().reduce(Integer::sum).orElse(0);
		
		model.addAttribute("products", products);
		model.addAttribute("sum", sum);
		model.addAttribute("productNumber", productNumber);
		
		return "shopping-cart";
	}
	
	@PostMapping("/checkout")
	public void checkout(HttpServletRequest request, HttpServletResponse response) throws JRException, IOException, ServletException {
		
		Map<Product, Integer> products = shoppingCartService.getProducts(request);
		
		shoppingCartService.savePurchase(products);
		
		int totalAmount = products.values().stream().reduce(Integer::sum).orElse(0);
		
		BigDecimal sum = products.entrySet()
				.stream()
				.map(e -> {
					BigDecimal pricePerProduct = e.getKey().getPrice();
					Integer amount = e.getValue();
					return pricePerProduct.multiply(BigDecimal.valueOf(amount));
				}).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
		
		Date now = new Date();
		
		Map<String, Object> params = new HashMap<>();
		params.put("customer", userService.getAuthenticatedUser() != null ? userService.getAuthenticatedUser().getUsername() : "No name");
		params.put("dateOfPurchase", now);
		params.put("sum", sum);
		params.put("totalAmount", totalAmount);
		
		List<JasperProduct> list = products.entrySet().stream().map(e -> {
			Product product = e.getKey();
			int amount = e.getValue();
			BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(amount));
			return jasperProductMapper.toJasperProduct(product, amount, totalPrice);
		}).toList();
		
		JRDataSource source = new JRBeanCollectionDataSource(list);
		InputStream inputStream = getClass().getResourceAsStream("/jasper/purchase_report.jrxml");
		JasperReport report = JasperCompileManager.compileReport(inputStream);
		JasperPrint print = JasperFillManager.fillReport(report, params, source);
		
		response.setContentType("application/x-download");
		response.addHeader("Content-disposition", "attachment; filename=purchase.pdf");
		shoppingCartService.emptyShoppingCart(request, response);
		OutputStream out = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(print, out);
		
		User user = userService.getAuthenticatedUser();
		
		if(user != null) {
			messageService.createMessage(user, "Notification", "You made a purchase at: " + now);
		}
	}
	
	@PostMapping("/clearCart")
	public String clearCart(HttpServletRequest request, HttpServletResponse response) {
		shoppingCartService.emptyShoppingCart(request, response);
		return "redirect:/shopping-cart";
	}
}
