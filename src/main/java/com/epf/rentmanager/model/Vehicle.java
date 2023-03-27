package com.epf.rentmanager.model;

import java.util.Objects;

public record Vehicle(long identifier, String constructor, String model, short seatCount) {

	public Vehicle(long identifier, String constructor, String model, short seatCount) {
		Objects.requireNonNull(constructor);
		Objects.requireNonNull(model);
		this.identifier = identifier;
		this.constructor = constructor.trim();
		this.model = model.trim();
		this.seatCount = seatCount;
	}

	@Override
	public String toString() {
		return constructor + " " + (model == null ? "" : model);

	}
}
