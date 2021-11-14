package org.formation.service;

import java.time.Instant;

import org.formation.model.PaymentRequest;
import org.formation.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class AccountService {

	@Value("${app.channel.account}")
	String ACCOUNT_CHANNEL;

	@Autowired
	KafkaTemplate<Long, PaymentResponse> kafkaOrderTemplate;

	@KafkaListener(topics = "#{'${app.channel.order}'}", id = "accountHandlePaymentRequest")
	public void handleCreateOrder(PaymentRequest pr) {

		PaymentResponse response = PaymentResponse.builder().instant(Instant.now()).requestId(pr.getOrderId())
				.outcome(false).build();

		log.info("Sendind paymentResponse " + response);
		kafkaOrderTemplate.send(ACCOUNT_CHANNEL, response);

	}
}
