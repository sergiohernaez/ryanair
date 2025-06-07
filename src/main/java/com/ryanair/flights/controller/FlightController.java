package com.ryanair.flights.controller;

import com.ryanair.flights.controller.dto.FlightDTO;
import com.ryanair.flights.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    @GetMapping("/interconnections")
    public List<FlightDTO> getFlights(@RequestParam("departure") String departure,
                                      @RequestParam("arrival") String arrival,
                                      @RequestParam("departureDateTime")
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureDateTime,
                                      @RequestParam("arrivalDateTime")
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalDateTime) {

        return flightService.getFlights(departure, arrival, departureDateTime, arrivalDateTime);

    }
}
