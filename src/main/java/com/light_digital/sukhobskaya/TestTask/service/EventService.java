package com.light_digital.sukhobskaya.TestTask.service;

import com.light_digital.sukhobskaya.TestTask.exception.NotFoundException;
import com.light_digital.sukhobskaya.TestTask.model.Event;
import com.light_digital.sukhobskaya.TestTask.model.User;
import com.light_digital.sukhobskaya.TestTask.repository.EventRepository;
import com.light_digital.sukhobskaya.TestTask.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    public Event get(int id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.orElseThrow(() -> new NotFoundException("Event with id=" +
                id + " not found!"));
    }

    @Transactional
    public void create(Event event) {
        event.setParticipants(new ArrayList<>());
        eventRepository.save(event);
    }

    @Transactional
    public void update(int id, Event event) {
        event.setId(id);
        eventRepository.save(event);
    }

    @Transactional
    public void delete(int id) {
        eventRepository.deleteById(id);
    }

    @Transactional
    public void userRegistrationForEvent(int userId, int eventId) {
        Optional<User> foundUser = userRepository.findById(userId);
        Optional<Event> foundEvent = eventRepository.findById(eventId);

        foundUser.get().getEvents().add(foundEvent.get());
        foundEvent.get().getParticipants().add(foundUser.get());
    }
}
