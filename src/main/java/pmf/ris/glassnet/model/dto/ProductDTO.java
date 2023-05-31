package pmf.ris.glassnet.model.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProductDTO {
	@NotBlank
	private String name;
	@NotNull
	@Min(0)
	private BigDecimal price;
}
