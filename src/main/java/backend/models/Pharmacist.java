package backend.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Pharmacist extends Doctor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6364275306913980399L;
	@ManyToOne
	private Pharmacy pharmacy;
	
	@Column(name="rating", unique=false, nullable=true)
	private double rating;

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	public double getRating() {
		return rating;
	}
	
	public void setRating(double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Pharmacist [pharmacy=" + pharmacy + ", name=" + name + ", surname=" + surname + ", email=" + email
				+ ", roles=" + roles + "]";
	}
	
}
