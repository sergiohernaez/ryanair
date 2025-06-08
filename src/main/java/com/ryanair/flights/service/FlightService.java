package com.ryanair.flights.service;

import com.ryanair.flights.client.RouteClient;
import com.ryanair.flights.client.ScheduleClient;
import com.ryanair.flights.client.domain.Day;
import com.ryanair.flights.client.domain.Flight;
import com.ryanair.flights.client.domain.Route;
import com.ryanair.flights.client.domain.Schedule;
import com.ryanair.flights.controller.dto.FlightDTO;
import com.ryanair.flights.controller.dto.LegDTO;
import com.ryanair.flights.domain.Itinerary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightService {

    private final RouteClient routeClient;
    private final ScheduleClient scheduleClient;

    public List<FlightDTO> getFlights(String departure, String arrival, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
        List<Route> routes = getRoutes();
        List<FlightDTO> flights = new ArrayList<>();
        List<FlightDTO> simple = getSimpleFlights(routes, departure, arrival, departureDateTime, arrivalDateTime);
        List<FlightDTO> combined = getCombinedFlights(routes, departure, arrival, departureDateTime, arrivalDateTime);

        flights.addAll(simple);
        flights.addAll(combined);
        return flights;
    }



    private List<FlightDTO> getSimpleFlights(List<Route> routes, String departure, String arrival, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
        List<FlightDTO> flights = new ArrayList<>();
        Itinerary itinerary = null;
        for(Route route: routes) {
            if(route.getAirportFrom().equals(departure) && route.getAirportTo().equals(arrival)) {
                itinerary = Itinerary.builder().departure(departure).arrival(arrival).build();
                break;
            }
        }
        if(Objects.isNull(itinerary))
            return new ArrayList<>();

        //TODO salida puede tener un mes distinto de llegada
        if(departureDateTime.getMonth().equals(arrivalDateTime.getMonth())) {
            Schedule schedules = getSchedules(departure, arrival, departureDateTime);
            for(Day day: schedules.getDays()) {
                if(day.equals(departureDateTime.getDayOfMonth())) {
                    for(Flight flight: day.getFlights()) {
                        if(flight.getDepartureTime().isAfter(departureDateTime.toLocalTime()) &&
                            flight.getArrivalTime().isBefore(arrivalDateTime.toLocalTime())) {
                            //MATCH
                            LegDTO legDTO = LegDTO.builder()
                                    .departureDateTime(LocalDateTime.of(departureDateTime.toLocalDate(), flight.getDepartureTime()))
                                    .arrivalDateTime(LocalDateTime.of(arrivalDateTime.toLocalDate(), flight.getArrivalTime()))
                                    .departureAirport(departure)
                                    .arrivalAirport(arrival)
                                    .build();
                            flights.add(FlightDTO.builder().stops(0).legs(List.of(legDTO)).build());
                        }
                    }
                }
            }
        }


        //mapear
        return flights;
    }

    private List<FlightDTO> getCombinedFlights(List<Route> routes, String departure, String arrival, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
        //TODO
        //sacar los datos y combinarlos en el dto
        List<Itinerary> itineraries = new ArrayList<>();
        for(Route route: routes) {
            if(route.getAirportFrom().equals(departure)) {
                for(Route route2: routes) {
                    if(route2.getAirportFrom().equals(route.getAirportTo()) && route2.getAirportTo().equals(arrival)) {
                        //MATCH
                        itineraries.add(Itinerary.builder().departure(departure).layover(route2.getAirportFrom()).arrival(arrival).build());
                    }
                }
            }
        }

        //para cada combinacion, sacar las schedules de cada trayecto, y construir la respuesta
        return List.of(FlightDTO.builder().build());
    }

    private List<Route> getRoutes() {
        return routeClient.getRoutes();
    }

    private Schedule getSchedules(String departure, String arrival, LocalDateTime date) {
        return scheduleClient.getSchedules(date.getMonthValue(), date.getYear(), departure, arrival);
    }
}
