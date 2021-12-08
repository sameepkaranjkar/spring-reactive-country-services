package com.app.country.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.WebServiceException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.app.country.configuration.AppConfig;
import com.app.country.error.handler.ClientException;
import com.app.country.error.handler.CountryNotFoundException;
import com.app.country.model.Countries;
import com.app.country.model.Country;
import com.app.country.response.CountriesResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * The Class CountryAPIServiceImpl.
 */
@Service
@Slf4j
public class CountryAPIServiceImpl implements CountryAPIService {
	private static final String ACCESS_KEY = "access_key";
	private static final String NAME = "name";

	@Autowired
	WebClient webClient;

	@Autowired
	AppConfig config;

	/**
	 * Gets the all countries.
	 *
	 * @param accessKey the access key
	 * @return the all countries
	 */
	@Override
	public Mono<CountriesResponse> getAllCountries(String accessKey) {
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put(ACCESS_KEY, accessKey);
		return generateResponse(webClient.get().uri(config.getCountryAllURL(), uriVariables).retrieve()
				.onStatus(HttpStatus::is4xxClientError, this::handle4xxErrorResponse)
				.onStatus(HttpStatus::is5xxServerError, this::handle5xxErrorResponse)
				.bodyToMono(new ParameterizedTypeReference<List<Countries>>() {
				}).log());
	}

	private Mono<CountriesResponse> generateResponse(Mono<List<Countries>> mono) {
		return mono.flatMap(country -> {
			CountriesResponse countriesResponse = new CountriesResponse();
			countriesResponse.setCountries(country);
			return Mono.just(countriesResponse);
		});
	}

	/**
	 * Gets the country by name.
	 *
	 * @param name       the name
	 * @param access_key the access key
	 * @return the country by name
	 */
	@Override
	public Mono<Country> getCountryByName(String name, String accessKey) {
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put(NAME, name);
		uriVariables.put(ACCESS_KEY, accessKey);
		return webClient.get().uri(config.getCountryByNameURL(), uriVariables).retrieve()
				.onStatus(HttpStatus::is4xxClientError, this::handle4xxErrorResponse)
				.onStatus(HttpStatus::is5xxServerError, this::handle5xxErrorResponse).bodyToFlux(Country.class).log()
				.filter(response -> (response.getName().equalsIgnoreCase(name)
						|| StringUtils.containsIgnoreCase(response.getName(), name)))
				.next();
	}

	public Mono<RuntimeException> handle4xxErrorResponse(ClientResponse clientResponse) {
		Mono<String> errorResponse = clientResponse.bodyToMono(String.class);
		return errorResponse.flatMap(message -> {
			log.error("....ErrorResponse Code is {}  and the exception message is {}", clientResponse.rawStatusCode(),
					message);
			if (clientResponse.rawStatusCode() == HttpStatus.NOT_FOUND.value()) {
				throw new CountryNotFoundException(message);
			} else {
				throw new ClientException(message);
			}

		});
	}

	public Mono<RuntimeException> handle5xxErrorResponse(ClientResponse clientResponse) {
		Mono<String> errorResponse = clientResponse.bodyToMono(String.class);
		return errorResponse.flatMap(message -> {
			log.error("....ErrorResponse Code is {}  and the exception message is {}", clientResponse.rawStatusCode(),
					message);
			throw new WebServiceException(message);
		});
	}
}
