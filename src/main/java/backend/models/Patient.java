package backend.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Patient extends User{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8762993377530933263L;

	@Column(name="points", unique=false, nullable=true)
	private double points;
	
	@Column(name = "category", nullable = true)
	private String category;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "patients_allergies", joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "medicine_id", referencedColumnName = "id"))
	private List<Medicine> allergies;
	
	@OneToMany(mappedBy = "patient")
	private List<Reservation> reservations;

	public Patient() {
		
	}
	
	public Patient(String name, String surname, String email, String password, Long addressId, String phoneNum, double points, String category) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.addressId = addressId;
		this.phoneNum = phoneNum;
		this.points = points;
		this.category = category;
	}
	
	public Patient(Long id, String name, String surname, String email, String password, Long addressId, String phoneNum, double points, String category) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.addressId = addressId;
		this.phoneNum = phoneNum;
		this.points = points;
		this.category = category;
	}

	public Patient(Long id, String name, String surname, String email, String password, Long addressId, String phoneNum, double points, String category, List<Medicine> allergies) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.addressId = addressId;
		this.phoneNum = phoneNum;
		this.points = points;
		this.category = category;
		this.allergies = allergies;
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
		return addressId;
	}

	public void setAddress(Long addressId) {
		this.addressId = addressId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public double getPoints() {
		return points;
	}
	
	public void setPoints(double points) {
		this.points = points;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public List<Medicine> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<Medicine> allergies) {
		this.allergies = allergies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Patient))
			return false;
		Patient other = (Patient) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Patient [points=" + points + ", category=" + category + ", allergies=" + allergies + ", roles=" + roles
				+ "]";
	}
	
}
