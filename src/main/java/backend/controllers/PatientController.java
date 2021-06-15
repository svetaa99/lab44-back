package backend.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import backend.dto.PatientDTO;
import backend.models.Patient;
import backend.models.Penalty;
import backend.models.User;
import backend.models.Visit;
import backend.services.impl.PatientService;
import backend.services.impl.PenaltyService;
import backend.services.impl.UserService;
import backend.services.impl.VisitService;
import comparators.PatientDTOComparator;

@RestController
@RequestMapping(value = "patients")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PatientController {

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private VisitService visitService;
	
	@Autowired
	private PenaltyService penaltyService;
	
	@Autowired
	private UserService userService;
	
	private static Gson g = new Gson();
	
	@GetMapping("/all")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST', 'LAB_ADMIN', 'HEAD_ADMIN')")
	public ResponseEntity<String> getPatients() {
		
		List<Patient> retVal = patientService.findAll();
		
		List<PatientDTO> patientsDTO = turnPatientsToDTO(retVal);

		return new ResponseEntity<String>(g.toJson(patientsDTO), HttpStatus.OK);
	}
	@GetMapping("/{name}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST', 'LAB_ADMIN', 'HEAD_ADMIN')")
	public ResponseEntity<String> getPatientsByName(@PathVariable String name){
		System.out.println("Returning patients searched by name...");
		List<Patient> retVal = patientService.findAllByName(name);
		
		List<PatientDTO> patientsDTO = turnPatientsToDTO(retVal);
		
		return new ResponseEntity<String>(g.toJson(patientsDTO), HttpStatus.OK);
	}
	@GetMapping("/{param}/{order}/{searchParam}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST', 'LAB_ADMIN', 'HEAD_ADMIN')")
	public ResponseEntity<String> getPatientsSorted(@PathVariable String param, @PathVariable int order, @PathVariable String searchParam){
		List<Patient> patients = new ArrayList<Patient>();
		List<PatientDTO> patientsDTO = new ArrayList<PatientDTO>();
		
		if(param.equals("date")) {
			patients = patientService.findAll();
			PatientDTOComparator dateComparator = new PatientDTOComparator();
			dateComparator.setOrder(order);
			patientsDTO = turnPatientsToDTO(patients);
			patientsDTO.sort(dateComparator);
		}
		
		else {
			patients = patientService.findAllSorted(param, order);
			patientsDTO = turnPatientsToDTO(patients);
		}
		if(searchParam.equals("no-search") || searchParam.equals(""))
			return new ResponseEntity<String>(g.toJson(patientsDTO), HttpStatus.OK);

		return new ResponseEntity<String>(g.toJson(patientsDTO
				.stream().filter(p -> p.getName().equalsIgnoreCase(searchParam)).collect(Collectors.toList())), HttpStatus.OK);
	}
	
	@GetMapping("/print-allergies/{id}")
	public ResponseEntity<PatientDTO> getAllergies(@PathVariable Long id) {
		Patient p = patientService.findById(id);
		
		System.out.println("ALEEEEERG" + p.getAllergies());
		
		return null;
	}
	
	@GetMapping("/penalty/{visitId}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST', 'LAB_ADMIN', 'HEAD_ADMIN')")
	public ResponseEntity<String> penalty(@PathVariable Long visitId){
		Visit v = visitService.findById(visitId);
		Long pId  = v.getPatientId();
		
		Penalty penalty = new Penalty(pId, LocalDate.now());
		penaltyService.save(penalty);
		
		return new ResponseEntity<String>("Saved", HttpStatus.OK);
	}
		
	@GetMapping("/registered-patient")
	public ResponseEntity<PatientDTO> getRegisteredPatient() {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		Patient p = patientService.findByEmail(token);
		
		return new ResponseEntity<PatientDTO>(new PatientDTO(p), HttpStatus.OK);
	}
	
	public List<PatientDTO> turnPatientsToDTO(List<Patient> patients){
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findUserByEmail(token);
		
		List<PatientDTO> patientsDTO = new ArrayList<>();
		
		for (Patient p : patients) {
			LocalDateTime lastVisit = visitService.lastVisitByPatientIdAndDoctorIdEquals(p.getId(), u.getId());
			if(lastVisit!=null)
				patientsDTO.add(new PatientDTO(p, lastVisit.toLocalDate()));
			else
				continue;
		}
		return patientsDTO;
	}
}
