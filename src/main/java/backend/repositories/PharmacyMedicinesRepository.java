package backend.repositories;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import backend.models.PharmacyMedicines;

public interface PharmacyMedicinesRepository extends JpaRepository<PharmacyMedicines, Long> {
	
	List<PharmacyMedicines> findByPharmacyIdEquals(Long pharmacyId);
	
	List<PharmacyMedicines> findByMedicineIdEquals(Long medicineId);
	
	List<PharmacyMedicines> findByMedicineNameContainingIgnoreCase(String medicineName);
	
	PharmacyMedicines findByPharmacyIdAndMedicineId(Long pharmacyId, Long medicineId);
	
	@Query("select pm from PharmacyMedicines pm where pm.medicine.id = ?1 and ?2 between pm.startDate and pm.endDate")
	List<PharmacyMedicines> findByMedicineIdAndTodaysDate(Long medicineId, long todaysDate);
	
	@Query("select pm from PharmacyMedicines pm where pm.pharmacy.id = ?1 and ?2 between pm.startDate and pm.endDate")
	List<PharmacyMedicines> findByPharmacyIdAndTodaysDate(Long pharmacyId, long todaysDate);
	
	@Query("select pm from PharmacyMedicines pm where pm.medicine.name = ?1 and ?2 between pm.startDate and pm.endDate")
	List<PharmacyMedicines> findByMedicineNameAndTodaysDate(String medicineName, long todaysDate);
	
	@Query("select pm from PharmacyMedicines pm where pm.pharmacy.id = ?1 and pm.medicine.name = ?2 and ?3 between pm.startDate and pm.endDate")
	List<PharmacyMedicines> findByPharmacyAndMedicineNameAndTodaysDate(Long pharmacyId, String medicineName, long todaysDate);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select pm from PharmacyMedicines pm where pm.pharmacy.id= ?1 and pm.medicine.id = ?2 and ?3 between pm.startDate and pm.endDate")
	PharmacyMedicines findByPharmacyIdAndMedicineIdAndTodaysDate(Long pharmacyId, Long medicineId, long todaysDate);
	
	@Query("select pm from PharmacyMedicines pm where pm.pharmacy.id = ?1 and pm.quantity > 0")
	List<PharmacyMedicines> findAvailableByPharmacyId(Long pharmacyId);
	
	List<PharmacyMedicines> findByMedicineNameAndPharmacyId(String medicineName, Long pharmacyId);
}
