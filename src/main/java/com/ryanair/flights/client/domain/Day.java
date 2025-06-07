package com.ryanair.flights.client.domain;

import lombok.Data;

import java.util.List;

@Data
public class Day {

    Integer day;
    List<Flight> flights;
}
