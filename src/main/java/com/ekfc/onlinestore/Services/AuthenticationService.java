package com.ekfc.onlinestore.Services;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ekfc.onlinestore.InterfaceRepos.usersRepo;
import com.ekfc.onlinestore.Models.AuthenticationResponse;
import com.ekfc.onlinestore.Models.users.users;
import com.ekfc.onlinestore.Models.users.usersDto;

@Service
public class AuthenticationService {
    private final usersRepo repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(usersRepo repository, PasswordEncoder passwordEncoder, JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(usersDto request) {
        users user = new users();
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setRole(request.getRole());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = repository.save(user);
        String token = jwtService.generateToke(user);

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(usersDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        users user = repository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToke(user);
        return new AuthenticationResponse(token);
    }

    public List<users> getAllUsers() {
        return repository.findAll();
    }

    public users getUserById(int id) {
        users user = repository.findById(id).orElseThrow(null);
        return user;
    }

}
