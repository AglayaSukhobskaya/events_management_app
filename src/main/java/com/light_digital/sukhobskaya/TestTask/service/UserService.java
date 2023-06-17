package com.light_digital.sukhobskaya.TestTask.service;

import com.light_digital.sukhobskaya.TestTask.exception.NotFoundException;
import com.light_digital.sukhobskaya.TestTask.model.Event;
import com.light_digital.sukhobskaya.TestTask.model.User;
import com.light_digital.sukhobskaya.TestTask.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        user.setEvents(new ArrayList<>());
        userRepository.save(user);
    }

    public User get(int id) {
        Optional<User> foundUser = userRepository.findById(id);
        return foundUser.orElseThrow(() -> new NotFoundException("User with id="
                + id + " not found!"));
    }

    public List<Event> getAllUserEvents(int id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty()) {
            throw new NotFoundException("User with id=" + id + " not found!");
        }
        return foundUser.get().getEvents();
    }

    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }
}
