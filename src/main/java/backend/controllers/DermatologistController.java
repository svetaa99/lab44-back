package backend.controllers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import backend.dto.DermatologistDTO;
import backend.dto.WorkHoursDTO;
import backend.models.Dermatologist;
import backend.models.LabAdmin;
import backend.models.Pharmacy;
import backend.models.WorkHours;
import backend.services.IDermatologistService;
import backend.services.ILabAdminService;
import backend.services.IPharmacyService;
import backend.services.impl.WorkHoursService;

@RestController
@RequestMapping(value = "dermatologists")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DermatologistController {
	
	@Autowired
	private IDermatologistService dermaService;
	
	@Autowired
	private IPharmacyService pharmacyService;
	
	@Autowired
	private ILabAdminService laService;
	
	@Autowired
	private WorkHoursService whService;
	
	private static Gson g = new Gson();
	
	private List<DermatologistDTO> createDTOList(List<Dermatologist> derms) {
		List<DermatologistDTO> dDTOs = new ArrayList<DermatologistDTO>();
		
		for (Dermatologist dermatologist : derms) {
			dDTOs.add(new DermatologistDTO(dermatologist));
		}
		
		return dDTOs;
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAnyRole('PATIENT', 'LAB_ADMIN', 'DERMATOLOGIST')")
	public ResponseEntity<List<DermatologistDTO>> findAll() {
		List<Dermatologist> derms = dermaService.findAll();
		
		return new ResponseEntity<List<DermatologistDTO>>(createDTOList(derms), HttpStatus.OK);
	}
	
	@GetMapping("/all-from-pharmacy/{pharmacyId}")
	public ResponseEntity<List<DermatologistDTO>> findAllFromPharmacy(@PathVariable Long pharmacyId) {
		Pharmacy p = pharmacyService.findById(pharmacyId);
		List<Dermatologist> dermatologists = dermaService.findAll()
				.stream().filter(d -> d.getPharmacies().contains(p)).collect(Collectors.toList());
		List<DermatologistDTO> dDTOs = createDTOList(dermatologists);
		
		return new ResponseEntity<List<DermatologistDTO>>(dDTOs, HttpStatus.OK);
	}
	
	@PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DermatologistDTO>> searchDermatologists(@RequestBody DermatologistDTO obj) {
		String name = obj.getName();
		String surname = obj.getSurname();
		
		List<Dermatologist> derms = dermaService.findAllByNameOrSurname(name, surname);
		
		if (name.equals("") && surname.equals("")) {
			return new ResponseEntity<List<DermatologistDTO>>(new ArrayList<DermatologistDTO>(), HttpStatus.OK);
		}
		
		return new ResponseEntity<List<DermatologistDTO>>(createDTOList(derms), HttpStatus.OK);
	}
	
	@PostMapping(value = "/filter/{params}/{values}")
	public ResponseEntity<List<DermatologistDTO>> filterDermatologists(@RequestBody List<DermatologistDTO> searchList, @PathVariable("params") String parameterList, @PathVariable("values") String valueList) {
		List<DermatologistDTO> retVal = searchList;
		
		String[] params = parameterList.split("\\+");
		String[] values = valueList.split("\\+");
		
		if (params[1].equals("true")) {
			retVal = retVal
					.stream()
					.filter(d -> d.getPharmacies().contains(pharmacyService.findById(Long.parseLong(values[1]))))
					.collect(Collectors.toList());
		} else {
			return new ResponseEntity<List<DermatologistDTO>>(createDTOList(dermaService.findAll()), HttpStatus.OK);
		}
		
		return new ResponseEntity<List<DermatologistDTO>>(retVal, HttpStatus.OK);
	}
	
	@PostMapping("/add-to-pharmacy/{doctorId}")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<DermatologistDTO> addToPharmacy(@RequestBody WorkHoursDTO whDTO, @PathVariable("doctorId") Long doctorId) {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin la = laService.findByEmail(token);
		Pharmacy p = la.getPharmacy();
		Dermatologist dermatologist = dermaService.findById(doctorId);
		
		if (dermatologist == null) {
			return new ResponseEntity<DermatologistDTO>(HttpStatus.BAD_REQUEST);
		}
		
		if (p.getDermatologists().contains(dermatologist)) {
			return new ResponseEntity<DermatologistDTO>(HttpStatus.BAD_REQUEST);
		}
		
		p.getDermatologists().add(dermatologist);
		dermatologist.getPharmacies().add(p);
		
		pharmacyService.save(p);
		dermaService.save(dermatologist);
		
		LocalTime startTime = LocalTime.parse(whDTO.getStartTime());
		LocalTime finishTime = LocalTime.parse(whDTO.getFinishTime());
		
		WorkHours wh = new WorkHours(dermatologist, p, startTime, finishTime);
		whService.save(wh);
		
		return new ResponseEntity<DermatologistDTO>(new DermatologistDTO(dermatologist), HttpStatus.OK);		
	}
	
	@PutMapping(value = "/update-work-hours/{id}")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<String> updateWorkHours(@RequestBody WorkHoursDTO obj, @PathVariable("id") Long doctorId) {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin la = laService.findByEmail(token);
		Pharmacy p = la.getPharmacy();
		
		LocalTime startTime = LocalTime.parse(obj.getStartTime());
		LocalTime finishTime = LocalTime.parse(obj.getFinishTime());
		Dermatologist d = dermaService.findById(doctorId);
		
		if (d == null) {
			return new ResponseEntity<String>("No doctor found.", HttpStatus.BAD_REQUEST);
		}
		
		if (startTime.isAfter(finishTime)) {
			return new ResponseEntity<String>("Start time must be before finish time.", HttpStatus.BAD_REQUEST);
		}
		
		List<WorkHours> allDoctorsWH = whService.findAllWorkHoursForDoctor(doctorId);
		
		for (WorkHours workHours : allDoctorsWH) {
			if ((workHours.getStartTime().isBefore(startTime) && startTime.isBefore(workHours.getFinishTime())) || 
					(workHours.getStartTime().isBefore(finishTime) && finishTime.isBefore(workHours.getFinishTime()))) {
				return new ResponseEntity<String>("Doctor works in another pharmacy at this time", HttpStatus.BAD_REQUEST);
			}
		}
		
		List<WorkHours> whList = whService.findWorkingHoursForDoctorByIdAndPharmacyId(doctorId, p.getId());
		WorkHours wh = null;
		
		if (whList.size() == 0) {
			wh = new WorkHours(d, p, startTime, finishTime);
		} else {
			wh = whList.get(0);			
		}
		
		wh.setStartTime(startTime);
		wh.setFinishTime(finishTime);
		
		whService.save(wh);
		
		return new ResponseEntity<String>(g.toJson(new WorkHoursDTO(wh)), HttpStatus.OK);
	}
}
