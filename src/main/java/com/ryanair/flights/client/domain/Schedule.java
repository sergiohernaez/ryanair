package com.ryanair.flights.client.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Schedule {

    Integer month;
    List<Day> days;
}
