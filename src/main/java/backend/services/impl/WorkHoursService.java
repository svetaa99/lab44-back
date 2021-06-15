package backend.services.impl;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.models.WorkHours;
import backend.repositories.WorkHoursRepository;
import backend.services.IService;

@Service
public class WorkHoursService implements IService<WorkHours> {
	
	@Autowired
	private WorkHoursRepository workHoursRepository;
	
	public List<WorkHours> getPharmaciesByTime(LocalTime time) {
		
		List<WorkHours> workHours = workHoursRepository.findAll();
		
		return workHours.stream()
				.filter(wh -> wh.getStartTime().isBefore(time) && wh.getFinishTime().isAfter(time)).collect(Collectors.toList());
	}

	@Override
	public List<WorkHours> findAll() {
		return workHoursRepository.findAll();
	}

	@Override
	public WorkHours findById(Long id) {
		return workHoursRepository.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public WorkHours save(WorkHours obj) {
		return workHoursRepository.save(obj);
	}

	@Override
	public void delete(WorkHours obj) {
		workHoursRepository.delete(obj);
		
	}
	
	@Transactional
	public List<WorkHours> findWorkingHoursForDoctorByIdAndPharmacyId(Long doctorId, Long pharmacyId){
		return workHoursRepository.findByDoctorIdAndPharmacyId(doctorId, pharmacyId);
	}
	
	public List<WorkHours> findAllWorkHoursForDoctor(Long doctorId) {
		return workHoursRepository.findByDoctorIdEquals(doctorId);
	}
}
