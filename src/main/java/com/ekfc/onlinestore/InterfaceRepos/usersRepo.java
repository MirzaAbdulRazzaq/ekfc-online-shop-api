package com.ekfc.onlinestore.InterfaceRepos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ekfc.onlinestore.Models.users.users;

public interface usersRepo extends JpaRepository<users, Integer> {

    Optional<users> findByUsername(String username);

}
