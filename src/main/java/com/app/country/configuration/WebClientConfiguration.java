package com.app.country.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * The Class WebClientConfiguration.
 */
@Configuration
@Slf4j
public class WebClientConfiguration {
	
	
    /**
     * Web client.
     *
     * @param builder the builder
     * @return the web client
     */
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
       return builder.filter(logRequest())
				.filter(logResponse()).build();
    }
    
    /**
     * Log request.
     *
     * @return the exchange filter function
     */
    private ExchangeFilterFunction logRequest() {
		return ExchangeFilterFunction.ofRequestProcessor(clinetRequest -> {
			log.info("....Request {} {}", clinetRequest.method(), clinetRequest.url());
			return Mono.just(clinetRequest);
		});
	}

	/**
	 * Log response.
	 *
	 * @return the exchange filter function
	 */
	private ExchangeFilterFunction logResponse() {
		return ExchangeFilterFunction.ofResponseProcessor(clinetResponse -> {
			log.info("....Response status code {} ", clinetResponse.statusCode());
			return Mono.just(clinetResponse);
		});
	}
}
