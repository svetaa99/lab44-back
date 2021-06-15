package backend.dto;

import backend.models.Medicine;
import backend.models.Pharmacy;
import backend.models.PharmacyMedicines;

public class PharmacyMedicinesDTO {
	
	private Long id;
	private Pharmacy pharmacy;
	private Medicine medicine;
	private double price;
	private int quantity;
	private long startDate;
	private long endDate;
	
	public PharmacyMedicinesDTO() {
		
	}

	public PharmacyMedicinesDTO(Long id, Pharmacy pharmacy, Medicine medicine, double price, int quantity, long startDate, long endDate) {
		super();
		this.id = id;
		this.pharmacy = pharmacy;
		this.medicine = medicine;
		this.price = price;
		this.quantity = quantity;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public PharmacyMedicinesDTO(PharmacyMedicines pm) {
		this(pm.getId(), pm.getPharmacy(), pm.getMedicine(), pm.getPrice(), pm.getQuantity(), pm.getStartDate(), pm.getEndDate());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
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
		PharmacyMedicinesDTO other = (PharmacyMedicinesDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
