package com.AlexTsoy.Task18.controllers;

import com.AlexTsoy.Task18.entity.Participant;
import com.AlexTsoy.Task18.exceptions.EntityErrorResponse;
import com.AlexTsoy.Task18.exceptions.EntityNotSaveExceprion;
import com.AlexTsoy.Task18.service.ParticipantService;
import com.AlexTsoy.Task18.util.validators.ParticipantValidator;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/participants")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParticipantsController {
    private final ParticipantService participantService;
    private final ParticipantValidator participantValidator;

    @ApiOperation("Get all participants")
    @GetMapping
    public List<Participant> getAllParticipants() {
        return participantService.findAll();

    }

    @ApiOperation("Get participant")
    @GetMapping("/{id}")
    public Participant getParticipantById(@PathVariable("id") int id) {
        return participantService.findById(id);
    }

    @ApiOperation("Create participant")
    @PostMapping()
    public Participant create(@RequestBody @Valid Participant participant, BindingResult bindingResult) {
        participantValidator.validate(participant, bindingResult);
        return participantService.save(participant);
    }

    @ApiOperation("Remove participant")
    @DeleteMapping("/{id}")
    public void removeParticipant(@PathVariable("id") int id) {
        participantService.delete(id);
    }

    @ApiOperation("Update participant")
    @PutMapping("/{id}")
    public void update(@PathVariable("id") int id, @RequestBody @Valid Participant participant, BindingResult bindingResult) {
        validator(participant, bindingResult);
        participant.setId(id);
        participantService.save(participant);
    }

    private void validator(Participant participant, BindingResult bindingResult) {
        participantValidator.validate(participant, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
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
