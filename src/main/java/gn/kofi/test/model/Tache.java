package gn.kofi.test.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Tache {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private LocalDate dateEchance;
	
	@Enumerated(EnumType.STRING)
	private Statut_tache statutTache;
	
	
	@ManyToOne()
	private User benevole;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Projet projet;
	
	public Tache() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Tache(Long id, String description, LocalDate dateEchance, Statut_tache statutTache, User benevole,
			Projet projet) {
		super();
		this.id = id;
		this.description = description;
		this.dateEchance = dateEchance;
		this.statutTache = statutTache;
		this.benevole = benevole;
		this.projet = projet;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Statut_tache getStatutTache() {
		return statutTache;
	}


	public void setStatutTache(Statut_tache statutTache) {
		this.statutTache = statutTache;
	}


	public User getBenevole() {
		return benevole;
	}
	public void setBenevole(User benevole) {
		this.benevole = benevole;
	}
	public Projet getProjet() {
		return projet;
	}
	public void setProjet(Projet projet) {
		this.projet = projet;
	}
	public LocalDate getDateEchance() {
		return dateEchance;
	}
	public void setDateEchance(LocalDate dateEchance) {
		this.dateEchance = dateEchance;
	}
	
	
	
	

}
