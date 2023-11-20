package org.formation.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "t_order")
@Data
public class Order {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private Instant date;
	
	private float discount;
	
	@ManyToOne
	private Client client;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	List<OrderItem> orderItems = new ArrayList<>();
	
}
