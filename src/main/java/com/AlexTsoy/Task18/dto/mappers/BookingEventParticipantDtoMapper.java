package com.AlexTsoy.Task18.dto.mappers;

import com.AlexTsoy.Task18.dto.BookingEventParticipantDto;
import com.AlexTsoy.Task18.entity.Booking;
import com.AlexTsoy.Task18.entity.Event;
import com.AlexTsoy.Task18.entity.Participant;
import org.springframework.stereotype.Component;

@Component
public class BookingEventParticipantDtoMapper {
    public BookingEventParticipantDto mapToBookingDto(Booking booking, Participant participant, Event event) {
        BookingEventParticipantDto dto = new BookingEventParticipantDto();
        dto.setId(booking.getId());
        dto.setAmountSlots(booking.getAmountSlots());
        dto.setEventId(event.getId());
        dto.setEventName(event.getName());
        dto.setEventDate(event.getDate());
        dto.setParticipantId(participant.getId());
        dto.setParticipantName(participant.getFirstName() + " " + participant.getLastName());
        dto.setParticipantPhone(participant.getPhoneNumber());
        return dto;
    }

    public Booking mapToBooking(BookingEventParticipantDto dto) {
        Booking booking = new Booking();
        Event event = new Event();
        Participant participant = new Participant();
        booking.setId(dto.getId());
        booking.setAmountSlots(dto.getAmountSlots());
        event.setId(dto.getEventId());
        booking.setEvent(event);
        participant.setId(dto.getParticipantId());
        booking.setParticipant(participant);
        return booking;
    }
}
