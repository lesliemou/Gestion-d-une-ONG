package gn.kofi.test.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import gn.kofi.test.UserRepository;
import gn.kofi.test.DAO.EvenementRepository;
import gn.kofi.test.DAO.ProjetRepository;
import gn.kofi.test.DAO.TacheRepository;
import gn.kofi.test.model.Don;
import gn.kofi.test.model.Evenement;
import gn.kofi.test.model.Projet;
import gn.kofi.test.model.Role;
import gn.kofi.test.model.Statut_projet;
import gn.kofi.test.model.Statut_tache;
import gn.kofi.test.model.Tache;
import gn.kofi.test.model.User;

@Controller
public class TacheController {
	
	@Autowired
	public TacheRepository tacheRepo;
	
	@Autowired
	public ProjetRepository ProjetRepo;
	
	@Autowired
	public UserRepository userRepo;
	
	@Autowired
	public EvenementRepository eventRepo;
	
	@GetMapping("/admin/tache")
	public String afficherListeDon(Model m) {
		List<Tache> listeTache= tacheRepo.findAll();
		List<Projet> listeProjet= ProjetRepo.findAll();
		List<User> listeUser= userRepo.findAll();
		m.addAttribute("listeTache", listeTache);
		m.addAttribute("listeProjet", listeProjet);
		m.addAttribute("listeUser", listeUser);
		List<User> listeBenevole=userRepo.findByRoles(Role.BENEVOLE);
		m.addAttribute("listeBenevole", listeBenevole);


		m.addAttribute("Statut_Tache", Statut_tache.values());
		return"Admin/admin-tache";
	}
	
	@PostMapping("/admin/tache/save")
	public String enregistrerEvent(Tache tache) {
		tacheRepo.save(tache);
		return "redirect:/admin/tache";
	}
	
	 @GetMapping("/admin/edit-tache/{id}") // Correction de l'URL
	    public String showEditUserForm(@PathVariable Long id, Model model) {
	    	Tache tache = tacheRepo.findById(id).get();
	        model.addAttribute("tache", tache);
	        List<Tache> listeTache= tacheRepo.findAll();
			List<Projet> listeProjet= ProjetRepo.findAll();
			List<User> listeUser= userRepo.findAll();
			List<User> listeBenevole=userRepo.findByRoles(Role.BENEVOLE);
			model.addAttribute("listeBenevole", listeBenevole);

			model.addAttribute("listeTache", listeTache);
			model.addAttribute("listeProjet", listeProjet);
			model.addAttribute("listeUser", listeUser);

			model.addAttribute("Statut_Tache", Statut_tache.values());
	        //model.addAttribute("roles", Role.values());
	        return "Admin/admin-tache";
	    }
	
	
	@GetMapping("admin/tache/delete/{id}")
	public String supprimerTache(@PathVariable(name="id") Long id, Model m) {
		tacheRepo.deleteById(id);
		return "redirect:/admin/tache";
	}
	
	@GetMapping("/benevole/projet")
	public String afficherProjet(Model m)
	{
		List<Projet> listeProjet= ProjetRepo.findAll();
		m.addAttribute("listeProjet", listeProjet);
		m.addAttribute("statutpro", Statut_projet.values());
		return"Benevole/benevole-projet";
		
	}
	
	@GetMapping("/benevole/event")
	public String afficherListeEvent(Model m) {
		List<Evenement> listeEvent= eventRepo.findAll();
		m.addAttribute("listeEvent", listeEvent);
		
		return"Benevole/benevole-event";
	}
	
	
	@GetMapping("/benevole/taches")
    public String afficherTaches(Model model, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("Utilisateur non authentifié");
        }

        // Récupérer l'email du bénévole connecté
        String email = principal.getName();

        // Trouver le bénévole dans la base de données
        User benevole = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Bénévole introuvable"));

        // Récupérer les tâches assignées au bénévole
        List<Tache> taches = tacheRepo.findByBenevole(benevole);
		List<Tache> listeTache= tacheRepo.findAll();

        // Ajouter les données au modèle pour Thymeleaf
		model.addAttribute("statut_tache", Statut_tache.values());
		model.addAttribute("listeTache", listeTache);

        model.addAttribute("taches", taches);
        model.addAttribute("nouvelleTache", new Tache()); // Pour postuler à une tâche
       

        return "Benevole/taches"; // Renvoie vers taches.html
    }
	
	
	@GetMapping("/benevole/tache/executer/{id}")
	public String executerTache(@PathVariable Long id) {
	    Tache tache = tacheRepo.findById(id).orElse(null);
	    if (tache != null && tache.getStatutTache() == Statut_tache.EN_COURS) {
	        LocalDate now = LocalDate.now();
	        //tache.setDateExecution(now);

	        if (!now.isAfter(tache.getDateEchance())) {
	            tache.setStatutTache(Statut_tache.TERMINE);
	        } else {
	            tache.setStatutTache(Statut_tache.EN_RETARD);
	        }

	        tacheRepo.save(tache);
	    }
	    return "redirect:/benevole/taches";
	}


	
}
