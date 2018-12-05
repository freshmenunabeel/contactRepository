package com.contactBook.contactBook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserContactDTO {

	@JsonProperty
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
