package backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.enums.VacationStatus;
import backend.models.Vacation;

public interface VacationRepository extends JpaRepository<Vacation, Long>{

	List<Vacation> findByDoctorIdEquals(Long doctorId);
	
	List<Vacation> findByDoctorIdAndStatus(Long doctorId, VacationStatus status);
	
	List<Vacation> findByStatus(VacationStatus status);
	
}
