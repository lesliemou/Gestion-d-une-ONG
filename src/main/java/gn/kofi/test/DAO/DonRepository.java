package gn.kofi.test.DAO;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gn.kofi.test.model.Don;
import gn.kofi.test.model.User;

public interface DonRepository  extends JpaRepository<Don, Long>{
	    List<Don> findByDonateur(User donateur);
	    
	        @Query
	        ("SELECT COALESCE(SUM(d.montant), 0) FROM Don d WHERE MONTH(d.date_don) = MONTH(CURRENT_DATE) AND YEAR(d.date_don) = YEAR(CURRENT_DATE)")
	        Double totalDonsMois();
}
