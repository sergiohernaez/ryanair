package com.ryanair.flights.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Itinerary {

    String departure;
    String layover;
    String arrival;


}
