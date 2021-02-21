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
	private CircuitBreakerFactory cbFactory;
	
	public Order processOrder(Order order ) {
		
		_sendMail(order);
		Order ret = orderRepository.save(order);
		_startDelivery(ret.getId());
		
		return ret;
	}
	
	private void _sendMail(Order order) {
		
		Courriel c = Courriel.builder().
				         to(order.getClient().getEmail()).text("Féliciations pour votre nouvelle commande").subject("Nouvelle commande").build();
		
		cbFactory.create("sendsimple").run(() -> restTemplate.postForObject("http://notification-service/sendSimple", c, String.class), throwable -> { System.out.println("FALLBACK"); return "fallback"; });
		
	}
	
	private void _startDelivery(Long orderId) {
		
		
		cbFactory.create("startDelivery").run(() -> restTemplate.postForObject("http://delivery-service/api/livraison", orderId, String.class), throwable -> { System.out.println("FALLBACK"); return "fallback"; });
		
	}
}
