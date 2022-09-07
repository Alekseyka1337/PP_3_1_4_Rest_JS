package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    void deleteUserById(Long id);

    void saveUser(User user);

    void updateUser(User user);

    User findUserByEmail(String email);

    User getUserById(Long id);

    //List<Role> getListRoles ();

    PasswordEncoder getPasswordEncoder();

    User passwordCoder (User user);
}
