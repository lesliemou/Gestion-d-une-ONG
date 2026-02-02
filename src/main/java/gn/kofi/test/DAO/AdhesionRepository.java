package gn.kofi.test.DAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gn.kofi.test.model.Adhesion;
import gn.kofi.test.model.Role;
import gn.kofi.test.model.Statut_adhesion;
import gn.kofi.test.model.User;

public interface AdhesionRepository extends JpaRepository<Adhesion, Long>{
	List<Adhesion> findByMembre(User membre);
	List<Adhesion> findByStatutAdhesion(Statut_adhesion statut);

	
	@Query
	("SELECT COALESCE(SUM(a.montant), 0) FROM Adhesion a WHERE MONTH(a.date_debut) = MONTH(CURRENT_DATE) AND YEAR(a.date_debut) = YEAR(CURRENT_DATE)")
   Double totalCotisationsMois();
}
