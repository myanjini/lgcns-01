package com.optimagrowth.license.model.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage {
	@JsonProperty("timestamp")
	private long timestamp;
	
	@JsonProperty("status")
	private int status;
	
	@JsonProperty("error")
	private String error;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("path")
	private String path;
	
	public ErrorMessage(String message, int status, String error, String path) {
		this.timestamp = System.currentTimeMillis();
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}
}
