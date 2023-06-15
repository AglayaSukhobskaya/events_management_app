package com.light_digital.sukhobskaya.TestTask.controller.user;

import com.light_digital.sukhobskaya.TestTask.dto.EventDTO;
import com.light_digital.sukhobskaya.TestTask.exception.Handler;
import com.light_digital.sukhobskaya.TestTask.security.AccountDetails;
import com.light_digital.sukhobskaya.TestTask.service.EventService;
import com.light_digital.sukhobskaya.TestTask.util.EventValidator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/events")
@AllArgsConstructor
public class EventUserController implements Handler {
    private final EventService eventService;
    private final ModelMapper modelMapper;
    private final EventValidator eventValidator;

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

    @PostMapping("/{id}")
    public ResponseEntity<HttpStatus> userRegistrationRofEvent(@AuthenticationPrincipal AccountDetails accountDetails,
                                               @PathVariable("id") int id) {
        eventValidator.isExist(id);

        eventService.userRegistrationForEvent(accountDetails.getAccount().getId(), id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
