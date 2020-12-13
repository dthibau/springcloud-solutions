package org.formation.service;

import org.formation.model.Order;
import org.formation.model.OrderRepository;
import org.formation.service.dependencies.Courriel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	public Order processOrder(Order order ) {
		
		_sendMail(order);
		return orderRepository.save(order);
	}
	
	private void _sendMail(Order order) {
		
		Courriel c = Courriel.builder().
				         to(order.getClient().getEmail()).text("Féliciations pour votre nouvelle commande").subject("Nouvelle commande").build();
		
		restTemplate.postForObject("http://notification-service/sendSimple", c, String.class); 
	}
}
