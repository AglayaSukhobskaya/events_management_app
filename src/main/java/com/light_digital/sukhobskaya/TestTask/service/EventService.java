package com.light_digital.sukhobskaya.TestTask.service;

import com.light_digital.sukhobskaya.TestTask.exception.NotFoundException;
import com.light_digital.sukhobskaya.TestTask.model.Event;
import com.light_digital.sukhobskaya.TestTask.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

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
}
