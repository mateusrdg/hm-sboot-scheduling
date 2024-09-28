package com.healthmed.domain.adapters.services;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.healthmed.domain.AppointmentSchedule;
import com.healthmed.domain.Doctor;
import com.healthmed.domain.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@Disabled(" ")
public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private AmazonSimpleEmailService sesClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendEmail() {
        // Given
        Doctor doctor = new Doctor();
        doctor.setName("João");
        doctor.setEmail("joao@example.com");

        Patient patient = new Patient();
        patient.setName("Maria");

        AppointmentSchedule appointmentSchedule = new AppointmentSchedule();
        appointmentSchedule.setDoctor(doctor);
        appointmentSchedule.setPatient(patient);
        appointmentSchedule.setDate(LocalDate.now()); // Exemplo de data
        appointmentSchedule.setStartTime(LocalTime.now()); // Exemplo de horário

        // When
        emailService.sendEmail(appointmentSchedule);

        // Then
        ArgumentCaptor<SendEmailRequest> requestCaptor = ArgumentCaptor.forClass(SendEmailRequest.class);
        verify(sesClient, times(1)).sendEmail(requestCaptor.capture());

        SendEmailRequest capturedRequest = requestCaptor.getValue();
        String expectedBody = String.format("Olá, Dr. %s!\nVocê tem uma nova consulta marcada!\nPaciente: %s.\nData e horário: %s às %s.",
                doctor.getName(),
                patient.getName(),
                appointmentSchedule.getDate(),
                appointmentSchedule.getStartTime());

        // Verificando se o e-mail foi enviado para o endereço correto
        assertEquals("joao@example.com", capturedRequest.getDestination().getToAddresses().get(0));

        // Verificando o corpo do e-mail
        assertEquals(expectedBody, capturedRequest.getMessage().getBody().getHtml().getData());
        // Verificando o assunto do e-mail
        assertEquals("Health&Med - Nova consulta agendada", capturedRequest.getMessage().getSubject().getData());
    }
}
