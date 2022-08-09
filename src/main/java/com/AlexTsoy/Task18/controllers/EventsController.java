package com.AlexTsoy.Task18.controllers;

import com.AlexTsoy.Task18.entity.Event;
import com.AlexTsoy.Task18.exceptions.EntityErrorResponse;
import com.AlexTsoy.Task18.exceptions.EntityNotSaveExceprion;
import com.AlexTsoy.Task18.service.EventService;
import com.AlexTsoy.Task18.util.validators.EventValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/events")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventsController {
    private final EventService eventService;
    private final EventValidator eventValidator;
    @GetMapping()
    public List<Event> getAllEvents() {
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable("id") int id) {
        return eventService.findById(id);
    }

    @PostMapping()
    public Event create(@RequestBody @Valid Event event, BindingResult bindingResult) {
        validator(event, bindingResult);
        return eventService.save(event);
    }

    @DeleteMapping("/{id}")
    public void removeEvent(@PathVariable("id") int id) {
        eventService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") int id, @RequestBody @Valid Event event, BindingResult bindingResult) {
        validator(event, bindingResult);
        event.setId(id);
        eventService.save(event);
    }

    private void validator(Event event, BindingResult bindingResult) {
        eventValidator.validate(event, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error: errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new EntityNotSaveExceprion(errorMessage.toString());
        }
    }

    @ExceptionHandler
    private ResponseEntity<EntityErrorResponse> saveException(EntityNotSaveExceprion e) {
        EntityErrorResponse response = new EntityErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
