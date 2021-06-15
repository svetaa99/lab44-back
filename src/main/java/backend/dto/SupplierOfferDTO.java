package backend.dto;

import backend.models.SupplierOffer;

public class SupplierOfferDTO {
	
	private Long orderId; 
	private Long supplierId;
	private double price;
	private long deadline;
	
	public SupplierOfferDTO() {
		
	}

	public SupplierOfferDTO(Long orderId, Long supplierId, double price, long deadline) {
		super();
		this.orderId = orderId;
		this.supplierId = supplierId;
		this.price = price;
		this.deadline = deadline;
	}
	
	public SupplierOfferDTO(SupplierOffer so) {
		this(so.getOrder().getId(), so.getSupplierId(), so.getOffer(), so.getDeadline());
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getDeadline() {
		return deadline;
	}

	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	

}
