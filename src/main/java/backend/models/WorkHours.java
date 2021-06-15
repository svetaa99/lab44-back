package backend.models;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class WorkHours {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	//@Column(name="doctor_id", unique=false, nullable=false)
	private Doctor doctor;
	
	@OneToOne
	//@Column(name="pharmacy_id", unique=false, nullable=false)
	private Pharmacy pharmacy;
	
	@Column(name="start_time", unique=false, nullable=false)
	private LocalTime startTime;
	
	@Column(name="finish_time", unique=false, nullable=false)
	private LocalTime finishTime;
	
	public WorkHours() {
		
	}

	public WorkHours(Doctor doctor, Pharmacy pharmacy, LocalTime startTime, LocalTime finishTime) {
		super();
		this.doctor = doctor;
		this.pharmacy = pharmacy;
		this.startTime = startTime;
		this.finishTime = finishTime;
	}

	public WorkHours(Long id, Doctor doctor, Pharmacy pharmacy, LocalTime startTime, LocalTime finishTime) {
		super();
		this.id = id;
		this.doctor = doctor;
		this.pharmacy = pharmacy;
		this.startTime = startTime;
		this.finishTime = finishTime;
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

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(LocalTime finishTime) {
		this.finishTime = finishTime;
	}

	@Override
	public String toString() {
		return "WorkHours [id=" + id + ", doctor=" + doctor + ", pharmacy=" + pharmacy + ", startTime=" + startTime
				+ ", finishTime=" + finishTime + "]";
	}
	
}
