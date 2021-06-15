package backend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.PromotionDTO;
import backend.enums.PromotionType;
import backend.models.LabAdmin;
import backend.models.Medicine;
import backend.models.Pharmacy;
import backend.models.Promotion;
import backend.models.ResponseObject;
import backend.services.ILabAdminService;
import backend.services.IPromotionService;

@RestController
@RequestMapping(value = "promotions")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PromotionController {
	
	@Autowired
	private IPromotionService promoService;
	
	@Autowired
	private ILabAdminService laService;
	
	private List<PromotionDTO> createDTOList(List<Promotion> promos) {
		List<PromotionDTO> pDTOs = new ArrayList<PromotionDTO>();
		
		for (Promotion promotion : promos) {
			pDTOs.add(new PromotionDTO(promotion));
		}
		
		return pDTOs;
	}
	
	@GetMapping(value = "/all")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<ResponseObject> findAllPromotions() {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin la = laService.findByEmail(token);
		Pharmacy p = la.getPharmacy();
		
		List<Promotion> promos = promoService.findAllByPharmacyId(p.getId());
		
		if (promos.size() == 0) {
			return new ResponseEntity<ResponseObject>(new ResponseObject(404, "No promotions found"), HttpStatus.NOT_FOUND);
		}
		
		List<PromotionDTO> pDTOs = createDTOList(promos);
		
		return new ResponseEntity<ResponseObject>(new ResponseObject(pDTOs, 200, "Ok"), HttpStatus.OK);
	}
	
	@PostMapping(value = "/create-promotion")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<ResponseObject> createNewPromotion(@RequestBody PromotionDTO obj) {
		String token = SecurityContextHolder.getContext().getAuthentication().getName();
		LabAdmin la = laService.findByEmail(token);
		Pharmacy p = la.getPharmacy();
		
		if (obj.getMedicines().size() == 0) {
			return new ResponseEntity<ResponseObject>(new ResponseObject(400, "Empty medicines list"), HttpStatus.BAD_REQUEST);
		}
		
		if (obj.getDiscount() < 0) {
			return new ResponseEntity<ResponseObject>(new ResponseObject(400, "Discount cannot be negative number"), HttpStatus.BAD_REQUEST);
		}
		
		if (obj.getStartDate() < 0 || obj.getEndDate() < 0 || obj.getStartDate() > obj.getEndDate()) {
			return new ResponseEntity<ResponseObject>(new ResponseObject(400, "Invalid dates."), HttpStatus.BAD_REQUEST);
		}
		
		if (obj.getType() != PromotionType.ACTION && obj.getType() != PromotionType.PROMOTION) {
			return new ResponseEntity<ResponseObject>(new ResponseObject(400, "Invalid promotion type."), HttpStatus.BAD_REQUEST);
		}
		
		for (Medicine m : obj.getMedicines()) {
			Promotion promo = promoService.findPromotionForMedicine(p.getId(), m.getId());
			if (promo != null) {
				return new ResponseEntity<ResponseObject>(new ResponseObject(400, "Medicine is already on sale"), HttpStatus.BAD_REQUEST);
			}
		}
		
		Promotion promotion = new Promotion(p, obj.getMedicines(), obj.getType(), obj.getDiscount(), obj.getText(), obj.getStartDate(), obj.getEndDate());
		promoService.save(promotion);
		PromotionDTO dto = new PromotionDTO(promotion);
		
		return new ResponseEntity<ResponseObject>(new ResponseObject(dto, 201, ""), HttpStatus.CREATED);
	}
	
	@GetMapping("/get-promotion-for-medicine/{pharmacyId}/{medicineId}")
	public ResponseEntity<ResponseObject> getPromotionForMedicine(@PathVariable Long pharmacyId, @PathVariable Long medicineId) {
		Promotion p = promoService.findPromotionForMedicine(pharmacyId, medicineId);
		if (p == null) {
			return new ResponseEntity<ResponseObject>(new ResponseObject(404, "No promotion found."), HttpStatus.OK);
		}
		
		PromotionDTO dto = new PromotionDTO(p);
		return new ResponseEntity<ResponseObject>(new ResponseObject(dto, 200, ""), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete-promotion/{promotionId}")
	public ResponseEntity<ResponseObject> deletePromotion(@PathVariable Long promotionId) {
		Promotion p = promoService.findById(promotionId);
		if (p == null) {
			return new ResponseEntity<ResponseObject>(new ResponseObject(400, "Invalid promotion id"), HttpStatus.BAD_REQUEST);
		}
		
		promoService.delete(p);
		
		return new ResponseEntity<ResponseObject>(HttpStatus.OK);
	}
	
}
