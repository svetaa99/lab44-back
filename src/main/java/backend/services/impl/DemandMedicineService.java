package backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.models.DemandMedicine;
import backend.repositories.DemandMedicineRepository;
import backend.services.IDemandMedicineService;


@Service
public class DemandMedicineService implements IDemandMedicineService {
	
	@Autowired
	private DemandMedicineRepository dmRepository;

	@Override
	public List<DemandMedicine> findAll() {
		return dmRepository.findAll();
	}

	@Override
	public DemandMedicine findById(Long id) {
		return dmRepository.findById(id).orElse(null);
	}

	@Override
	public DemandMedicine save(DemandMedicine obj) {
		return dmRepository.save(obj);
	}

	@Override
	public void delete(DemandMedicine obj) {
		dmRepository.delete(obj);
	}

	@Override
	public List<DemandMedicine> findAllByPharmacyId(Long pharmacyId) {
		return dmRepository.findByPharmacyIdEquals(pharmacyId);
	}

	
}
