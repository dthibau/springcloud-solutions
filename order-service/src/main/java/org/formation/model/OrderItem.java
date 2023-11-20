package org.formation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class OrderItem {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String refProduct;
	
	private float price;
	
	private int quantity;
	
	@ManyToOne
	@JsonIgnore
	private Order order;
	
	
}
