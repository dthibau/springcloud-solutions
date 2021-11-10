package org.formation.notification;

import org.springframework.stereotype.Service;

@Service
public class NotificationClientFallback implements NotificationClient {

	@Override
	public String sendSimple(Courriel email) {
		System.out.println("FALLING BACK");
	     return "OK";
	}

}
