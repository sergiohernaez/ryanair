package com.ryanair.flights.utils;

import com.ryanair.flights.client.domain.Day;
import com.ryanair.flights.client.domain.Flight;
import com.ryanair.flights.client.domain.Route;
import com.ryanair.flights.client.domain.Schedule;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static List<Route> getRoutes() {
        return List.of(
                Route.builder().airportFrom("MAD").airportTo("DUB").build(),
                Route.builder().airportFrom("MAD").airportTo("STN").build(),
                Route.builder().airportFrom("STN").airportTo("DUB").build()
        );
    }

    public static Schedule getSchedulesMadridDublin() {
        return getSchedule(31, 10, 12);
    }

    public static Schedule getSchedulesMadridLondon() {
        return getSchedule(31, 11, 12);
    }

    public static Schedule getSchedulesLondonDublin() {
        return getSchedule(31, 15, 16);
    }

    public static Schedule getSchedulesEmpty() {
        return Schedule.builder().build();
    }

    private static Schedule getSchedule(Integer numberOfDays, Integer startHour, Integer endHour) {
        Schedule schedule = Schedule.builder().build();
        List<Day> days = new ArrayList<>();
        for(int i = 1; i<=numberOfDays; i++){
            days.add(Day.builder().day(i).flights(List.of(
                    Flight.builder()
                            .number(1)
                            .departureTime(LocalTime.of(startHour,0))
                            .arrivalTime(LocalTime.of(endHour,0))
                            .build()
            )).build());
        }
        schedule.setDays(days);
        return schedule;
    }
}
