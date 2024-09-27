package com.healthmed.domain.ports.interfaces;


public interface ScheduleServicePort {

    void loadMessageFromSQS(String message);
}
