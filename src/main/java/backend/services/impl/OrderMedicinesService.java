package backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.models.OrderMedicines;
import backend.repositories.OrderMedicinesRepository;
import backend.services.IOrderMedicinesService;

@Service
public class OrderMedicinesService implements IOrderMedicinesService {

	@Autowired
	private OrderMedicinesRepository omRepository;
	
	@Override
	public List<OrderMedicines> findAll() {
		return omRepository.findAll();
	}

	@Override
	public OrderMedicines findById(Long id) {
		return omRepository.findById(id).orElseGet(null);
	}

	@Override
	public OrderMedicines save(OrderMedicines obj) {
		omRepository.save(obj);
		return obj;
	}

	@Override
	public void delete(OrderMedicines obj) {
		omRepository.delete(obj);
	}

	@Override
	public List<OrderMedicines> findByOrderId(Long orderId) {
		return omRepository.findByOrderIdEquals(orderId);
	}

}
