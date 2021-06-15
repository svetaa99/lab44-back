package backend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.DemandMedicineDTO;
import backend.models.DemandMedicine;
import backend.models.LabAdmin;
import backend.services.IDemandMedicineService;
import backend.services.ILabAdminService;

@RestController
@RequestMapping(value = "demandmedicines")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DemandMedicineController {
	
	@Autowired
	private IDemandMedicineService dmService;
	
	@Autowired
	private ILabAdminService laService;
	
	private List<DemandMedicineDTO> createDTOList(List<DemandMedicine> dms) {
		List<DemandMedicineDTO> dmDTOs = new ArrayList<DemandMedicineDTO>();
		
		for (DemandMedicine dm : dms) {
			dmDTOs.add(new DemandMedicineDTO(dm));
		}
		
		return dmDTOs;
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<List<DemandMedicineDTO>> getAllDemandMedicines() {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin la = laService.findByEmail(token);
		
		List<DemandMedicine> dms = dmService.findAllByPharmacyId(la.getPharmacy().getId());
		List<DemandMedicineDTO> dmDTOs = createDTOList(dms);
		
		return new ResponseEntity<List<DemandMedicineDTO>>(dmDTOs, HttpStatus.OK);
	}

}
