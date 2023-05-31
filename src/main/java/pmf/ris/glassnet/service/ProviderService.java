package pmf.ris.glassnet.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pmf.ris.glassnet.model.Provider;
import pmf.ris.glassnet.model.User;
import pmf.ris.glassnet.repository.AuthorityRepository;
import pmf.ris.glassnet.repository.ProviderRepository;

@Service
@Transactional
public class ProviderService {
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProviderRepository providerRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	public void saveProvider(Provider provider) {
		User currentUser = userService.getAuthenticatedUser();
		
		provider.setUser(currentUser);
		providerRepository.save(provider);
		currentUser.setProvider(provider);
		currentUser.getAuthorities().add(authorityRepository.findProviderAuthority());
		
		userService.reauthenticate(currentUser);
	}
}
