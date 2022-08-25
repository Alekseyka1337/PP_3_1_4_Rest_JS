package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
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
    public String getAllUsers (Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "getAllUsers";
    }

    @GetMapping(value = "/admin/new")
    public String createUserForm (Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listRoles", userService.getListRoles());
        return "/createUser";
    }

    @GetMapping(value = "/admin/{id}/update")
    public String updateUserByIdForm (@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("listRoles", userService.getListRoles());
        return "updateUser";
    }

    @DeleteMapping(value = "/admin/{id}/delete")
    public String deleteUserById (@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
    @PostMapping(value = "/admin/save")
    public String createUser(Model model, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        model.addAttribute("listRoles", userService.getListRoles());
        if (userService.findUserByEmail(user.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.user", "Аккаунт с данным email уже существует");
        }
        if (bindingResult.hasErrors()) {
            return "createUser";
        }
        userService.saveOrUpdateUser(user);
        return "redirect:/admin";
    }
    @PatchMapping("/admin/update")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        model.addAttribute("listRoles", userService.getListRoles());
        if (userService.findUserByEmail(user.getEmail()) != null && !userService.findUserByEmail(user.getEmail()).getId().equals(user.getId())) {
            bindingResult.rejectValue("email", "error.user", "Аккаунт с данным email уже существует");
        }
        if (bindingResult.hasErrors()) {
            return "updateUser";
        }
        userService.saveOrUpdateUser(user);
        return "redirect:/admin";
    }
}
