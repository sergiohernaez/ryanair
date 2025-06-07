package com.ryanair.flights.client.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Day {

    Integer day;
    List<Flight> flights;
}
