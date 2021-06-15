package backend.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DoctorTerms {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="doctor_id", unique=false, nullable=false)
	private Long doctorId;

	@Column(name="pharmacy_id", unique=false, nullable=false)
	private Long pharmacyId;

	@Column(name="start", unique=false, nullable=false)
	private LocalDateTime start;
	
	@Column(name="finish", unique=false, nullable=false)
	private LocalDateTime finish;
	
	public DoctorTerms() {
		
	}

	public DoctorTerms(Long doctorId, Long pharmacyId, LocalDateTime start, LocalDateTime finish) {
		super();
		this.doctorId = doctorId;
		this.pharmacyId = pharmacyId;
		this.start = start;
		this.finish = finish;
	}

	public DoctorTerms(Long id, Long doctorId, Long pharmacyId, LocalDateTime start, LocalDateTime finish) {
		super();
		this.id = id;
		this.doctorId = doctorId;
		this.pharmacyId = pharmacyId;
		this.start = start;
		this.finish = finish;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public Long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
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
		if (!(obj instanceof DoctorTerms))
			return false;
		DoctorTerms other = (DoctorTerms) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DoctorTerms [id=" + id + ", doctorId=" + doctorId + ", pharmacyId=" + pharmacyId + ", start=" + start
				+ ", finish=" + finish + "]";
	}
	
	
		
}
