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
        // with delays between 4.95s - 5.95s, every other request would consistently fail, throwing this ResponseClosedException.
        runExperiment(4950L);
//        runExperiment(5450L);
        runExperiment(5950L);
        // when the delayMillis is set to be even larger, there are consistent warnings showing up among requests
        runExperiment(6100L);
    }

    private void runExperiment(Long delayMillis) throws InterruptedException {
        System.out.printf("%n Running 10 request with %d ms delay between requests %n %n", delayMillis);
        Clock clock = Clock.systemUTC();
        URI uri = UriBuilder.of("/").build();
        MutableHttpRequest<Object> req = HttpRequest.GET(uri)
                .header(AUTHORIZATION, FAKE_TOKEN);

        for(int i = 0; i <= 10; i++ ) {
            long start = clock.instant().toEpochMilli();
            HttpResponse<?> result = null;
            try {
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
