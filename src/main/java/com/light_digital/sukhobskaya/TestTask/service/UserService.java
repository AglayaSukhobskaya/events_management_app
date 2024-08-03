package com.light_digital.sukhobskaya.TestTask.service;

import com.light_digital.sukhobskaya.TestTask.exception.NotFoundException;
import com.light_digital.sukhobskaya.TestTask.model.Event;
import com.light_digital.sukhobskaya.TestTask.model.User;
import com.light_digital.sukhobskaya.TestTask.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Transactional
    public void register(@NotNull User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        user.setEvents(new ArrayList<>());
        userRepository.save(user);
    }

    public User get(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id=" + id + " not found!"));
    }

    public List<Event> getAllUserEvents(Integer id) {
        var foundUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id=" + id + " not found!"));
        return foundUser.getEvents();
    }

    @Transactional
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
