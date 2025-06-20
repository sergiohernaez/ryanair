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
        if (validateDatesAreSameDay(departureDateTime, arrivalDateTime)) {
            List<Route> routes = getRoutes();
            List<FlightDTO> flights = new ArrayList<>();
            List<FlightDTO> simple = getSimpleFlights(routes, departure, arrival, departureDateTime, arrivalDateTime);
            List<FlightDTO> combined = getCombinedFlights(routes, departure, arrival, departureDateTime, arrivalDateTime);

            flights.addAll(simple);
            flights.addAll(combined);
            return flights;
        }
        return new ArrayList<>();
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

        if(departureDateTime.getMonth().equals(arrivalDateTime.getMonth())) {
            Schedule schedules = getSchedules(departure, arrival, departureDateTime);
            for(Day day: schedules.getDays()) {
                if(day.getDay().equals(departureDateTime.getDayOfMonth())) {
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

        return flights;
    }

    private List<FlightDTO> getCombinedFlights(List<Route> routes, String departure, String arrival, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
        List<Itinerary> itineraries = new ArrayList<>();
        List<FlightDTO> flights = new ArrayList<>();

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

        if(itineraries.isEmpty())
            return new ArrayList<>();

        if(departureDateTime.getMonth().equals(arrivalDateTime.getMonth())) {
            for(Itinerary itinerary: itineraries) {
                //first flight
                Schedule schedulesFirst = getSchedules(itinerary.getDeparture(), itinerary.getLayover(), departureDateTime);
                for(Day day: schedulesFirst.getDays()) {
                    if(day.getDay().equals(departureDateTime.getDayOfMonth())) {
                        for(Flight flightFirst: day.getFlights()) {
                            if(flightFirst.getDepartureTime().isAfter(departureDateTime.toLocalTime()) &&
                                    flightFirst.getArrivalTime().isBefore(arrivalDateTime.toLocalTime())) {
                                //MATCH
                                LegDTO legFirstDTO = LegDTO.builder()
                                        .departureDateTime(LocalDateTime.of(departureDateTime.toLocalDate(), flightFirst.getDepartureTime()))
                                        .arrivalDateTime(LocalDateTime.of(arrivalDateTime.toLocalDate(), flightFirst.getArrivalTime()))
                                        .departureAirport(itinerary.getDeparture())
                                        .arrivalAirport(itinerary.getLayover())
                                        .build();

                                //second flight
                                Schedule schedulesSecond = getSchedules(itinerary.getLayover(), itinerary.getArrival(), departureDateTime);

                                for(Day daySecond: schedulesSecond.getDays()) {
                                    if (daySecond.getDay().equals(departureDateTime.getDayOfMonth())) {
                                        for (Flight flightSecond : daySecond.getFlights()) {
                                            if (flightSecond.getDepartureTime().isAfter(flightFirst.getArrivalTime().plusHours(2)) &&
                                                    flightSecond.getArrivalTime().isBefore(arrivalDateTime.toLocalTime())) {
                                                //MATCH
                                                LegDTO legSecondDTO = LegDTO.builder()
                                                        .departureDateTime(LocalDateTime.of(departureDateTime.toLocalDate(), flightSecond.getDepartureTime()))
                                                        .arrivalDateTime(LocalDateTime.of(arrivalDateTime.toLocalDate(), flightSecond.getArrivalTime()))
                                                        .departureAirport(itinerary.getLayover())
                                                        .arrivalAirport(itinerary.getArrival())
                                                        .build();

                                                //create combined flight and add
                                                flights.add(FlightDTO.builder().stops(1).legs(List.of(legFirstDTO, legSecondDTO)).build());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return flights;
    }

    private List<Route> getRoutes() {
        return routeClient.getRoutes();
    }

    private Schedule getSchedules(String departure, String arrival, LocalDateTime date) {
        return scheduleClient.getSchedules(date.getMonthValue(), date.getYear(), departure, arrival);
    }

    private boolean validateDatesAreSameDay(LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
        return departureDateTime.toLocalDate().isEqual(arrivalDateTime.toLocalDate());
    }
}
