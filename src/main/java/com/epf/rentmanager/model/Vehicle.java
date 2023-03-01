package com.epf.rentmanager.model;

import java.util.Objects;

public class Vehicle {
	private long identifier;
	private String constructor;
	private String model;
	private short seatCount;

	public Vehicle() {
	}

	public Vehicle(long identifier, String constructor, String model, short seatCount) {
		this.identifier = identifier;
		this.constructor = constructor;
		this.model = model;
		this.seatCount = seatCount;
	}

	public Vehicle(long identifier, String constructor, short seatCount) {
		this.identifier = identifier;
		this.constructor = constructor;
		this.seatCount = seatCount;
	}

	@Override
	public String toString() {
		return constructor + " " + (model==null ? "":model) + " (" + seatCount + " places)";

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Vehicle vehicle = (Vehicle) o;

		if (identifier != vehicle.identifier) return false;
		if (seatCount != vehicle.seatCount) return false;
		if (!Objects.equals(constructor, vehicle.constructor)) return false;
		return Objects.equals(model, vehicle.model);
	}

	@Override
	public int hashCode() {
		int result = (int) (identifier ^ (identifier >>> 32));
		result = 31 * result + (constructor != null ? constructor.hashCode() : 0);
		result = 31 * result + (model != null ? model.hashCode() : 0);
		result = 31 * result + (int) seatCount;
		return result;
	}

	public long getIdentifier() {
		return identifier;
	}

	public void setIdentifier(long identifier) {
		this.identifier = identifier;
	}

	public String getConstructor() {
		return constructor;
	}

	public void setConstructor(String constructor) {
		this.constructor = constructor;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public short getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(short seatCount) {
		this.seatCount = seatCount;
	}
}
