package backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import backend.enums.Status;
import backend.models.Visit;

public interface VisitRepository extends JpaRepository<Visit, Long>{

	List<Visit> findByPatientIdEquals(Long patientId);
	
	List<Visit> findByDoctorIdEquals(Long doctorId);
	
	List<Visit> findByIdEquals(Long id);
	
	List<Visit> findByPatientIdAndDoctorIdAndStatus(Long patientId, Long doctorId, Status status);
	
	List<Visit> findByPatientIdAndPharmacyAndStatus(Long patientId, Long pharmacyId, Status status);

}
