package gn.kofi.test.controller;

import java.security.Principal;
import java.util.Optional;

//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gn.kofi.test.UserRepository;
import gn.kofi.test.model.User;

@Controller
public class ProfilController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Affichage de la page profil
    @GetMapping("/profil")
    public String afficherProfil(Model model, Authentication authentication) {
        String email = authentication.getName();
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            model.addAttribute("utilisateur", userOpt.get());
        }

        return "profil"; // correspond au fichier profil.html
    }

    // Traitement de la modification du mot de passe
    @PostMapping("/profil/modifier-mot-de-passe")
    public String modifierMotDePasse(@RequestParam String ancienMotDePasse,
                                     @RequestParam String nouveauMotDePasse,
                                     Authentication authentication,
                                     RedirectAttributes redirectAttributes) {

        String email = authentication.getName();
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (passwordEncoder.matches(ancienMotDePasse, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(nouveauMotDePasse));
                userRepository.save(user);
                redirectAttributes.addFlashAttribute("success", "Mot de passe mis à jour avec succès !");
            } else {
                redirectAttributes.addFlashAttribute("erreur", "Ancien mot de passe incorrect !");
            }
        }

        return "redirect:/profil";
    }
}
