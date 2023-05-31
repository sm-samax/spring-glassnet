package pmf.ris.glassnet.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pmf.ris.glassnet.model.Product;
import pmf.ris.glassnet.model.Provider;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findAllByProvider(Provider provider);
	
	List<Product> findAllByNameContaining(String name, Pageable pageable);

}
