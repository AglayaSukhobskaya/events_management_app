package com.light_digital.sukhobskaya.TestTask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {

    @NotBlank(message = "Name should not be empty!")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters!")
    private String name;

    private int price;
}
