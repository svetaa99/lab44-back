package backend.dto;

import java.util.ArrayList;
import java.util.List;

import backend.models.Order;
import backend.models.OrderMedicines;
import backend.models.Pharmacy;

public class OrderDTO {
	
	private Long id;
	private Pharmacy pharmacy;
	private List<MedicineQuantityDTO> orderMedicines;
	private long deadline;
	
	public OrderDTO(Long id, Pharmacy pharmacy, List<MedicineQuantityDTO> orderMedicines, long deadline) {
		super();
		this.id = id;
		this.pharmacy = pharmacy;
		this.orderMedicines = orderMedicines;
		this.deadline = deadline;
	}
	
	public OrderDTO() {
		
	}
	
	public List<MedicineQuantityDTO> createMQList(Order o) {
		List<OrderMedicines> orderMedicines = o.getOrders();
		List<MedicineQuantityDTO> mqList = new ArrayList<MedicineQuantityDTO>();
		for (OrderMedicines om : orderMedicines) {
			MedicineQuantityDTO mq = new MedicineQuantityDTO(om.getMedicine(), om.getQuantity());
			mqList.add(mq);
		}
		
		return mqList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public long getDeadline() {
		return deadline;
	}

	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}

	public List<MedicineQuantityDTO> getOrderMedicines() {
		return orderMedicines;
	}

	public void setOrderMedicines(List<MedicineQuantityDTO> orderMedicines) {
		this.orderMedicines = orderMedicines;
	}
	
	

}
