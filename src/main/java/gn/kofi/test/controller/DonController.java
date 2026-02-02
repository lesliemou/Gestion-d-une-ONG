package gn.kofi.test.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gn.kofi.test.UserRepository;
import gn.kofi.test.DAO.DonRepository;
import gn.kofi.test.DAO.EvenementRepository;
import gn.kofi.test.DAO.ProjetRepository;
import gn.kofi.test.model.Adhesion;
import gn.kofi.test.model.Don;
import gn.kofi.test.model.Evenement;
import gn.kofi.test.model.Projet;
import gn.kofi.test.model.Role;
import gn.kofi.test.model.Statut_adhesion;
import gn.kofi.test.model.Statut_don;
import gn.kofi.test.model.Statut_projet;
import gn.kofi.test.model.User;

@Controller
public class DonController {
	
	@Autowired
	private DonRepository donRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ProjetRepository ProjetRepo;
	
	@Autowired
	public EvenementRepository eventRepo;
	

	@GetMapping("/admin/don")
	public String afficherListeDon(Model m) {
		List<Don> listeDon= donRepo.findAll();
		List<User> Users= userRepo.findAll();
		List<User> listeDonateur=userRepo.findByRoles(Role.DONATEUR);
		m.addAttribute("listeDonateur", listeDonateur);

		m.addAttribute("listeDon", listeDon);
		m.addAttribute("Users", Users);
		m.addAttribute("statuDon", Statut_don.values());
		return"Admin/admin-don";
	}
	
	@PostMapping("/admin/don/save")
	public String enregistrerEvent(Don don) {
		don.setDate_don(LocalDate.now());
		donRepo.save(don);
		return "redirect:/admin/don";
	}
	
	
	@GetMapping("admin/don/delete/{id}")
	public String supprimerDon(@PathVariable(name="id") Long id, Model m) {
		donRepo.deleteById(id);
		return "redirect:/admin/don";
	}
	
	
	@GetMapping("/admin/valider-don/{id}")
	public String validerAdhesion(@PathVariable Long id, RedirectAttributes redirectAttributes) {
	   Don don = donRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Don introuvable"));

	    //LocalDate date = LocalDate.now();

	    //don.setDonateur(donateur);
        don.setDate_don(LocalDate.now());
        don.setStatutDon(Statut_don.validé);
        donRepo.save(don);
	    return "redirect:/admin/don";

	}
	
	 @GetMapping("/admin/edit-don/{id}") // Correction de l'URL
	    public String showEditUserForm(@PathVariable Long id, Model model) {
	    	Don don = donRepo.findById(id).get();
	        model.addAttribute("don", don);
	        List<User> listeDonateur=userRepo.findByRoles(Role.BENEVOLE);
			model.addAttribute("listeDonateur", listeDonateur);
			model.addAttribute("statuDon", Statut_don.values());

	        //model.addAttribute("roles", Role.values());
	        return "Admin/admin-don";
	    }
	
	
	
	@GetMapping("/donateur/projet")
	public String afficherProjet(Model m)
	{
		List<Projet> listeProjet= ProjetRepo.findAll();
		m.addAttribute("listeProjet", listeProjet);
		m.addAttribute("statutpro", Statut_projet.values());
		return"Donateur/donateur-projet";
		
	}
	
	@GetMapping("/donateur/event")
	public String afficherListeEvent(Model m) {
		List<Evenement> listeEvent= eventRepo.findAll();
		m.addAttribute("listeEvent", listeEvent);
		
		return"Donateur/donateur-event";
	}
	
	
	@GetMapping("/donateur/dons")
    public String afficherDons(Model model, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("Utilisateur non authentifié");
        }

        // Récupérer l'email du donateur connecté
        String email = principal.getName();

        // Trouver le donateur dans la base de données
        User donateur = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Donateur introuvable"));

        // Récupérer l’historique des dons du donateur
        List<Don> historiqueDons = donRepo.findByDonateur(donateur);

        // Ajouter les données au modèle pour Thymeleaf
        model.addAttribute("historiqueDons", historiqueDons);
        model.addAttribute("nouveauDon", new Don()); // Pour le formulaire de don

        return "Donateur/dons"; // Renvoie vers dons.html
    }
	
	

    @PostMapping("/donateur/don")
    public String effectuerDon( Don don, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("Utilisateur non authentifié");
        }

        // Récupérer le donateur
        String email = principal.getName();
        User donateur = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Donateur introuvable"));

        // Enregistrer le don
        don.setDonateur(donateur);
        don.setDate_don(LocalDate.now());
        don.setStatutDon(Statut_don.en_attente);
        donRepo.save(don);

        return "redirect:/donateur/dons";
    }



}
