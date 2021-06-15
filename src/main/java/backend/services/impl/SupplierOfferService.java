package backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.models.SupplierOffer;
import backend.repositories.SupplierOfferRepository;
import backend.services.ISupplierOfferService;

@Service
public class SupplierOfferService implements ISupplierOfferService {
	
	@Autowired
	private SupplierOfferRepository soRepository;

	@Override
	public List<SupplierOffer> findAll() {
		return soRepository.findAll();
	}

	@Override
	public SupplierOffer findById(Long id) {
		return soRepository.findById(id).orElseGet(null);
	}

	@Override
	public SupplierOffer save(SupplierOffer obj) {
		soRepository.save(obj);
		return obj;
	}

	@Override
	public void delete(SupplierOffer obj) {
		soRepository.delete(obj);
	}

	@Override
	public List<SupplierOffer> findAllByOrderId(Long orderId) {
		return soRepository.findByOrderIdEquals(orderId);
	}

	@Override
	public SupplierOffer findByOrderIdAndSupplierId(Long orderId, Long supplierId) {
		return soRepository.findByOrderIdEqualsAndSupplierIdEquals(orderId, supplierId);
	}

}
