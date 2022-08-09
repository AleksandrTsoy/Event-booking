package com.AlexTsoy.Task18.util.validators;

import com.AlexTsoy.Task18.entity.Participant;
import com.AlexTsoy.Task18.repositories.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParticipantValidator implements Validator {

    private final ParticipantRepository participantRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return Participant.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Participant participant = (Participant) target;
        if (participantRepository.findParticipantByPhoneNumber(participant.getPhoneNumber()).isPresent()) {
            errors.rejectValue("phoneNumber", "", "This phone number is used");
        }
    }
}
