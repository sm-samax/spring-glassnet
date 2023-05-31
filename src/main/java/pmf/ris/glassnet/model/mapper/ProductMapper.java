package pmf.ris.glassnet.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import pmf.ris.glassnet.model.Product;
import pmf.ris.glassnet.model.dto.ProductDTO;

@Mapper(componentModel = "Spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
	Product toProduct(ProductDTO dto);
	
	ProductDTO toDTO(Product product);
}
