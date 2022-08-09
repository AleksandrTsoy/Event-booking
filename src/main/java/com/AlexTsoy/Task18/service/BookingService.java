package com.AlexTsoy.Task18.service;

import com.AlexTsoy.Task18.dto.BookingEventParticipantDto;
import com.AlexTsoy.Task18.dto.mappers.BookingEventParticipantDtoMapper;
import com.AlexTsoy.Task18.entity.Booking;
import com.AlexTsoy.Task18.entity.Event;
import com.AlexTsoy.Task18.entity.Participant;
import com.AlexTsoy.Task18.exceptions.EntityNotFoundException;
import com.AlexTsoy.Task18.exceptions.OverBookingException;
import com.AlexTsoy.Task18.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EventService eventService;

    private final ParticipantService participantService;

    private final BookingEventParticipantDtoMapper dtoMapper;

    public List<BookingEventParticipantDto> findAll() {
        List<BookingEventParticipantDto> dto = new ArrayList<>();
        for (Booking booking : bookingRepository.findAll()) {
            Participant participant = participantService.findById(booking.getParticipant().getId());
            Event event = eventService.findById(booking.getEvent().getId());
            dto.add(dtoMapper.mapToBookingDto(booking, participant, event));
        }
        return dto;
    }

    public BookingEventParticipantDto findOne(int id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> {
            log.error("Entity not found");
            return new EntityNotFoundException();
        });
        Participant participant = participantService.findById(booking.getParticipant().getId());
        Event event = eventService.findById(booking.getEvent().getId());
        return dtoMapper.mapToBookingDto(booking, participant, event);
    }

    @Transactional
    public Booking save(BookingEventParticipantDto dto) {
        Booking booking = dtoMapper.mapToBooking(dto);
        Booking newBooking;
        Event event = eventService.findById(booking.getEvent().getId());
        if (event.getAmountFreeSlots() >= booking.getAmountSlots()) {
            newBooking = bookingRepository.save(booking);
            event.setAmountFreeSlots(event.getAmountFreeSlots() - booking.getAmountSlots());
            eventService.edit(event);
        } else {
            log.error("Overbooking");
            throw new OverBookingException();
        }
        return newBooking;
    }

    @Transactional
    public void delete(int id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> {
            log.error("Entity not found");
            return new EntityNotFoundException();
        });
        bookingRepository.deleteById(id);
        Event event = eventService.findById(booking.getEvent().getId());
        event.setAmountFreeSlots(event.getAmountFreeSlots() + booking.getAmountSlots());
        eventService.edit(event);
    }

    @Transactional
    public void edit(BookingEventParticipantDto dto) {
        Booking updateBooking = dtoMapper.mapToBooking(dto);
        Booking booking = bookingRepository.findById(updateBooking.getId()).orElseThrow(() -> {
            log.error("Entity not found");
            return new EntityNotFoundException();
        });
        int newSlots = updateBooking.getAmountSlots() - booking.getAmountSlots();
        booking.setAmountSlots(updateBooking.getAmountSlots());
        Event event = eventService.findById(booking.getEvent().getId());
        if (event.getAmountFreeSlots() >= newSlots) {
            event.setAmountFreeSlots(event.getAmountFreeSlots() - newSlots);
            eventService.edit(event);
            bookingRepository.save(booking);
        } else {
            log.error("Overbooking");
            throw new OverBookingException();
        }
    }
}
