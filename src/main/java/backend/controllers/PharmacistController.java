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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import backend.dto.PharmacistDTO;
import backend.dto.WorkHoursDTO;
import backend.models.Pharmacist;
import backend.models.Role;
import backend.models.User;
import backend.models.WorkHours;
import backend.services.IPharmacistService;
import backend.services.impl.UserService;
import backend.services.impl.WorkHoursService;

@RestController
@RequestMapping(value = "pharmacist")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PharmacistController {
	
	@Autowired
	private IPharmacistService pharmacistService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WorkHoursService whService;
	
	private static Gson g = new Gson();
	
	private List<PharmacistDTO> createPharmacistDTOList(List<Pharmacist> pharmacists) {
		
		List<PharmacistDTO> pharmacistsDTO = new ArrayList<PharmacistDTO>();
		
		for (Pharmacist p : pharmacists) {
			PharmacistDTO pDTO = null;
			if (p.getPharmacy() != null) {
				List<WorkHours> whList = whService.findWorkingHoursForDoctorByIdAndPharmacyId(p.getId(), p.getPharmacy().getId());
				
				if (whList.size() == 0) {
					pDTO = new PharmacistDTO(p);
				} else {
					WorkHours wh = whList.get(0);
					pDTO = new PharmacistDTO(p.getId(), p.getName(), p.getSurname(), p.getEmail(), p.getAddress(), 
							p.getPhoneNum(), p.getRating(), p.getPharmacy(), wh.getStartTime().toString(), wh.getFinishTime().toString());
					
				}
			} else {
				pDTO = new PharmacistDTO(p);				
			}
			
			pharmacistsDTO.add(pDTO);
		}
		
		return pharmacistsDTO;
	}
	
	@GetMapping(value = "/all")
	@PreAuthorize("hasAnyRole('PATIENT', 'LAB_ADMIN', 'PHARMACIST')")
	public ResponseEntity<List<PharmacistDTO>> getAllPharmacists() {
		List<Pharmacist> pList = pharmacistService.findAll();
		List<PharmacistDTO> pDTOs = createPharmacistDTOList(pList);
		
		return new ResponseEntity<List<PharmacistDTO>>(pDTOs, HttpStatus.OK);
	}
	
	@GetMapping("/all-from-pharmacy/{pharmacyId}")
	public ResponseEntity<List<PharmacistDTO>> getAllFromPharmacy(@PathVariable Long pharmacyId) {
		List<Pharmacist> pharmacists = pharmacistService.findAllByPharmacy(pharmacyId);
		List<PharmacistDTO> pDTOs = createPharmacistDTOList(pharmacists);
		
		return new ResponseEntity<List<PharmacistDTO>>(pDTOs, HttpStatus.OK);
	}
	
	@PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PharmacistDTO>> searchPharmacists(@RequestBody PharmacistDTO obj) {
		String name = obj.getName();
		String surname = obj.getSurname();
		
		List<Pharmacist> pharmacists = pharmacistService.findAllByNameOrSurname(name, surname);
		
		if (name.equals("") && surname.equals("")) {
			return new ResponseEntity<List<PharmacistDTO>>(new ArrayList<PharmacistDTO>(), HttpStatus.OK);
		}
		
		return new ResponseEntity<List<PharmacistDTO>>(createPharmacistDTOList(pharmacists), HttpStatus.OK);
	}
	
	@PostMapping(value = "/filter/{params}/{values}")
	public ResponseEntity<List<PharmacistDTO>> filterPharmacists(@RequestBody List<PharmacistDTO> searchList, @PathVariable("params") String parameterList, @PathVariable("values") String valueList) {
		List<PharmacistDTO> retVal = searchList;
		
		String[] params = parameterList.split("\\+");
		String[] values = valueList.split("\\+");
		
		if (params[0].equals("true")) {
			retVal = retVal
					.stream()
					.filter(p -> p.getRating() == Double.parseDouble(values[0]))
					.collect(Collectors.toList());
		} else if (params[1].equals("true")) {
			retVal = retVal
					.stream()
					.filter(p -> p.getPharmacy().getId() == Integer.parseInt(values[1]))
					.collect(Collectors.toList());
		} else {
			return new ResponseEntity<List<PharmacistDTO>>(createPharmacistDTOList(pharmacistService.findAll()), HttpStatus.OK);
		}
		
		return new ResponseEntity<List<PharmacistDTO>>(retVal, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<List<PharmacistDTO>> getPharmacistsFromPharmacy(@PathVariable Long id) {
		List<Pharmacist> pharmacists = pharmacistService.findAllByPharmacy(id);
		return new ResponseEntity<List<PharmacistDTO>>(createPharmacistDTOList(pharmacists), HttpStatus.OK);
	}
	
	@GetMapping("/sort/rating/{type}/{id}")
	public ResponseEntity<List<PharmacistDTO>> getSortedByRating(@PathVariable String type, @PathVariable Long id) {
		List<Pharmacist> pharmacists = (List<Pharmacist>) pharmacistService.sortByRating(type, id);
		List<PharmacistDTO> pharmacistsDTO = createPharmacistDTOList(pharmacists);
		
		return new ResponseEntity<List<PharmacistDTO>>(pharmacistsDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/create-new", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<String> createNewPharmacist(@RequestBody PharmacistDTO obj) {
		Pharmacist p = new Pharmacist();
		
		List<User> allP = userService.findAll();
		int size = allP.size();
		
		for (User user : allP) {
			if (user.getEmail().equals(obj.getEmail())) {
				return new ResponseEntity<String>("Already existing email.", HttpStatus.BAD_REQUEST);
			}
		}
		
		if (obj.getName().equals("") || obj.getSurname().equals("") || obj.getEmail().equals("") || obj.getPhoneNum().equals("") || 
				obj.getAddress() == 0l) {
			return new ResponseEntity<String>("Empty field.", HttpStatus.BAD_REQUEST);
		}
		
		LocalTime startTime = LocalTime.parse(obj.getStartTime());
		LocalTime finishTime = LocalTime.parse(obj.getFinishTime());
		
		if (finishTime.isBefore(startTime)) {
			return new ResponseEntity<String>("Invalid start and finish work hours.", HttpStatus.BAD_REQUEST);
		}
		
		p.setId(size + 1l);
		p.setName(obj.getName());
		p.setSurname(obj.getSurname());
		p.setEmail(obj.getEmail());
		p.setPassword("chang3m3");   // generic password that the pharmacist will change when first loggs in
		p.setAddress(obj.getAddress());
		p.setPhoneNum(obj.getPhoneNum());
		p.setPharmacy(obj.getPharmacy());
		p.setRating(0);
		
		Role role = new Role();
		role.setName("ROLE_PHARMACIST");
		role.setId(3l);
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		
		p.setRoles(roles);
		
		pharmacistService.save(p);
		
		WorkHours wh = new WorkHours();
		wh.setDoctor(p);
		wh.setPharmacy(obj.getPharmacy());
		wh.setStartTime(startTime);
		wh.setFinishTime(finishTime);
		whService.save(wh);
		
		return new ResponseEntity<String>(g.toJson(new PharmacistDTO(p)), HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/update-work-hours/{id}")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<String> updateWorkHours(@RequestBody WorkHoursDTO obj, @PathVariable("id") Long doctorId) {
		LocalTime startTime = LocalTime.parse(obj.getStartTime());
		LocalTime finishTime = LocalTime.parse(obj.getFinishTime());
		Pharmacist p = pharmacistService.findById(doctorId);
		
		if (p == null) {
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
		
		List<WorkHours> whList = whService.findWorkingHoursForDoctorByIdAndPharmacyId(doctorId, p.getPharmacy().getId());
		WorkHours wh = null;
		
		if (whList.size() == 0) {
			wh = new WorkHours(p, p.getPharmacy(), startTime, finishTime);
		} else {
			wh = whList.get(0);			
		}
		
		wh.setStartTime(startTime);
		wh.setFinishTime(finishTime);
		
		whService.save(wh);
		
		return new ResponseEntity<String>(g.toJson(new WorkHoursDTO(wh)), HttpStatus.OK);
	}
	
}
