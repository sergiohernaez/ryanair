package com.ryanair.flights.service;

import com.ryanair.flights.client.RouteClient;
import com.ryanair.flights.client.ScheduleClient;
import com.ryanair.flights.client.domain.Route;
import com.ryanair.flights.client.domain.Schedule;
import com.ryanair.flights.controller.dto.FlightDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightService {

    private final RouteClient routeClient;
    private final ScheduleClient scheduleClient;

    public List<FlightDTO> getFlights(String departure, String arrival, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
        log.debug(String.valueOf(departureDateTime));
        List<Route> routes = getRoutes();


        List<FlightDTO> simple = getSimpleFlights(routes, departure, arrival, departureDateTime, arrivalDateTime);
        List<FlightDTO> combined = getCombinedFlights(routes, departure, arrival, departureDateTime, arrivalDateTime);


        return List.of(new FlightDTO());
    }



    private List<FlightDTO> getSimpleFlights(List<Route> routes, String departure, String arrival, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
        //TODO
        //sacar los datos y combinarlos en el dto
        List<Schedule> schedules = getSchedules(departure, arrival, departureDateTime, arrivalDateTime);
        return List.of(new FlightDTO());
    }

    private List<FlightDTO> getCombinedFlights(List<Route> routes, String departure, String arrival, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
        //TODO
        //sacar los datos y combinarlos en el dto
        for(Route route: routes) {
            if(route.getAirportFrom().equals(departure)) {
                for(Route route2: routes) {
                    if(route2.getAirportFrom().equals(route.getAirportTo()) && route2.getAirportTo().equals(arrival)) {
                        //MATCH

                    }
                }
            }
        }
        return List.of(new FlightDTO());
    }

    private List<Route> getRoutes() {
        return routeClient.getRoutes();
    }

    private List<Schedule> getSchedules(String departure, String arrival, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
        //TODO
        Integer month = 7;
        Integer year = 2025;
        return scheduleClient.getSchedules(month, year, departure, arrival);
    }
}
