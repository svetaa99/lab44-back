package backend.models;

import javax.persistence.Entity;


@Entity
public class Doctor extends User{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6078131993224161011L;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
