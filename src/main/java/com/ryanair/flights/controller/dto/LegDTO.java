package com.ryanair.flights.controller.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LegDTO {

    String departureAirport;
    String arrivalAirport;
    LocalDateTime departureDateTime;
    LocalDateTime arrivalDateTime;
}
