package com.AlexTsoy.Task18.controllers;

import com.AlexTsoy.Task18.dto.BookingEventParticipantDto;
import com.AlexTsoy.Task18.entity.Booking;
import com.AlexTsoy.Task18.exceptions.EntityErrorResponse;
import com.AlexTsoy.Task18.exceptions.EntityNotFoundException;
import com.AlexTsoy.Task18.exceptions.EntityNotSaveExceprion;
import com.AlexTsoy.Task18.exceptions.OverBookingException;
import com.AlexTsoy.Task18.service.BookingService;
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
@RequestMapping("/bookings")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookingsController {
    private final BookingService bookingService;

    @GetMapping()
    public List<BookingEventParticipantDto> getAllBookings()throws OverBookingException, EntityNotFoundException {
        return bookingService.findAll();

    }

    @GetMapping("/{id}")
    public BookingEventParticipantDto getBookingById(@PathVariable("id") int id) {
        return bookingService.findOne(id);
    }

    @PostMapping()
    public Booking create(@RequestBody @Valid BookingEventParticipantDto dto, BindingResult bindingResult) {
        validator(bindingResult);
        return bookingService.save(dto);
    }

    @DeleteMapping("/{id}")
    public void removeBooking(@PathVariable("id") int id) {
        bookingService.delete(id);
    }

    @PutMapping("/{id}")
    public void changeData(@PathVariable("id") int id, @RequestBody @Valid BookingEventParticipantDto dto, BindingResult bindingResult) throws OverBookingException, EntityNotFoundException {
        validator(bindingResult);
        dto.setId(id);
        bookingService.edit(dto);
    }

    private void validator(BindingResult bindingResult) {
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
