
package com.app.country.model;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(exclude="alpha3Code")
@JsonPropertyOrder({ "name", "country_code", "capital", "region", "alpha3Code" })
public class Country {
	private String name;
	private String region;
	private String capital;
	private String countryCode;
	private String alpha3Code;

	@JsonProperty("country_code")
	public String getCountryCode() {
		return countryCode;
	}

	@JsonProperty("alpha2Code")
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	protected static Country empty() {
		return new Country();
	}

	public static Country of(String name, String capital, String region,String countryCode) {
		if (!StringUtils.hasLength(name) && !StringUtils.hasLength(capital)) {
			throw new IllegalArgumentException(" Country name and Capital Name cannot be null!");
		}
		Country country = empty();
		country.setName(name);
		country.setCapital(capital);
		country.setRegion(region);
		country.setCountryCode(countryCode);
		return country;
	}
}
