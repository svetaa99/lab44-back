package backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.enums.VacationStatus;
import backend.models.Vacation;
import backend.repositories.VacationRepository;
import backend.services.IService;

@Service
public class VacationService implements IService<Vacation>{

	@Autowired
	private VacationRepository vacationRepository;
	
	public List<Vacation> findByDoctorIdEquals(Long doctorId){
		return vacationRepository.findByDoctorIdEquals(doctorId);
	}
	
	public List<Vacation> findByDoctorIdAndStatus(Long doctorId, VacationStatus status){
		return vacationRepository.findByDoctorIdAndStatus(doctorId, status);
	}
	
	public List<Vacation> findByStatus(VacationStatus status){
		return vacationRepository.findByStatus(status);
	}
	@Override
	public List<Vacation> findAll() {
		return vacationRepository.findAll();
	}

	@Override
	public Vacation findById(Long id) {
		return vacationRepository.findById(id).orElse(null);
	}

	@Override
	public Vacation save(Vacation obj) {
		return vacationRepository.save(obj);
	}

	@Override
	public void delete(Vacation obj) {
		vacationRepository.delete(obj);
	}

}
