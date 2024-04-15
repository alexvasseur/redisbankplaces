package com.redislabs.demos.redisbankplaces;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/auth-login.html", "assets/**", "/api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form)->form
                        .loginPage("/index.html")
                        //.loginProcessingUrl("/perform_login")
                        //.successForwardUrl("/index.html")
                        //.defaultSuccessUrl("/index2.html", true)
                        //.successForwardUrl("/api/login_success_handler")
                        //.failureForwardUrl("/api/login_failure_handler")
                        //.defaultSuccessUrl("/index.html", true)
                        //.failureUrl("/auth-login.html?error=true")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());
        SecurityFilterChain sfc = http.build();
        return sfc;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("alex")
                        .password("alexv")//"{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                        .roles("USER")
                        .build();
        return new InMemoryUserDetailsManager(user);
    }

}
