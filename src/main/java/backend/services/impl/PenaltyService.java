package backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.models.Penalty;
import backend.repositories.PenaltyRepository;
import backend.services.IService;

@Service
public class PenaltyService implements IService<Penalty>{

	@Autowired
	private PenaltyRepository penaltyRepo;
	
	@Override
	public List<Penalty> findAll() {
		return penaltyRepo.findAll();
	}

	@Override
	public Penalty findById(Long id) {
		return penaltyRepo.findById(id).orElse(null);
	}

	@Override
	public Penalty save(Penalty obj) {
		return penaltyRepo.save(obj);
	}

	@Override
	public void delete(Penalty obj) {
		penaltyRepo.delete(obj);		
	}
	
	public List<Penalty> findByPatientId(Long id) {
		return penaltyRepo.findByPatientIdEquals(id);
	}
	
	public long countPenaltiesByPatientId(Long patientId) {
		return penaltyRepo.countByPatientId(patientId);
	}
	
}
