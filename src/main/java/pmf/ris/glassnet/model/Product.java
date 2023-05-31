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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@SequenceGenerator(name = "product_seq_gen", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_gen")
	private Long id;
	private String name;
	private BigDecimal price;
	private Integer soldCopies = 0;
	
	private Instant creationTime;
	
	@ManyToOne
	private Provider provider;
	
	@OneToOne
	private Image image;
	
	@ManyToMany(mappedBy = "products")
	private List<Purchase> purchases;
}
