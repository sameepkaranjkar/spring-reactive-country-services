package com.app.country.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.app.country.api.service.CountryAPIService;
import com.app.country.model.Country;
import com.app.country.response.CountriesResponse;

import reactor.core.publisher.Mono;

/**
 * Service class for getting Country information using Open external API
 * 
 * @author SameepK
 */

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryAPIService countryAPIService;

	/**
	 * Gets the details of country with given name
	 * 
	 * @param name : Name of the country
	 * @param accessKey      the api key
	 * @return Countries details
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws ExecutionException
	 * @throws RestClientException
	 */
	@Override
	public Mono<Country> getCountryByName(String name, String accessKey){
		return countryAPIService.getCountryByName(name, accessKey);
	}

	/**
	 * Gets the all countries details
	 * 
	 * @param accessKey the api key
	 * @return Countries details
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws ExecutionException
	 * @throws RestClientException
	 */
	@Override
	public Mono<CountriesResponse> getAllCountries(String accessKey){
		return countryAPIService.getAllCountries(accessKey);
	}
}
