package backend.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="supplier_offers")
public class SupplierOffer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(name="supplier_id")
	Long supplierId;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	Order order;
	
	@Column
	double price;
	
	@Column
	long deadline;
	
	public SupplierOffer() {
		
	}

	public SupplierOffer(Long id, Long supplierId, Order order, double offer, long deadline) {
		super();
		this.id = id;
		this.supplierId = supplierId;
		this.order = order;
		this.price = offer;
		this.deadline = deadline;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public double getOffer() {
		return price;
	}

	public void setOffer(double price) {
		this.price = price;
	}

	public long getDeadline() {
		return deadline;
	}

	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}
	
	
}
