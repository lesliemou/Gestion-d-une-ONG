package gn.kofi.test.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gn.kofi.test.model.Tache;
import gn.kofi.test.model.User;

public interface TacheRepository extends JpaRepository<Tache, Long> {
	List<Tache> findByBenevole(User benevole);

}
