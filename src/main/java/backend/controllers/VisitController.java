package backend.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import backend.dto.PatientDTO;
import backend.dto.VisitDTO;
import backend.enums.Status;
import backend.models.Dermatologist;
import backend.models.Doctor;
import backend.models.LabAdmin;
import backend.models.Patient;
import backend.models.Pharmacist;
import backend.models.Pharmacy;
import backend.models.Report;
import backend.models.ResponseObject;
import backend.models.User;
import backend.models.Visit;
import backend.models.WorkHours;
import backend.services.ILabAdminService;
import backend.services.impl.DoctorService;
import backend.services.impl.PatientService;
import backend.services.impl.PenaltyService;
import backend.services.impl.ReportService;
import backend.services.impl.UserService;
import backend.services.impl.VisitService;
import backend.services.impl.WorkHoursService;

@RestController
@RequestMapping(value = "appointments")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VisitController {

	@Autowired
	private VisitService visitService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private PenaltyService penaltyService;
	
	@Autowired
	private ILabAdminService laService;
	
	@Autowired
	private WorkHoursService whService;
	
	private static Gson g = new Gson();
	
	@PostMapping(value = "/make-appointment", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<String> makeAppointment(@RequestBody Visit newReservation){
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findUserByEmail(token);
		
		Long doctorId = u.getId();
		
		newReservation.setDoctorId(doctorId);
		newReservation.setStatus(Status.RESERVED);
		
		if(visitService.save(newReservation) == null) {		//Transactional method
			if(!checkTermTaken(newReservation))
				return new ResponseEntity<String>("Patient unavailable", HttpStatus.OK);
			
			if(!checkTermDerm(newReservation, newReservation.getDoctorId()))
				return new ResponseEntity<String>("You already have scheduled meeting at that time", HttpStatus.OK);
			
			if(!checkIfInWorkingHours(newReservation)) {
				return new ResponseEntity<String>("Not in your working hours", HttpStatus.OK);
			}
		}
		Patient p = patientService.findById(newReservation.getPatientId());
		String patientsEmail = p.getEmail();
		
		LocalDateTime ldt = newReservation.getStart();
		
		notifyPatientViaEmail(patientsEmail, doctorId, ldt, p.getName());
		
		return new ResponseEntity<String>(g.toJson(newReservation), HttpStatus.OK);
	}

	@PostMapping(value="/make-appointment-patient", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<String> makeAppointmentPatient(@RequestBody Visit newReservation){
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findUserByEmail(token);
		Long patientId = u.getId();
		
		// Check if has 3 penalties
		if (penaltyService.countPenaltiesByPatientId(u.getId()) >= 3) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		if(visitService.makePharmacistAppointmentPatient(newReservation, patientId) == null)//transactional method
			return new ResponseEntity<String>("FAIL", HttpStatus.OK);//obradi na frontu!
		
		System.out.println(newReservation);
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

	@PostMapping(value = "/report-visit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<String> saveAppointment(@RequestBody Report newReport){
		Visit v = visitService.findById(newReport.getVisitId());
		v.setStatus(Status.FINISHED);
		visitService.save(v);
		
		reportService.save(newReport);
		return new ResponseEntity<String>("Report saved!", HttpStatus.OK);
	}
	
	@GetMapping("/to-dermatologists")
	public ResponseEntity<List<VisitDTO>> getMyAppointmentsToDermatologists() {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findUserByEmail(token);
		
		List<Visit> appointments = visitService.findByPatientIdEquals(u.getId());
		List<Visit> myAppointments = new ArrayList<Visit>();
		
		for (Visit appointment : appointments) {
			if (appointment.getStatus().equals(Status.RESERVED)) {
				Doctor d = doctorService.findById(appointment.getDoctorId());
				if (d instanceof Dermatologist) {
					myAppointments.add(appointment);
				}
			}
		}
		
		List<VisitDTO> visitsDTO = new ArrayList<>();
		for (Visit visit : myAppointments) {
			VisitDTO visitDTO = new VisitDTO(visit.getId(), patientService.findById(visit.getPatientId()), doctorService.findById(visit.getDoctorId()), visit.getStart(), visit.getFinish());
			visitsDTO.add(visitDTO);
		}
		
		return new ResponseEntity<List<VisitDTO>>(visitsDTO, HttpStatus.OK);
	}
	
	@GetMapping("/visits-to-dermatologists")
	public ResponseEntity<List<VisitDTO>> getMyVisitsToDermatologists() {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findUserByEmail(token);
		
		List<Visit> appointments = visitService.findByPatientIdEquals(u.getId());
		List<Visit> myAppointments = new ArrayList<Visit>();
		
		for (Visit appointment : appointments) {
			if (appointment.getStatus().equals(Status.FINISHED)) {
				Doctor d = doctorService.findById(appointment.getDoctorId());
				if (d instanceof Dermatologist) {
					myAppointments.add(appointment);
				}
			}
		}
		
		List<VisitDTO> visitsDTO = new ArrayList<>();
		for (Visit visit : myAppointments) {
			VisitDTO visitDTO = new VisitDTO(visit.getId(), patientService.findById(visit.getPatientId()), doctorService.findById(visit.getDoctorId()), visit.getStart(), visit.getFinish());
			visitsDTO.add(visitDTO);
		}
		
		return new ResponseEntity<List<VisitDTO>>(visitsDTO, HttpStatus.OK);
	}
	
	@GetMapping("/visits-to-pharmacist")
	public ResponseEntity<List<VisitDTO>> getMyVisitsToPharmacist() {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findUserByEmail(token);
		
		List<Visit> appointments = visitService.findByPatientIdEquals(u.getId());
		List<Visit> myAppointments = new ArrayList<Visit>();
		
		for (Visit appointment : appointments) {
			if (appointment.getStatus().equals(Status.FINISHED)) {
				Doctor d = doctorService.findById(appointment.getDoctorId());
				if (d instanceof Pharmacist) {
					myAppointments.add(appointment);
				}
			}
		}
		
		List<VisitDTO> visitsDTO = new ArrayList<>();
		for (Visit visit : myAppointments) {
			VisitDTO visitDTO = new VisitDTO(visit.getId(), patientService.findById(visit.getPatientId()), doctorService.findById(visit.getDoctorId()), visit.getStart(), visit.getFinish());
			visitsDTO.add(visitDTO);
		}
		
		return new ResponseEntity<List<VisitDTO>>(visitsDTO, HttpStatus.OK);
	}
	
	@GetMapping("/to-pharmacist")
	public ResponseEntity<List<VisitDTO>> getMyAppointmentsToPharmacists() {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findUserByEmail(token);
		
		List<Visit> appointments = visitService.findByPatientIdEquals(u.getId());
		List<Visit> myAppointments = new ArrayList<Visit>();
		
		for (Visit appointment : appointments) {
			if (appointment.getStatus().equals(Status.RESERVED)) {
				Doctor d = doctorService.findById(appointment.getDoctorId());
				if (d instanceof Pharmacist) {
					myAppointments.add(appointment);
				}
			}
		}
		
		List<VisitDTO> visitsDTO = new ArrayList<>();
		for (Visit visit : myAppointments) {
			VisitDTO visitDTO = new VisitDTO(visit.getId(), patientService.findById(visit.getPatientId()), doctorService.findById(visit.getDoctorId()), visit.getStart(), visit.getFinish());
			visitsDTO.add(visitDTO);
		}
		
		return new ResponseEntity<List<VisitDTO>>(visitsDTO, HttpStatus.OK);
	}
	
	@GetMapping("/cancel-my-reservation/{id}")
	public ResponseEntity<List<VisitDTO>> cancelAppointment(@PathVariable Long id) {
		Visit visit = visitService.findById(id);
		
		visit.setStatus(Status.CANCELED);
		
		visitService.save(visit);
		
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findUserByEmail(token);
		
		List<Visit> appointments = visitService.findByPatientIdEquals(u.getId());
		List<Visit> myAppointments = new ArrayList<Visit>();
		
		for (Visit appointment : appointments) {
			if (appointment.getStatus().equals(Status.RESERVED)) {
				Doctor d = doctorService.findById(appointment.getDoctorId());
				if (d instanceof Dermatologist) {
					myAppointments.add(appointment);
				}
			}
		}
		
		List<VisitDTO> visitsDTO = new ArrayList<>();
		for (Visit visit1 : myAppointments) {
			VisitDTO visitDTO = new VisitDTO(visit1.getId(), patientService.findById(visit1.getPatientId()), doctorService.findById(visit1.getDoctorId()), visit1.getStart(), visit1.getFinish());
			visitsDTO.add(visitDTO);
		}
		
		return new ResponseEntity<List<VisitDTO>>(visitsDTO, HttpStatus.OK);
	}

	@GetMapping("/doctor/{doctorId}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<String> getAppointmentsForDoctor(@PathVariable Long doctorId){
		List<Visit> appointments = visitService.findByDoctorIdEquals(doctorId);
		return new ResponseEntity<String>(g.toJson(appointments), HttpStatus.OK);
	}
	
	@GetMapping("/patient/{patientId}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<String> getAppointmentsForPatient(@PathVariable Long patientId){
		List<Visit> appointments = visitService.findByPatientIdEquals(patientId);
		return new ResponseEntity<String>(g.toJson(appointments), HttpStatus.OK);
	}
	
	@GetMapping("/td")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<List<VisitDTO>> getDoctorsAppointmentsToDo(){
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		Long docId = userService.findUserByEmail(token).getId();
		
		List<Visit> visits = visitService.findByDoctorIdFuture(docId);
		
		List<VisitDTO> visitsDTO = new ArrayList<VisitDTO>();
		
		for (Visit visit : visits) {
			Long visitId = visit.getId();
			Patient visitPatient = patientService.findById(visit.getPatientId());
			Doctor visitDoctor = doctorService.findById(visit.getDoctorId());
			LocalDateTime visitStart = visit.getStart();
			LocalDateTime visitFinish = visit.getFinish();
			VisitDTO newVisitDTO = new VisitDTO(visitId, visitPatient, visitDoctor, visitStart, visitFinish);
			
			if(visit.getStatus() == Status.RESERVED)
				visitsDTO.add(newVisitDTO);
		}
		Collections.sort(visitsDTO);
		return new ResponseEntity<List<VisitDTO>>(visitsDTO,  HttpStatus.OK);
	}
	@GetMapping("/get-user/{visitId}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<PatientDTO> getPatientByVisitId(@PathVariable Long visitId){
		Visit v = visitService.findById(visitId);
		Patient p = patientService.findById(v.getPatientId());
		
		return new ResponseEntity<PatientDTO>(new PatientDTO(p), HttpStatus.OK);
	}
	
	@GetMapping("/get-pharmacy/{visitId}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Long> getPharmacyIdByVisitId(@PathVariable Long visitId){
		Visit v = visitService.findById(visitId);
		Long pharmacyId = v.getPharmacy();
		return new ResponseEntity<Long>(pharmacyId, HttpStatus.OK);
	}
	
	@GetMapping("/get-monthly-visits")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<ResponseObject> getMonthlyVisits() {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin admin = laService.findByEmail(token);
		Pharmacy p = admin.getPharmacy();
		
		List<Visit> retVisits = visitService.findAll();
		Map<Integer, Integer> retMap = new HashMap<Integer, Integer>();

		for (int i = 1; i < 13; i++) {
			int monthNum = i;
			List<Visit> visits = retVisits.stream().filter(v -> v.getStart().getMonthValue() == monthNum && v.getPharmacy() == p.getId()).collect(Collectors.toList());
			retMap.put(i, visits.size());
		}
		
		return new ResponseEntity<ResponseObject>(new ResponseObject(retMap, 200, ""), HttpStatus.OK);
		
	}
	
	@GetMapping("/get-quarter-visits")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<ResponseObject> getQuarterVisits() {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin admin = laService.findByEmail(token);
		Pharmacy p = admin.getPharmacy();
		
		List<Visit> retVisits = visitService.findAll();
		List<Visit> firstQ = retVisits.stream()
				.filter(v -> ((v.getStart().getMonthValue() == 1) || (v.getStart().getMonthValue() == 2) ||
						(v.getStart().getMonthValue() == 3) || (v.getStart().getMonthValue() == 4))
						&& v.getPharmacy() == p.getId())
				.collect(Collectors.toList());
		
		List<Visit> secondQ = retVisits.stream()
				.filter(v -> ((v.getStart().getMonthValue() == 5) || (v.getStart().getMonthValue() == 6) ||
						(v.getStart().getMonthValue() == 7) || (v.getStart().getMonthValue() == 8))
						&& v.getPharmacy() == p.getId())
				.collect(Collectors.toList());
		
		List<Visit> thirdQ = retVisits.stream()
				.filter(v -> ((v.getStart().getMonthValue() == 9) || (v.getStart().getMonthValue() == 10) ||
						(v.getStart().getMonthValue() == 11) || (v.getStart().getMonthValue() == 12))
						&& v.getPharmacy() == p.getId())
				.collect(Collectors.toList());
		
		Map<Integer, Integer> retMap = new HashMap<Integer, Integer>();
		
		retMap.put(1, firstQ.size());
		retMap.put(2, secondQ.size());
		retMap.put(3, thirdQ.size());
		
		return new ResponseEntity<ResponseObject>(new ResponseObject(retMap, 200, ""), HttpStatus.OK);
		
	}
	
	@GetMapping("/get-year-visits/{yearNum}")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<ResponseObject> getYearVisits(@PathVariable int yearNum) {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin admin = laService.findByEmail(token);
		Pharmacy p = admin.getPharmacy();
		
		List<Visit> retVisits = visitService.findAll();
		Map<Integer, Integer> retMap = new HashMap<Integer, Integer>();
		
		for (int i = 0; i < yearNum; i++) {
			int selectedYear = LocalDateTime.now().getYear() - i;
			List<Visit> visits = retVisits.stream().filter(v -> v.getStart().getYear() == selectedYear && v.getPharmacy() == p.getId()).collect(Collectors.toList());
			retMap.put(selectedYear, visits.size());
		}
		
		return new ResponseEntity<ResponseObject>(new ResponseObject(retMap, 200, ""), HttpStatus.OK);
	}
	
	@GetMapping("/valid-check/{visitId}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<String> checkValidVisitId(@PathVariable Long visitId){
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findUserByEmail(token);
		
		Visit v = visitService.findById(visitId);
		
		ResponseEntity<String> f = new ResponseEntity<String>("false", HttpStatus.OK);

		if(v.getDoctorId() != u.getId())
			return f;
		if(v.getStart().isAfter(LocalDateTime.now()) || v.getFinish().isBefore(LocalDateTime.now()))
			return f;
		
		return new ResponseEntity<String>("true", HttpStatus.OK);
	}
	
	
	
	public void notifyPatientViaEmail(String patientsEmail, Long doctorId, LocalDateTime startTime, String patientsName) {
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

		   // -- Set the FROM and TO fields --
		      msg.setFrom(new InternetAddress("labonibato44@gmail.com"));
		      msg.setRecipients(Message.RecipientType.TO, 
		                        InternetAddress.parse(patientsEmail, false));
		      msg.setSubject("Doctors appointment");
		      
			  String NAME = patientsName;
			  
			  Doctor doc = doctorService.findById(doctorId);
			  String DOCTORS_NAME = doc.getName();
			  
			  String formatterS = "HH:mm dd-MM-yyyy";
			  DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatterS);
			  String DATE_TIME = formatter.format(startTime);
			  
		      msg.setText("Dear " + NAME + ",\nYour doctor " + DOCTORS_NAME + " has appointed new treatment at " + DATE_TIME + ".\nSincerely yours,\nLABONI44.");
		      msg.setSentDate(new Date());
		      Transport.send(msg);
		      System.out.println("Message sent.");
		    }catch (MessagingException e){ 
		    	e.printStackTrace();
		    }
	}
	private boolean checkTermTaken(Visit newReservation) {
		List<Visit> patientsAppointments = visitService.findByPatientIdEquals(newReservation.getPatientId());
		
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
		List<Visit> doctorsAppointments = visitService.findByDoctorIdEquals(doctorId);
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
		for (WorkHours wh : whService.findWorkingHoursForDoctorByIdAndPharmacyId(newTerm.getDoctorId(), newTerm.getPharmacy())) {
			if(newTerm.getStart().toLocalTime().isAfter(wh.getStartTime()) && newTerm.getFinish().toLocalTime().isBefore(wh.getFinishTime()))
				return true;
		}			
		return false;
	}
	
}
