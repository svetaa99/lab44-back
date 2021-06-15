package backend.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.enums.Status;
import backend.models.DoctorTerms;
import backend.models.Visit;
import backend.models.WorkHours;
import backend.repositories.DoctorTermsRepository;
import backend.services.IService;

@Service
public class DoctorTermsService implements IService<DoctorTerms>{

	@Autowired
	private DoctorTermsRepository doctorTermsRepository;
	
	@Autowired
	private VisitService visitService;
	
	@Autowired
	private WorkHoursService whService;

	@Override
	public List<DoctorTerms> findAll() {
		return doctorTermsRepository.findAll();
	}

	@Override
	public DoctorTerms findById(Long id) {
		return doctorTermsRepository.findById(id).orElse(null);
	}
	
	public List<DoctorTerms> findByDoctorIdEquals(Long id){
		return doctorTermsRepository.findByDoctorIdEquals(id);
	}

	@Transactional
	@Override
	public DoctorTerms save(DoctorTerms obj) {
		return doctorTermsRepository.save(obj);
	}

	@Override
	public void delete(DoctorTerms obj) {
		doctorTermsRepository.delete(obj);
	}
	
	public void makeReservation(Long termId, Long patientId) {
		DoctorTerms doctorTerm = findById(termId);
		
		// Make appointment
		Visit newVisit = new Visit();
		newVisit.setPatientId(patientId);
		newVisit.setDoctorId(doctorTerm.getDoctorId());
		newVisit.setStart(doctorTerm.getStart());
		newVisit.setFinish(doctorTerm.getFinish());
		newVisit.setPharmacy(doctorTerm.getPharmacyId());
		newVisit.setStatus(Status.RESERVED);
		
		visitService.save(newVisit);
		
		delete(doctorTerm);
	}
	public List<DoctorTerms> findAllFutureTerms(){
		return doctorTermsRepository.findAll().stream().filter(dt -> dt.getStart().isAfter(LocalDateTime.now())).collect(Collectors.toList());
	}
	
	@Transactional
	public String createNewTerm(DoctorTerms newTerm) {
		if(checkIfTakenTerm(newTerm)) {
			if(checkIfInWorkingHours(newTerm)) {
				save(newTerm);
				return "ok";
			}
			else
				return "Not in doctors working hours";
		}
		else {
			return "Taken term";
		}
	}
	
	public boolean checkIfTakenTerm(DoctorTerms newTerm) {
		List<DoctorTerms> doctorsTakenTerms = findByDoctorIdEquals(newTerm.getDoctorId());
		LocalDateTime startTime = newTerm.getStart();
		LocalDateTime finishTime = newTerm.getFinish();
		
		for (DoctorTerms doctorTerms : doctorsTakenTerms) {
			if(startTime.isAfter(doctorTerms.getStart()) && startTime.isBefore(doctorTerms.getFinish())) 
				return false;
			else if(finishTime.isAfter(doctorTerms.getStart()) && finishTime.isBefore(doctorTerms.getFinish())) 
				return false;
			else if(startTime.isBefore(doctorTerms.getStart()) && finishTime.isAfter(doctorTerms.getFinish())) 
				return false;
			else if(startTime.equals(doctorTerms.getStart()) || finishTime.equals(doctorTerms.getFinish()))
				return false;
		}
		return true;
	}
	
	public boolean checkIfInWorkingHours(DoctorTerms newTerm) {
		for (WorkHours wh : whService.findWorkingHoursForDoctorByIdAndPharmacyId(newTerm.getDoctorId(), newTerm.getPharmacyId())) {
			if(newTerm.getStart().toLocalTime().isAfter(wh.getStartTime()) && newTerm.getFinish().toLocalTime().isBefore(wh.getFinishTime()))
				return true;
		}
		return false;
	}
	
}
