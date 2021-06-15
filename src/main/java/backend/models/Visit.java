package backend.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import backend.enums.Status;

@Entity
public class Visit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="patient_id", unique=false, nullable=false)
	private Long patientId;
	
	@Column(name="doctor_id", unique=false, nullable=false)
	private Long doctorId;
	
	@Column(name="start_time", unique=false, nullable=false)
	private LocalDateTime start;
	
	@Column(name="finish_time", unique=false, nullable=true)
	private LocalDateTime finish;
	
	@Column(name="pharmacy_id", unique=false, nullable=false) 
	private Long pharmacy;
	
	@Column(name="status", unique=false, nullable=true)
	private Status status;

	public Visit() {
		
	}
	
	public Visit(Long patientId, Long doctorId, LocalDateTime start, LocalDateTime finish) {
		super();
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.start = start;
		this.finish = finish;
	}

	public Visit(Long id, Long patientId, Long doctorId, LocalDateTime start, LocalDateTime finish) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.start = start;
		this.finish = finish;
	}
	
	public Visit(Long id, Long patientId, Long doctorId, LocalDateTime start, LocalDateTime finish, Long pharmacy,
			Status status) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.start = start;
		this.finish = finish;
		this.pharmacy = pharmacy;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
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
	
	public Long getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Long pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Visit))
			return false;
		Visit other = (Visit) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Visit [id=" + id + ", patientId=" + patientId + ", doctorId=" + doctorId + ", start=" + start
				+ ", finish=" + finish + ", pharmacy=" + pharmacy + ", status=" + status + "]";
	}
	
}
