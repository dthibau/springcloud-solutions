package org.formation.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Livraison {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String noCommande;
	
	@OneToOne
	private Livreur livreur;
	
	private Status status;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Trace> historique = new ArrayList<>();

	private Instant creationDate;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNoCommande() {
		return noCommande;
	}

	public void setNoCommande(String noCommande) {
		this.noCommande = noCommande;
	}

	public Livreur getLivreur() {
		return livreur;
	}

	public void setLivreur(Livreur livreur) {
		this.livreur = livreur;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Trace> getHistorique() {
		return historique;
	}

	public void setHistorique(List<Trace> historique) {
		this.historique = historique;
	}
	
	public void addTrace(Trace trace) {
		historique.add(trace);
	}

	public Instant getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Instant creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livraison other = (Livraison) obj;
		if (id != other.id)
			return false;
		return true;
	}


}
