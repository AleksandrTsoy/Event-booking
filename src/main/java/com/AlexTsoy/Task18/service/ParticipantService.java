package com.AlexTsoy.Task18.service;

import com.AlexTsoy.Task18.entity.Participant;
import com.AlexTsoy.Task18.exceptions.EntityNotFoundException;
import com.AlexTsoy.Task18.repositories.ParticipantRepository;
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
public class ParticipantService {
    private final ParticipantRepository participantRepository;

    public List<Participant> findAll() {
        return participantRepository.findAll();
    }

    public Participant findById(int id) {
        return participantRepository.findById(id).orElseThrow(() -> {
            log.error("Entity not found");
            return new EntityNotFoundException();
        });
    }

    @Transactional
    public Participant save(Participant participant) {
        return participantRepository.save(participant);
    }

    @Transactional
    public void delete(int id) {
        participantRepository.deleteById(id);
    }
}
