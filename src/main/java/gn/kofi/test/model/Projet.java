package gn.kofi.test.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Projet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
   private String nom;
private String description;
private LocalDate date_debut;
private LocalDate  date_fin;
private Double montant;

@Enumerated(EnumType.STRING)
private Statut_projet statPro;

@OneToMany(mappedBy = "projet",cascade = CascadeType.REMOVE)
private List<Tache> tache;

public Projet() {
	super();
	// TODO Auto-generated constructor stub
}

public Projet(long id, String nom, String description, LocalDate  date_debut, LocalDate  date_fin, Double montant,
		Statut_projet statPro, List<Tache> tache) {
	super();
	this.id = id;
	this.nom = nom;
	this.description = description;
	this.date_debut = date_debut;
	this.date_fin = date_fin;
	this.montant = montant;
	this.statPro = statPro;
	this.tache = tache;
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

public LocalDate  getDate_debut() {
	return date_debut;
}

public void setDate_debut(LocalDate  date_debut) {
	this.date_debut = date_debut;
}

public LocalDate  getDate_fin() {
	return date_fin;
}

public void setDate_fin(LocalDate  date_fin) {
	this.date_fin = date_fin;
}

public Double getMontant() {
	return montant;
}

public void setMontant(Double montant) {
	this.montant = montant;
}

public Statut_projet getStatPro() {
	return statPro;
}

public void setStatPro(Statut_projet statPro) {
	this.statPro = statPro;
}

public List<Tache> getTache() {
	return tache;
}

public void setTache(List<Tache> tache) {
	this.tache = tache;
}



}
