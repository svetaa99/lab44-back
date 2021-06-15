package backend.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Penalty {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="patient_id", unique=false, nullable=false)
	private Long patientId;
	
	@Column(name="date", unique=false, nullable=true)
	private LocalDate date;
	
	public Penalty() {
		
	}

	public Penalty(Long patient, LocalDate date) {
		super();
		this.patientId = patient;
		this.date = date;
	}
	
	public Penalty(Long id, Long patient, LocalDate date) {
		super();
		this.id = id;
		this.patientId = patient;
		this.date = date;
	}
	
	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Penalty [patient=" + patientId + ", date=" + date + "]";
	}
}
