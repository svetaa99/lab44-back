package backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import backend.models.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
	
	List<Promotion> findByPharmacyIdEquals(Long pharmacyId);
	
	@Query("select p from Promotion p inner join p.medicines pm where p.pharmacy.id = ?1 and ?2 = pm.id")
	Promotion findByPharmacyIdEqualsAndMedicinesIdContains(Long pharmacyId, Long medicineId);
}
