package backend.controllers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import backend.dto.MedicineDTO;
import backend.enums.Status;
import backend.models.LabAdmin;
import backend.models.Medicine;
import backend.models.Patient;
import backend.models.Pharmacy;
import backend.models.PharmacyMedicines;
import backend.models.Reservation;
import backend.models.ResponseObject;
import backend.models.Visit;
import backend.services.ILabAdminService;
import backend.services.IMedicineService;
import backend.services.IPharmacyMedicinesService;
import backend.services.IReservationService;
import backend.services.impl.PatientService;
import backend.services.impl.VisitService;

@RestController
@RequestMapping(value = "medicines")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MedicineController {
	
	@Autowired
	private IMedicineService medicineService;
	
	@Autowired
	private ILabAdminService laService;
	
	@Autowired
	private VisitService visitService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private IPharmacyMedicinesService pharmMedService;
	
	@Autowired
	private IReservationService reservationService;
	
	private List<MedicineDTO> createMedicineDTOList(List<Medicine> medicines) {
		List<MedicineDTO> medicinesDTO = new ArrayList<MedicineDTO>();
		
		for (Medicine medicine : medicines) {
			MedicineDTO medicineDTO = new MedicineDTO(medicine);
			medicinesDTO.add(medicineDTO);
		}
		
		return medicinesDTO;
	}

	@GetMapping("/all")
	public ResponseEntity<List<MedicineDTO>> getAllMedicines() {
		List<Medicine> medicines = medicineService.findAll();
		List<MedicineDTO> medicinesDTO = createMedicineDTOList(medicines);
		
		return new ResponseEntity<List<MedicineDTO>>(medicinesDTO, HttpStatus.OK);
	}
	
	//method is used for visits
	@GetMapping("/all/{visitId}") 
	public ResponseEntity<List<MedicineDTO>> getAllMedicinesInPharmacy(@PathVariable Long visitId){
		Visit v = visitService.findById(visitId);
		Long pharmacyId = v.getPharmacy();
		
		List<PharmacyMedicines> pharmMedicines = pharmMedService.findAllByPharmacyId(pharmacyId);
		
		List<Medicine> medicines = new ArrayList<Medicine>();
		pharmMedicines.forEach(med -> { if(!medicines.contains(med.getMedicine())) medicines.add(med.getMedicine()); });
		
		List<MedicineDTO> medicinesDTO = createMedicineDTOList(medicines);
		
		return new ResponseEntity<List<MedicineDTO>>(medicinesDTO, HttpStatus.OK);
	}
	
	@GetMapping("/search/{name}")
	public ResponseEntity<List<MedicineDTO>> getAllByName(@PathVariable String name) {
		List<Medicine> medicines = (List<Medicine>) medicineService.findAllByName(name);
		List<MedicineDTO> medicinesDTO = createMedicineDTOList(medicines);
		
		return new ResponseEntity<List<MedicineDTO>>(medicinesDTO, HttpStatus.OK);
	}
	
	//method is used for visits
	@GetMapping("/search/{name}/{visitId}")
	public ResponseEntity<List<MedicineDTO>> getAllByNameInPharmacy(@PathVariable String name, @PathVariable Long visitId) {
		Visit v = visitService.findById(visitId);
		Long pharmacyId = v.getPharmacy();
		
		List<PharmacyMedicines> pharmMedicines = pharmMedService.findByMedicineNameAndPharmacyId(name, pharmacyId);
		
		List<Medicine> medicines = new ArrayList<Medicine>();
		pharmMedicines.forEach(med -> medicines.add(med.getMedicine()));
		
		List<MedicineDTO> medicinesDTO = createMedicineDTOList(medicines);
		
		return new ResponseEntity<List<MedicineDTO>>(medicinesDTO, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MedicineDTO> getById(@PathVariable Long id) {
		Medicine medicine = medicineService.findById(id);
		if (medicine.equals(null)) {
			return new ResponseEntity<MedicineDTO>(HttpStatus.NOT_FOUND);
		}
		
		MedicineDTO medicineDTO = new MedicineDTO(medicine);
		
		return new ResponseEntity<MedicineDTO>(medicineDTO, HttpStatus.OK);
	}
	@GetMapping("/substitute/{id}/{visitId}")
	public ResponseEntity<List<MedicineDTO>> getSubstituteForMedicine(@PathVariable Long id, @PathVariable Long visitId){
		List<Medicine> substituteList = medicineService.getSubstituteForMedicine(id);
		
		//filter this list so there is no allergens or unavailable medicine
		Visit v = visitService.findById(visitId);
		Patient p = patientService.findById(v.getPatientId());
		List<Medicine> allergies = p.getAllergies();
		
		List<PharmacyMedicines> available = pharmMedService.findAvailableByPharmacyId(v.getPharmacy());
		List<Medicine> availableMedicine = new ArrayList<Medicine>();
		available.forEach(m -> availableMedicine.add(m.getMedicine()));
		
		List<Medicine> filteredList = 
				substituteList
				.stream()
				.filter(med -> !allergies.contains(med) && availableMedicine.contains(med))
				.collect(Collectors.toList());
		
		List<MedicineDTO> dtoList = createMedicineDTOList(filteredList);
		
		return new ResponseEntity<List<MedicineDTO>>(dtoList, HttpStatus.OK);
	}
	
	@GetMapping("/get-monthly-medicine-consumption")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<ResponseObject> getMonthlyMedicines() {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin admin = laService.findByEmail(token);
		Pharmacy p = admin.getPharmacy();
		
		List<Reservation> retList = reservationService.findByPharmacyAndStatus(p.getId(), Status.FINISHED);
		Map<Integer, Integer> retMap = new HashMap<Integer, Integer>();
		
		for (int i = 1; i < 13; i++) {
			int monthNum = i;
			int totalCount = 0;
			List<Reservation> res = retList.stream()
					.filter(r -> LocalDateTime.ofInstant(Instant.ofEpochMilli(r.getDate()), ZoneId.systemDefault()).getMonthValue() == monthNum)
					.collect(Collectors.toList());
			
			for (Reservation reservation : res) {
				totalCount += reservation.getQuantity();
			}
			
			retMap.put(i, totalCount);
		}
		
		return new ResponseEntity<ResponseObject>(new ResponseObject(retMap, 200, ""), HttpStatus.OK);
	}
	
	@GetMapping("/get-quarter-medicine-consumption")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<ResponseObject> getQuarterMedicineConsupmtion() {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin admin = laService.findByEmail(token);
		Pharmacy p = admin.getPharmacy();
		
		List<Reservation> retList = reservationService.findByPharmacyAndStatus(p.getId(), Status.FINISHED);
		Map<Integer, Integer> retMap = new HashMap<Integer, Integer>();
		
		int quarter = 1;
		for (int i = 1; i < 13; i+=4) {
			int totalCount = 0;
			for (int j = 0; j < 4; j++) {
				int monthNum = i + j;
				List<Reservation> res = retList.stream()
						.filter(r -> LocalDateTime.ofInstant(Instant.ofEpochMilli(r.getDate()), ZoneId.systemDefault()).getMonthValue() == monthNum)
						.collect(Collectors.toList());
				
				for (Reservation reservation : res) {
					totalCount += reservation.getQuantity();
				}
				
			}
			retMap.put(quarter, totalCount);
			quarter++;
		}
		
		return new ResponseEntity<ResponseObject>(new ResponseObject(retMap, 200, ""), HttpStatus.OK);
	}
	
	@GetMapping("/get-year-medicine-consumption/{yearNum}")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<ResponseObject> getYearMedicineConsupmtion(@PathVariable int yearNum) {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin admin = laService.findByEmail(token);
		Pharmacy p = admin.getPharmacy();
		
		List<Reservation> retList = reservationService.findByPharmacyAndStatus(p.getId(), Status.FINISHED);
		Map<Integer, Integer> retMap = new HashMap<Integer, Integer>();
		
		for (int i = 0; i < yearNum; i++) {
			int selectedYear = LocalDateTime.now().getYear() - i;
			int totalCount = 0;
			List<Reservation> res = retList.stream()
					.filter(r -> LocalDateTime.ofInstant(Instant.ofEpochMilli(r.getDate()), ZoneId.systemDefault()).getYear() == selectedYear)
					.collect(Collectors.toList());
			
			for (Reservation reservation : res) {
				totalCount += reservation.getQuantity();
			}
			
			retMap.put(selectedYear, totalCount);
		}
		
		return new ResponseEntity<ResponseObject>(new ResponseObject(retMap, 200, ""), HttpStatus.OK);
	}
	
}
