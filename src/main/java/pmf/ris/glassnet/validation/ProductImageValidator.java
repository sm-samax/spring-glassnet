package pmf.ris.glassnet.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class ProductImageValidator implements ConstraintValidator<ValidProductImages, MultipartFile[]>{

	@Override
	public boolean isValid(MultipartFile[] value, ConstraintValidatorContext context) {
		boolean valid = false;
		
		for(MultipartFile file : value) {
			if(!"".equals(file.getOriginalFilename()) && file.getSize() > 0L) {
				valid = true;
			}
			
			if(file.getSize() > 8388608L)
			{
				return false;
			}
		}
		
		return valid;
	}

}
