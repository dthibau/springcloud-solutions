package org.formation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="notification-service", fallback=NotificationClientFallback.class)
public interface NotificationClient {

	@RequestMapping(method = RequestMethod.POST, value = "/sendSimple", consumes = "application/json")
    String sendSimple(Courriel email);

	

}