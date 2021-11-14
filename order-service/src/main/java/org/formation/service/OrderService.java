package org.formation.service;

import java.time.Instant;

import javax.persistence.EntityNotFoundException;

import org.formation.model.Order;
import org.formation.model.OrderRepository;
import org.formation.model.PaymentRequest;
import org.formation.model.PaymentResponse;
import org.formation.service.dependencies.Courriel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
	RestTemplate restTemplate;

	@Autowired
	private CircuitBreakerFactory cbFactory;

	@Autowired
	KafkaTemplate<Long, PaymentRequest> kafkaOrderTemplate;

	public Order processOrder(Order order) {

		_sendMail(order);
		order.setStatus(ORDER_PENDING);
		Order ret = orderRepository.save(order);
		// _startDelivery(ret.getId());
		PaymentRequest pr = PaymentRequest.builder().orderId(ret.getId()).clientId(ret.getClient().getId())
				.amount(order.getAmount()).build();
		log.info("OrderService sending PaymentRequest " + pr);
		kafkaOrderTemplate.send(ORDER_CHANNEL, pr);

		return ret;
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


	private void _sendMail(Order order) {

		Courriel c = Courriel.builder().to(order.getClient().getEmail())
				.text("Féliciations pour votre nouvelle commande").subject("Nouvelle commande").build();

		cbFactory.create("sendsimple").run(
				() -> restTemplate.postForObject("http://notification-service/sendSimple", c, String.class),
				throwable -> {
					System.out.println("FALLBACK");
					return "fallback";
				});

	}

	private void _startDelivery(Long orderId) {

		cbFactory.create("startDelivery").run(
				() -> restTemplate.postForObject("http://delivery-service/api/livraison", orderId, String.class),
				throwable -> {
					System.out.println("FALLBACK");
					return "fallback";
				});

	}
}
