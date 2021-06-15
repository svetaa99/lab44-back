package backend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.MedicineQuantityDTO;
import backend.dto.OrderDTO;
import backend.dto.SupplierOfferDTO;
import backend.models.DemandMedicine;
import backend.models.LabAdmin;
import backend.models.Medicine;
import backend.models.Order;
import backend.models.OrderMedicines;
import backend.models.Pharmacy;
import backend.models.PharmacyMedicines;
import backend.models.SupplierOffer;
import backend.services.IDemandMedicineService;
import backend.services.ILabAdminService;
import backend.services.IMedicineService;
import backend.services.IOrderMedicinesService;
import backend.services.IOrderService;
import backend.services.IPharmacyMedicinesService;
import backend.services.IPharmacyService;
import backend.services.ISupplierOfferService;

@RestController
@RequestMapping(value = "orders")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {
	
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private IMedicineService medicineService;
	
	@Autowired
	private IOrderMedicinesService omService;
	
	@Autowired
	private IPharmacyMedicinesService pmService;
	
	@Autowired
	private IPharmacyService pharmacyService;
	
	@Autowired
	private ISupplierOfferService soService;
	
	@Autowired
	private ILabAdminService laService;
	
	@Autowired
	private IDemandMedicineService dmService;
	
	@PostMapping(value = "/create-order", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
		if (orderDTO.getOrderMedicines().size() == 0) {
			return new ResponseEntity<OrderDTO>(HttpStatus.BAD_REQUEST);
		}
		
		if (orderDTO.getDeadline() < 0) {
			return new ResponseEntity<OrderDTO>(HttpStatus.BAD_REQUEST);
		}
		
		Pharmacy pharmacy = orderDTO.getPharmacy();
		if (!pharmacyService.findAll().contains(pharmacy)) {
			return new ResponseEntity<OrderDTO>(HttpStatus.NOT_FOUND);
		}
		
		Order o = new Order();
		o.setDeadline(orderDTO.getDeadline());
		o.setPharmacy(pharmacy);
		orderService.save(o);
		
		List<MedicineQuantityDTO> medicines = orderDTO.getOrderMedicines();
		
		for (MedicineQuantityDTO mq : medicines) {
			Medicine m = mq.getMedicine();
			OrderMedicines om = new OrderMedicines();
			om.setMedicine(m);
			om.setOrder(o);
			om.setQuantity(mq.getQuantity());
			
			omService.save(om);
		}
		
		Long pharmacyId = pharmacy.getId();
		
		List<Medicine> meds = pmService.findAllMedicinesInPharmacy(pharmacyId);
		
		for (MedicineQuantityDTO mq : medicines) {
			Medicine m = mq.getMedicine();
			if (!meds.contains(m)) {
				PharmacyMedicines pm = new PharmacyMedicines();
				pm.setMedicine(m);
				
				Pharmacy p = pharmacyService.findById(pharmacyId);
				pm.setPharmacy(p);
				
				pm.setPrice(100);
				pm.setQuantity(mq.getQuantity());
				pmService.save(pm);
			} else {
				PharmacyMedicines pm = pmService.findPharmacyMedicinesByIds(pharmacyId, m.getId());
				int quantity = pm.getQuantity();
				int newQuantity = quantity + mq.getQuantity();
				pm.setQuantity(newQuantity);
				pmService.save(pm);
			}
		}
		
		return new ResponseEntity<OrderDTO>(orderDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/get-all-orders/{id}")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<List<OrderDTO>> viewAllOrders(@PathVariable("id") Long id) {
		Pharmacy p = pharmacyService.findById(id);
		
		List<Order> orders = orderService.findAllFromPharmacyId(id);
		List<OrderDTO> oDTOs = new ArrayList<OrderDTO>();
		for (Order order : orders) {
			OrderDTO oDTO = new OrderDTO();
			oDTO.setId(order.getId());
			oDTO.setOrderMedicines(oDTO.createMQList(order));
			oDTO.setDeadline(order.getDeadline());
			oDTO.setPharmacy(p);
			oDTOs.add(oDTO);
		}
		
		return new ResponseEntity<List<OrderDTO>>(oDTOs, HttpStatus.OK);
	}
	
	@GetMapping(value = "/list-offers/{pharmacyId}/{orderId}")
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<List<SupplierOfferDTO>> listAllOffers(@PathVariable("pharmacyId") Long pharmacyId, @PathVariable("orderId") Long orderId) {
		List<Order> orders = orderService.findAllFromPharmacyId(pharmacyId);
		if (orders.size() == 0) {
			return new ResponseEntity<List<SupplierOfferDTO>>(new ArrayList<SupplierOfferDTO>(), HttpStatus.OK);
		}
		
		List<SupplierOffer> sos = soService.findAllByOrderId(orderId);
		List<SupplierOfferDTO> soDTOlist = new ArrayList<SupplierOfferDTO>();
		
		for (SupplierOffer so : sos) {
			soDTOlist.add(new SupplierOfferDTO(so));
		}
		
		return new ResponseEntity<List<SupplierOfferDTO>>(soDTOlist, HttpStatus.OK);
	}
	
	@PostMapping(value = "/accept-offer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('LAB_ADMIN')")
	public ResponseEntity<SupplierOfferDTO> acceptOffer(@RequestBody SupplierOfferDTO soDTO) {
		List<OrderMedicines> omList = omService.findByOrderId(soDTO.getOrderId());
		
		Long pharmacyId = (long) 2;
		
		List<Medicine> meds = pmService.findAllMedicinesInPharmacy(pharmacyId);
		
		for (OrderMedicines om : omList) {
			Medicine m = om.getMedicine();
			if (!meds.contains(m)) {
				PharmacyMedicines pm = new PharmacyMedicines();
				pm.setMedicine(m);
				
				Pharmacy p = pharmacyService.findById(pharmacyId);
				pm.setPharmacy(p);
				
				pm.setPrice(100);
				pm.setQuantity(om.getQuantity());
				pmService.save(pm);
			} else {
				PharmacyMedicines pm = pmService.findPharmacyMedicinesByIds(pharmacyId, m.getId());
				int quantity = pm.getQuantity();
				int newQuantity = quantity + om.getQuantity();
				pm.setQuantity(newQuantity);
				pmService.save(pm);
			}
		}
		
		List<SupplierOffer> soList = soService.findAllByOrderId(soDTO.getOrderId());
		for (SupplierOffer so : soList) {
			soService.delete(so);
		}
		
		orderService.delete(orderService.findById(soDTO.getOrderId()));
		
		return new ResponseEntity<SupplierOfferDTO>(soDTO, HttpStatus.OK);
	}

}
