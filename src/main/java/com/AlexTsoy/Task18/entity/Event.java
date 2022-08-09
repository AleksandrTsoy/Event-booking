package com.AlexTsoy.Task18.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int id;
    @NotEmpty(message = "Name should not be empty.")
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Future
    @NotNull(message = "Date should not be empty")
    @Column(name = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;
    @Min(value = 1, message = "Amount slots should be more than 0")
    @Column(name = "amount_slots")
    private int amountSlots;
    @Min(value = 1, message = "Amount slots should be more than 0")
    @Column(name = "amount_free_slots")
    private int amountFreeSlots;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Booking> bookings;
}
