package backend.dto;

import backend.models.DemandMedicine;
import backend.models.Medicine;

public class DemandMedicineDTO {
	
	private Long id;
	private Long pharmacyId;
	private Medicine medicine;
	private int quantity;
	
	public DemandMedicineDTO() {
		
	}
	
	public DemandMedicineDTO(DemandMedicine dm) {
		this(dm.getId(), dm.getPharmacy().getId(), dm.getMedicine(), dm.getQuantity());
	}
	
	public DemandMedicineDTO(Long pharmacyId, Medicine medicine, int quantity) {
		super();
		this.pharmacyId = pharmacyId;
		this.medicine = medicine;
		this.quantity = quantity;
	}

	public DemandMedicineDTO(Long id, Long pharmacyId, Medicine medicine, int quantity) {
		super();
		this.id = id;
		this.pharmacyId = pharmacyId;
		this.medicine = medicine;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
	
}
