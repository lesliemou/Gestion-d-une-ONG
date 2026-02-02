package gn.kofi.test.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gn.kofi.test.UserRepository;
import gn.kofi.test.DAO.AdhesionRepository;
import gn.kofi.test.DAO.ProjetRepository;
import gn.kofi.test.model.Adhesion;
import gn.kofi.test.model.Evenement;
import gn.kofi.test.model.Projet;
import gn.kofi.test.model.Role;
import gn.kofi.test.model.Statut_adhesion;
import gn.kofi.test.model.Statut_projet;
import gn.kofi.test.model.User;



@Controller
public class AdhesionController {
	
	@Autowired
	private AdhesionRepository adhesionRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ProjetRepository ProjetRepo;
	
	
	@GetMapping("/admin/adhesion")
	public String afficherListeAdhesion(Model m) {
		List<Adhesion> listeAdhesion= adhesionRepo.findAll();
		List<User> users= userRepo.findAll();
		List<User> listeMembre=userRepo.findByRoles(Role.MEMBRE);
		m.addAttribute("listeMembre", listeMembre);
		m.addAttribute("listeAdhesion", listeAdhesion);
		m.addAttribute("statutAdh", Statut_adhesion.values());
		m.addAttribute("users", users);
		

		//System.out.println(userRepo.findAll());
		return"Admin/admin-adhesion";
	}
	
	@PostMapping("/admin/adhesion/save")
	public String enregistrerAdh(Adhesion adhesion) {
		 adhesion.setDate_debut(LocalDate.now());
		    adhesion.setDate_fin(LocalDate.now().plusDays(1));
		adhesionRepo.save(adhesion);
		return "redirect:/admin/adhesion";
	}
	
	@GetMapping("/admin/adhesion/delete/{id}")
	public String supprimerAdh(@PathVariable(name="id") Long id, Model m) {
		adhesionRepo.deleteById(id);
		return "redirect:/admin/adhesion";
	}
	
	
	
	@GetMapping("/admin/valider-adhesion/{id}")
	public String validerAdhesion(@PathVariable Long id, RedirectAttributes redirectAttributes) {
	    Adhesion adhesion = adhesionRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Adhésion introuvable"));

	    LocalDate dateDebut = LocalDate.now();
	    LocalDate dateFin = dateDebut.plusDays(1); // ou 30 jours ou autre selon ta logique

	    adhesion.setDate_debut(dateDebut);
	    adhesion.setDate_fin(dateFin);
	    adhesion.setStatutAdhesion(Statut_adhesion.active);
	    adhesionRepo.save(adhesion);
	    return "redirect:/admin/adhesion";

	}
	
	 @GetMapping("/admin/edit-adhesion/{id}") // Correction de l'URL
	    public String showEditUserForm(@PathVariable Long id, Model model) {
	    	Adhesion adh = adhesionRepo.findById(id).get();
	        model.addAttribute("adh", adh);
	        List<User> listeMembre=userRepo.findByRoles(Role.MEMBRE);
			model.addAttribute("listeMembre", listeMembre);
			model.addAttribute("statutAdh", Statut_adhesion.values());

	        //model.addAttribute("roles", Role.values());
	        return "Admin/admin-adhesion";
	    }

	
	
	
	 @GetMapping("/membre/projet")
		public String afficherProjet(Model m)
		{
			List<Projet> listeProjet= ProjetRepo.findAll();
			m.addAttribute("listeProjet", listeProjet);
			m.addAttribute("statutpro", Statut_projet.values());
			return"Membres/membre-projet";
			
		}
	
	 @GetMapping("/membre/cotisations")
	    public String afficherCotisations(Model model, Principal principal) {
	        if (principal == null) {
	            throw new RuntimeException("Utilisateur non authentifié");
	        }

	        // Récupérer l'email de l'utilisateur connecté
	        String email = principal.getName();

	        // Trouver le membre à partir de l'email
	        User membre = userRepo.findByEmail(email)
	                .orElseThrow(() -> new RuntimeException("Membre introuvable"));

	        // Récupérer l’historique des cotisations du membre
	        List<Adhesion> historique = adhesionRepo.findByMembre(membre);

	        // Ajouter les données au modèle pour Thymeleaf
	        model.addAttribute("historique", historique);
	        model.addAttribute("nouvelleAdhesion", new Adhesion()); // Pour le formulaire de paiement

	        return "Membres/cotisations"; // Renvoie vers cotisations.html
	    }
	
	
	 @PostMapping("/membre/cotisations/payer")
	 public String payerCotisation(@RequestParam double montant,
	                               Principal principal,
	                               RedirectAttributes redirectAttributes) {
	     if (principal == null) {
	         redirectAttributes.addFlashAttribute("erreur", "Vous devez être connecté pour effectuer un paiement.");
	         return "redirect:/login"; // Redirige vers la page de connexion
	     }

	     // Vérification du montant
	     if (montant <= 0) {
	         redirectAttributes.addFlashAttribute("erreur", "Le montant doit être supérieur à zéro.");
	         return "redirect:/membre/cotisations";
	     }

	     // Récupérer l'email de l'utilisateur connecté
	     String email = principal.getName();
	     User membre = userRepo.findByEmail(email)
	             .orElseThrow(() -> new RuntimeException("Membre introuvable"));

	     // Récupérer toutes les adhésions du membre
	     List<Adhesion> adhesions = adhesionRepo.findByMembre(membre);

	     // Vérifier si l'un des éléments de la liste a un statut actif
	     boolean hasActiveAdhesion = adhesions.stream()
	                                          .anyMatch(adhesion -> adhesion.getStatutAdhesion() == Statut_adhesion.active);

	     if (hasActiveAdhesion) {
	         redirectAttributes.addFlashAttribute("erreur", "Votre adhésion est encore active. Vous ne pouvez pas payer maintenant.");
	         return "redirect:/membre/cotisations"; // Retourne à la page des cotisations
	     }


	     // Enregistrer la nouvelle adhésion
	     Adhesion adhesion = new Adhesion();
	     adhesion.setMembre(membre);
	     adhesion.setMontant(montant);
	     adhesion.setStatutAdhesion(Statut_adhesion.en_attente);
	     
	     adhesionRepo.save(adhesion);

	     redirectAttributes.addFlashAttribute("success", "Paiement réussi ! Votre adhésion est maintenant active.");
	     return "redirect:/membre/cotisations"; // Recharge la page avec l'historique mis à jour
	 }



}
