package com.light_digital.sukhobskaya.TestTask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends AccountDTO {

    @NotBlank(message = "Name should not be empty!")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 50 characters")
    private String name;

    @Range(min = 16)
    private int age;
}
