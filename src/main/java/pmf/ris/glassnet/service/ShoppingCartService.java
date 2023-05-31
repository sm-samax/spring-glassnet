package pmf.ris.glassnet.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pmf.ris.glassnet.model.Product;
import pmf.ris.glassnet.model.User;

@Service
@Transactional
public class ShoppingCartService {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	public Map<Product, Integer> getProducts(HttpServletRequest request) {
		
		Map<Product, Integer> products = new HashMap<>();
		String shoppingCart = null;
		
		
		if(userService.getAuthenticatedUser() != null) {
			shoppingCart = userService.getAuthenticatedUser().getShoppingCart();
		}
		else if(request.getCookies() != null) {
			for(Cookie cookie : request.getCookies()) {
				if(cookie.getName().equals("shopping-cart")) {
					shoppingCart = cookie.getValue();
					break;
				}
			}
		}
		
		if(shoppingCart != null && !"".equals(shoppingCart)) {
			String[] asd = shoppingCart.split("a");
			
			Arrays.asList(asd).stream()
				.forEach(part -> {
					String[] parts = part.split("=");
					Long id = Long.valueOf(parts[0]);
					Integer amount = Integer.valueOf(parts[1]);
					Product product = productService.findById(id);
					if(product != null) {
						products.put(product, amount);													
					}
				});
		}
		
		
		return products;
	}
	
	public void emptyShoppingCart(HttpServletRequest request, HttpServletResponse response) {
		User user = userService.getAuthenticatedUser();
		
		if(user != null) {
			user.setShoppingCart(null);
		}
		else {
			Cookie cookie = new Cookie("shopping-cart", "");
			response.addCookie(cookie);
		}
		
	}

	public void savePurchase(Map<Product, Integer> products) {
		products.entrySet().forEach(e -> {
			Product product = e.getKey();
			int oldValue = product.getSoldCopies() != null ? product.getSoldCopies() : 0;
			product.setSoldCopies(oldValue + e.getValue());
		});
		
	}
}
