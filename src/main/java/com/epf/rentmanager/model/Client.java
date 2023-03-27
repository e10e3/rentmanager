package com.epf.rentmanager.model;

import java.time.LocalDate;
import java.util.Objects;

public record Client(long identifier, String lastName, String firstName, String emailAddress,
					 LocalDate birthDate) {

	public Client(long identifier, String lastName, String firstName, String emailAddress,
				  LocalDate birthDate) {
		Objects.requireNonNull(lastName);
		Objects.requireNonNull(firstName);
		Objects.requireNonNull(emailAddress);
		this.identifier = identifier;
		this.lastName = lastName.trim();
		this.firstName = firstName.trim();
		this.emailAddress = emailAddress.trim();
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
}
