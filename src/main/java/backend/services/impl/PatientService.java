package backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import backend.models.Patient;
import backend.repositories.PatientRepository;
import backend.services.IPatientService;

@Service
public class PatientService implements IPatientService{ 

	@Autowired
	private PatientRepository patientRepository;

	@Override
	public List<Patient> findAll() {
		return patientRepository.findAll();
	}
	
	public List<Patient> findAllSorted(String param, int order){
		Sort sortOrder = Sort.by(param);
		return patientRepository.findAll(order == 0 ? sortOrder.ascending() : sortOrder.descending());
	}
	
	@Override
	public Patient findByEmail(String email) {
		return patientRepository.findByEmailEquals(email);
	}
	
	public List<Patient> findAllByName(String name){
		return patientRepository.findAllByName(name);
	}
	
	@Override
	public Patient findById(Long id) {
		return patientRepository.findById(id).orElse(null);
	}

	@Override
	public Patient save(Patient obj) {
		return patientRepository.save(obj);
	}

	@Override
	public void delete(Patient obj) {
		patientRepository.delete(obj);
	}

	
}
