package backend.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.enums.Status;
import backend.models.Visit;
import backend.models.WorkHours;
import backend.repositories.VisitRepository;
import backend.repositories.WorkHoursRepository;
import backend.services.IService;

@Service
@Transactional(readOnly = true)
public class VisitService implements IService<Visit>{

	@Autowired
	private VisitRepository visitRepository;
	
	@Autowired 
	private WorkHoursRepository whRepository;
	
	@Override
	public List<Visit> findAll() {
		return visitRepository.findAll();
	}

	@Override
	public Visit findById(Long id) {
		return visitRepository.findById(id).orElse(null);
	}
	
	public List<Visit> findByDoctorIdEquals(Long doctorId){
		return visitRepository.findByDoctorIdEquals(doctorId);
	}
	
	public List<Visit> findByPatientIdEquals(Long patientId){
		return visitRepository.findByPatientIdEquals(patientId);
	}
	
	public LocalDateTime lastVisitByPatientIdAndDoctorIdEquals(Long patientId, Long doctorId) {
		List<Visit> allPatientsVisits = visitRepository.findByPatientIdAndDoctorIdAndStatus(patientId, doctorId, Status.FINISHED);

		if(allPatientsVisits.size() == 0)
			return null;
		
		LocalDateTime max = allPatientsVisits.get(0).getStart();
		for (Visit visit : allPatientsVisits) {
			if(visit.getStart().isAfter(max))
				max = visit.getStart();
		}
		return max;
	}
	
	public List<Visit> findByDoctorIdFuture(Long doctorId){
		List<Visit> allDoctorVisits = visitRepository.findByDoctorIdEquals(doctorId);
		
		return allDoctorVisits.stream().filter(v -> v.getFinish().isAfter(LocalDateTime.now())).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = false)
	@Override
	public Visit save(Visit obj) {
		if(!checkTermTaken(obj))
			return null;
		
		if(!checkTermDerm(obj, obj.getDoctorId()))
			return null;
		
		if(!checkIfInWorkingHours(obj)) {
			return null;
		}
		return visitRepository.save(obj);
	}

	@Override
	public void delete(Visit obj) {
		// TODO Auto-generated method stub
		
	}
	
	public Visit makePharmacistAppointmentPatient(Visit newReservation, Long patientId) {
		newReservation.setPatientId(patientId);
		newReservation.setFinish(newReservation.getStart().plusHours(1));
		
		if(!checkTermTaken(newReservation))
			return null;
		if(checkTermDerm(newReservation, newReservation.getDoctorId()))
			return null;
		if(checkIfInWorkingHours(newReservation))
			return null;
		
		newReservation.setStatus(Status.RESERVED);
	
		save(newReservation);
		return newReservation;
	}
	
	public List<Visit> findByDoctorAndPatient(Long doctorId, Long patientId) {
		return visitRepository.findByPatientIdAndDoctorIdAndStatus(patientId, doctorId, Status.FINISHED);
	}
	
	public List<Visit> findByPatientAndPharmacy(Long patientId, Long pharmacyId) {
		return visitRepository.findByPatientIdAndPharmacyAndStatus(patientId, pharmacyId, Status.FINISHED);
	}
	
	private boolean checkTermTaken(Visit newReservation) {
		List<Visit> patientsAppointments = findByPatientIdEquals(newReservation.getPatientId());
		
		LocalDateTime startTime = newReservation.getStart();
		LocalDateTime finishTime = newReservation.getFinish();
		
		for (Visit visit : patientsAppointments) {
			if(startTime.isAfter(visit.getStart()) && startTime.isBefore(visit.getFinish())) 
				return false;
			else if(finishTime.isAfter(visit.getStart()) && finishTime.isBefore(visit.getFinish())) 
				return false;
			else if(startTime.isBefore(visit.getStart()) && finishTime.isAfter(visit.getFinish()))
				return false;
			else if(startTime.equals(visit.getStart()) || finishTime.equals(visit.getFinish()))
				return false;
		}
		return true;
	}
	
	private boolean checkTermDerm(Visit newReservation, Long doctorId) {
		List<Visit> doctorsAppointments = findByDoctorIdEquals(doctorId);
		LocalDateTime startTime = newReservation.getStart();
		LocalDateTime finishTime = newReservation.getFinish();
		
		for (Visit visit : doctorsAppointments) {
			if(startTime.isAfter(visit.getStart()) && startTime.isBefore(visit.getFinish())) 
				return false;
			else if(finishTime.isAfter(visit.getStart()) && finishTime.isBefore(visit.getFinish())) 
				return false;
			else if(startTime.isBefore(visit.getStart()) && finishTime.isAfter(visit.getFinish()))
				return false;
			else if(startTime.equals(visit.getStart()) || finishTime.equals(visit.getFinish()))
				return false;
		}
		
		return true;
	}
	private boolean checkIfInWorkingHours(Visit newTerm) {
		for (WorkHours wh : whRepository.findByDoctorIdAndPharmacyId(newTerm.getDoctorId(), newTerm.getPharmacy())) {
			if(newTerm.getStart().toLocalTime().isAfter(wh.getStartTime()) && newTerm.getFinish().toLocalTime().isBefore(wh.getFinishTime()))
				return true;
		}			
		return false;
	}
	
}
