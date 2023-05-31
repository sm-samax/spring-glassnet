package pmf.ris.glassnet.model.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserDTO {
	@NotBlank
	private String username;
	@NotBlank
	private String email;
	@NotBlank
	private String password;
}
