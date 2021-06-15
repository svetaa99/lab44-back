package backend.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import backend.dto.DermatologistDTO;
import backend.dto.LabAdminDTO;
import backend.dto.PharmacistDTO;
import backend.models.Dermatologist;
import backend.models.LabAdmin;
import backend.models.Pharmacist;
import backend.models.Pharmacy;
import backend.models.WorkHours;
import backend.services.IDermatologistService;
import backend.services.ILabAdminService;
import backend.services.IPharmacistService;
import backend.services.IPharmacyService;
import backend.services.impl.WorkHoursService;

@RestController
@RequestMapping(value = "labadmins")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LabAdminController {

	//private static Gson g = new Gson();

	@Autowired
	private ILabAdminService laService;
	
	@Autowired
	private IPharmacistService pharmacistService;
	
	@Autowired
	private IDermatologistService dermatologistService;
	
	@Autowired
	private IPharmacyService pharmacyService;
	
	@Autowired
	private WorkHoursService whService;
	
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
	
	private List<DermatologistDTO> createDTOList(List<Dermatologist> derms) {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin la = laService.findByEmail(token);
		Pharmacy p = la.getPharmacy();
		
		List<DermatologistDTO> dDTOs = new ArrayList<DermatologistDTO>();
		
		for (Dermatologist dermatologist : derms) {
			List<WorkHours> whList = whService.findWorkingHoursForDoctorByIdAndPharmacyId(p.getId(), p.getId());
			WorkHours wh = whList.get(0);
			DermatologistDTO dDTO = new DermatologistDTO(dermatologist);
			dDTO.setStartTime(wh.getStartTime().toString());
			dDTO.setFinishTime(wh.getFinishTime().toString());
			dDTOs.add(dDTO);
		}
		
		return dDTOs;
	}
	
	@GetMapping("/registered-admin")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<LabAdminDTO> getRegisteredAdmin() {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin la = laService.findByEmail(token);
		
		return new ResponseEntity<LabAdminDTO>(new LabAdminDTO(la), HttpStatus.OK);
	}
	
	@GetMapping("/all-pharmacists")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<List<PharmacistDTO>> getAllPharmacists() {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin la = laService.findByEmail(token);
		Pharmacy p = la.getPharmacy();
		
		List<Pharmacist> pharmacists = pharmacistService.findAllByPharmacy(p.getId());
		List<PharmacistDTO> pDTOs = createPharmacistDTOList(pharmacists);
		
		return new ResponseEntity<List<PharmacistDTO>>(pDTOs, HttpStatus.OK);
	}
	
	@GetMapping("/all-dermatologists")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<List<DermatologistDTO>> getAllDermatologists() {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin la = laService.findByEmail(token);
		Pharmacy p = la.getPharmacy();
		
		List<Dermatologist> dermatologists = dermatologistService.findAll()
				.stream().filter(d -> d.getPharmacies().contains(p)).collect(Collectors.toList());
		List<DermatologistDTO> dDTOs = createDTOList(dermatologists);
			
		return new ResponseEntity<List<DermatologistDTO>>(dDTOs, HttpStatus.OK);
	}
	
	@DeleteMapping("/remove-pharmacist/{id}")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<String> removePharmacistFromPharmacy(@PathVariable("id") Long doctorId) {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin la = laService.findByEmail(token);
		Pharmacy p = la.getPharmacy();
		
		Pharmacist pharmacist = pharmacistService.findById(doctorId);
		if (pharmacist == null) {
			return new ResponseEntity<String>("No pharmacist with this id found", HttpStatus.BAD_REQUEST);
		}
		
		if (pharmacist.getPharmacy().getId() != p.getId()) {
			return new ResponseEntity<String>("Pharmacist not found in this pharmacy", HttpStatus.BAD_REQUEST);
		}
		
		p.getPharmacists().remove(pharmacist);
		pharmacist.setPharmacy(null);
		pharmacistService.save(pharmacist);
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@DeleteMapping("/remove-dermatologist/{id}")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<String> removeDermatologistFromPharmacy(@PathVariable("id") Long doctorId) {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin la = laService.findByEmail(token);
		Pharmacy p = la.getPharmacy();
		
		Dermatologist d = dermatologistService.findById(doctorId);
		if (d == null) {
			return new ResponseEntity<String>("No dermatologist with this id found", HttpStatus.BAD_REQUEST);
		}
		
		if (!d.getPharmacies().contains(p)) {
			return new ResponseEntity<String>("Dermatologist not found in this pharmacy", HttpStatus.BAD_REQUEST);
		}
		
		p.getDermatologists().remove(d);
		d.getPharmacies().remove(p);
		
		dermatologistService.save(d);
		pharmacyService.save(p);
		
		List<WorkHours> whList = whService.findWorkingHoursForDoctorByIdAndPharmacyId(doctorId, p.getId());
		WorkHours wh = whList.get(0);
		
		whService.delete(wh);
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}
