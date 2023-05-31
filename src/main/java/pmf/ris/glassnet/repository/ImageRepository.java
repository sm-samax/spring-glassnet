package pmf.ris.glassnet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pmf.ris.glassnet.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
