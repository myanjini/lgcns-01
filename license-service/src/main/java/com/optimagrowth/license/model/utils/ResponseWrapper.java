package com.optimagrowth.license.model.utils;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper {
	@JsonProperty("metadata")
	private Object metadata;

	@JsonProperty("errors")
	private List<ErrorMessage> errors;
}
