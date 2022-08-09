package com.AlexTsoy.Task18.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
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
    @NotEmpty(message = "Email should not be empty.")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

}
