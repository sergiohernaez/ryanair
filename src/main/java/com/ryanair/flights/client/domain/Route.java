package com.ryanair.flights.client.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Route {

    String airportFrom;
    String airportTo;
    String connectingAirport;
    Boolean newRoute;
    Boolean seasonalRoute;
    String operator;
    String group;
}
