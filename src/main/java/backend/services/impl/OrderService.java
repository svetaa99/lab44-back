package backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.models.Order;
import backend.repositories.OrderRepository;
import backend.services.IOrderService;

@Service
public class OrderService implements IOrderService {
	
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	@Override
	public Order findById(Long id) {
		return orderRepository.findById(id).orElseGet(null);
	}

	@Transactional
	@Override
	public Order save(Order obj) {
		orderRepository.save(obj);
		return obj;
	}

	@Override
	public void delete(Order obj) {
		orderRepository.delete(obj);
	}

	@Override
	public List<Order> findAllFromPharmacyId(Long pharmacyId) {
		return orderRepository.findByPharmacyIdEquals(pharmacyId);
	}

}
