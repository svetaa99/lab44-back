package backend.controllers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.PharmacyDTO;
import backend.dto.PharmacyMedicinesDTO;
import backend.enums.Status;
import backend.models.Address;
import backend.models.LabAdmin;
import backend.models.Medicine;
import backend.models.Pharmacy;
import backend.models.PharmacyMedicineAddRemoveObject;
import backend.models.PharmacyMedicines;
import backend.models.Reservation;
import backend.models.ResponseObject;
import backend.models.WorkHours;
import backend.services.IAddressService;
import backend.services.ILabAdminService;
import backend.services.IMedicineService;
import backend.services.IPharmacyMedicinesService;
import backend.services.IPharmacyService;
import backend.services.IReservationService;
import backend.services.impl.WorkHoursService;

@RestController
@RequestMapping(value = "pharmacies")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PharmacyController {
	
	@Autowired
	private IPharmacyService pharmacyService;
	
	@Autowired
	private IMedicineService medicineService;
	
	@Autowired
	private IPharmacyMedicinesService pmService;
	
	@Autowired
	private ILabAdminService laService;
	
	@Autowired
	private IAddressService addressService;
	
	@Autowired
	private WorkHoursService whService;
	
	@Autowired
	private IReservationService reservationService;
	
	private List<PharmacyDTO> createPharmacyDTOList(List<Pharmacy> pharmacies) {
		List<PharmacyDTO> pharmaciesDTO = new ArrayList<PharmacyDTO>();
		
		for (Pharmacy pharmacy : pharmacies) {
			PharmacyDTO pharmacyDTO = new PharmacyDTO(pharmacy.getId(), pharmacy.getName(), pharmacy.getAddress(), pharmacy.getDescription(), pharmacy.getRating(), pharmacy.getpharmacistPrice());
			pharmaciesDTO.add(pharmacyDTO);
		}
		
		return pharmaciesDTO;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<PharmacyDTO>> getAllPharmacies() {
		List<Pharmacy> pharmacies = pharmacyService.findAll();
		List<PharmacyDTO> pharmaciesDTO = createPharmacyDTOList(pharmacies);
		
		return new ResponseEntity<List<PharmacyDTO>>(pharmaciesDTO, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PharmacyDTO> getById(@PathVariable Long id) {
		Pharmacy pharmacy = pharmacyService.findById(id);
		if (pharmacy.equals(null)) {
			return new ResponseEntity<PharmacyDTO>(HttpStatus.NOT_FOUND);
		}

		PharmacyDTO pharmacyDTO = new PharmacyDTO(pharmacy.getId(), pharmacy.getName(), pharmacy.getAddress(), pharmacy.getDescription(), pharmacy.getRating(), pharmacy.getpharmacistPrice());
		
		return new ResponseEntity<PharmacyDTO>(pharmacyDTO, HttpStatus.OK);
	}
	
	@GetMapping("/search/{name}")
	public ResponseEntity<List<PharmacyDTO>> getAllByName(@PathVariable String name) {
		List<Pharmacy> pharmacies = (List<Pharmacy>) pharmacyService.findAllByName(name);
		List<PharmacyDTO> pharmaciesDTO = createPharmacyDTOList(pharmacies);
		
		return new ResponseEntity<List<PharmacyDTO>>(pharmaciesDTO, HttpStatus.OK);
	}
	
	@GetMapping("/filter/{rating}")
	public ResponseEntity<List<PharmacyDTO>> getAllByRate(@PathVariable double rating) {
		List<Pharmacy> pharmacies = (List<Pharmacy>) pharmacyService.findAllByRating(rating);
		List<PharmacyDTO> pharmaciesDTO = createPharmacyDTOList(pharmacies);
		
		return new ResponseEntity<List<PharmacyDTO>>(pharmaciesDTO, HttpStatus.OK);
	}
	
	@GetMapping("/sort/price/{type}")
	public ResponseEntity<List<PharmacyDTO>> getAllSortedByPrice(@PathVariable String type) {

		List<Pharmacy> pharmacies = (List<Pharmacy>) pharmacyService.sortByPharmacistPrice(type);
		List<PharmacyDTO> pharmaciesDTO = createPharmacyDTOList(pharmacies);
		
		return new ResponseEntity<List<PharmacyDTO>>(pharmaciesDTO, HttpStatus.OK);
	}
	
	@GetMapping("/sort/rating/{type}")
	public ResponseEntity<List<PharmacyDTO>> getAllSortedByRating(@PathVariable String type) {

		List<Pharmacy> pharmacies = (List<Pharmacy>) pharmacyService.sortByRating(type);
		List<PharmacyDTO> pharmaciesDTO = createPharmacyDTOList(pharmacies);
		
		return new ResponseEntity<List<PharmacyDTO>>(pharmaciesDTO, HttpStatus.OK);
	}
	
	@PutMapping("/update-pharmacy")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<ResponseObject> updatePharmacy(@RequestBody PharmacyDTO obj) {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin la = laService.findByEmail(token);
		Pharmacy p = la.getPharmacy();
		
		if (obj.getName().equals("") || obj.getDescription().equals("")) {
			return new ResponseEntity<ResponseObject>(new ResponseObject(400, "Fields cannot be empty"), HttpStatus.BAD_REQUEST);
		}
		
		if (obj.getAddress() == null) {
			return new ResponseEntity<ResponseObject>(new ResponseObject(400, "Address cannot be empty"), HttpStatus.BAD_REQUEST);
		}
		
		p.setName(obj.getName());
		p.setDescription(obj.getDescription());
		
		Address oldAddress = obj.getAddress();
		Address address = new Address(oldAddress.getStreet(), oldAddress.getNumber(), oldAddress.getCity(), oldAddress.getCountry(),
				oldAddress.getLongitude(), oldAddress.getLatitude());
		p.setAddress(address);
		addressService.save(address);
		pharmacyService.save(p);
		
		return new ResponseEntity<ResponseObject>(new ResponseObject(p, 200, ""), HttpStatus.OK);
	}
	
	@GetMapping("/freeTerms/{time}")
	public ResponseEntity<List<Pharmacy>> getAllPharmaciesByTime(@PathVariable("time") String timeS) {
		LocalTime time = LocalTime.parse(timeS);
		List<WorkHours> wh = whService.getPharmaciesByTime(time);
		
		List<Pharmacy> pharmacies = new ArrayList<Pharmacy>();
		for (WorkHours workHours : wh) {
			if(!pharmacies.contains(workHours.getPharmacy())) {
				pharmacies.add(workHours.getPharmacy());
			}
		}
		System.out.println("sizeeeee: " + pharmacies.size());
		return new ResponseEntity<List<Pharmacy>>(pharmacies, HttpStatus.OK);
	}
	
	@PostMapping(value = "/add-medicine", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<PharmacyMedicinesDTO> addMedicineToPharmacy(@RequestBody PharmacyMedicineAddRemoveObject obj) {
		Pharmacy pharmacy = pharmacyService.findById(obj.getPharmacyId());
		if (pharmacy.equals(null)) {
			return new ResponseEntity<PharmacyMedicinesDTO>(HttpStatus.NOT_FOUND);
		}
		
		Medicine medicine = medicineService.findById(obj.getMedicineId());
		if (medicine.equals(null)) {
			return new ResponseEntity<PharmacyMedicinesDTO>(HttpStatus.NOT_FOUND);
		}
		
		double price = obj.getPrice();
		if (price < 0) {
			return new ResponseEntity<PharmacyMedicinesDTO>(HttpStatus.BAD_REQUEST);
		}
		
		int quantity = obj.getQuantity();
		if (quantity < 1) {
			return new ResponseEntity<PharmacyMedicinesDTO>(HttpStatus.BAD_REQUEST);
		}
		
		PharmacyMedicines pm = new PharmacyMedicines();
		pm.setPharmacy(pharmacy);
		pm.setMedicine(medicine);
		pm.setQuantity(quantity);
		
		long startDate = 0, endDate = 0;
		
		if (obj.getStartDate() == 0 || obj.getEndDate() == 0) {
			Calendar calendar = Calendar.getInstance();
			startDate = calendar.getTimeInMillis();
			calendar.add(Calendar.MONTH, 1);
			endDate = calendar.getTimeInMillis();
		} else {
			if (obj.getStartDate() > obj.getEndDate() || obj.getStartDate() < 0 || obj.getEndDate() < 0) {
				return new ResponseEntity<PharmacyMedicinesDTO>(HttpStatus.BAD_REQUEST);
			}
			
			startDate = obj.getStartDate();
			endDate = obj.getEndDate();
		}
		
		pm.setStartDate(startDate);
		pm.setEndDate(endDate);
		
		pm = pmService.save(pm);
		return new ResponseEntity<PharmacyMedicinesDTO>(new PharmacyMedicinesDTO(pm), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/delete-medicine")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<PharmacyMedicinesDTO> deleteMedicineFromPharmacy(@RequestBody PharmacyMedicineAddRemoveObject obj) {	
		PharmacyMedicines pm = pmService.findPharmacyMedicinesByIds(obj.getPharmacyId(), obj.getMedicineId());
		if (pm == null) {
			return new ResponseEntity<PharmacyMedicinesDTO>(HttpStatus.NOT_FOUND);
		}
		
		pmService.delete(pm);
		
		return new ResponseEntity<PharmacyMedicinesDTO>(HttpStatus.OK);
	}
	
	@PutMapping(value = "/update-quantity", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<PharmacyMedicinesDTO> updateMedicineQuantityInPharmacy(@RequestBody PharmacyMedicineAddRemoveObject obj) {
		PharmacyMedicines pm = pmService.findByPharmacyIdAndMedicineIdAndTodaysDate(obj.getPharmacyId(), obj.getMedicineId(), new Date().getTime());
		pm.setQuantity(obj.getQuantity());
		
		pm = pmService.save(pm);
		return new ResponseEntity<PharmacyMedicinesDTO>(new PharmacyMedicinesDTO(pm), HttpStatus.OK);
	}
	
	@PutMapping(value = "/update-price", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<ResponseObject> updateMedicinePrice(@RequestBody PharmacyMedicineAddRemoveObject obj) {
		ResponseObject response = pmService.updateMedicinePrice(obj);
		HttpStatus status = null;
		if (response.getStatus() == 200) {
			response.setRetObj(new PharmacyMedicinesDTO((PharmacyMedicines)response.getRetObj()));
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<ResponseObject>(response, status);
	}
	
	@GetMapping("/get-profits/{startDate}/{endDate}")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<ResponseObject> getPharmacyProfits(@PathVariable long startDate, @PathVariable long endDate) {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin admin = laService.findByEmail(token);
		Pharmacy p = admin.getPharmacy();
		
		List<Reservation> retList = reservationService.findByPharmacyAndStatus(p.getId(), Status.FINISHED);
		Map<Long, Double> retMap = new HashMap<Long, Double>();
		
		LocalDateTime startDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(startDate), ZoneId.systemDefault());
		LocalDateTime endDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(endDate), ZoneId.systemDefault());
		
		for (int i = startDateTime.getDayOfYear(); i <= endDateTime.getDayOfYear(); i++) {
			Year y = Year.of(startDateTime.getYear());
			LocalDate dt = y.atDay(i);
			double totalProfit = 0;
			for (int j = 0; j < retList.size(); j++) {
				LocalDateTime currDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(retList.get(j).getDate()), ZoneId.systemDefault());
				if (currDateTime.getDayOfYear() == i) {
					totalProfit += retList.get(j).getTotalPrice();
				}
			}
			retMap.put(dt.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli(), totalProfit);
		}
		
		
		
		return new ResponseEntity<ResponseObject>(new ResponseObject(new TreeMap<Long, Double>(retMap), 200, ""), HttpStatus.OK);
	}
	
}
