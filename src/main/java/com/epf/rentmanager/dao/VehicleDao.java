package com.epf.rentmanager.dao;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao {

	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";
	private static VehicleDao instance = null;

	private VehicleDao() {
	}

	public static VehicleDao getInstance() {
		if (instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}

	public long create(Vehicle vehicle) throws DaoException {
		long vehicle_id = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_VEHICLE_QUERY,
			                                                                  Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, vehicle.getConstructor());
			preparedStatement.setShort(2, vehicle.getSeatCount());
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();

			while (rs.next()) {
				vehicle_id = rs.getLong("id");
				assert rs.getString("constructeur").equals(vehicle.getConstructor());
				assert rs.getShort("nb_places") == vehicle.getSeatCount();
			}

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}

		return vehicle_id;
	}

	public long delete(Vehicle vehicle) throws DaoException {
		long vehicle_id = vehicle.getIdentifier();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VEHICLE_QUERY,
			                                                                  Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, vehicle_id);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();

			while (rs.next()) {
				assert rs.getLong("id") == vehicle_id;
			}

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}

		return vehicle_id;
	}

	public void update(long id, Vehicle newData) throws DaoException {
	}

	public Vehicle findById(long id) throws DaoException {
		Vehicle vehicle = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLE_QUERY);
			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				vehicle = new Vehicle(id, rs.getString("constructeur"), rs.getShort("nb_places"));
			}

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return vehicle;
	}

	public List<Vehicle> findAll() throws DaoException {
		ArrayList<Vehicle> vehicles = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_VEHICLES_QUERY);

			while (rs.next()) {
				vehicles.add(new Vehicle(rs.getInt("id"), rs.getString("constructeur"),
				                         rs.getShort("nb_places")));
			}

			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return vehicles;

	}

}
