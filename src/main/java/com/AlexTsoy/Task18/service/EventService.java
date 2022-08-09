package com.AlexTsoy.Task18.service;

import com.AlexTsoy.Task18.entity.Event;
import com.AlexTsoy.Task18.exceptions.EntityNotFoundException;
import com.AlexTsoy.Task18.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventService {
    private final EventRepository eventRepository;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event findById(int id) {
        return eventRepository.findById(id).orElseThrow(() -> {
            log.error("Entity not found");
            return new EntityNotFoundException();
        });
    }

    @Transactional
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Transactional
    public void delete(int id) {
        eventRepository.deleteById(id);
    }

    @Transactional
    public void edit(Event event) {
        eventRepository.save(event);
    }

}
