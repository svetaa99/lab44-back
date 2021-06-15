package backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.models.Penalty;
import backend.models.User;
import backend.services.impl.PenaltyService;
import backend.services.impl.UserService;

@RestController
@RequestMapping(value = "penalty")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PenaltyController {

	@Autowired
	private PenaltyService penaltyService;
	
	@Autowired
	private UserService userService;
	
	@Scheduled(cron = "@monthly")
	public void restartPenalties() {
		List<Penalty> penalties = penaltyService.findAll();
		for (Penalty penalty : penalties) {
			penaltyService.delete(penalty);
		}
	}
	
	@GetMapping("/my")
	public long count() {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		User u = userService.findUserByEmail(token);
		return penaltyService.countPenaltiesByPatientId(u.getId());
	}
	
}
