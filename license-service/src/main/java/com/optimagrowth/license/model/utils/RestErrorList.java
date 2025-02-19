package com.optimagrowth.license.model.utils;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestErrorList extends ArrayList<ErrorMessage> {
	@JsonProperty("status")
	private HttpStatus status;
	
	@JsonProperty("errors")
	private ArrayList<ErrorMessage> errors;
	
	public RestErrorList(HttpStatus status, ErrorMessage... errors) {
		this.status = status;
		this.errors = new ArrayList<>(java.util.Arrays.asList(errors));
	}
}
