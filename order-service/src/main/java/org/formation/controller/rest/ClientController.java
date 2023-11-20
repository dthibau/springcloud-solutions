package org.formation.controller.rest;

import java.util.List;

import org.formation.model.Client;
import org.formation.model.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

	
	@Autowired
	ClientRepository clientRepository;
	
	@GetMapping
	List<Client> findAll() {
		return clientRepository.findAll();
	}


}
