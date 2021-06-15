package backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.models.LabAdmin;
import backend.repositories.LabAdminRepository;
import backend.services.ILabAdminService;

@Service
public class LabAdminService implements ILabAdminService {
	
	@Autowired
	private LabAdminRepository laRepository;

	@Override
	public List<LabAdmin> findAll() {
		return laRepository.findAll();
	}

	@Override
	public LabAdmin findById(Long id) {
		return laRepository.findById(id).orElseGet(null);
	}

	@Override
	public LabAdmin save(LabAdmin obj) {
		laRepository.save(obj);
		return obj;
	}

	@Override
	public void delete(LabAdmin obj) {
		laRepository.delete(obj);
	}

	@Override
	public LabAdmin findByEmail(String email) {
		return laRepository.findByEmailEquals(email);
	}

}
