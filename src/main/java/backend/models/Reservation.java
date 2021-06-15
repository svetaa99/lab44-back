package backend.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import backend.enums.Status;

@Entity
@Table(name="reservations")
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne
	@JoinColumn(name = "patient_id")
	Patient patient;
	
	@ManyToOne
	@JoinColumn(name = "pharmacy_id")
	Pharmacy pharmacy;
	
	@ManyToOne
	@JoinColumn(name = "medicine_id")
	Medicine medicine;
	
	@Column(name = "date")
	long date;
	
	@Column(name = "quantity")
	int quantity;
	
	@Column(name = "total_price")
	double totalPrice;
	
	@Column(name = "status")
	Status status;
	
	public Reservation() {
		
	}
	
	public Reservation(Patient patient, Pharmacy pharmacy, Medicine medicine, long date, int quantity,
			double totalPrice, Status status) {
		super();
		this.patient = patient;
		this.pharmacy = pharmacy;
		this.medicine = medicine;
		this.date = date;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.status = status;
	}

	public Reservation(Long id, Patient patient, Pharmacy pharmacy, Medicine medicine, long date, int quantity, double totalPrice, Status status) {
		super();
		this.id = id;
		this.patient = patient;
		this.pharmacy = pharmacy;
		this.medicine = medicine;
		this.date = date;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
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

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Reservation [patient=" + patient + ", pharmacy=" + pharmacy + ", medicine=" + medicine + ", date="
				+ date + ", quantity=" + quantity + ", totalPrice=" + totalPrice +  ", status=" + status + "]";
	}
	
	
}
