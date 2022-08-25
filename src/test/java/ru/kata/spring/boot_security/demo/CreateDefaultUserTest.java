package ru.kata.spring.boot_security.demo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CreateDefaultUserTest {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;

	@Test
	public void createUser(){
		User user = new User();
		user.setEmail("admin@mail.ru");
		user.setPassword(new BCryptPasswordEncoder().encode("admin"));
		user.setRoles(createRoles());
		user.setFirstName("admin");
		user.setLastName("admin");
		user.setAge((byte) 0);
		userRepository.save(user);
	}

	public List<Role> createRoles(){
		Role role1 = new Role("ROLE_ADMIN");
		Role role2 = new Role("ROLE_USER");
		roleRepository.save(role1);
		roleRepository.save(role2);
		List<Role> roles = new ArrayList<>();
		roles.add(role1);
		roles.add(role2);
		return roles;
	}
}


