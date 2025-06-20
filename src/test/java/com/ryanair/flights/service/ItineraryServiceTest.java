package com.ryanair.flights.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class ItineraryServiceTest {

    @InjectMocks
    FlightService flightService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(flightService, "layoverHours", 2);
    }


}
