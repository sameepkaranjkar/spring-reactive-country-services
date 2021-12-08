package com.app.country.service;

import com.app.country.model.Country;
import com.app.country.response.CountriesResponse;

import reactor.core.publisher.Mono;

public interface CountryService {
	public Mono<CountriesResponse> getAllCountries(String accessKey);

	public Mono<Country> getCountryByName(String name, String accessKey);
}
