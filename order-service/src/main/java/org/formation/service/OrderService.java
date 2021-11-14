package org.formation.service;


import org.formation.model.Order;
import org.formation.model.OrderRepository;
import org.formation.model.PaymentRequest;
import org.formation.model.PaymentResponse;
import org.formation.service.dependencies.Courriel;
import org.formation.service.dependencies.Livraison;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.Resource;
import lombok.extern.java.Log;

@Service
@Log
public class OrderService {

	public static String ORDER_PENDING = "PENDING";
	public static String ORDER_CANCELED = "CANCELED";
	public static String ORDER_CONFIRMED = "CONFIRMED";


	@Value("${app.channel.order}")
	String ORDER_CHANNEL;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CircuitBreakerFactory<?, ?> cbFactory;

	@Resource
	RestTemplate notificationClient;
	
	@Resource
	RestTemplate deliveryClient;
	
	@Autowired
	KafkaTemplate<Long, PaymentRequest> kafkaOrderTemplate;
	
	public Order processOrder(Order order ) {
		
		order = orderRepository.save(order);
		
		Courriel c = Courriel.builder().to(order.getClient().getEmail())
									.subject("Merci pour votre commande")
									.text("Voici les conditions de retrait de la commande").build();
		
//		log.info(_sendMail(c));
		log.info("" + _startDelivery(order.getId()));
		
		PaymentRequest pr = PaymentRequest.builder().orderId(order.getId()).clientId(order.getClient().getId())
				.amount(order.getAmount()).build();
		log.info("OrderService sending PaymentRequest " + pr);
		kafkaOrderTemplate.send(ORDER_CHANNEL, pr);
		
		return order;
	}
	

	@KafkaListener(topics = "#{'${app.channel.account}'}", id = "OrderhandlePaymentResponse")
	public void handlePaymentREquest(PaymentResponse pr) {
		
		log.info("OrderService handlePaymentResponse " + pr);

		Order order = orderRepository.findById(pr.getRequestId()).orElse(new Order() );

		if ( pr.getOutcome() ) {
			order.setStatus(ORDER_CONFIRMED);
		} else {
			order.setStatus(ORDER_CANCELED);
		}
		orderRepository.save(order);

	}


	private String _sendMail(Courriel c) {
		
		return cbFactory.create("sendMail").run(
				() -> notificationClient.postForObject("/sendSimple", c, String.class),
				throwable -> "FALLBACK : " + throwable);
	}
	
	private Livraison _startDelivery(Long orderId) {	
				
		return cbFactory.create("startDelivery").run(() -> deliveryClient.postForObject("/api/livraison?noCommande="+orderId, null, Livraison.class), throwable -> { System.out.println("FALLBACK"); return null; });
				
	}


}
