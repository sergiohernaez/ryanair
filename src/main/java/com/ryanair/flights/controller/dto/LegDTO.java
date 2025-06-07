package com.ryanair.flights.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LegDTO {

    String departureAirport;
    String arrivalAirport;
    LocalDateTime departureDateTime;
    LocalDateTime arrivalDateTime;
}
