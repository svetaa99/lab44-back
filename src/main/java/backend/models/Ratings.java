package backend.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ratings {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "obj_id", unique=false, nullable=false)
	private Long objId;
	
	@Column(name = "type", unique=false, nullable=false)
	private int type;
	
	@Column(name="patient_id", unique=false, nullable=false)
	private Long patientId;
	
	@Column(name = "mark", unique=false, nullable=false)
	private int mark;
	
	public Ratings() {
		
	}

	public Ratings(Long id, Long objId, int type, Long patientId, int mark) {
		super();
		this.id = id;
		this.objId = objId;
		this.type = type;
		this.patientId = patientId;
		this.mark = mark;
	}
	
	public Ratings(Long objId, int type, Long patientId, int mark) {
		super();
		this.objId = objId;
		this.type = type;
		this.patientId = patientId;
		this.mark = mark;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getObjId() {
		return objId;
	}

	public void setObjId(Long objId) {
		this.objId = objId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + mark;
		result = prime * result + ((objId == null) ? 0 : objId.hashCode());
		result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
		result = prime * result + type;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ratings other = (Ratings) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mark != other.mark)
			return false;
		if (objId == null) {
			if (other.objId != null)
				return false;
		} else if (!objId.equals(other.objId))
			return false;
		if (patientId == null) {
			if (other.patientId != null)
				return false;
		} else if (!patientId.equals(other.patientId))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ratings [id=" + id + ", objId=" + objId + ", type=" + type + ", patientId=" + patientId + ", mark="
				+ mark + "]";
	}
	
	
	
}
