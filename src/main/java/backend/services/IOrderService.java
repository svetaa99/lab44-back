package backend.services;

import java.util.List;

import backend.models.Order;

public interface IOrderService extends IService<Order> {
	
	List<Order> findAllFromPharmacyId(Long pharmacyId);
}
