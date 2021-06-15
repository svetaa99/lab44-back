package backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.models.OrderMedicines;

public interface OrderMedicinesRepository extends JpaRepository<OrderMedicines, Long> {
	
	List<OrderMedicines> findByOrderIdEquals(Long orderId);
}
