package backend.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class LabAdmin extends User {
	
	private static final long serialVersionUID = -6364275306913980399L;

	@ManyToOne
	private Pharmacy pharmacy;

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
}
