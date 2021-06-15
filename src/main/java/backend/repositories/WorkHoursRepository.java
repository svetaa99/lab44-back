package backend.repositories;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import backend.models.WorkHours;

public interface WorkHoursRepository extends JpaRepository<WorkHours, Long>{

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<WorkHours> findByDoctorIdAndPharmacyId(Long doctorId, Long pharmacyId);

	List<WorkHours> findByDoctorIdEquals(Long doctorId);
}
