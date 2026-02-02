package gn.kofi.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import gn.kofi.test.DAO.EvenementRepository;
import gn.kofi.test.model.Evenement;
import gn.kofi.test.model.Role;
import gn.kofi.test.model.User;


@Controller
public class EvenementController {
	
	@Autowired
	 private EvenementRepository eventRepo;
	
	@GetMapping("/admin/evenement")
	public String afficherListeEvent(Model m) {
		List<Evenement> listeEvent= eventRepo.findAll();
		m.addAttribute("listeEvent", listeEvent);
		
		return"Admin/admin-evenement";
	}
	
	@PostMapping("/admin/evenement/save")
	public String enregistrerEvent(Evenement evt) {
		eventRepo.save(evt);
		return "redirect:/admin/evenement";
	}
	
	 @GetMapping("/admin/edit-evenement/{id}") // Correction de l'URL
	    public String showEditUserForm(@PathVariable Long id, Model model) {
	    	Evenement event = eventRepo.findById(id).get();
	        model.addAttribute("event", event);
	        //model.addAttribute("roles", Role.values());
	        return "Admin/admin-evenement";
	    }
	    
	
	@GetMapping("admin/evenement/delete/{id}")
	public String supprimerEven(@PathVariable(name="id") Long id, Model m) {
		eventRepo.deleteById(id);
		return "redirect:/admin/evenement";
	}

}
