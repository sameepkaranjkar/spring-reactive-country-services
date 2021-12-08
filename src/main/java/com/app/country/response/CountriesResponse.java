package com.app.country.response;

import java.util.List;

import com.app.country.model.Countries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CountriesResponse {
   private List<Countries> countries;
}