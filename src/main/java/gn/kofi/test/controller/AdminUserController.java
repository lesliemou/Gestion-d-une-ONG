package gn.kofi.test.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import gn.kofi.springCleSecondaire.entities.Produit;
//import gn.kofi.springCleSecondaire.entities.Produit;
import gn.kofi.test.UserRepository;
import gn.kofi.test.model.Role;
import gn.kofi.test.model.User;

@Controller
public class AdminUserController {
     
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminUserController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @GetMapping("/login")
    public String afficherLoginPage() {
		return"login";
    }
    
    @GetMapping("/admin/menu")
    public String Affichermenu() {
		return"Admin/MenuAdmin";
	}
    
    @GetMapping("/membre/menu")
    public String AffichermenuM() {
		return"Membres/MenuMembre";
	}
    @GetMapping("/donateur/menu")
    public String AffichermenuD() {
		return"Donateur/MenuDonateur";
	}
    
    @GetMapping("/benevole/menu")
    public String AffichermenuB() {
		return"Benevole/MenuBenevole";
	}
	

    @GetMapping("/admin/users") // Correction de l'URL
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("roles", Role.values());
        return "admin-users";
    }
    
    

    @GetMapping("/admin/edit-user/{id}") // Correction de l'URL
    public String showEditUserForm(@PathVariable Long id, Model model) {
    	User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "admin-users";
    }
    
    @PostMapping("/admin/user/save")
    public String enregistrerUtilisateur(User user) {
        // Vérifier si le mot de passe est déjà encodé (pour modification)
        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        }

        userRepository.save(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/edit-user/{id}") // Correction de l'URL
    public String updateUser(@PathVariable Long id, @RequestParam String email, @RequestParam(required = false) String password) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(email);
            if (password != null && !password.isEmpty()) {
                user.setPassword(passwordEncoder.encode(password));
            }
            userRepository.save(user);
        }
        return "redirect:/admin/users";
    
}
    @PostMapping("/admin/toggle-user/{id}") // Correction
    public String toggleUserStatus(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        optionalUser.ifPresent(user -> {
            user.setEnabled(!user.isEnabled());
            userRepository.save(user);
        });
        return "redirect:/admin/users";
    }
    
    @PostMapping("/admin/add-user")
    public String addUser(@RequestParam String email, 
    		              @RequestParam String nom,
    		              @RequestParam String prenom,
    		              @RequestParam String adresse,
    		              @RequestParam String telephone,
                          @RequestParam String password, 
                          @RequestParam Role roles,Model m) {
        if (userRepository.findByEmail(email).isPresent()) {
            return "redirect:/admin/users?error=exists";
        }
        
        User newUser = new User();
        newUser.setNom(nom);
        newUser.setPrenom(prenom);
        newUser.setTelephone(telephone);
        newUser.setAdresse(adresse);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRoles(roles);
        newUser.setEnabled(true);

        userRepository.save(newUser);
        return "redirect:/admin/users?success=added";
    }

}
