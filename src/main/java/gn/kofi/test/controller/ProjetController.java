package gn.kofi.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import gn.kofi.test.DAO.ProjetRepository;
import gn.kofi.test.model.Evenement;
import gn.kofi.test.model.Projet;
import gn.kofi.test.model.Statut_projet;





@Controller
public class ProjetController {
	
	@Autowired
	private ProjetRepository projetRepo;

	@GetMapping("/admin/projet")
	public String afficherListeDon(Model m) {
		List<Projet> listeProjet= projetRepo.findAll();
		m.addAttribute("listeProjet", listeProjet);
		m.addAttribute("statutpro", Statut_projet.values());
		return"Admin/admin-projet";
	}
	
	@PostMapping("/admin/projet/save")
	public String enregistrerpro(Projet projet) {
		projetRepo.save(projet);
		return "redirect:/admin/projet";
	}
	
	 @GetMapping("/admin/edit-projet/{id}") // Correction de l'URL
	    public String showEditUserForm(@PathVariable Long id, Model model) {
	    	Projet projet = projetRepo.findById(id).get();
	        model.addAttribute("projet", projet);
			model.addAttribute("statutpro", Statut_projet.values());

	        //model.addAttribute("roles", Role.values());
	        return "Admin/admin-projet";
	    }
	 
	
	
	@GetMapping("/admin/projet/delete/{id}")
	public String supprimerDon(@PathVariable(name="id") Long id, Model m) {
		projetRepo.deleteById(id);
		return "redirect:/admin/projet";
	}
}
