package backend.dto;

import java.util.List;

import backend.models.Medicine;

public class MedicineQuantityDTO {
	
	private Medicine medicine;
	private int quantity;
	
	public MedicineQuantityDTO() {
		
	}
	
	public MedicineQuantityDTO(Medicine medicine, int quantity) {
		this.medicine = medicine;
		this.quantity = quantity;
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
