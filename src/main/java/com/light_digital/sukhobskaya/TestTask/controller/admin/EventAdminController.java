package com.light_digital.sukhobskaya.TestTask.controller.admin;

import com.light_digital.sukhobskaya.TestTask.dto.EventDTO;
import com.light_digital.sukhobskaya.TestTask.exception.Handler;
import com.light_digital.sukhobskaya.TestTask.model.Admin;
import com.light_digital.sukhobskaya.TestTask.model.Event;
import com.light_digital.sukhobskaya.TestTask.security.AccountDetails;
import com.light_digital.sukhobskaya.TestTask.service.EventService;
import com.light_digital.sukhobskaya.TestTask.util.EventValidator;
import com.light_digital.sukhobskaya.TestTask.util.AdminValidator;
import com.light_digital.sukhobskaya.TestTask.util.ValidationUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/events")
@AllArgsConstructor
public class EventAdminController implements Handler {

    private final EventService eventService;
    private final ModelMapper modelMapper;
    private final EventValidator eventValidator;
    private final AdminValidator adminValidator;

    @GetMapping
    public List<EventDTO> getAll(@AuthenticationPrincipal AccountDetails accountDetails) {
        return eventService.getAll().stream()
                .filter(event -> event.getOwner().getId().equals(accountDetails.getAccount().getId()))
                .map(event -> modelMapper.map(event, EventDTO.class))
                .toList();
    }

    @GetMapping("/{id}")
    public EventDTO get(@AuthenticationPrincipal AccountDetails personDetails,
                        @PathVariable("id") int id) {
        Event event = eventService.get(id);
        eventValidator.eventBelongsToPerson(personDetails, event);
        return modelMapper.map(event, EventDTO.class);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@AuthenticationPrincipal AccountDetails accountDetails,
                                             @RequestBody @Valid EventDTO eventDTO,
                                             BindingResult bindingResult) {
//        personValidator.hasContract(personDetails);

        Event event = modelMapper.map(eventDTO, Event.class);
        eventValidator.validate(event, bindingResult);

        ValidationUtil.checkDataValidity(bindingResult);

        event.setOwner((Admin) accountDetails.getAccount());
        eventService.create(event);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@AuthenticationPrincipal AccountDetails accountDetails,
                                             @PathVariable("id") int id,
                                             @RequestBody @Valid EventDTO eventDTO,
                                             BindingResult bindingResult) {
        eventValidator.isExist(id);
        eventValidator.eventBelongsToPerson(accountDetails, eventService.get(id));

        Event event = modelMapper.map(eventDTO, Event.class);
        event.setOwner((Admin) accountDetails.getAccount());
        eventValidator.validate(event, bindingResult);

        ValidationUtil.checkDataValidity(bindingResult);

        eventService.update(id, event);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@AuthenticationPrincipal AccountDetails personDetails,
                                             @PathVariable("id") int id) {
        eventValidator.isExist(id);
        eventValidator.eventBelongsToPerson(personDetails, eventService.get(id));
        eventService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
