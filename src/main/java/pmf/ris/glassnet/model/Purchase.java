package pmf.ris.glassnet.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
	
	@Id
	@SequenceGenerator(name = "purchase_seq_gen", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_seq_gen")
	private Long id;
	
	private Instant date;
	
	@Min(0)
	private BigDecimal price;
	
	@ManyToOne
	private User customer;
	
	@ManyToMany
	private List<Product> products;
}
