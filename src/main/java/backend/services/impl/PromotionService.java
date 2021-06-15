package backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.models.Promotion;
import backend.repositories.PromotionRepository;
import backend.services.IPromotionService;

@Service
public class PromotionService implements IPromotionService {

	@Autowired
	private PromotionRepository promoRepository;
	
	@Override
	public List<Promotion> findAll() {
		return promoRepository.findAll();
	}

	@Override
	public Promotion findById(Long id) {
		return promoRepository.findById(id).orElse(null);
	}

	@Override
	public Promotion save(Promotion obj) {
		return promoRepository.save(obj);
	}

	@Override
	public void delete(Promotion obj) {
		promoRepository.delete(obj);

	}

	@Override
	public List<Promotion> findAllByPharmacyId(Long pharmacyId) {
		return promoRepository.findByPharmacyIdEquals(pharmacyId);
	}

	@Override
	public Promotion findPromotionForMedicine(Long pharmacyId, Long medicineId) {
		return promoRepository.findByPharmacyIdEqualsAndMedicinesIdContains(pharmacyId, medicineId);
	}

}
