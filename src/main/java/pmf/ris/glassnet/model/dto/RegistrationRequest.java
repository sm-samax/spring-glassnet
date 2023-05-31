package pmf.ris.glassnet.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import pmf.ris.glassnet.validation.ValidateConfirmPassword;

@Data
@ValidateConfirmPassword
public class RegistrationRequest {
	@NotBlank(message = "Username is mandatory!")
	@Size(min = 4 ,max = 100)
	private String username;
	@NotBlank(message = "Email is mandatory!")
	private String email;
	@NotBlank(message = "Password is mandatory!")
	@Size(min = 6, message = "Password must be at least 6 character long!")
	private String password;
	
	@NotBlank
	@Size(min = 6)
	private String confirmPassword;
}
