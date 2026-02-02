package gn.kofi.test;

//import org.apache.tomcat.util.net.DispatchType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.util.matcher.DispatcherTypeRequestMatcher;

//import jakarta.servlet.DispatcherType;

@Configuration
public class SecurityConfig {
	
	 @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	      return authConfig.getAuthenticationManager();
	    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/register","/login", "/css/**", "/js/**").permitAll() // Routes publiques
                .requestMatchers("/profil").authenticated() // <-- ✅ Tous les utilisateurs connectés peuvent accéder à /profil

                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // Restriction aux admins
                .requestMatchers("/membre/**").hasAuthority("ROLE_MEMBRE")
                .requestMatchers("/donateur/**").hasAuthority("ROLE_DONATEUR")
                .requestMatchers("/benevole/**").hasAuthority("ROLE_BENEVOLE")
                .anyRequest().authenticated() // Toute autre requête nécessite une connexion
            )
            .formLogin()
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler()) // Gestion personnalisée de la redirection
                .permitAll()
                .and()
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
            
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
            boolean isMembre = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_MEMBRE"));
            boolean isDonateur = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_DONATEUR"));
            boolean isBenevole = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_BENEVOLE"));
            
            if (isAdmin) {
                response.sendRedirect("/admin/tableauBord");
            }  else if(isMembre) {
            	
            	response.sendRedirect("/membre/cotisations");
            	
            }
          else if(isDonateur) {
            	
            	response.sendRedirect("/donateur/dons");
            	
            } 
            
          else if(isBenevole) {
          	
          	response.sendRedirect("/benevole/taches");
          	
          } 
            
            else {
                response.sendRedirect("/home");
            }
        };
    }
}

