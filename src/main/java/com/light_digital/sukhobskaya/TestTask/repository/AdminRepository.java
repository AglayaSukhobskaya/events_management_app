package com.light_digital.sukhobskaya.TestTask.repository;

import com.light_digital.sukhobskaya.TestTask.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByLogin(String login);
}
