package backend.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Pharmacy pharmacy;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderMedicines> orders;
	
	@OneToMany(mappedBy = "order")
	private List<SupplierOffer> offers;
	
	@Column
	private long deadline;
	
	public Order() {
		
	}

	public Order(Long id, long deadline) {
		super();
		this.id = id;
		this.deadline = deadline;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getDeadline() {
		return deadline;
	}

	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}

	public List<OrderMedicines> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderMedicines> orders) {
		this.orders = orders;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	
}
