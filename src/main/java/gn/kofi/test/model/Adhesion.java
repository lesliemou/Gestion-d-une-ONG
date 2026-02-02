package gn.kofi.test.model;

import java.time.LocalDate;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Adhesion {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate date_debut;
	private LocalDate date_fin;
	private Double montant;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "statut_adhesion")
	private Statut_adhesion statutAdhesion;
	
	
	@ManyToOne()
	private User membre;


	public Adhesion() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Adhesion(long id, LocalDate date_debut, LocalDate date_fin, Double montant, Statut_adhesion statutAdhesion,
			User membre) {
		super();
		this.id = id;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		this.montant = montant;
		this.statutAdhesion = statutAdhesion;
		this.membre = membre;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public LocalDate getDate_debut() {
		return date_debut;
	}


	public void setDate_debut(LocalDate date_debut) {
		this.date_debut = date_debut;
	}


	public LocalDate getDate_fin() {
		return date_fin;
	}


	public void setDate_fin(LocalDate date_fin) {
		this.date_fin = date_fin;
	}


	public Double getMontant() {
		return montant;
	}


	public void setMontant(Double montant) {
		this.montant = montant;
	}


	public Statut_adhesion getStatutAdhesion() {
		return statutAdhesion;
	}


	public void setStatutAdhesion(Statut_adhesion statutAdhesion) {
		this.statutAdhesion = statutAdhesion;
	}


	public User getMembre() {
		return membre;
	}


	public void setMembre(User membre) {
		this.membre = membre;
	}


	

}
