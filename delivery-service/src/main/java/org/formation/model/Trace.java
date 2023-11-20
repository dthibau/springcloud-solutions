package org.formation.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Trace {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	Status oldStatus,newStatus;
	
	private Instant date;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Status getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(Status oldStatus) {
		this.oldStatus = oldStatus;
	}

	public Status getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(Status newStatus) {
		this.newStatus = newStatus;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}


}
