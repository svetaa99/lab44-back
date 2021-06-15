package backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import backend.enums.Status;
import backend.models.PharmacyMedicines;
import backend.models.Reservation;
import backend.repositories.ReservationRepository;
import backend.services.IPharmacyMedicinesService;
import backend.services.IReservationService;

@Service
public class ReservationService implements IReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private IPharmacyMedicinesService pmService;
	
	@Override
	public List<Reservation> findAll() {
		return reservationRepository.findAll();
	}

	@Override
	public Reservation findById(Long id) {
		return reservationRepository.findById(id).orElse(null);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public Reservation save(Reservation obj) {
		return reservationRepository.save(obj);
	}

	@Override
	public void delete(Reservation obj) {
		reservationRepository.delete(obj);
	}
	
	@Override
	public List<Reservation> findMy(Long patientId) {
		return reservationRepository.findAllByPatientId(patientId);
	}
	
	@Override
	public List<Reservation> findReserved() {
		return reservationRepository.findAllByStatus(Status.RESERVED);
	}

	@Override
	public List<Reservation> findByPatientAndMedicineReserved(Long patientId, Long medicineId) {
		return reservationRepository.findAllByPatientIdAndMedicineIdAndStatus(patientId, medicineId, Status.FINISHED);
	}

	@Override
	public List<Reservation> findByPatientAndPharmacyReserved(Long pharmacyId, Long patientId) {
		return reservationRepository.findAllByPatientIdAndPharmacyIdAndStatus(patientId, pharmacyId, Status.FINISHED);
	}

	@Override
	public List<Reservation> findByPharmacyAndStatus(Long pharmacyId, Status status) {
		return reservationRepository.findByPharmacyIdAndStatus(pharmacyId, status);
	}
	
	@Transactional
	@Override
	public Reservation createReservation(Reservation reservation) {
		reservation.setStatus(Status.RESERVED);
		reservation = save(reservation);
		
		PharmacyMedicines pm = pmService.updateAfterReservation(reservation, reservation.getQuantity());
		if (pm == null) {
			return null;
		}
		
		return reservation;
	}
	
	@Transactional
	@Override
	public Reservation cancelReservationPatient(Long id) {
		Reservation res = findById(id);
		
		pmService.updateAfterReservationCancel(res);
		
		delete(res);
		return res;
	}
	
}
