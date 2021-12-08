package com.app.country.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
public class AppConfig {

	@Value("${country.byname.url}")
	private String countryByNameURL;
	
	@Value("${country.all.url}")
	private String countryAllURL;

	public String getCountryByNameURL() {
		return countryByNameURL;
	}

	public void setCountryByNameURL(String countryByNameURL) {
		this.countryByNameURL = countryByNameURL;
	}

	public String getCountryAllURL() {
		return countryAllURL;
	}

	public void setCountryAllURL(String countryAllURL) {
		this.countryAllURL = countryAllURL;
	}
	
}
