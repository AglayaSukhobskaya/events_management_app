package com.light_digital.sukhobskaya.TestTask.util;

import com.light_digital.sukhobskaya.TestTask.exception.NotFoundException;
import com.light_digital.sukhobskaya.TestTask.exception.NotValidDataException;
import com.light_digital.sukhobskaya.TestTask.model.Event;
import com.light_digital.sukhobskaya.TestTask.repository.EventRepository;
import com.light_digital.sukhobskaya.TestTask.security.AccountDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class EventValidator implements Validator {

    private final EventRepository eventRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Event.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Assert.notNull("target", "Event should not be null!");
        Event event = (Event) target;

        if (eventRepository.findByName(event.getName()).isPresent()) {
            errors.rejectValue("name", "", "Event with this name already exists!");
        }
    }

    public void isExist(int id) {
        if (eventRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Event with id=" + id + " not found!");
        }
    }

    public void eventBelongsToPerson(AccountDetails accountDetails, Event event) {
        if (!event.getOwner().getId().equals(accountDetails.getAccount().getId())) {
            throw new NotValidDataException("Event with id=" + event.getId() + " does not belong to this person!");
        }
    }
}
