package com.contactBook.contactBook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserCreateAccountRequestDTO {

	@JsonProperty
	private String name;

	private String uniqueUserId;

	@JsonProperty
	private String password;

	@JsonProperty
	private List<UserContactDTO> contacts;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserContactDTO> getContacts() {
		return contacts;
	}

	public void setContacts(List<UserContactDTO> contacts) {
		this.contacts = contacts;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUniqueUserId() {
		return uniqueUserId;
	}

	public void setUniqueUserId(String uniqueUserId) {
		this.uniqueUserId = uniqueUserId;
	}
}
