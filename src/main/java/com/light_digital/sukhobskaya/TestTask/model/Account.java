package com.light_digital.sukhobskaya.TestTask.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "login", nullable = false, unique = true)
    @NotBlank(message = "Login should not be empty!")
    @Size(min = 2, max = 50, message = "Login should be between 2 and 50 characters")
    @Email(message = "Login should be a valid email!")
    String login;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password should not be empty!")
    @Size(min = 6, message = "Password should contain at least 6 characters.")
    String password;

    @Column(name = "role", nullable = false)
    @NotBlank(message = "Role should not be empty!")
    String role;
}
