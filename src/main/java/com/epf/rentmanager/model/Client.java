package com.epf.rentmanager.model;

import java.time.LocalDate;
import java.util.Objects;

public class Client {
	private long identifier;
	private String lastName;
	private String firstName;
	private String emailAddress;
	private LocalDate birthDate;

	public Client() {
	}

	public Client(long identifier, String lastName, String firstName, String emailAddress, LocalDate birthDate) {
		this.identifier = identifier;
		this.lastName = lastName;
		this.firstName = firstName;
		this.emailAddress = emailAddress;
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "Client{" + "identifier=" + identifier + ", lastName='" + lastName + '\'' +
		       ", firstName='" + firstName + '\'' + ", emailAddress='" + emailAddress + '\'' +
		       ", birthDate=" + birthDate + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Client client = (Client) o;

		if (identifier != client.identifier) return false;
		if (!Objects.equals(lastName, client.lastName)) return false;
		if (!Objects.equals(firstName, client.firstName)) return false;
		if (!Objects.equals(emailAddress, client.emailAddress)) return false;
		return Objects.equals(birthDate, client.birthDate);
	}

	@Override
	public int hashCode() {
		int result = (int) (identifier ^ (identifier >>> 32));
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
		result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
		return result;
	}

	public long getIdentifier() {
		return identifier;
	}

	public void setIdentifier(long identifier) {
		this.identifier = identifier;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
}
