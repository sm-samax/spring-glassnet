package pmf.ris.glassnet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pmf.ris.glassnet.exception.EmailAlreadyInUseException;
import pmf.ris.glassnet.exception.IncorrectLoginException;
import pmf.ris.glassnet.model.PersistedAuthority;
import pmf.ris.glassnet.model.User;
import pmf.ris.glassnet.model.dto.LoginRequest;
import pmf.ris.glassnet.model.dto.RegistrationRequest;
import pmf.ris.glassnet.model.mapper.UserMapper;
import pmf.ris.glassnet.repository.AuthorityRepository;
import pmf.ris.glassnet.repository.UserRepository;

@Service
@Transactional
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private MessageService messageService;
	
	public String registerUser(RegistrationRequest registration) {
		if(userRepository.findByEmail(registration.getEmail()) != null) {
			throw new EmailAlreadyInUseException();
		}
		
		String encodedPassword = passwordEncoder.encode(registration.getPassword());
		List<PersistedAuthority> authorities = List.of(authorityRepository.findUserAuthority());
		User user = userRepository.save(mapper.toUser(registration, encodedPassword, authorities));
		
		String token = tokenService.generateToken(user);
		authenticateUser(token);
		return token;
	}

	public String loginUser(LoginRequest login) {
		User userFromDatabase = userRepository.findByEmail(login.getEmail());
		if(userFromDatabase == null || !passwordEncoder.matches(login.getPassword(), userFromDatabase.getPassword())) {
			throw new IncorrectLoginException();
		}
		
		String token = tokenService.generateToken(userFromDatabase);
		authenticateUser(token);
		return token;
	}
	
	public void updateUser(RegistrationRequest user) {
		User authUser = getAuthenticatedUser();
		if(!authUser.getEmail().equals(user.getEmail())) {
			if(userRepository.findByEmail(user.getEmail()) != null) {
				throw new EmailAlreadyInUseException();
			}
		}
		
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		User updatedUser = mapper.toUser(user, encodedPassword, authUser.getAuthorities());
		updatedUser.setId(authUser.getId());
		updatedUser.setAvatar(authUser.getAvatar());
		
		userRepository.save(updatedUser);
		
		reauthenticate(updatedUser);
		messageService.createMessage(getAuthenticatedUser(), "Notification", "Your profile has been updated.");
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		return mapper.toUserDetails(user);
	}

	public void reauthenticate(User user) {
		 String token = tokenService.generateToken(user);
		 authenticateUser(token);
	}
	
	public void authenticateUser(String token) {
		BearerTokenAuthenticationToken auth = new BearerTokenAuthenticationToken(token);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
	public User getAuthenticatedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String token = auth.getName();
		String email = null;
		try {
			email = tokenService.extractEmail(token);
		} catch (Exception e) {
			return null;
		}
		return userRepository.findByEmail(email);
	}
	
	public RegistrationRequest getAuthenticatedUserDTO() {
		return mapper.toDTO(getAuthenticatedUser());
	}

	public boolean hasAuthority(String authority) {
		return getAuthenticatedUser().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(authority));
	}
}
