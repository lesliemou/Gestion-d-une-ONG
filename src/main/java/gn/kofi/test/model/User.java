package gn.kofi.test.model;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
   private String nom;
   private String prenom;
  private String email;
 private  String password;
 private  String telephone;
 private  String adresse;
 
 @Enumerated(EnumType.STRING)
 private Role roles;
 private boolean enabled=true;
 
 @OneToMany(mappedBy = "membre")
 private List<Adhesion> adhesion;
 
 @OneToMany(mappedBy = "benevole")
 private List<Tache> tache;
 
 @OneToMany(mappedBy = "donateur")
 private List<Don> don ;
 
 
 
public User() {
	super();
	// TODO Auto-generated constructor stub
}

public User(Long id, String nom, String prenom, String email, String password, String telephone, String adresse,
		Role roles, boolean enabled, List<Adhesion> adhesion, List<Tache> tache, List<Don> don) {
	super();
	this.id = id;
	this.nom = nom;
	this.prenom = prenom;
	this.email = email;
	this.password = password;
	this.telephone = telephone;
	this.adresse = adresse;
	this.roles = roles;
	this.enabled = enabled;
	this.adhesion = adhesion;
	this.tache = tache;
	this.don = don;
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getNom() {
	return nom;
}

public void setNom(String nom) {
	this.nom = nom;
}

public String getPrenom() {
	return prenom;
}

public void setPrenom(String prenom) {
	this.prenom = prenom;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getTelephone() {
	return telephone;
}

public void setTelephone(String telephone) {
	this.telephone = telephone;
}

public String getAdresse() {
	return adresse;
}

public void setAdresse(String adresse) {
	this.adresse = adresse;
}

public Role getRoles() {
	return roles;
}

public void setRoles(Role roles) {
	this.roles = roles;
}

public boolean isEnabled() {
	return enabled;
}

public void setEnabled(boolean enabled) {
	this.enabled = enabled;
}

public List<Adhesion> getAdhesion() {
	return adhesion;
}

public void setAdhesion(List<Adhesion> adhesion) {
	this.adhesion = adhesion;
}

public List<Tache> getTache() {
	return tache;
}

public void setTache(List<Tache> tache) {
	this.tache = tache;
}

public List<Don> getDon() {
	return don;
}

public void setDon(List<Don> don) {
	this.don = don;
}

 
 

}
