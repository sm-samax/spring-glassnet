package pmf.ris.glassnet.filter;

import java.io.IOException;
import java.time.Instant;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import pmf.ris.glassnet.service.UserService;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtDecoder jwtDecoder;

	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getCookies() == null) {
			doFilter(request, response, filterChain);
			return;
		}

		Cookie cookie = null;
		String token = null;

		for (Cookie current : request.getCookies()) {
			if (current.getName().equals("_jwt")) {
				cookie = current;
				token = cookie.getValue();
				break;
			}
		}

		if (token != null) {
			try {
				Jwt decodedToken = jwtDecoder.decode(token);

				if (!expired(decodedToken)) {
					userService.authenticateUser(token);
				} else {
					clearCorruptedToken(cookie, response);
				}
			} catch (Exception e) {
				clearCorruptedToken(cookie, response);
			}
		} 
		
		filterChain.doFilter(request, response);
	}

	private boolean expired(Jwt token) {
		return Instant.now().isAfter(token.getExpiresAt());
	}

	private void clearCorruptedToken(Cookie cookie, HttpServletResponse response) {
		cookie.setValue("");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return;
	}
}
