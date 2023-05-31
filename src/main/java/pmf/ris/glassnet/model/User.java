package pmf.ris.glassnet.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(exclude = "provider")
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@SequenceGenerator(name = "user_seq_gen", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = true)
	private String shoppingCart;
	
	@OneToOne(optional = true)
	private Image avatar;
	
	@OneToOne(optional = true)
	private MedicalExpert doctor;
	
	@OneToOne(optional = true)
	private Provider provider;
	
	@ManyToMany
	private List<PersistedAuthority> authorities;
	
	@OneToMany(mappedBy = "subject")
	private List<Message> messages = new ArrayList<>();
	
	@OneToMany(mappedBy = "patient")
	private List<Appointment> appointments;
	
	@OneToMany(mappedBy = "customer")
	private List<Purchase> purchases = new ArrayList<>();
}
