package com.ryanair.flights.client.domain;

import lombok.Data;

import java.time.LocalTime;

@Data
public class Flight {

    Integer number;
    LocalTime departureTime;
    LocalTime arrivalTime;
}
