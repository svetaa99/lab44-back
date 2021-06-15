package backend.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.DermatologistDTO;
import backend.dto.PharmacistDTO;
import backend.models.Dermatologist;
import backend.models.Doctor;
import backend.models.LabAdmin;
import backend.models.Pharmacist;
import backend.models.Pharmacy;
import backend.models.Ratings;
import backend.models.ResponseObject;
import backend.models.User;
import backend.services.IDermatologistService;
import backend.services.ILabAdminService;
import backend.services.IPharmacistService;
import backend.services.impl.DoctorService;
import backend.services.impl.RatingService;
import backend.services.impl.ReservationService;
import backend.services.impl.UserService;
import backend.services.impl.VisitService;

@RestController
@RequestMapping(value = "ratings")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RatingsController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private VisitService visitService;
	
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private ILabAdminService laService;
	
	@Autowired
	private IPharmacistService pharmacistService;
	
	@Autowired
	private IDermatologistService dermatologistService;
	
	@GetMapping("/rate-pharmacy/{pharmacyId}/{mark}")
	public ResponseEntity<String> ratePharmacy(@PathVariable Long pharmacyId, @PathVariable int mark) {
		
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findUserByEmail(token);
		int type = 1;
		if(!ratingService.findByPatientAndObjAndType(u.getId(), pharmacyId, type).isEmpty()) {
			System.out.println("Vec je ocenio");
			return new ResponseEntity<String>("You already rated this pharmacy.", HttpStatus.BAD_REQUEST);
		}
		
		if (!reservationService.findByPatientAndPharmacyReserved(pharmacyId, u.getId()).isEmpty() || !visitService.findByPatientAndPharmacy(u.getId(), pharmacyId).isEmpty()) {
			Ratings rating = new Ratings();
			rating.setMark(mark);
			rating.setObjId(pharmacyId);
			rating.setPatientId(u.getId());
			rating.setType(type); // 1 for pharmacy
			
			ratingService.save(rating);
			
			return new ResponseEntity<String>(HttpStatus.OK);
		} else {
			String msg = "You need to have finished appointment in this pharmacy or reserved medicine";
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/rate-medicine/{medicineId}/{mark}")
	public ResponseEntity<String> rateMedicine(@PathVariable Long medicineId, @PathVariable int mark) {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findUserByEmail(token);
		
		if(!ratingService.findByPatientAndObjAndType(u.getId(), medicineId, 4).isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		if (reservationService.findByPatientAndMedicineReserved(u.getId(), medicineId).isEmpty()) {
			return new ResponseEntity<String>("Nema pravo", HttpStatus.BAD_REQUEST);
		}
		
		Ratings rating = new Ratings();
		rating.setMark(mark);
		rating.setObjId(medicineId);
		rating.setPatientId(u.getId());
		rating.setType(4); // 4 for medicine
		
		ratingService.save(rating);
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@GetMapping("/rate-docotr/{doctorId}/{mark}")
	public ResponseEntity<String> rateDoctor(@PathVariable Long doctorId, @PathVariable int mark) {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findUserByEmail(token);
		
		Doctor doc = doctorService.findById(doctorId);
		
		if (doc instanceof Dermatologist) {
			if(!ratingService.findByPatientAndObjAndType(u.getId(), doctorId, 2).isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
		} else {
			if(!ratingService.findByPatientAndObjAndType(u.getId(), doctorId, 3).isEmpty()) {
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
		}
		
		if (visitService.findByDoctorAndPatient(doctorId, u.getId()).isEmpty()) {
			return new ResponseEntity<String>("Nema pravo", HttpStatus.BAD_REQUEST);
		}
		
		Ratings rating = new Ratings();
		rating.setMark(mark);
		rating.setObjId(doctorId);
		rating.setPatientId(u.getId());
		
		Doctor d = doctorService.findById(rating.getObjId());
		if (d instanceof Dermatologist) {
			rating.setType(2);	// 2 for dermatologist
		} else {
			rating.setType(3);	// 3 for pharmacist
		}
		
		ratingService.save(rating);
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@GetMapping("/get-rating/{objId}/{type}")
	public ResponseEntity<Double> getRating(@PathVariable Long objId, @PathVariable int type) {
		List<Ratings> ratings = ratingService.findByObjIdAndType(objId, type);
		
		if (ratings.size() == 0) {
			return new ResponseEntity<Double>(HttpStatus.BAD_REQUEST);
		}
		
		int count = ratings.size();
		
		if (count == 0) {
			double mark = 0;
			return new ResponseEntity<Double>(mark, HttpStatus.OK);
		}
		
		int total = 0;
		for (Ratings rating : ratings) {
			total += rating.getMark();
		}
		
		double mark = total / count;
		return new ResponseEntity<Double>(mark, HttpStatus.OK);
	}
	
	private double calculateRatings(List<Ratings> ratings) {
		int count = ratings.size();
		
		if (count == 0) {
			double mark = 0;
			return 0;
		}
		
		int total = 0;
		for (Ratings rating : ratings) {
			total += rating.getMark();
		}
		
		double mark = total / count;
		return mark;
	}

	@GetMapping("/get-dermatologists-rating")
	public ResponseEntity<ResponseObject> getDermatologistsRating() {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin la = laService.findByEmail(token);
		Pharmacy p = la.getPharmacy();
		
		List<DermatologistDTO> dtos = new ArrayList<DermatologistDTO>();
		
		List<Dermatologist> dermatologists = dermatologistService.findAll()
				.stream().filter(d -> d.getPharmacies().contains(p)).collect(Collectors.toList());
		for (Dermatologist dermatologist : dermatologists) {
			double rating = calculateRatings(ratingService.findByObjIdAndType(dermatologist.getId(), 2));
			DermatologistDTO dto = new DermatologistDTO();
			dto.setName(dermatologist.getName());
			dto.setSurname(dermatologist.getSurname());
			dto.setEmail(dermatologist.getEmail());
			dto.setRating(rating);
			dtos.add(dto);
		}
		
		return new ResponseEntity<ResponseObject>(new ResponseObject(dtos, 200, ""), HttpStatus.OK);
	}
	
	@GetMapping("/get-pharmacists-rating")
	public ResponseEntity<ResponseObject> getPharmacistsRating() {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin la = laService.findByEmail(token);
		Pharmacy p = la.getPharmacy();
		
		List<PharmacistDTO> dtos = new ArrayList<PharmacistDTO>();
		
		List<Pharmacist> pList = pharmacistService.findAllByPharmacy(p.getId());
		for (Pharmacist pharmacist : pList) {
			double rating = calculateRatings(ratingService.findByObjIdAndType(pharmacist.getId(), 3));
			PharmacistDTO dto = new PharmacistDTO();
			dto.setName(pharmacist.getName());
			dto.setSurname(pharmacist.getSurname());
			dto.setEmail(pharmacist.getEmail());
			dto.setRating(rating);
			dtos.add(dto);
		}
		
		return new ResponseEntity<ResponseObject>(new ResponseObject(dtos, 200, ""), HttpStatus.OK);
	}
}
