package pmf.ris.glassnet.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Provider {
	@Id
	@SequenceGenerator(name = "provider_seq_gen", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "provider_seq_gen")
	private Long id;
	
	private String companyName;
	
	@OneToOne(optional = false)
	private User user;
	
	@OneToOne(optional = true, cascade = CascadeType.ALL)
	private Address address;
	
	@OneToMany(mappedBy = "provider")
	private List<Product> products;
}
