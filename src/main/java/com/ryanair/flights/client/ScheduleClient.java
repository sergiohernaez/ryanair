package com.ryanair.flights.client;

import com.ryanair.flights.client.domain.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleClient {

    @Value("${scheduleUrl}")
    private final String url;

    public Schedule getSchedules(Integer month, Integer year, String departure, String arrival) {

        WebClient client = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
                .build();

        Mono<Schedule> response = client.get()
                .uri(url + "/" + departure + "/" + arrival + "/years/" + year + "/months/" + month)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Schedule>() {});

        Schedule schedule = response.block();
        return schedule;
    }
}
