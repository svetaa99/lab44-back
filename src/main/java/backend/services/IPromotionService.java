package backend.services;

import java.util.List;

import backend.models.Promotion;

public interface IPromotionService extends IService<Promotion> {
	
	List<Promotion> findAllByPharmacyId(Long pharmacyId);
	
	Promotion findPromotionForMedicine(Long pharmacyId, Long medicineId);
}
