package backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findByPharmacyIdEquals(Long pharmacyId);
	
}
