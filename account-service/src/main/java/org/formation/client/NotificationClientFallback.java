package org.formation.client;

import org.springframework.stereotype.Service;

@Service
public class NotificationClientFallback implements NotificationClient {

	public String sendSimple(Courriel email) {
		System.out.println("FALLING BACK");
		return "OK";
	}

}
