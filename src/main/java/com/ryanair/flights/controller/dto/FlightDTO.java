package com.ryanair.flights.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class FlightDTO {

    Integer stops;
    List<LegDTO> legs;
}
