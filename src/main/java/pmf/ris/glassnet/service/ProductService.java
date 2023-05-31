package pmf.ris.glassnet.service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pmf.ris.glassnet.model.Image;
import pmf.ris.glassnet.model.Product;
import pmf.ris.glassnet.model.Provider;
import pmf.ris.glassnet.model.User;
import pmf.ris.glassnet.model.dto.ProductDTO;
import pmf.ris.glassnet.model.mapper.ProductMapper;
import pmf.ris.glassnet.repository.ProductRepository;

@Service
@Transactional
public class ProductService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ProductMapper productMapper;

	public void addProduct(MultipartFile image, ProductDTO product) throws IOException {
		
		Image savedImage = imageService.saveImage(image);
		
		Product savedProduct = productMapper.toProduct(product);
		
		savedProduct.setCreationTime(Instant.now());
		savedProduct.setImage(savedImage);
		savedProduct.setProvider(userService.getAuthenticatedUser().getProvider());

		productRepository.save(savedProduct);
		
		messageService.createMessage(userService.getAuthenticatedUser(), "Notification", String.format("Product '%s' added.", savedProduct.getName()));
	}

	public List<Product> findAllByProvider(Provider provider) {
		if(provider == null) {
			return List.of();
		}
		
		return productRepository.findAllByProvider(provider);
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public List<Product> findSearchResults(Integer page, String searchTarget, String sort, boolean desc) {
		
		Pageable pageable = null;
		
		if(sort != null) {
			if(desc) {
				pageable = PageRequest.of(page, 16, Sort.by(sort).descending());
			} else {			
				pageable = PageRequest.of(page, 16, Sort.by(sort).ascending());
			}
		}
		else {
			pageable = PageRequest.of(page, 16);
		}
		
		
		if(searchTarget != null && !searchTarget.isBlank()) {
			return productRepository.findAllByNameContaining(searchTarget, pageable);
		}
		
		return productRepository.findAll(pageable).toList();
	}

	public Product findById(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	public void updateProduct(Long id, MultipartFile image, ProductDTO product) throws IOException {
		Product savedProduct = productRepository.findById(id).get();
		Image productImage = imageService.saveImage(image);
		savedProduct.setName(product.getName());
		savedProduct.setPrice(product.getPrice());
		savedProduct.setImage(productImage);
		productRepository.save(savedProduct);
		messageService.createMessage(userService.getAuthenticatedUser(), "Notification", String.format("Product '%s' got updated.", savedProduct.getName()));
	}
	
	public ProductDTO dto(Long id) {
		return dto(productRepository.findById(id).get());
	}
	
	public ProductDTO dto(Product product) {
		return productMapper.toDTO(product);
	}

	public void deleteProduct(Long id) {
		String productName = productRepository.findById(id).get().getName();
		productRepository.deleteById(id);
		messageService.createMessage(userService.getAuthenticatedUser(), "Notification", String.format("Product '%s' got deleted.", productName));
	}

	public void putToCart(HttpServletRequest request, HttpServletResponse response, Long id, Integer amount) {
		User user = userService.getAuthenticatedUser();
		String shoppingCart = null;
		
		if(user != null) {
			shoppingCart = user.getShoppingCart();
		}
		else if(request.getCookies() != null) {
			for(Cookie cookie : request.getCookies()) {
				if(cookie.getName().equals("shopping-cart")) {
					shoppingCart = cookie.getValue();
					break;
				}
			}
		}
		
		if(shoppingCart == null) {
			shoppingCart = String.valueOf(id) + "=" + String.valueOf(amount) + "a";
		}
		else {
			String oldValue = shoppingCart;
			
			String prefix = String.valueOf(id) + "=";
			
			int index = oldValue.indexOf(prefix);
			
			if(index != -1) {
				String part = oldValue.substring(index, oldValue.indexOf("a", index));
				String count = part.split("=")[1];
				Long newCount = amount + Long.valueOf(count);
				String newPart = String.valueOf(id) + "=" + String.valueOf(newCount);
				shoppingCart = oldValue.replace(part, newPart);
			} else {
				String appendValue = String.valueOf(id) + "=" + String.valueOf(amount) + "a";
				shoppingCart = oldValue.concat(appendValue);
			}	
		}
		
		if(user != null) {
			user.setShoppingCart(shoppingCart);
		}
		else {
			Cookie cookie = new Cookie("shopping-cart", shoppingCart);
			cookie.setMaxAge(Integer.MAX_VALUE);
			response.addCookie(cookie);
		}
	}
}
