package gn.kofi.test.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gn.kofi.test.UserRepository;
import gn.kofi.test.model.Role;
import gn.kofi.test.model.User;


	@Controller
	public class AdminRegistrationController {
	    private final UserRepository userRepository;
	    
	    private final BCryptPasswordEncoder passwordEncoder;

	    public AdminRegistrationController(UserRepository userRepository,  BCryptPasswordEncoder passwordEncoder) {
	        this.userRepository = userRepository;
	        //this.roleRepository = roleRepository;
	        this.passwordEncoder = passwordEncoder;
	    }

	    @GetMapping("/register")
	    public String showRegistrationForm() {
	        return "register";
	    }

	    @PostMapping("/register")
	    public String registerAdmin(@RequestParam String email, @RequestParam String password) {
	        if (userRepository.findByEmail(email).isPresent()) {
	            return "redirect:/register?error";
	        }

	       

	        User admin = new User();
	        admin.setEmail(email);
	        admin.setPassword(passwordEncoder.encode(password));
	        admin.setRoles(Role.ADMIN);
	        admin.setEnabled(true);

	        userRepository.save(admin);
	        return "redirect:/login?registered=true";
	    }
	}

