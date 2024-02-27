package com.ekfc.onlinestore.Services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ekfc.onlinestore.InterfaceRepos.usersRepo;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final usersRepo userRepository;

    public UserDetailsServiceImp(usersRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
