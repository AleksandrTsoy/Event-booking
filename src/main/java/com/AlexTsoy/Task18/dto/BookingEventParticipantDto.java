package com.AlexTsoy.Task18.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingEventParticipantDto {
    private int id;
    @Min(value = 1, message = "Amount slots should be more than 0")
    private int amountSlots;
    @Min(value = 1)
    private int eventId;
    private String eventName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime eventDate;
    @Min(value = 1)
    private int participantId;
    private String participantName;
    private String participantPhone;
}
