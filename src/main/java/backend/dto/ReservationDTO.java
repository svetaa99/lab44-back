package backend.dto;

import backend.models.Medicine;
import backend.models.Patient;
import backend.models.Pharmacy;
import backend.models.Reservation;

public class ReservationDTO {
	
	private Long id;
	private Patient patient;
	private Pharmacy pharmacy;
	private Medicine medicine;
	private long date;
	private int quantity;
	private double totalPrice;
	
	public ReservationDTO() {
		
	}
	
	
	public ReservationDTO(Long id, Patient patient, Pharmacy pharmacy, Medicine medicine, long date, int quantity, double totalPrice) {
		super();
		this.id = id;
		this.patient = patient;
		this.pharmacy = pharmacy;
		this.medicine = medicine;
		this.date = date;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}


	public ReservationDTO(Reservation r) {
		this(r.getId(), r.getPatient(), r.getPharmacy(), r.getMedicine(), r.getDate(), r.getQuantity(), r.getTotalPrice());
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
	
}
