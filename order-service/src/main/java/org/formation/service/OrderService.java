package org.formation.service;

import org.formation.model.Order;
import org.formation.model.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	public Order processOrder(Order order ) {
		
		return orderRepository.save(order);
	}
	
}
