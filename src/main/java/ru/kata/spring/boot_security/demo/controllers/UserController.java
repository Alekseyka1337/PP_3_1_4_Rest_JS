package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;


@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String user(Principal principal, Model model) {
        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping(value = "/admin")
    public String adminPanel (@ModelAttribute("user") User user, Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("listRoles", userService.getListRoles());
        return "adminPanel";
    }

    @DeleteMapping(value = "/admin/{id}/delete")
    public String deleteUserById (@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
    @PostMapping(value = "/admin/save")
    public String createUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("listRoles", userService.getListRoles());
        return userService.saveUser(user);
    }
    @PatchMapping("/admin/update")
    public String updateUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("listRoles", userService.getListRoles());
        return userService.updateUser(user);
    }
}
