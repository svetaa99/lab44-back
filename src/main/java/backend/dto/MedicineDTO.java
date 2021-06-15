package backend.dto;

import java.util.ArrayList;
import java.util.List;

import backend.enums.MedicineTypes;
import backend.models.Medicine;
import backend.models.Pharmacy;

public class MedicineDTO {

	private Long id;
	private String name;
	private MedicineTypes type;
	private String specification;
	private List<Pharmacy> pharmacies = new ArrayList<>();
	
	public MedicineDTO(Medicine m) {
		this(m.getId(), m.getName(), m.getType(), m.getSpecification());
	}

	public MedicineDTO(Long id, String name, MedicineTypes type, String specification) {
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
	public String toString() {
		return "MedicineDTO [id=" + id + ", name=" + name + ", type=" + type + ", specification=" + specification
				+ ", pharmacies=" + pharmacies + "]";
	}
	
}
