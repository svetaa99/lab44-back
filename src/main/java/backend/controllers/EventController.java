package backend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.EventDTO;
import backend.dto.VisitDTO;
import backend.enums.VacationStatus;
import backend.models.Doctor;
import backend.models.User;
import backend.models.Vacation;
import backend.models.Visit;
import backend.services.impl.PatientService;
import backend.services.impl.UserService;
import backend.services.impl.VacationService;
import backend.services.impl.VisitService;

@RestController
@RequestMapping(value = "events")
public class EventController {

	@Autowired
	VisitService visitService;
	
	@Autowired
	VacationService vacationService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PatientService patientService;
	
	@GetMapping("all")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<List<EventDTO>> getAll(){
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findUserByEmail(token);
		Long doctorId = u.getId();
		
		List<Visit> allVisits = visitService.findByDoctorIdEquals(doctorId);
		
		List<VisitDTO> visitsDTO = new ArrayList<>();
		for (Visit visit : allVisits) {
			VisitDTO visitDTO = new VisitDTO(visit.getId(), patientService.findById(visit.getPatientId()), (Doctor) u, visit.getStart(), visit.getFinish());
			visitsDTO.add(visitDTO);
		}
		
		List<EventDTO> retVal = new ArrayList<EventDTO>();
		for (VisitDTO visitDTO : visitsDTO) {
			retVal.add(new EventDTO(visitDTO));
		}
		
		List<Vacation> allVacations = vacationService.findByDoctorIdAndStatus(doctorId, VacationStatus.APPROVED);
		
		for (Vacation vacation : allVacations) {
			retVal.add(new EventDTO(vacation));
		}
		
		return new ResponseEntity<List<EventDTO>>(retVal, HttpStatus.OK);
	}
}
