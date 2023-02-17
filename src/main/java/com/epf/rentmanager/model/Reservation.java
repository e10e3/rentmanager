package com.epf.rentmanager.model;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {
	private long identifier;
	private Client renterClient;
	private Vehicle rentedVehicle;
	private LocalDate startDate;
	private LocalDate endDate;

	public Reservation() {
	}

	public Reservation(long identifier, Client renterClient, Vehicle rentedVehicle, LocalDate startDate, LocalDate endDate) {
		this.identifier = identifier;
		this.renterClient = renterClient;
		this.rentedVehicle = rentedVehicle;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "Reservation{" + "identifier=" + identifier + ", renterClient=" + renterClient + ", rentedVehicle=" + rentedVehicle + ", startDate=" + startDate + ", endDate=" + endDate + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Reservation that = (Reservation) o;

		if (identifier != that.identifier) return false;
		if (!Objects.equals(renterClient, that.renterClient)) return false;
		if (!Objects.equals(rentedVehicle, that.rentedVehicle)) return false;
		if (!Objects.equals(startDate, that.startDate)) return false;
		return Objects.equals(endDate, that.endDate);
	}

	@Override
	public int hashCode() {
		int result = (int) (identifier ^ (identifier >>> 32));
		result = 31 * result + (renterClient != null ? renterClient.hashCode() : 0);
		result = 31 * result + (rentedVehicle != null ? rentedVehicle.hashCode() : 0);
		result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
		result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
		return result;
	}

	public long getIdentifier() {
		return identifier;
	}

	public void setIdentifier(long identifier) {
		this.identifier = identifier;
	}

	public Client getRenterClient() {
		return renterClient;
	}

	public void setRenterClient(Client renterClient) {
		this.renterClient = renterClient;
	}

	public Vehicle getRentedVehicle() {
		return rentedVehicle;
	}

	public void setRentedVehicle(Vehicle rentedVehicle) {
		this.rentedVehicle = rentedVehicle;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
}
