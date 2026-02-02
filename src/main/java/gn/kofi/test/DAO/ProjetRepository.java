package gn.kofi.test.DAO;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gn.kofi.test.model.Projet;
import gn.kofi.test.model.Statut_projet;

public interface ProjetRepository extends JpaRepository<Projet, Long>{
	 @Query
	 ("SELECT COALESCE(SUM(p.montant), 0) FROM Projet p WHERE MONTH(p.date_debut) = MONTH(CURRENT_DATE) AND YEAR(p.date_debut) = YEAR(CURRENT_DATE)")
	    Double totalDepensesMois();
	 
	 List<Projet> findByStatPro(Statut_projet statut);


}
