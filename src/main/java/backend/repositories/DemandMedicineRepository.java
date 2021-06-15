package backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.models.DemandMedicine;

public interface DemandMedicineRepository extends JpaRepository<DemandMedicine, Long> {
	
	List<DemandMedicine> findByPharmacyIdEquals(Long pharmacyId);
}
