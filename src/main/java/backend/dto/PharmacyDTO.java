package backend.dto;

import backend.models.Address;

public class PharmacyDTO {
	
	private Long id;
	private String name;
	private Address address;
	private String description;
	private double rating;
	private double pharmacistPrice;
	
	public PharmacyDTO() {
		
	}

	public PharmacyDTO(Long id, String name, Address address, String description, double rating, double pharmacistPrice) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.rating = rating;
		this.pharmacistPrice = pharmacistPrice;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public double getpharmacistPrice() {
		return pharmacistPrice;
	}

	public void setpharmacistPrice(double pharmacistPrice) {
		this.pharmacistPrice = pharmacistPrice;
	}


	@Override
	public String toString() {
		return "PharmacyDTO [id=" + id + ", name=" + name + ", address=" + address + ", description=" + description
				+ ", rating=" + rating + ", pharmacistPrice=" + pharmacistPrice + "]";
	}
	
	
}
