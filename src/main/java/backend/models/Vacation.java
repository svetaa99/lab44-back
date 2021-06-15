package backend.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import backend.enums.VacationStatus;
import backend.enums.VacationType;

@Entity
public class Vacation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Doctor doctor;
	
	@Column(name="start", unique=false, nullable=false)
	private LocalDate start;

	@Column(name="finish", unique=false, nullable=false)
	private LocalDate finish;
	
	@Column(name="status", unique=false, nullable=true)
	private VacationStatus status;
	
	@Column(name="type", unique=false, nullable=true)
	private VacationType type;

	public Vacation() {
		
	}
	
	public Vacation(LocalDate start, LocalDate finish, VacationType type) {
		super();
		this.start = start;
		this.finish = finish;
		this.type = type;
	}

	public Vacation(Long id, Doctor doctor, LocalDate start, LocalDate finish, VacationStatus status) {
		super();
		this.id = id;
		this.doctor = doctor;
		this.start = start;
		this.finish = finish;
		this.status = status;
	}
	
	public Vacation(Long id, Doctor doctor, LocalDate start, LocalDate finish, VacationStatus status,
			VacationType type) {
		super();
		this.id = id;
		this.doctor = doctor;
		this.start = start;
		this.finish = finish;
		this.status = status;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getFinish() {
		return finish;
	}

	public void setFinish(LocalDate finish) {
		this.finish = finish;
	}

	public VacationStatus getStatus() {
		return status;
	}

	public void setStatus(VacationStatus status) {
		this.status = status;
	}
	
	public VacationType getType() {
		return type;
	}

	public void setType(VacationType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Vacation [id=" + id + ", doctor=" + doctor + ", start=" + start + ", finish=" + finish + ", status="
				+ status + "]";
	}
}
