package com.lptest;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static org.mockito.Mockito.mock;

import java.net.URI;
import java.time.Clock;

@MicronautTest
class ReproduceIssueTest {
    public static final String AUTHORIZATION = "Authorization";
    private static final String FAKE_TOKEN = "Bearer asdf123secrettoken098jkl";
    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void runTest() throws InterruptedException {

        runExperiment(100L);
        runExperiment(1950L);
//        runExperiment(3500L);
        runExperiment(4900L);
    }

    private void runExperiment(Long delayMillis) throws InterruptedException {
        System.out.printf("%n Running 10 request with %d ms delay between requests %n %n", delayMillis);
        Clock clock = Clock.systemUTC();

        for(int i = 0; i <= 10; i++ ) {
            long start = clock.instant().toEpochMilli();
            HttpResponse<?> result = null;
            try {
                URI uri = UriBuilder.of("/").build();
                MutableHttpRequest<Object> req = HttpRequest.GET(uri)
                        .header(AUTHORIZATION, FAKE_TOKEN);
                result = client.toBlocking().exchange(req, String.class);
            } catch (HttpClientResponseException e) {
                result = e.getResponse();
            }
            long totalTime = clock.instant().toEpochMilli() - start;
            System.out.printf("   --%d ms  -- %s -- %s  %n", totalTime,  result.status().getCode(), result.getBody());
            System.out.printf("- Sleeping for %d ms -- %n", delayMillis);
            Thread.sleep(delayMillis);
        }
    }

}
