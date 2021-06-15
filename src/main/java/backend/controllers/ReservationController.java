package backend.controllers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.ReservationDTO;
import backend.enums.Status;
import backend.models.Medicine;
import backend.models.Patient;
import backend.models.Penalty;
import backend.models.Pharmacist;
import backend.models.Pharmacy;
import backend.models.PharmacyMedicines;
import backend.models.Reservation;
import backend.models.User;
import backend.services.IMedicineService;
import backend.services.IPatientService;
import backend.services.IPharmacyMedicinesService;
import backend.services.IPharmacyService;
import backend.services.IReservationService;
import backend.services.impl.PenaltyService;
import backend.services.impl.PharmacistService;
import backend.services.impl.UserService;

@RestController
@RequestMapping(value = "reservations")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReservationController {
	
	@Autowired
	private IReservationService reservationService;
	
	@Autowired
	private IPatientService patientService;
	
	@Autowired
	private IPharmacyService pharmacyService;
	
	@Autowired
	private IMedicineService medicineService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PharmacistService pharmacistService;

	@Autowired
	private UserService us;
	
	@Autowired
	private PenaltyService penaltyService;

	
	private List<ReservationDTO> createReservationDTOList(List<Reservation> reservations) {
		List<ReservationDTO> rDTOs = new ArrayList<ReservationDTO>();
		
		for (Reservation reservation : reservations) {
			rDTOs.add(new ReservationDTO(reservation));
		}
		
		return rDTOs;
	}
	
	@GetMapping()
	public ResponseEntity<List<ReservationDTO>> getAll() {
		List<Reservation> reservations = reservationService.findAll();
		
		return new ResponseEntity<List<ReservationDTO>>(createReservationDTOList(reservations), HttpStatus.OK);
	}
	
	@GetMapping("/count/{patientId}")
	public long count(@PathVariable Long patientId) {
		return penaltyService.countPenaltiesByPatientId(patientId);
	}
	
	@GetMapping("/{reservationId}")
	@PreAuthorize("hasRole('PHARMACIST')")
	public ResponseEntity<ReservationDTO> getById(@PathVariable Long reservationId){
		Reservation r = reservationService.findById(reservationId);
		
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findUserByEmail(token);
		Pharmacist p = (Pharmacist) u;
		
		ReservationDTO rDTO = new ReservationDTO();
		ResponseEntity<ReservationDTO> empty = new ResponseEntity<ReservationDTO>(rDTO, HttpStatus.OK);
		
		if(r == null) 
			return empty;

		if(!r.getPharmacy().equals(p.getPharmacy()))
			return empty;
		
		if(r.getDate() < System.currentTimeMillis())
			return empty;
		
		if(r.getStatus() != Status.RESERVED)
			return empty;
		
		rDTO = new ReservationDTO(r);
		return new ResponseEntity<ReservationDTO>(rDTO, HttpStatus.OK);
	}
	
	@GetMapping("/confirm/{reservationId}")
	@PreAuthorize("hasRole('PHARMACIST')")
	public ResponseEntity<ReservationDTO> confirmReservation(@PathVariable Long reservationId){
		Reservation r = reservationService.findById(reservationId);
		
		if(r == null) {
			return new ResponseEntity<ReservationDTO>(new ReservationDTO(), HttpStatus.OK);
		}
		
		notifyPatientViaEmail(r);
		
		r.setStatus(Status.FINISHED);
		reservationService.save(r);
		
		return new ResponseEntity<ReservationDTO>(new ReservationDTO(r), HttpStatus.OK);
	}
	
	@GetMapping("/reservations-my")
	public ResponseEntity<List<ReservationDTO>> getMy() {
		
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = us.findUserByEmail(token);
		if (u == null) {
			System.out.println("Must login");
		}
		
		List<Reservation> reservations = reservationService.findMy(u.getId());
		
		return new ResponseEntity<List<ReservationDTO>>(createReservationDTOList(reservations), HttpStatus.OK);
	}
	
	@GetMapping("cancel-reservation/{id}")
	public ResponseEntity<List<ReservationDTO>> cancelReservation(@PathVariable Long id) {
		reservationService.cancelReservationPatient(id);
		
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = us.findUserByEmail(token);
		List<Reservation> reservations = reservationService.findMy(u.getId());
		
		return new ResponseEntity<List<ReservationDTO>>(createReservationDTOList(reservations), HttpStatus.OK);
	}
	
	@Scheduled(cron = "0 */15 * * * *")
	public void givePenalty() {
		
		List<Reservation> allReservations = reservationService.findReserved();
		
		for (Reservation reservation : allReservations) {
			LocalDateTime reservationDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(reservation.getDate()), TimeZone.getDefault().toZoneId());
			if (reservationDate.isBefore(LocalDateTime.now())) {
				Penalty penalty = new Penalty(reservation.getPatient().getId(), LocalDate.now());
				penaltyService.save(penalty);
				
				reservation.setStatus(Status.CANCELED);
				reservationService.save(reservation);
			}
		}
	}
	
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReservationDTO> createReservation(@RequestBody Reservation reservation) {
		Patient patient = reservation.getPatient();
		
		if (patientService.findById(patient.getId()).equals(null)) {
			return new ResponseEntity<ReservationDTO>(HttpStatus.NOT_FOUND);
		}
		
		// Check if has 3 penalties
		if (penaltyService.countPenaltiesByPatientId(patient.getId()) >= 3) {
			return new ResponseEntity<ReservationDTO>(HttpStatus.BAD_REQUEST);
		}
		
		Pharmacy pharmacy = reservation.getPharmacy();
		if (pharmacyService.findById(pharmacy.getId()).equals(null)) {
			return new ResponseEntity<ReservationDTO>(HttpStatus.NOT_FOUND);
		}
		
		Medicine medicine = reservation.getMedicine();
		if (medicineService.findById(medicine.getId()).equals(null)) {
			return new ResponseEntity<ReservationDTO>(HttpStatus.NOT_FOUND);
		}
		
		long date = reservation.getDate();
		if (date < 0) {
			return new ResponseEntity<ReservationDTO>(HttpStatus.BAD_REQUEST);	// status code 400
		}
		
		int quantity = reservation.getQuantity();
		if (quantity < 1) {
			return new ResponseEntity<ReservationDTO>(HttpStatus.BAD_REQUEST);	// status code 400
		}
		
		double totalPrice = reservation.getTotalPrice();
		if (totalPrice < 0) {
			return new ResponseEntity<ReservationDTO>(HttpStatus.BAD_REQUEST);	// status code 400
		}
		
		reservation = reservationService.createReservation(reservation);
		if (reservation == null) {
			return new ResponseEntity<ReservationDTO>(HttpStatus.BAD_REQUEST);
		}
		
		ReservationDTO rDTO = new ReservationDTO(reservation);
		return new ResponseEntity<ReservationDTO>(rDTO, HttpStatus.OK);
	}
	
	private void notifyPatientViaEmail(Reservation r) {
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		 // Get a Properties object
		    Properties props = System.getProperties();
		    props.setProperty("mail.smtp.host", "smtp.gmail.com");
		    props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		    props.setProperty("mail.smtp.socketFactory.fallback", "false");
		    props.setProperty("mail.smtp.port", "465");
		    props.setProperty("mail.smtp.socketFactory.port", "465");
		    props.put("mail.smtp.auth", "true");
		    props.put("mail.debug", "true");
		    props.put("mail.store.protocol", "pop3");
		    props.put("mail.transport.protocol", "smtp");
		    final String username = "labonibato44@gmail.com";//
		    final String password = "filipsvetauki1";
		    try{
		      Session session = Session.getDefaultInstance(props, 
		                          new Authenticator(){
		                             protected PasswordAuthentication getPasswordAuthentication() {
		                                return new PasswordAuthentication(username, password);
		                             }});

		   // -- Create a new message --
		      Message msg = new MimeMessage(session);
		      String patientsEmail = r.getPatient().getEmail();
		   // -- Set the FROM and TO fields --
		      msg.setFrom(new InternetAddress("labonibato44@gmail.com"));
		      msg.setRecipients(Message.RecipientType.TO, 
		                        InternetAddress.parse(patientsEmail, false));
		      msg.setSubject("Issued medicine");
		      
			  String NAME = r.getPatient().getName();
			  
			  String token = SecurityContextHolder.getContext().getAuthentication().getName();
			  User u = userService.findUserByEmail(token);
			  Long pharmacistId = u.getId();
			  
			  String DOCTORS_NAME = pharmacistService.findById(pharmacistId).getName();
			  
			  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
			  String DATE_TIME = LocalDateTime.now().format(formatter);
			  
			  String MEDICINE_NAME = r.getMedicine().getName();
			  
			  double PRICE = r.getTotalPrice();
			  
		      msg.setText("Dear " + NAME + ",\nConfirmation that pharmacist " + DOCTORS_NAME + " issued you medicine " 
		      + MEDICINE_NAME + " at " + DATE_TIME + " for a price of " + PRICE + ".\nSincerely yours,\nLABONI44.");
		      msg.setSentDate(new Date());
		      Transport.send(msg);
		      System.out.println("Message sent.");
		    }catch (MessagingException e){ 
		    	e.printStackTrace();
		    }
	}
}
