package gn.kofi.test.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import gn.kofi.test.UserRepository;
import gn.kofi.test.DAO.AdhesionRepository;
import gn.kofi.test.DAO.DonRepository;
import gn.kofi.test.DAO.EvenementRepository;
import gn.kofi.test.DAO.ProjetRepository;
import gn.kofi.test.model.Adhesion;
import gn.kofi.test.model.Evenement;
import gn.kofi.test.model.Projet;
import gn.kofi.test.model.Role;
import gn.kofi.test.model.Statut_adhesion;
import gn.kofi.test.model.Statut_projet;

@Controller
public class TableauDeBordController {
	
	@Autowired
    private UserRepository userRepository;
	@Autowired
	private AdhesionRepository adhesionRepo;
	@Autowired
	private DonRepository donRepository;
	@Autowired
    private ProjetRepository projetRepository;
	
	@Autowired
	private EvenementRepository eventRepo;

	
	 /*@GetMapping("/admin/tableauBord")
	   // public String AffichermenuB() {
		//	return"Admin/Dashbord";
		}*/

	 
	    @GetMapping("/admin/tableauBord")
	    public String showDashboard(Model model) {
	        // Nombre total de membres actifs
	    	
	    	List<Adhesion> retard = adhesionRepo.findByStatutAdhesion(Statut_adhesion.expiree);
	        int membresActifs = userRepository.countByRolesAndEnabled(Role.MEMBRE, true);

	        // Nombre total de bénévoles disponibles
	        int benevolesDisponibles = userRepository.countByRolesAndEnabled(Role.BENEVOLE, true);

	        // Envoyer les statistiques au template
	        model.addAttribute("membresActifs", membresActifs);
	        model.addAttribute("benevolesDisponibles", benevolesDisponibles);
	        model.addAttribute("retard", retard);

	        
	        Double cotisations = adhesionRepo.totalCotisationsMois();
	        Double dons = donRepository.totalDonsMois();
	        Double depenses = projetRepository.totalDepensesMois();
	        Double solde = cotisations+dons-(depenses);
	        model.addAttribute("cotisations", cotisations);
	        model.addAttribute("dons", dons);
	        model.addAttribute("depenses", depenses);
	        model.addAttribute("solde", solde);
	        
	        List<Projet> projetsEnCours = projetRepository.findByStatPro(Statut_projet.En_cours);
	        model.addAttribute("projetsEnCours", projetsEnCours);
	        
	        Evenement prochainEvenement = eventRepo.findFirstByDateAfterOrderByDateAsc(LocalDate.now());
	        model.addAttribute("prochainEvenement", prochainEvenement);

	        return "Admin/Dashbord"; // Vue Thymeleaf
	    }
}
