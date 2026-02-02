package gn.kofi.test.DAO;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import gn.kofi.test.model.Evenement;

public interface EvenementRepository extends JpaRepository<Evenement, Long> {
	Evenement findFirstByDateAfterOrderByDateAsc(LocalDate date);

}
