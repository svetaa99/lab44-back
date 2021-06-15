package backend.services;

import java.util.List;

import backend.models.SupplierOffer;

public interface ISupplierOfferService extends IService<SupplierOffer> {
	
	List<SupplierOffer> findAllByOrderId(Long orderId);
	
	SupplierOffer findByOrderIdAndSupplierId(Long orderId, Long supplierId);
}
