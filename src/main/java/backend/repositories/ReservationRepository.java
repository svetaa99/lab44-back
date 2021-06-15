package backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.enums.Status;
import backend.models.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	public List<Reservation> findAllByPatientId(Long patientId);
	
	public List<Reservation> findAllByStatus(Status status);
	
	public List<Reservation> findByPharmacyIdAndStatus(Long pharmacyId, Status status);
	
	public List<Reservation> findAllByPatientIdAndMedicineIdAndStatus(Long patientId, Long medicineId, Status status);
	
	public List<Reservation> findAllByPatientIdAndPharmacyIdAndStatus(Long patientId, Long pharmacyId, Status status);
	
}
