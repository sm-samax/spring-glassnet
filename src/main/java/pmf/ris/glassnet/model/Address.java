package pmf.ris.glassnet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

	@Id
	@SequenceGenerator(name = "address_seq_gen", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq_gen")
	private Long id;
	
	private String state;
	private String province;
	private String city;
	private String zip;
	private String street;
	private Integer streetNumber;
	
	@OneToOne
	private MedicalExpert doctor; 
}
