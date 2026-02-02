package gn.kofi.test.model;

import java.time.LocalDate;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Evenement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
   private String nom;
   private String description;
   private LocalDate date;
   private String lieu;
public Evenement() {
	super();
	// TODO Auto-generated constructor stub
}
public Evenement(long id, String nom, String description, LocalDate date, String lieu) {
	super();
	this.id = id;
	this.nom = nom;
	this.description = description;
	this.date = date;
	this.lieu = lieu;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public LocalDate getDate() {
	return date;
}
public void setDate(LocalDate date) {
	this.date = date;
}
public String getLieu() {
	return lieu;
}
public void setLieu(String lieu) {
	this.lieu = lieu;
}
  

   
}
