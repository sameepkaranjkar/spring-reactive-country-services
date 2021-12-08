package com.app.country.model;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Countries {

	private String name;
	private String countryCode;

	@JsonProperty("alpha2Code")
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@JsonProperty("country_code")
	public String getCountryCode() {
		return countryCode;
	}
	
	
	protected static Countries empty() {
		return new Countries();
	}

	public static Countries of(String name,String countryCode) {
		if (!StringUtils.hasLength(name) && !StringUtils.hasLength(countryCode)) {
			throw new IllegalArgumentException(" Country name and Country code cannot be null!");
		}
		Countries country = empty();
		country.setName(name);
		country.setCountryCode(countryCode);
		return country;
	}
}