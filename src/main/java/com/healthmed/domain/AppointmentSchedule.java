package com.healthmed.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Setter
@Getter
public class AppointmentSchedule {

    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isBooked;
    private Doctor doctor;
    private Patient patient;

    public AppointmentSchedule() {
    }

    public AppointmentSchedule(Long id, LocalDate date, LocalTime startTime, LocalTime endTime, boolean isBooked, Doctor doctor, Patient patient) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isBooked = isBooked;
        this.doctor = doctor;
        this.patient = patient;
    }


}
