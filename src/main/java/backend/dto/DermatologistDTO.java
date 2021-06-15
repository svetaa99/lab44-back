package backend.dto;

import java.util.List;

import backend.models.Dermatologist;
import backend.models.Pharmacy;

public class DermatologistDTO {
	
	private Long id;
	private String name;
	private String surname;
	private String email;
	private Long address;
	private List<Pharmacy> pharmacies;
	private String startTime;
	private String finishTime;
	private double rating;
	
	public DermatologistDTO() {
		
	}
	
	public DermatologistDTO(Dermatologist d) {
		this(d.getId(), d.getName(), d.getSurname(), d.getAddress(), d.getEmail(), d.getPharmacies());
	}

	public DermatologistDTO(Long id, String name, String surname, Long address, String email) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.address = address;
	}
	
	public DermatologistDTO(Long id, String name, String surname, Long address, String email, List<Pharmacy> pharmacies) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.email = email;
		this.pharmacies = pharmacies;
	}
	
	public DermatologistDTO(Long id, String name, String surname, Long address, String email, List<Pharmacy> pharmacies, 
			String startTime, String finishTime) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.email = email;
		this.pharmacies = pharmacies;
		this.startTime = startTime;
		this.finishTime = finishTime;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Long getAddress() {
		return address;
	}

	public void setAddress(Long address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Pharmacy> getPharmacies() {
		return pharmacies;
	}

	public void setPharmacies(List<Pharmacy> pharmacies) {
		this.pharmacies = pharmacies;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	
	
}
