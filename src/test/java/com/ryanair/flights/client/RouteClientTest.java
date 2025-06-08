package com.ryanair.flights.client;

import com.ryanair.flights.client.domain.Route;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RouteClientTest {

    @InjectMocks
    RouteClient routeClient;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(routeClient, "url", "https://services-api.ryanair.com/views/locate/3/routes");
    }

    @Test
    void getRoutes_ok() {
        List<Route> routes = routeClient.getRoutes();
        assertEquals(routes.isEmpty(), false);

    }
}
