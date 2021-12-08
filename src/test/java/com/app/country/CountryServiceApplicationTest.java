
package com.app.country;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.country.controller.CountryApplicationController;
import com.app.country.service.CountryService;

@SpringBootTest
class CountryServiceApplicationTest {

	@Autowired
	private CountryApplicationController countryApiController;

	@Autowired
	private CountryService countryService;

	@Test
	void contextLoads() throws Exception {
		assertThat(countryApiController).isNotNull();
		assertThat(countryService).isNotNull();
	}
}
