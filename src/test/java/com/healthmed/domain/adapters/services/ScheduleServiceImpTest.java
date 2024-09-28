package com.healthmed.domain.adapters.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthmed.domain.AppointmentSchedule;
import com.healthmed.domain.Doctor;
import com.healthmed.domain.Patient;
import com.healthmed.domain.ports.interfaces.ScheduleServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ScheduleServiceImpTest {

    @InjectMocks
    private ScheduleServiceImp scheduleService;

    @Mock
    private EmailService emailService;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadMessageFromSQS_Success() throws Exception {
        // Given
        String message = "{\"id\":1,\"date\":\"2024-09-30T10:00:00\",\"doctorId\":1,\"patientId\":1}";
        AppointmentSchedule appointmentSchedule = new AppointmentSchedule();
        appointmentSchedule.setId(1L);
        appointmentSchedule.setDate(LocalDate.now());
        appointmentSchedule.setDoctor(new Doctor());
        appointmentSchedule.setPatient(new Patient());

        when(objectMapper.readValue(message, AppointmentSchedule.class)).thenReturn(appointmentSchedule);

        // When
        scheduleService.loadMessageFromSQS(message);

        // Then
        verify(emailService, times(1)).sendEmail(appointmentSchedule);
    }

    @Test
    public void testLoadMessageFromSQS_JsonProcessingException() throws Exception {
        // Given
        String message = "invalid json";

        when(objectMapper.readValue(message, AppointmentSchedule.class)).thenThrow(new JsonProcessingException("Error") {});

        // When / Then
        assertThrows(RuntimeException.class, () -> scheduleService.loadMessageFromSQS(message));
    }
}
