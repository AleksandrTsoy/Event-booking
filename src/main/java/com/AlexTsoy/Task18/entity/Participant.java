package com.AlexTsoy.Task18.entity;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "participants")
@Getter
@Setter
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private int id;
    @NotEmpty(message = "First name should not be empty.")
    @Column(name = "first_name")
    private String firstName;
    @NotEmpty(message = "Last name should not be empty.")
    @Column(name = "last_name")
    private String lastName;
    @Pattern(regexp = "8+-+9+\\d{2}+-+\\d{3}+-+\\d{2}+-+\\d{2}", message = "Your phone should be in this format: 8-9XX-XXX-XX-XX")
    @NotEmpty(message = "Phone number should not be empty.")
    @Size(min = 15, max = 15)
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Booking> bookings;
}
