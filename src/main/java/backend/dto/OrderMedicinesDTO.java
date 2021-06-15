package backend.dto;

import backend.models.Medicine;
import backend.models.Order;
import backend.models.OrderMedicines;

public class OrderMedicinesDTO {

	private Long id;
	private Order order;
	private Medicine medicine;
	private int quantity;
	
	public OrderMedicinesDTO(Long id, Order order, Medicine medicine, int quantity) {
		super();
		this.order = order;
		this.medicine = medicine;
		this.quantity = quantity;
	}
	
	public OrderMedicinesDTO(OrderMedicines om) {
		this(om.getId(), om.getOrder(), om.getMedicine(), om.getQuantity());
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
