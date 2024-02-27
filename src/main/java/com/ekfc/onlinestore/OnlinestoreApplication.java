package com.ekfc.onlinestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ekfc.onlinestore.InterfaceRepos.usersRepo;
import com.ekfc.onlinestore.Models.users.users;

@SpringBootApplication
public class OnlinestoreApplication {

	private final usersRepo repository;
	private final PasswordEncoder passwordEncoder;

	public OnlinestoreApplication(usersRepo repository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;

		if (this.repository.count() == 0) {
			users admin = new users();
			admin.setName("Abdul Razzaq");
			admin.setUsername("shakir");
			admin.setRole("ADMIN");
			admin.setPassword(this.passwordEncoder.encode("1234"));

			users defaultUser = new users();
			defaultUser.setName("Abdul Razzaq Ahmad");
			defaultUser.setUsername("mirza");
			defaultUser.setRole("USER");
			defaultUser.setPassword(this.passwordEncoder.encode("1234"));

			this.repository.save(admin);
			this.repository.save(defaultUser);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(OnlinestoreApplication.class, args);
	}

}
