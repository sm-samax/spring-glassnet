package pmf.ris.glassnet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pmf.ris.glassnet.model.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long>{

}
