package backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.models.Doctor;
import backend.repositories.DoctorRepository;
import backend.services.IService;

@Service
public class DoctorService implements IService<Doctor> {

	@Autowired
	private DoctorRepository doctorRepo;

	@Override
	public List<Doctor> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Doctor findById(Long id) {
		return doctorRepo.findById(id).orElse(null);
	}

	@Override
	public Doctor save(Doctor obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Doctor obj) {
		// TODO Auto-generated method stub
		
	}
	
}
