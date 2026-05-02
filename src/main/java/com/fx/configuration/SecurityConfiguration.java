package com.fx.configuration;

import com.fx.repository.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserRepository userRepository;

    @Bean //it should/could be separate service but WOW WE GO LAMBDA LET`S USE IT EVERYWHERE
    public UserDetailsService userDetailsService() {
        return username -> {
            var userCredential = userRepository.findByUsername(username);
            if(userCredential != null){
                return userCredential;
            }
            throw new UsernameNotFoundException("Not found");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login", "/registration").permitAll()
                .requestMatchers("/static/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout ->
                logout
                    .permitAll()
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/login?logout")
                    .deleteCookies("JSESSIONID")
                    .logoutUrl("/logout"));
        return http.build();
    }

}
