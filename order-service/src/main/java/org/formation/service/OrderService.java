package org.formation.service;

import org.formation.model.Order;
import org.formation.model.OrderRepository;
import org.formation.service.dependencies.Courriel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.Resource;
import lombok.extern.java.Log;

@Service
@Log
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CircuitBreakerFactory<?, ?> cbFactory;

	@Resource
	RestTemplate notificationClient;
	
	public Order processOrder(Order order ) {
		
		order = orderRepository.save(order);
		
		Courriel c = Courriel.builder().to(order.getClient().getEmail())
									.subject("Merci pour votre commande")
									.text("Voici les conditions de retrait de la commande").build();
		
		log.info(_sendMail(c));
		return order;
	}
	

	private String _sendMail(Courriel c) {
		
		return cbFactory.create("sendMail").run(
				() -> notificationClient.postForObject("/sendSimple", c, String.class),
				throwable -> "FALLBACK : " + throwable);
	}
	
}
