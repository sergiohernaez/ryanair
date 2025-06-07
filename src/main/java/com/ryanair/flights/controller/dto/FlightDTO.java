package com.ryanair.flights.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FlightDTO {

    Integer stops;
    List<LegDTO> legs;
}
