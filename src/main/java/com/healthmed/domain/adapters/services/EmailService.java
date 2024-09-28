package com.healthmed.domain.adapters.services;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.healthmed.domain.AppointmentSchedule;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final AmazonSimpleEmailService sesClient = AmazonSimpleEmailServiceClientBuilder.standard().build();

    public void sendEmail(AppointmentSchedule appointmentSchedule) {
        String body = String.format("Olá, Dr. %s!\nVocê tem uma nova consulta marcada!\nPaciente: %s.\nData e horário: %s às %s.",
                appointmentSchedule.getDoctor().getName(),
                appointmentSchedule.getPatient().getName(),
                appointmentSchedule.getDate(),
                appointmentSchedule.getStartTime());


        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new com.amazonaws.services.simpleemail.model.Destination().withToAddresses(appointmentSchedule.getDoctor().getEmail()))
                .withMessage(new com.amazonaws.services.simpleemail.model.Message()
                        .withBody(new com.amazonaws.services.simpleemail.model.Body()
                                .withHtml(new com.amazonaws.services.simpleemail.model.Content().withCharset("UTF-8").withData(body)))
                        .withSubject(new com.amazonaws.services.simpleemail.model.Content().withCharset("UTF-8").withData("Health&Med - Nova consulta agendada")))
                .withSource("mateus.rodrigues.alv@gmail.com");

        sesClient.sendEmail(request);
    }
}
