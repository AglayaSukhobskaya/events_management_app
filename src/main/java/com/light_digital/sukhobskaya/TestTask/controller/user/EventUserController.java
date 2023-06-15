package com.light_digital.sukhobskaya.TestTask.controller.user;

import com.light_digital.sukhobskaya.TestTask.dto.EventDTO;
import com.light_digital.sukhobskaya.TestTask.service.EventService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/events")
@AllArgsConstructor
public class EventUserController {
    private final EventService eventService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<EventDTO> getAll() {
        return eventService.getAll().stream()
                .map(event -> modelMapper.map(event, EventDTO.class))
                .toList();
    }

    @GetMapping("/{id}")
    public EventDTO get(@PathVariable("id") int id) {
        return modelMapper.map(eventService.get(id), EventDTO.class);
    }
}
