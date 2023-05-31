package pmf.ris.glassnet.model.mapper;

import java.math.BigDecimal;

import org.mapstruct.Mapper;

import pmf.ris.glassnet.model.Product;
import pmf.ris.glassnet.model.dto.JasperProduct;

@Mapper(componentModel = "spring")
public interface JasperProductMapper {

	JasperProduct toJasperProduct(Product product, int amount, BigDecimal totalPrice);
}
