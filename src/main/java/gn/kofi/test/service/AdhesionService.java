package gn.kofi.test.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import gn.kofi.test.DAO.AdhesionRepository;
import gn.kofi.test.model.Adhesion;
import gn.kofi.test.model.Statut_adhesion;

@Service
public class AdhesionService {
	
    @Autowired
	private AdhesionRepository adhesionRepo;

    public AdhesionService(AdhesionRepository adhesionRepo) {
        this.adhesionRepo = adhesionRepo;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Exécute tous les jours à minuit
    public void verifierEtMettreAJourStatuts() {
        LocalDate aujourdHui = LocalDate.now();
        

        // Récupérer toutes les adhésions actives 
        List<Adhesion> adhesions = adhesionRepo.findAll();  // Nous utilisons findAll pour récupérer toutes les adhésions

        for (Adhesion adhesion : adhesions) {
            if (adhesion.getStatutAdhesion() == Statut_adhesion.active) {
                // Vérifier si la date de fin est passée
                if (adhesion.getDate_fin().isBefore(aujourdHui)) {
                    // Si la date de fin est dépassée, mettre le statut à expiré
                    adhesion.setStatutAdhesion(Statut_adhesion.expiree);
                    adhesionRepo.save(adhesion);  // Sauvegarder la mise à jour
                    System.out.println("=> Statut mis à jour à expirée pour l'adhésion ID : " + adhesion.getId());

                }
            }

        }
    }
    
}
