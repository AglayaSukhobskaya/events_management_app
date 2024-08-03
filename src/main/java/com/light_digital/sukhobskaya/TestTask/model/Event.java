package com.light_digital.sukhobskaya.TestTask.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Name should not be empty!")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters!")
    String name;

    @Column(name = "price")
    Integer price;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    Admin owner;

    @ManyToMany(mappedBy = "events")
    List<User> participants;
}
