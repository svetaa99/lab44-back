package backend.services;

import java.util.List;

import backend.models.OrderMedicines;

public interface IOrderMedicinesService extends IService<OrderMedicines> {
	List<OrderMedicines> findByOrderId(Long orderId);
}
