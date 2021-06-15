package backend.controllers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.enums.VacationStatus;
import backend.enums.VacationType;
import backend.models.Doctor;
import backend.models.LabAdmin;
import backend.models.Pharmacy;
import backend.models.User;
import backend.models.Vacation;
import backend.services.ILabAdminService;
import backend.services.impl.UserService;
import backend.services.impl.VacationService;

@RestController
@RequestMapping(value = "vacation")
public class VacationController {

	@Autowired
	private VacationService vacationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ILabAdminService laService;
	
	@GetMapping("/all")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<List<Vacation>> getAllVacations() {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin la = laService.findByEmail(token);
		Pharmacy p = la.getPharmacy();
		
		List<Vacation> retList = new ArrayList<Vacation>();
		
		for (Vacation v : vacationService.findAll()) {
			if (p.getDermatologists().contains(v.getDoctor()) || p.getPharmacists().contains(v.getDoctor())) {
				retList.add(v);
			}
		}
		
		return new ResponseEntity<List<Vacation>>(retList, HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST', 'LAB_ADMIN')")
	public ResponseEntity<Vacation> saveVacationRequest(@RequestBody Vacation vacation){
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findUserByEmail(token);
		
		vacation.setDoctor((Doctor) u);
		vacation.setStatus(VacationStatus.ON_HOLD);
		System.out.println("TYPE : " + vacation.getType());
		
		Vacation saved = vacationService.save(vacation);
		
		return new ResponseEntity<Vacation>(saved, HttpStatus.OK);
	}
	
	@GetMapping("/current")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<String> getCurrent(){
		//checks if employee has unresolved vacation requests VacationStatus = ON_HOLD
		//returns YES or NO
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findUserByEmail(token);
		Long doctorId = u.getId();
		
		List<Vacation> list = vacationService.findByDoctorIdAndStatus(doctorId, VacationStatus.ON_HOLD);
		
		if(list.size() > 0)
			return new ResponseEntity<String>("YES", HttpStatus.OK);
		
		return new ResponseEntity<String>("NO", HttpStatus.OK);
	}
	
	@PutMapping("/update-vacation/{id}/{status}/{text}")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<String> updateVacation(@PathVariable("id") Long vacationId, @PathVariable("status") int status, @PathVariable("text") String text) {
		Vacation v = vacationService.findById(vacationId);
		if (v == null) {
			return new ResponseEntity<String>("Not found vacation with this id.", HttpStatus.NOT_FOUND);
		}
		
		if (status > 2 || status < 0) {
			return new ResponseEntity<String>("Invalid status submited", HttpStatus.BAD_REQUEST);
		}
		
		switch(status) {
		case 0:
			v.setStatus(VacationStatus.ON_HOLD);
			break;
		case 1:
			v.setStatus(VacationStatus.APPROVED);
			break;
		case 2:
			v.setStatus(VacationStatus.DECLINED);
			break;
		}
		
		vacationService.save(v);
		sendEmailToDoctor(v, text);
		return new ResponseEntity<String>("Done", HttpStatus.OK);
	}
	
	private void sendEmailToDoctor(Vacation v, String text) {
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
	      String doctorsEmail = v.getDoctor().getEmail();
	   // -- Set the FROM and TO fields --
	      msg.setFrom(new InternetAddress("labonibato44@gmail.com"));
	      msg.setRecipients(Message.RecipientType.TO, 
	                        InternetAddress.parse(doctorsEmail, false));
	      msg.setSubject("Vacation information");
	      
		  String NAME = v.getDoctor().getName();
		  
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		  String START_DATE= v.getStart().format(formatter);
		  String FINISH_DATE = v.getFinish().format(formatter);
		  
		  VacationType VACATION_TYPE = v.getType();
		  VacationStatus VACATION_STATUS = v.getStatus();
		  String declineText = ""; 
		  
		  if (VACATION_STATUS == VacationStatus.DECLINED) {
			  declineText += "\nReason: " + text;
		  }
		  
	      msg.setText("Dear " + NAME + ",\nYour vacation request\n\nType: " + VACATION_TYPE.toString() + "\nStart date: " + START_DATE + 
	    		  "\nFinish date: " + FINISH_DATE + "\n\nHas been " + VACATION_STATUS.toString() + declineText + "\n\nSincerely yours,\nLABONI44");
	      msg.setSentDate(new Date());
	      Transport.send(msg);
	    }catch (MessagingException e){ 
	    	e.printStackTrace();
	    }
	}
}
