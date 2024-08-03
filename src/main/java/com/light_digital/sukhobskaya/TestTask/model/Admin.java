package com.light_digital.sukhobskaya.TestTask.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "admins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Admin extends Account {
    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name should not be empty!")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters!")
    String name;

    @OneToOne(mappedBy = "admin")
    Contract contract;

    @OneToOne(mappedBy = "admin")
    Application application;

    @OneToMany(mappedBy = "owner")
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<Event> events;
}
