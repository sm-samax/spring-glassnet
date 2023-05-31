package pmf.ris.glassnet.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import pmf.ris.glassnet.model.PersistedAuthority;
import pmf.ris.glassnet.model.User;

@Service
public class TokenService {

	@Autowired
	private JwtEncoder encoder;
	
	@Autowired
	private JwtDecoder decoder;
	
	public String generateToken(User user) {
		Instant now = Instant.now();
		
		String scope = user.getAuthorities().stream()
				.map(PersistedAuthority::getAuthority)
				.collect(Collectors.joining(" "))
				.concat(" GUEST");
		
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuedAt(now)
				.expiresAt(now.plus(15, ChronoUnit.MINUTES))
				.issuer("self")
				.subject(user.getEmail())
				.claim("scope", scope)
				.build();
		
		return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}
	
	public String extractEmail(String token) {
		return decoder.decode(token).getSubject();
	}
}
