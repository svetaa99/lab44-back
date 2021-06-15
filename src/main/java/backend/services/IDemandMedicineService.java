package backend.services;

import java.util.List;

import backend.models.DemandMedicine;

public interface IDemandMedicineService extends IService<DemandMedicine> {
	
	List<DemandMedicine> findAllByPharmacyId(Long pharmacyId);

}
