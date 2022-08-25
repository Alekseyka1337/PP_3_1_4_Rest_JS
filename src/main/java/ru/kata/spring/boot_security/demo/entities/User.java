package ru.kata.spring.boot_security.demo.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;


@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @SequenceGenerator(name = "usersIdSeq", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersIdSeq")
    private Long id;

    @Pattern(regexp = "[A-Za-zА-Яа-я]{2,15}", message = "Имя должно содержать от 2 до 15 русских или латинских символов")
    @Column(name = "first_name")
    private String firstName;

    @Pattern(regexp = "[A-Za-zА-Яа-я]{2,15}", message = "Фамилия должна содержать от 2 до 15 русских или латинских символов")
    @Column(name = "last_name")
    private String lastName;

    @Min(value = 0, message = "Возраст должен быть больше 0")
    @Max(value = 127, message = "Возраст должен быть меньше 127")
    @Column(name="age")
    private byte age;


    @Email(message = "Введите корректный email")
    @NotEmpty(message = "Email не может быть пустым")
    @Column(name = "email" , unique = true)
    private String email;

    @NotEmpty(message = "Пароль не может быть пустым")
    @Size(min = 4, message = "Пароль должен содержать больше 4 символов")
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
