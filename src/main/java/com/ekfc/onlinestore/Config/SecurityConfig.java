package com.ekfc.onlinestore.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ekfc.onlinestore.Filter.JwtAuthFilter;
import com.ekfc.onlinestore.Services.UserDetailsServiceImp;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImp userDetailsServiceImp;
    private final JwtAuthFilter jwtAuthFilterr;

    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp, JwtAuthFilter jwtAuthFilterr) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtAuthFilterr = jwtAuthFilterr;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req.requestMatchers("/api/users/register", "/api/users/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/products/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/products/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasAnyAuthority("ADMIN")
                                .anyRequest()
                                .authenticated())
                .exceptionHandling(
                        exception -> exception.accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter()
                                    .write("Access Denied: You don't have permission to access this resource.");
                        }))
                .userDetailsService(userDetailsServiceImp)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilterr, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passworEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
