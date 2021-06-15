package backend.dto;

import java.time.LocalTime;

import backend.models.Pharmacist;
import backend.models.Pharmacy;

public class PharmacistDTO {

	private Long id;
	private String name;
	private String surname;
	private String email;
	private Long address;
	private String phoneNum;
	private double rating;
	private Pharmacy pharmacy;
	private String startTime;
	private String finishTime;
	
	public PharmacistDTO() {}
	
	public PharmacistDTO(Pharmacist p) {
		this(p.getId(), p.getName(), p.getSurname(), p.getEmail(), p.getAddress(), p.getPhoneNum(), p.getRating(), p.getPharmacy());
	}

	public PharmacistDTO(Long id, String name, String surname, String email, Long address, String phoneNum, double rating, Pharmacy pharmacy) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.address = address;
		this.phoneNum = phoneNum;
		this.rating = rating;
		this.pharmacy = pharmacy;
	}
	
	public PharmacistDTO(Long id, String name, String surname, String email, Long address, String phoneNum, double rating, Pharmacy pharmacy,
			String startTime, String finishTime) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.address = address;
		this.phoneNum = phoneNum;
		this.rating = rating;
		this.pharmacy = pharmacy;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getAddress() {
		return address;
	}

	public void setAddress(Long address) {
		this.address = address;
	}
	
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
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

	@Override
	public String toString() {
		return "PharmacistDTO [id=" + id + ", name=" + name + ", surname=" + surname + ", address=" + address
				+ ", pharmacy=" + pharmacy + "]";
	}
	
}
