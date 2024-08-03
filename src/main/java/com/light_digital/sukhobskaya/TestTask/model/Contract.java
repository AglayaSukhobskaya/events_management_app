package com.light_digital.sukhobskaya.TestTask.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "contracts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Contract {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "signed")
    Boolean signed;

    @OneToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    Admin admin;

    public Contract(Admin admin) {
        this.admin = admin;
    }
}
