package backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import backend.models.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{

	@Query("select p from Patient p")
	public List<Patient> getAllPatients();
	
	public List<Patient> findAllByName(String name);
	
	public Patient findByEmailEquals(String email);
	
}
