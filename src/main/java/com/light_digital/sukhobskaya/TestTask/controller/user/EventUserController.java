package com.light_digital.sukhobskaya.TestTask.controller.user;

import com.light_digital.sukhobskaya.TestTask.dto.EventDTO;
import com.light_digital.sukhobskaya.TestTask.exception.Handler;
import com.light_digital.sukhobskaya.TestTask.security.AccountDetails;
import com.light_digital.sukhobskaya.TestTask.service.EventService;
import com.light_digital.sukhobskaya.TestTask.util.EventValidator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/events")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventUserController implements Handler {
    EventService eventService;
    ModelMapper modelMapper;
    EventValidator eventValidator;

    @GetMapping
    public List<EventDTO> getAll() {
        return eventService.getAll().stream()
                .map(event -> modelMapper.map(event, EventDTO.class))
                .toList();
    }

    @GetMapping("/{id}")
    public EventDTO get(@PathVariable("id") Integer id) {
        return modelMapper.map(eventService.get(id), EventDTO.class);
    }

    @PostMapping("/{id}")
    public ResponseEntity<HttpStatus> userRegistrationRofEvent(@AuthenticationPrincipal AccountDetails accountDetails,
                                                               @PathVariable("id") Integer id) {
        eventValidator.isExist(id);

        eventService.userRegistrationForEvent(accountDetails.getAccount().getId(), id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
