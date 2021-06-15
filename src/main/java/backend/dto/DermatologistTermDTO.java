package backend.dto;

import java.time.LocalDateTime;

import backend.models.Pharmacy;

public class DermatologistTermDTO {
	
	private Long id;
	private String dermatologistName;
	private String dermatologistSurname;
	private Pharmacy pharmacy;
	private double price;
	private LocalDateTime date;
	
	public DermatologistTermDTO() {
		
	}

	public DermatologistTermDTO(Long id, String dermatologistName, String dermatologistSurname, Pharmacy pharmacy, double price, LocalDateTime date) {
		super();
		this.id = id;
		this.dermatologistName = dermatologistName;
		this.dermatologistSurname = dermatologistSurname;
		this.pharmacy = pharmacy;
		this.price = price;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDermatologistName() {
		return dermatologistName;
	}

	public void setDermatologistName(String dermatologistName) {
		this.dermatologistName = dermatologistName;
	}

	public String getDermatologistSurname() {
		return dermatologistSurname;
	}

	public void setDermatologistSurname(String dermatologistSurname) {
		this.dermatologistSurname = dermatologistSurname;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	
}
