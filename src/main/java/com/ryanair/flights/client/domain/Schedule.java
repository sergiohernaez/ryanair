package com.ryanair.flights.client.domain;

import lombok.Data;

import java.util.List;

@Data
public class Schedule {

    Integer month;
    List<Day> days;
}
