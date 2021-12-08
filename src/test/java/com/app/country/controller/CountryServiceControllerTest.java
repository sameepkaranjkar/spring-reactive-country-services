
package com.app.country.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.app.country.constants.CountryURIConstants;
import com.app.country.model.Country;
import com.app.country.response.CountriesResponse;
import com.github.javafaker.Faker;

@SpringBootTest
@AutoConfigureWebTestClient
class CountryServiceControllerTest {
	private static final Country FINLAND = Country.of("Finland", "Helsinki", "Europe", "FI");
	private static final String ACCESS_KEY = "access_key";

	@Autowired
	private WebTestClient webTestClient;


	@Test
	void getCountryByName() {
		webTestClient.get()
				.uri(CountryURIConstants.GET_COUNTRY_BY_NAME, FINLAND.getName())
				.header(ACCESS_KEY, CountryURIConstants.GET_API_KEY).accept(MediaType.APPLICATION_NDJSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(Country.class)
				.consumeWith((response) -> {
					assertTrue(FINLAND.getName().equalsIgnoreCase(response.getResponseBody().getName()));
					assertTrue(FINLAND.getCapital().equalsIgnoreCase(response.getResponseBody().getCapital()));
					assertTrue(FINLAND.getRegion().equalsIgnoreCase(response.getResponseBody().getRegion()));
				});
	}
	
	@Test
	void getCountryByNameNotFound() {
		webTestClient.get()
					.uri(CountryURIConstants.GET_COUNTRY_BY_NAME,Faker.instance().address().cityName())
					.header(ACCESS_KEY, CountryURIConstants.GET_API_KEY)
					.exchange()
					.expectStatus()
					.isNotFound();
	}

	@Test
	void getAllCountries() {
		webTestClient.get()
				.uri(CountryURIConstants.GET_ALL_COUNTIRES)
				.header(ACCESS_KEY, CountryURIConstants.GET_API_KEY)
				.exchange()
				.expectStatus().isOk()
				.expectBody(CountriesResponse.class)
				.consumeWith((response) -> {
					assertNotNull(response.getResponseBody());
				});
	}
}
