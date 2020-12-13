package org.formation.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Role {
	
	public static String USER="ROLE_USER";
	public static String CUSTOMER="ROLE_CUSTOMER";
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
}
