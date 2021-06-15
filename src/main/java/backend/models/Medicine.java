package backend.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import backend.enums.MedicineTypes;

@Entity
public class Medicine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "type", nullable = false)
	private MedicineTypes type;
	
	@Column(name = "specification", nullable = false)
	private String specification;
	
	@JsonIgnore
	@OneToMany(mappedBy = "medicine")
	private List<PharmacyMedicines> pharmacyMedicines;
	
	@OneToMany(mappedBy = "medicine")
	private List<Reservation> reservations;
	
	@OneToMany(mappedBy = "medicine")
	private List<OrderMedicines> orders;
	
	@OneToMany(mappedBy = "medicine")
	private List<DemandMedicine> demands;
	
	@ManyToMany
	private List<Promotion> promotions;
	
	public Medicine() {}

	public Medicine(Long id, String name, MedicineTypes type, String specification) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.specification = specification;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MedicineTypes getType() {
		return type;
	}

	public void setType(MedicineTypes type) {
		this.type = type;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medicine other = (Medicine) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Medicine [id=" + id + ", name=" + name + ", type=" + type.toString() + ", specification=" + specification + "]";
	}
	
	
}
