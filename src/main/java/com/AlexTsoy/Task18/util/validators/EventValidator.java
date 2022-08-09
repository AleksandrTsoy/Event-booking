package com.AlexTsoy.Task18.util.validators;

import com.AlexTsoy.Task18.entity.Event;
import com.AlexTsoy.Task18.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventValidator implements Validator {

    private final EventRepository eventRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return Event.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Event event = (Event) target;
        if (eventRepository.findEventByNameAndDate(event.getName(), event.getDate()).isPresent()) {
            errors.rejectValue("date", "", "This event is exist");
        }
    }
}
