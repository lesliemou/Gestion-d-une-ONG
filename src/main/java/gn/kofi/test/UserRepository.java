package gn.kofi.test;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gn.kofi.test.model.Adhesion;
import gn.kofi.test.model.Role;
import gn.kofi.test.model.Statut_adhesion;
import gn.kofi.test.model.User;
import java.util.List;


public interface UserRepository  extends JpaRepository<User, Long>{
  Optional<User>  findByEmail(String email); 
 // User findByEmail(String email);

  int countByRolesAndEnabled(Role role, boolean enabled);
	
  List<User> findByRoles(Role role);

}
