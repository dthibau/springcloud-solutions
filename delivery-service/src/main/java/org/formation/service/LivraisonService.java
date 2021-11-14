package org.formation.service;

import java.time.Instant;

import org.formation.model.Livraison;
import org.formation.model.PaymentResponse;
import org.formation.model.Status;
import org.formation.model.Trace;
import org.formation.repository.LivraisonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class LivraisonService {

	@Autowired
	LivraisonRepository livraisonRepository;
	
	@KafkaListener(topics = "#{'${app.channel.account}'}", id = "deliveryHandlePaymentResponse")
	public void handlePaymentREquest(PaymentResponse pr) {

		log.info("DeliveryService handle PaymentResponse " + pr);
		if ( pr.getOutcome() ) {
			createLivraison(""+pr.getRequestId());
		} 

	}

	public Livraison createLivraison(String noCommande) {
		Livraison livraison = new Livraison();
		livraison.setNoCommande(noCommande);
		Trace trace = new Trace();
		trace.setNewStatus(Status.CREE);
		trace.setDate(Instant.now());
		livraison.addTrace(trace);
		livraison.setCreationDate(trace.getDate());
		livraison.setStatus(Status.CREE);
		livraison = livraisonRepository.save(livraison);
		
		return livraison;
	}
	
	
}
