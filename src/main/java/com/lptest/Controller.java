package com.lptest;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@io.micronaut.http.annotation.Controller("/")
public class Controller {
    private MicronautClient micronautClient;

    @Inject
    public Controller(MicronautClient micronautClient) {
        this.micronautClient = micronautClient;
    }

    @Get
    Publisher<MutableHttpResponse<String>> get() {
        return Mono.from(micronautClient.get())
                .map(
                    it -> {
                        int charCount = it.body().length();
                        System.out.printf("  - Micronaut launch page has %d characters -- %n", charCount);
                        return HttpResponse.ok("Micronaut launch page has " + charCount);
                    }
                )
                .onErrorResume(it ->
                        Mono.just(HttpResponse.serverError(it.toString()))
                );
    }
}
