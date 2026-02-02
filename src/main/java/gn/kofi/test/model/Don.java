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
public class Don {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Double montant;
	private LocalDate date_don;
	
	@Enumerated(EnumType.STRING)
	//@Column(name = "statut_adhesion")
	private Statut_don statutDon;
	
	
	@ManyToOne()
	private  User donateur;

	public Don() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Don(Long id, Double montant, LocalDate date_don, Statut_don statutDon, User donateur) {
		super();
		this.id = id;
		this.montant = montant;
		this.date_don = date_don;
		this.statutDon = statutDon;
		this.donateur = donateur;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public LocalDate getDate_don() {
		return date_don;
	}

	public void setDate_don(LocalDate date_don) {
		this.date_don = date_don;
	}

	public User getDonateur() {
		return donateur;
	}

	public void setDonateur(User donateur) {
		this.donateur = donateur;
	}
	
	public Statut_don getStatutDon() {
		return statutDon;
	}

	public void setStatutDon(Statut_don statutDon) {
		this.statutDon = statutDon;
	}

	

}
