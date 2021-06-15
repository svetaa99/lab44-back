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
@Table(name="order_medicines")
public class OrderMedicines {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	Order order;

	@ManyToOne
	@JoinColumn(name = "medicine_id")
	Medicine medicine;
	
	@Column
	int quantity;
	
	public OrderMedicines() {
		
	}

	public OrderMedicines(Long id, Order order, Medicine medicine, int quantity) {
		super();
		this.id = id;
		this.order = order;
		this.medicine = medicine;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	

}
