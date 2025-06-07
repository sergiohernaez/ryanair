package com.ryanair.flights.client.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class Flight {

    Integer number;
    LocalTime departureTime;
    LocalTime arrivalTime;
}
