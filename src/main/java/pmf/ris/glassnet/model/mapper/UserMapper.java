package pmf.ris.glassnet.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.core.userdetails.UserDetails;

import pmf.ris.glassnet.model.PersistedAuthority;
import pmf.ris.glassnet.model.User;
import pmf.ris.glassnet.model.dto.RegistrationRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
	default UserDetails toUserDetails(User user) {
		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getEmail())
				.password(user.getPassword())
				.authorities(user.getAuthorities())
				.build();
	}
	
	@Mapping(source = "encodedPassword", target = "password")
	User toUser(RegistrationRequest registration, String encodedPassword, List<PersistedAuthority> authorities);

	RegistrationRequest toDTO(User user);
}
