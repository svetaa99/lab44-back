package backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.models.SupplierOffer;

public interface SupplierOfferRepository extends JpaRepository<SupplierOffer, Long>{
	
	List<SupplierOffer> findByOrderIdEquals(Long orderId);
	
	SupplierOffer findByOrderIdEqualsAndSupplierIdEquals(Long orderId, Long supplierId);
}
