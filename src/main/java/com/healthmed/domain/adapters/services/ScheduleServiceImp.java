package com.healthmed.domain.adapters.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthmed.domain.AppointmentSchedule;
import com.healthmed.domain.Doctor;
import com.healthmed.domain.Patient;
import com.healthmed.domain.ports.interfaces.ScheduleServicePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
public class ScheduleServiceImp implements ScheduleServicePort {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    public ScheduleServiceImp(ObjectMapper objectMapper, EmailService emailService) {
        this.objectMapper = objectMapper;
        this.emailService = emailService;
    }

    @SqsListener("consulta")
    @PostConstruct
    public void loadMessageFromSQS(String message) {
        AppointmentSchedule appointmentSchedule;
        try {
            log.info("recebendo mensagem da fila de status de pedido: {}", message);
            appointmentSchedule = objectMapper.readValue(message, AppointmentSchedule.class);
            emailService.sendEmail(appointmentSchedule);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
