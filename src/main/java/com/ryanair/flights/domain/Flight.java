package com.ryanair.flights.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Flight {

    String departure;
    String layover;
    String arrival;


}
