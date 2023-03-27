package com.epf.rentmanager.model;

import java.time.LocalDate;
import java.util.Objects;

public record Reservation(long identifier, Client renterClient, Vehicle rentedVehicle,
						  LocalDate startDate, LocalDate endDate) {

	public Reservation {
		Objects.requireNonNull(renterClient);
		Objects.requireNonNull(rentedVehicle);
		Objects.requireNonNull(startDate);
		Objects.requireNonNull(endDate);
	}

	@Override
	public String toString() {
		return renterClient + " loue " + rentedVehicle + " du " + startDate + " au " + endDate;
	}
}
