package backend.dto;

import java.time.LocalDate;

import backend.models.LabAdmin;
import backend.models.Pharmacy;

public class LabAdminDTO {
	
	private Long id;
	private String name;
	private String surname;
	private String email;
	private Long address;
	private String phoneNum;
	private Pharmacy pharmacy;
	
	public LabAdminDTO() {
		
	}

	public LabAdminDTO(Long id, String name, String surname, String email, Long address, String phoneNum, Pharmacy pharmacy) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.address = address;
		this.phoneNum = phoneNum;
		this.pharmacy = pharmacy;
	}
	
	public LabAdminDTO(LabAdmin la) {
		this(la.getId(), la.getName(), la.getSurname(), la.getEmail(), la.getAddress(), la.getPhoneNum(), la.getPharmacy());
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

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	
}
