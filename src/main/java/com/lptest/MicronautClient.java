package com.lptest;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.HttpClientConfiguration;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

import java.time.Duration;
import java.util.Optional;

@Client( value = "https://micronaut.io/launch", configuration = MicronautClient.ClientConfig.class)
public interface MicronautClient {

    @Get
    Publisher<HttpResponse<String>> get();

    @Singleton
    class ClientConfig extends HttpClientConfiguration {

        /**
         * Obtains the connection pool configuration.
         *
         * @return The connection pool configuration.
         */
        private final HttpClientConfiguration configuration;

        @Inject
        public ClientConfig(HttpClientConfiguration configuration) {
            this.configuration = configuration;
        }
        @Override
        public ConnectionPoolConfiguration getConnectionPoolConfiguration() {
            return configuration.getConnectionPoolConfiguration();
        }
        /*
        Suspecting that it doesn't read the ReadTimeout below -- it persists reading the default value of 10s
         */
        @Override
        public Optional<Duration> getReadTimeout() {
            return Optional.of(Duration.ofSeconds(8));
        }
//        @Override
//        public Optional<Duration> getConnectionPoolIdleTimeout() {
//            return Optional.of(Duration.ofSeconds(5));
//        }
    }
}
