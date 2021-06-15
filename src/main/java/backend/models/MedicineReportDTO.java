package backend.models;

public class MedicineReportDTO {

	private Medicine medicine;
	
	private boolean available;
	
	private boolean allergic;
	
	public MedicineReportDTO() {
		
	}
	
	public MedicineReportDTO(Medicine medicine) {
		super();
		this.medicine = medicine;
	}

	public MedicineReportDTO(Medicine medicine, boolean available, boolean allergic) {
		super();
		this.medicine = medicine;
		this.available = available;
		this.allergic = allergic;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isAllergic() {
		return allergic;
	}

	public void setAllergic(boolean allergic) {
		this.allergic = allergic;
	}
	
	
	
}
