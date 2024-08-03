package com.light_digital.sukhobskaya.TestTask.repository;

import com.light_digital.sukhobskaya.TestTask.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
}
