package backend.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.enums.Status;
import backend.models.Medicine;
import backend.models.MedicineDays;
import backend.models.MedicineReportDTO;
import backend.models.Patient;
import backend.models.PharmacyMedicines;
import backend.models.Report;
import backend.models.Reservation;
import backend.models.Visit;
import backend.services.IMedicineService;
import backend.services.IPharmacyMedicinesService;
import backend.services.IReservationService;
import backend.services.impl.PatientService;
import backend.services.impl.PharmacyService;
import backend.services.impl.ReportService;
import backend.services.impl.VisitService;


@RestController
@RequestMapping(value = "reports")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReportController {

	@Autowired
	private ReportService reportService;
	
	@Autowired
	private IPharmacyMedicinesService pmService;
	
	@Autowired
	private IMedicineService medicineService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private VisitService visitService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private IReservationService reservationService;

	//private static Gson g = new Gson();
	
	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<List<MedicineReportDTO>> saveAppointment(@RequestBody Report newReport){
		
		List<MedicineReportDTO> medDTO = new ArrayList<MedicineReportDTO>();
		
		Visit visitReport = visitService.findById(newReport.getVisitId());
		Long pharmacyId = visitReport.getPharmacy();
		
		for (MedicineDays medDays : newReport.getMedicineDays()) {
			MedicineReportDTO newMedRepDTO = new MedicineReportDTO(medicineService.findById(medDays.getmedicine()));
			
			newMedRepDTO.setAvailable(checkAvailable(pharmacyId, medDays.getmedicine()));
			
			if(!newMedRepDTO.isAvailable()) {
				//send notification to lab admin
				PharmacyMedicines newPm = pmService.findByPharmacyIdAndMedicineIdAndTodaysDate(pharmacyId, newMedRepDTO.getMedicine().getId(), System.currentTimeMillis());
				newPm.incRequests();
				pmService.save(newPm);
			}
			
			Long patientId = visitReport.getPatientId();
			newMedRepDTO.setAllergic(checkAllergic(patientId, medDays.getmedicine()));
			
			medDTO.add(newMedRepDTO);
		}
		if(medDTO.stream().filter(m -> m.isAllergic() || !m.isAvailable()).collect(Collectors.toList()).size() > 0) {
			System.out.println("Failed");
		}
		else {
			System.out.println("Success");
			visitReport.setStatus(Status.FINISHED);
			visitService.save(visitReport);
			reportService.save(newReport);
			updateMedicineQuantity(medDTO, pharmacyId);
			createMedicineReservations(pharmacyId, visitReport.getPatientId(), medDTO);
		}
		return new ResponseEntity<List<MedicineReportDTO>>(medDTO, HttpStatus.OK);
	}
	public boolean checkAvailable(Long pharmacyId, Long medicineId) {
		PharmacyMedicines pm = pmService.findByPharmacyIdAndMedicineIdAndTodaysDate(pharmacyId, medicineId, System.currentTimeMillis());
		
		if(pm == null)
			return false;
		
		if(pm.getQuantity() <= 0)
			return false;
		else {
			return true;
		}
	}
	public boolean checkAllergic(Long patientId, Long medicineId) {
		Patient p = patientService.findById(patientId);
		for (Medicine allergy : p.getAllergies()) {
			if(medicineId == allergy.getId())
				return true;
		}
		return false;
	}
	public void updateMedicineQuantity(List<MedicineReportDTO> allGood, Long pharmacyId) {
		PharmacyMedicines pm;
		for (MedicineReportDTO medRep : allGood) {
			pm = pmService.findByPharmacyIdAndMedicineIdAndTodaysDate(pharmacyId, medRep.getMedicine().getId(), System.currentTimeMillis());
			pm.decQuantity();
			pmService.save(pm);
		}
	}
	public void createMedicineReservations(Long pharmId, Long patId, List<MedicineReportDTO> medicines) {
		Reservation r;
		for (MedicineReportDTO med : medicines) {
			r = new Reservation(patientService.findById(patId),
					pharmacyService.findById(pharmId), 
					med.getMedicine(),
					System.currentTimeMillis(),
					1, 
					pmService.findByPharmacyIdAndMedicineIdAndTodaysDate(pharmId, med.getMedicine().getId(), System.currentTimeMillis())
					.getPrice(),
					Status.FINISHED);
			reservationService.save(r);
		}
	}
}
