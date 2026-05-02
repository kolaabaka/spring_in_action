package com.fx.controller;

import com.fx.dto.jpa.UserCredential;
import com.fx.repository.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginRegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model){
        model.addAttribute("userCredential", new UserCredential());
        return "registrationForm";
    }

    @PostMapping("/registration")
    public String registrationUser(UserCredential userCredential){
        userRepository.save(userCredential.encodePassword(passwordEncoder));
        return "redirect:login";
    }
}
