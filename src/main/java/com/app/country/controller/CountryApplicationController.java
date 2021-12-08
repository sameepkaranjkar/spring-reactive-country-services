package com.app.country.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.app.country.error.handler.CountryNotFoundException;
import com.app.country.model.Country;
import com.app.country.response.CountriesResponse;
import com.app.country.service.CountryService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import reactor.core.publisher.Mono;

/**
 * The Main Controller class for country API to handle all the requests
 * 
 * @author SameepK
 */
@RestController
public class CountryApplicationController {

	private static final String ACCESS_KEY_HEADER_CAN_NOT_BE_EMPTY = "access_key Header value can not be empty";

	private static final String COUNTRY_NAME_CANNOT_BE_NULL = "Country name cannot be empty";

	@Autowired
	private CountryService countryService;

	/**
	 * Gets the the details of all countries from country API service
	 * 
	 * @param accessKey Mandatory request header parameter : access_key API Key
	 * @return Details of the all Country available
	 * @throws CountryNotFoundException Country not found exception
	 */

	@GetMapping(value = "/countries", produces = MediaType.APPLICATION_NDJSON_VALUE)
	@CrossOrigin(origins = { "localhost" })
	@ApiOperation(value = "Get all countries information")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Get all countries information", responseContainer = "Mono", response = CountriesResponse.class),
			@ApiResponse(code = 400, message = "Invalid argument Provided (Bad Request)", response = String.class),
			@ApiResponse(code = 500, message = "Internal service error", response = RuntimeException.class) })

	public ResponseEntity<Mono<CountriesResponse>> getAllCountries(
			@RequestHeader(value = "access_key", required = true) String accessKey) {

		Assert.hasLength(accessKey, ACCESS_KEY_HEADER_CAN_NOT_BE_EMPTY);
		return ResponseEntity.status(HttpStatus.OK).body(countryService.getAllCountries(accessKey));

	}

	/**
	 * Gets the the details of the country based on name from external service
	 *
	 * @param name      Mandatory path variable parameter : name of the country
	 * @param accessKey Mandatory request header parameter : access_key API Key
	 * @return Details of the given Country
	 * @throws CountryNotFoundException Country not found exception
	 */

	@GetMapping(value = "/countries/{name}", produces = MediaType.APPLICATION_NDJSON_VALUE)
	@CrossOrigin(origins = { "localhost" })
	@ApiOperation(value = "Get specific Country information")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Country with given name", responseContainer = "Mono", response = Country.class),
			@ApiResponse(code = 400, message = "Invalid argument Provided (Bad Request)", response = String.class),
			@ApiResponse(code = 404, message = "Country with given name not found", response = String.class),
			@ApiResponse(code = 500, message = "Internal service error", response = RuntimeException.class) })

	public ResponseEntity<Mono<Country>> getCountryByName(@PathVariable String name,
			@RequestHeader(value = "access_key", required = true) String accessKey) {

		Assert.hasLength(name, COUNTRY_NAME_CANNOT_BE_NULL);
		Assert.hasLength(accessKey, ACCESS_KEY_HEADER_CAN_NOT_BE_EMPTY);
		return ResponseEntity.status(HttpStatus.OK)
				.body(countryService.getCountryByName(StringUtils.trimWhitespace(name), accessKey));

	}
}
