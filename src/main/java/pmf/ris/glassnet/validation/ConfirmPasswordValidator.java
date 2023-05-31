package pmf.ris.glassnet.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import pmf.ris.glassnet.model.dto.RegistrationRequest;

public class ConfirmPasswordValidator implements ConstraintValidator<ValidateConfirmPassword, RegistrationRequest>{

	@Override
	public boolean isValid(RegistrationRequest value, ConstraintValidatorContext context) {
		return value.getPassword().equals(value.getConfirmPassword());
	}

}
