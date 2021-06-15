package backend.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PharmacyMedicines {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne
	@JoinColumn(name = "pharmacy_id")
	Pharmacy pharmacy;
	
	@ManyToOne
	@JoinColumn(name = "medicine_id")
	Medicine medicine;
	
	@Column(name = "price")
	double price;
	
	@Column(name = "quantity", columnDefinition = "Int default '0'")
	int quantity;
	
	@Column
	long startDate;

	@Column
	long endDate;
	
	@Column(columnDefinition = "Int default '0'")
	int requests;
	
	public PharmacyMedicines() {
		
	}
	
	public PharmacyMedicines(Pharmacy pharmacy, Medicine medicine, double price, int quantity, long startDate, long endDate) {
		super();
		this.pharmacy = pharmacy;
		this.medicine = medicine;
		this.price = price;
		this.quantity = quantity;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public PharmacyMedicines(Long id, Pharmacy pharmacy, Medicine medicine, double price, int quantity, long startDate, long endDate) {
		super();
		this.id = id;
		this.pharmacy = pharmacy;
		this.medicine = medicine;
		this.price = price;
		this.quantity = quantity;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public int getRequests() {
		return requests;
	}

	public void setRequests(int requests) {
		this.requests = requests;
	}
	
	public void incRequests() {
		this.requests++;
	}
	
	public void decRequests() {
		this.requests--;
	}
	
	public void incQuantity(int by) {
		this.quantity += by;
	}
	
	public void decQuantity() {
		this.quantity --;
	}
}
