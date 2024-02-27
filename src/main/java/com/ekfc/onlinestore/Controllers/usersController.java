package com.ekfc.onlinestore.Controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekfc.onlinestore.Models.AuthenticationResponse;
import com.ekfc.onlinestore.Models.users.users;
import com.ekfc.onlinestore.Models.users.usersDto;
import com.ekfc.onlinestore.Services.AuthenticationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/users")
public class usersController {

    private final AuthenticationService authenticationService;

    public usersController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ResponseEntity<List<users>> getUsers() {
        List<users> users = authenticationService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<users> getuserById(@PathVariable int id) {
        users user = authenticationService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> regiter(@RequestBody usersDto request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody usersDto request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
