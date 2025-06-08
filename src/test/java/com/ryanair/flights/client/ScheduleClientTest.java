package com.ryanair.flights.client;

import com.ryanair.flights.client.domain.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ScheduleClientTest {

    @InjectMocks
    ScheduleClient scheduleClient;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(scheduleClient, "url", "https://services-api.ryanair.com/timtbl/3/schedules");
    }

    @Test
    void getRoutes_ok() {
        Schedule schedules = scheduleClient.getSchedules(7, 2025, "MAD", "BGY");
        assertNotNull(schedules);

    }
}
