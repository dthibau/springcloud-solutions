package org.formation.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="notification-service", fallback=NotificationClientFallback.class)
public interface NotificationClient {

	@PostMapping( value = "/sendSimple", consumes = "application/json")
	String sendSimple(Courriel email);
}
