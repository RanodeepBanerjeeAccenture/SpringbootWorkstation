package com.accenture.usermanagementapp.config;

import com.accenture.usermanagementapp.security.CustomAuthenticationSuccessHandler;
import com.accenture.usermanagementapp.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;  // Inject CustomUserDetailsService

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;  // Inject CustomAuthenticationSuccessHandler

    // Use NoOpPasswordEncoder for plain text passwords (for development/testing purposes)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();  // No encoding (plain text passwords)
    }

    // AuthenticationManager bean definition
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService)  // Use CustomUserDetailsService
                .passwordEncoder(passwordEncoder());     // Use NoOpPasswordEncoder
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/login", "/register", "/error").permitAll()  // Allow login and registration without authentication
                .requestMatchers("/api/admin/users/**").hasRole("ADMIN")
                .requestMatchers("/api/users/register").permitAll()  // Allow registration API without authentication
                .requestMatchers("/adminDashboard").hasRole("ADMIN")  // Ensure only admins can access the admin dashboard
                .requestMatchers("/dashboard").hasRole("USER")  // Ensure only users with ROLE_USER can access the user dashboard
                .anyRequest().authenticated()  // All other requests require authentication
                .and()
                .formLogin()
                .loginPage("/login")  // Specify the login page
                .successHandler(customAuthenticationSuccessHandler)  // Custom redirect handler
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf().disable()  // Disable CSRF for non-browser clients (if necessary)
                .cors().configurationSource(corsConfigurationSource());  // Enable CORS for API requests

        // Change session creation policy to default or stateful
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);  // Default: manage sessions as needed

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:4200");  // Frontend URL (adjust if necessary)
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);  // Apply globally
        return source;
    }
}
