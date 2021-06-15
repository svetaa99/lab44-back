package backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.models.Medicine;
import backend.repositories.MedicineRepository;
import backend.services.IMedicineService;

@Service
public class MedicineService implements IMedicineService{

	@Autowired
	private MedicineRepository medicineRepository;

	@Override
	public List<Medicine> findAll() {
		return medicineRepository.findAll();
	}
	
	@Override
	public List<Medicine> findAllByName(String name) {
		return medicineRepository.findAllByNameContainingIgnoreCase(name);
	}

	@Override
	public Medicine findById(Long id) {
		return medicineRepository.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public Medicine save(Medicine obj) {
		return medicineRepository.save(obj);
	}

	@Override
	public void delete(Medicine obj) {
		medicineRepository.delete(obj);
	}

	@Override
	public List<Medicine> getSubstituteForMedicine(Long id) {
		System.out.println("dobijen id: " + id);
		Medicine current = medicineRepository.findById(id).get();
		List<Medicine> retVal = medicineRepository.findByType(current.getType());
		retVal.remove(current);
		return retVal;
	}
	
	
	
}
