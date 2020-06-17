package org.formation.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Position {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

	private long id;
	private double latitude;
	private double longitude;
	private long timestamp;
	private long noCommande;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Position id(long id) {
		this.id = id;
		return this;
	}
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public Position latitude(double latitude) {
		this.latitude = latitude;
		return this;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Position longitude(double longitude) {
		this.longitude = longitude;
		return this;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public Position timestamp(long timestamp) {
		this.timestamp = timestamp;
		return this;
	}
	
	public long getNoCommande() {
		return noCommande;
	}
	public void setNoCommande(long noCommande) {
		this.noCommande = noCommande;
	}
	public Position noCommande(long noCommande) {
		this.noCommande = noCommande;
		return this;
	}
	@Override
	public String toString() {
		return "Position [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", timestamp=" + timestamp
				+ ", noCommande=" + noCommande + "]";
	}
	
	
}
