package backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.models.Pharmacist;
import backend.models.Pharmacy;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {
	
	List<Pharmacist> findAllByPharmacyIdEquals(Long pharmacyId);
	
	List<Pharmacist> findAllByPharmacyIdOrderByRatingAsc(Long pharmacistId);
	
	List<Pharmacist> findAllByPharmacyIdOrderByRatingDesc(Long pharmacistId);
	
	List<Pharmacist> findByNameContainingIgnoreCase(String name);
	
	List<Pharmacist> findBySurnameContainingIgnoreCase(String surname);
	
	List<Pharmacist> findByNameAndSurnameIgnoreCase(String name, String surname);

}
