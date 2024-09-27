package com.healthmed.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthmed.domain.adapters.services.EmailService;
import com.healthmed.domain.adapters.services.ScheduleServiceImp;
import com.healthmed.domain.ports.interfaces.ScheduleServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    ScheduleServicePort scheduleServicePort(ObjectMapper objectMapper, EmailService emailService) {
        return new ScheduleServiceImp(objectMapper, emailService);
    }

}
