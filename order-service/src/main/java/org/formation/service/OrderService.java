package org.formation.service;

import org.formation.model.Order;
import org.formation.model.OrderRepository;
import org.formation.service.dependencies.Courriel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	CircuitBreakerFactory cbFactory;
	
	
	public Order processOrder(Order order ) {
		
		_sendCourriel(order);
		
		Order ret = orderRepository.save(order);
		
		_startDelivery(order);
		
		return ret;
	}
	
	private void _sendCourriel(Order order) {
		Courriel c = Courriel.builder().to(order.getClient().getEmail()).text("Féliciations pour votre nouvelle commande").subject("Nouvelle commande").build();
					
		String status = cbFactory.create("notification").run(
				() -> { 
					System.out.println("Appels");
					return restTemplate.postForObject("http://notification-service/sendSimple", c, String.class); 
				},
				throwable -> "fallback");
				
		System.out.println(status);
	}
	
	private void _startDelivery(Order order) {
					
		String status = cbFactory.create("delivery").run(
				() -> { 
					return restTemplate.postForObject("http://delivery-service/api/livraison?noCommande="+order.getId(), order.getId(), String.class); 
				},
				throwable -> "fallback");
				
		System.out.println(status);
	}	
}
