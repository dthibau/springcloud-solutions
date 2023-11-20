package org.formation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Livreur {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	

}
