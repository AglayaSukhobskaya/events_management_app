package com.light_digital.sukhobskaya.TestTask.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminDTO extends AccountDTO {
    @NotBlank(message = "Name should not be empty!")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters!")
    String name;
}
