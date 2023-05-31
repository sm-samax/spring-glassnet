package pmf.ris.glassnet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pmf.ris.glassnet.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

}
