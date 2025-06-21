package com.ryanair.flights.service;

import com.ryanair.flights.client.RouteClient;
import com.ryanair.flights.client.ScheduleClient;
import com.ryanair.flights.controller.dto.FlightDTO;
import com.ryanair.flights.controller.dto.LegDTO;
import com.ryanair.flights.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @Mock
    RouteClient routeClient;

    @Mock
    ScheduleClient scheduleClient;

    @InjectMocks
    FlightService flightService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(flightService, "layoverHours", 2);
    }

    @Test
    void getFlights_ok() {

        LocalDate date = LocalDate.of(2025, Month.DECEMBER, 1);

        when(routeClient.getRoutes()).thenReturn(TestUtils.getRoutes());
        when(scheduleClient.getSchedules(12,2025,"MAD","DUB")).thenReturn(TestUtils.getSchedulesMadridDublin());
        when(scheduleClient.getSchedules(12,2025,"MAD","STN")).thenReturn(TestUtils.getSchedulesMadridLondon());
        when(scheduleClient.getSchedules(12,2025,"STN","DUB")).thenReturn(TestUtils.getSchedulesLondonDublin());

        List<FlightDTO> flights = flightService.getFlights(
                "MAD",
                "DUB",
                date.atTime(8,0),
                date.atTime(22,0));

        assertEquals(2, flights.size());

        for(FlightDTO flight: flights) {
            if(flight.getStops().equals(0)) {
                assertEquals(1, flight.getLegs().size());
                LegDTO leg = flight.getLegs().getFirst();
                assertEquals("MAD",leg.getDepartureAirport());
                assertEquals("DUB",leg.getArrivalAirport());
                assertEquals(date.atTime(10,0),leg.getDepartureDateTime());
                assertEquals(date.atTime(12,0),leg.getArrivalDateTime());
            } else {
                assertEquals(2, flight.getLegs().size());
                LegDTO firstLeg = flight.getLegs().get(0);
                assertEquals("MAD",firstLeg.getDepartureAirport());
                assertEquals("STN",firstLeg.getArrivalAirport());
                assertEquals(date.atTime(11,0),firstLeg.getDepartureDateTime());
                assertEquals(date.atTime(12,0),firstLeg.getArrivalDateTime());

                LegDTO secondLeg = flight.getLegs().get(1);
                assertEquals("STN",secondLeg.getDepartureAirport());
                assertEquals("DUB",secondLeg.getArrivalAirport());
                assertEquals(date.atTime(15,0),secondLeg.getDepartureDateTime());
                assertEquals(date.atTime(16,0),secondLeg.getArrivalDateTime());
            }
        }
    }

}
