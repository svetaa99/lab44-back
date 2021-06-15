package backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.models.Penalty;

public interface PenaltyRepository extends JpaRepository<Penalty, Long>{

	public List<Penalty> findByPatientIdEquals(Long id);
	
	long countByPatientId(Long patientId);
	
}
