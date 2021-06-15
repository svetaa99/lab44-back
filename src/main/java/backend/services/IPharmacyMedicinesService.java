package backend.services;

import java.util.List;

import backend.models.Medicine;
import backend.models.Pharmacy;
import backend.models.PharmacyMedicineAddRemoveObject;
import backend.models.PharmacyMedicines;
import backend.models.Reservation;
import backend.models.ResponseObject;

public interface IPharmacyMedicinesService extends IService<PharmacyMedicines> {
	
	List<Medicine> findAllMedicinesInPharmacy(Long pharmacyId);
	
	List<Pharmacy> findAllPharmaciesWithMedicine(Long medicineId);
	
	PharmacyMedicines findPharmacyMedicinesByIds(Long pharmacyId, Long medicineId);
	
	List<PharmacyMedicines> findAllByPharmacyId(Long pharmacyId);
	
	List<PharmacyMedicines> findAllByMedicineId(Long medicineId);
	
	List<PharmacyMedicines> findAllByMedicineName(String medicineName);
	
	List<PharmacyMedicines> findByMedicineIdAndTodaysDate(Long medicineId, long todaysDate);
	
	List<PharmacyMedicines> findByPharmacyIdAndTodaysDate(Long pharmacyId, long todaysDate);
	
	List<PharmacyMedicines> findByMedicineNameAndTodaysDate(String medicineName, long todaysDate);
	
	List<PharmacyMedicines> findByPharmacyAndMedicineNameAndTodaysDate(Long pharmacyId, String medicineName, long todaysDate);
	
	PharmacyMedicines findByPharmacyIdAndMedicineIdAndTodaysDate(Long pharmacyId, Long medicineId, long todaysDate);
	
	List<PharmacyMedicines> findAvailableByPharmacyId(Long pharmacyId);
	
	List<PharmacyMedicines> findByMedicineNameAndPharmacyId(String medicineName, Long pharmacyId);
	
	PharmacyMedicines updateAfterReservation(Reservation reservation, int quantity);
	
	PharmacyMedicines updateAfterReservationCancel(Reservation reservation);
	
	ResponseObject updateMedicinePrice(PharmacyMedicineAddRemoveObject obj);
}
