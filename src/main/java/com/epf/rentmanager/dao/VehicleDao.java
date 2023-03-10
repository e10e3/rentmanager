package com.epf.rentmanager.dao;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VehicleDao {

	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur=?, modele=?, nb_places=? WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(id) AS count FROM Vehicle;";

	public long create(Vehicle vehicle) throws DaoException {
		long vehicleId = 0;
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(CREATE_VEHICLE_QUERY,
				                                                                  Statement.RETURN_GENERATED_KEYS)
		) {
			preparedStatement.setString(1, vehicle.getConstructor());
			preparedStatement.setString(2, vehicle.getModel());
			preparedStatement.setShort(3, vehicle.getSeatCount());
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();

			if (rs.next()) {
				vehicleId = rs.getLong("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}

		return vehicleId;
	}

	public long delete(Vehicle vehicle) throws DaoException {
		long vehicleId = vehicle.getIdentifier();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VEHICLE_QUERY,
				                                                                  Statement.RETURN_GENERATED_KEYS)
		) {
			preparedStatement.setLong(1, vehicleId);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();

			rs.next();
			assert rs.getLong("id") == vehicleId;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return vehicleId;
	}

	public void update(long id, Vehicle newData) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_VEHICLE_QUERY)
		) {
			preparedStatement.setString(1, newData.getConstructor());
			preparedStatement.setString(2, newData.getModel());
			preparedStatement.setShort(3, newData.getSeatCount());
			preparedStatement.setLong(4, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	public Vehicle findById(long id) throws DaoException {
		Vehicle vehicle = null;
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLE_QUERY)
		) {
			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				assert rs.getLong("id") == id;
				vehicle = new Vehicle(id, rs.getString("constructeur"), rs.getString("modele"),
				                      rs.getShort("nb_places"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return vehicle;
	}

	public List<Vehicle> findAll() throws DaoException {
		ArrayList<Vehicle> vehicles = new ArrayList<>();
		try (
				Connection connection = ConnectionManager.getConnection();
				Statement statement = connection.createStatement()
		) {
			ResultSet rs = statement.executeQuery(FIND_VEHICLES_QUERY);

			while (rs.next()) {
				vehicles.add(new Vehicle(rs.getInt("id"), rs.getString("constructeur"), rs.getString("modele"),
				                         rs.getShort("nb_places")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return vehicles;
	}

	public int count() throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				Statement statement = connection.createStatement()
		) {
			int vehicleCount = 0;

			ResultSet rs = statement.executeQuery(COUNT_VEHICLES_QUERY);

			if (rs.next()) {
				vehicleCount = rs.getInt("count");
			}
			return vehicleCount;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

}
