package com.light_digital.sukhobskaya.TestTask.repository;

import com.light_digital.sukhobskaya.TestTask.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {

    Optional<Event> findByName(String name);
}
