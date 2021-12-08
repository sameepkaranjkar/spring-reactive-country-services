
package com.app.country.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.app.country.constants.CountryURIConstants;
import com.app.country.controller.CountryApplicationController;
import com.app.country.error.handler.CountryNotFoundException;
import com.app.country.model.Countries;
import com.app.country.model.Country;
import com.app.country.response.CountriesResponse;
import com.github.javafaker.Faker;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@Import(CountryApplicationController.class)
class CountryServiceTest {
	private static final Country FINLAND = Country.of("Finland", "Helsinki", "Europe", "FI");
	private static final Country DENMARK = Country.of("Denmark", "Copenhagen", "Europe", "DK");
	private static final String ACCESS_KEY = "access_key";

	@MockBean
	private CountryService countryService;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void testGetCountryByName() {
		
		Mockito.when(this.countryService.getCountryByName(FINLAND.getName(), CountryURIConstants.GET_API_KEY))
				.thenReturn(Mono.just(FINLAND));
		
		webTestClient.get()
				.uri(CountryURIConstants.GET_COUNTRY_BY_NAME, FINLAND.getName())
				.header(ACCESS_KEY, CountryURIConstants.GET_API_KEY)
				.accept(MediaType.APPLICATION_NDJSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(Country.class)
				.consumeWith((response) -> {
					assertTrue(FINLAND.getName().equalsIgnoreCase(response.getResponseBody().getName()));
				});
	}

	@Test
	void testGetAllCountries() {
		CountriesResponse expectedResponse = createCountriesResponse();
		Mockito.when(this.countryService.getAllCountries(CountryURIConstants.GET_API_KEY))
				.thenReturn(Mono.just(expectedResponse));

		CountriesResponse actualResponse = webTestClient.get()
											.uri(CountryURIConstants.GET_ALL_COUNTIRES)
											.header(ACCESS_KEY, CountryURIConstants.GET_API_KEY)
											.exchange()
											.expectStatus().isOk()
											.expectHeader()
											.contentType(MediaType.APPLICATION_NDJSON)
											.expectBody(CountriesResponse.class)
											.returnResult()
											.getResponseBody();
		assertEquals(expectedResponse.getCountries().size() ,actualResponse.getCountries().size());								
	}

	@Test
	void getCountryNotFound() {
		
		String notFoundCountryName = Faker.instance().address().cityName();
		
		Mockito.when(this.countryService.getCountryByName(notFoundCountryName, CountryURIConstants.GET_API_KEY))
				.thenThrow(new CountryNotFoundException(notFoundCountryName));
		
		webTestClient.get()
					.uri(CountryURIConstants.GET_COUNTRY_BY_NAME, notFoundCountryName)
					.header(ACCESS_KEY, CountryURIConstants.GET_API_KEY)
					.exchange()
					.expectStatus()
					.isNotFound();

	}

	private CountriesResponse createCountriesResponse() {
		Countries finland = Countries.of(FINLAND.getName(), FINLAND.getCountryCode());
		Countries denmark = Countries.of(DENMARK.getName(), DENMARK.getCountryCode());
		List<Countries> countries = new ArrayList<>();
		countries.add(finland);
		countries.add(denmark);
		return new CountriesResponse(countries);
	}

}
