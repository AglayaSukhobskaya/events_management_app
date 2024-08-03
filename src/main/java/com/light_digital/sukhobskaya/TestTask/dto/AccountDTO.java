package com.light_digital.sukhobskaya.TestTask.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDTO {
    @NotBlank(message = "Login should not be empty!")
    @Size(min = 2, max = 50, message = "Login should be between 2 and 50 characters")
    @Email(message = "Login should be a valid email!")
    String login;

    @NotBlank(message = "Password should not be empty!")
    @Size(min = 6, message = "Password should contain at least 6 characters.")
    String password;
}
