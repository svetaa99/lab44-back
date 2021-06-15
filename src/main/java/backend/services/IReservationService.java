package backend.services;

import java.util.List;

import backend.enums.Status;
import backend.models.Reservation;

public interface IReservationService extends IService<Reservation> {

	List<Reservation> findMy(Long patientId);

	List<Reservation> findReserved();
	
	List<Reservation> findByPharmacyAndStatus(Long pharmacyId, Status status);
	
	List<Reservation> findByPatientAndMedicineReserved(Long patientId, Long medicineId);
	
	List<Reservation> findByPatientAndPharmacyReserved(Long pharmacyId, Long patientId);
	
	Reservation cancelReservationPatient(Long id);
	
	Reservation createReservation(Reservation reservation);
}
