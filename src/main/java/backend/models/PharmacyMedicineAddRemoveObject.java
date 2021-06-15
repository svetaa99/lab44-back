package backend.models;

public class PharmacyMedicineAddRemoveObject {
	
	private Long pharmacyId;
	private Long medicineId;
	private double price;
	private int quantity;
	private long startDate;
	private long endDate;
	
	public PharmacyMedicineAddRemoveObject() {
		
	}

	public PharmacyMedicineAddRemoveObject(Long pharmacyId, Long medicineId, double price, int quantity, long startDate, long endDate) {
		super();
		this.pharmacyId = pharmacyId;
		this.medicineId = medicineId;
		this.price = price;
		this.quantity = quantity;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public PharmacyMedicineAddRemoveObject(Long pharmacyId, Long medicineId) {
		super();
		this.pharmacyId = pharmacyId;
		this.medicineId = medicineId;
		this.price = 0;
		this.quantity = 0;
	}
	
	public PharmacyMedicineAddRemoveObject(Long pharmacyId, Long medicineId, int quantity) {
		super();
		this.pharmacyId = pharmacyId;
		this.medicineId = medicineId;
		this.price = 0;
		this.quantity = quantity;
	}
	
	public PharmacyMedicineAddRemoveObject(Long pharmacyId, Long medicineId, double price) {
		super();
		this.pharmacyId = pharmacyId;
		this.medicineId = medicineId;
		this.price = price;
		this.quantity = 0;
	}

	public Long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public Long getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(Long medicineId) {
		this.medicineId = medicineId;
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
	public String toString() {
		return "PharmacyMedicineAddRemoveObject [pharmacyId=" + pharmacyId + ", medicineId=" + medicineId + ", price="
				+ price + ", quantity=" + quantity + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
