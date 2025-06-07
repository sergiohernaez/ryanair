package com.ryanair.flights.client;

import com.ryanair.flights.client.domain.Schedule;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduleClient {

    public List<Schedule> getSchedules(Integer month, Integer year, String departure, String arrival) {

        //TODO
        return List.of(new Schedule());
    }
}
