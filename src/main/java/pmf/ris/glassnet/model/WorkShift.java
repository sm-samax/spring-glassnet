package pmf.ris.glassnet.model;

import java.time.DayOfWeek;
import java.time.Instant;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pmf.ris.glassnet.model.constants.WorkShiftPolicy;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkShift {

	@Id
	@SequenceGenerator(name = "work_shift_seq_gen", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "work_shift_seq_gen")
	private Long id;
	
	private Instant start;
	private Instant end;
	
	@Enumerated(EnumType.STRING)
	private WorkShiftPolicy policy;
	
	@ElementCollection
	@Size(min = 1)
	private List<DayOfWeek> retention;
	
	@ManyToOne
	private MedicalExpert worker;
	
}
