package pmf.ris.glassnet.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class MedicalExpert {
	@Id
	@SequenceGenerator(name = "medical_expert_seq_gen", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medical_expert_seq_gen")
	private Long id;
	
	private String professionalName;
	private String professionalTitle;
	private Integer experience;
	
	@OneToOne(optional = false)
	private User user;
	
	@OneToOne(optional = false)
	private Address address;
	
	@OneToMany(mappedBy = "worker")
	private List<WorkShift> workShifts;
	
	@OneToMany(mappedBy = "doctor")
	private List<Appointment> appointments;
}
