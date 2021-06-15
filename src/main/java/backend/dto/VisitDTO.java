package backend.dto;

import java.time.LocalDateTime;

import backend.models.Doctor;
import backend.models.Patient;
import backend.models.Visit;

public class VisitDTO implements Comparable<VisitDTO>{

	private Long id;
	
	private Patient patient;
	
	private Doctor doctor;
	
	private LocalDateTime start;
	
	private LocalDateTime finish;
	
	
	public VisitDTO() {
		
	}

	public VisitDTO(Long id, Patient patient, Doctor doctor, LocalDateTime start, LocalDateTime finish) {
		super();
		this.id = id;
		this.patient = patient;
		this.doctor = doctor;
		this.start = start;
		this.finish = finish;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Patient getpatient() {
		return patient;
	}


	public void setpatient(Patient patient) {
		this.patient = patient;
	}


	public Doctor getdoctor() {
		return doctor;
	}


	public void setdoctor(Doctor doctor) {
		this.doctor = doctor;
	}


	public LocalDateTime getStart() {
		return start;
	}


	public void setStart(LocalDateTime start) {
		this.start = start;
	}


	public LocalDateTime getFinish() {
		return finish;
	}


	public void setFinish(LocalDateTime finish) {
		this.finish = finish;
	}

	@Override
	public int compareTo(VisitDTO o) {
		return this.getStart().compareTo(o.getStart());
	}
	
	
	
}