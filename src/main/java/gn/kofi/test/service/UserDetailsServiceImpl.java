package gn.kofi.test.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import gn.kofi.test.UserRepository;
import gn.kofi.test.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired 
	private UserRepository userRepository;

	    public UserDetailsServiceImpl(UserRepository userRepository) {
	        this.userRepository = userRepository;
	        
	    }

	    @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	        User user = userRepository.findByEmail(email)
	                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
	       
	        
	        if (!user.isEnabled()) {
	            throw new UsernameNotFoundException("Compte désactivé");
	        }

	        return new org.springframework.security.core.userdetails.User(
	                user.getEmail(),
	                user.getPassword(),
	                List.of(new 
	               SimpleGrantedAuthority("ROLE_" + user.getRoles().name()))
	        );
	    }

}
