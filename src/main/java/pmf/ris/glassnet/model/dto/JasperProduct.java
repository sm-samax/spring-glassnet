package pmf.ris.glassnet.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JasperProduct {
	private String name;
	private BigDecimal price;
	private int amount;
	private BigDecimal totalPrice;
}
