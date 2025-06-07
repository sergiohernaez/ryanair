package com.ryanair.flights.client;

import com.ryanair.flights.client.domain.Route;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class RouteClient {

    @Value("${client.route.url}")
    private String url;

    public List<Route> getRoutes() {
        WebClient client = WebClient.create(url);
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.POST);

        Mono<List<Route>> response = client.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Route>>() {});

        List<Route> routes = response.block();
        return routes;
    }
}
