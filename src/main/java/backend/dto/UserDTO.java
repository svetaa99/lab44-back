package backend.dto;

import backend.models.User;

public class UserDTO {
	
	private String name;
	private String surname;
	private String email;
	private String password;
	private long addressId;
	private String phoneNum;
	
	public UserDTO(User u) {
		this(u.getName(), u.getSurname(), u.getEmail(), u.getPassword(), u.getAddress(), u.getPhoneNum());
	}
	
	public UserDTO() {}
	
	public UserDTO(String name, String surname, String email, String password, long addressId, String phoneNum) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.addressId = addressId;
		this.phoneNum = phoneNum;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	@Override
	public String toString() {
		return "UserDTO [name=" + name + ", surname=" + surname + ", email=" + email + ", password=" + password
				+ ", addressId=" + addressId + ", phoneNum=" + phoneNum + "]";
	}
	
}
