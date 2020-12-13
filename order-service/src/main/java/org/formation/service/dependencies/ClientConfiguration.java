package org.formation.service.dependencies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfiguration {
	
	@Autowired
	RestTemplateBuilder builder;

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return builder.build();
	}
}
