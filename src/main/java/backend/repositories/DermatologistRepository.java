package backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.models.Dermatologist;

public interface DermatologistRepository extends JpaRepository<Dermatologist, Long> {

	List<Dermatologist> findByNameContainingIgnoreCase(String name);
	
	List<Dermatologist> findBySurnameContainingIgnoreCase(String surname);
	
	List<Dermatologist> findByNameAndSurnameIgnoreCase(String name, String surname);
	
	List<Dermatologist> findByPharmaciesContaining(Long pharmacyId);
}
