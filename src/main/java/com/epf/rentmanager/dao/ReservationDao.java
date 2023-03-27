package com.epf.rentmanager.dao;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationDao {
	private static final String CLIENT_FIELDS = "Client.id, nom, prenom, email, naissance";
	private static final String VEHICLE_FIELDS = "Vehicle.id, constructeur, modele, nb_places";
	private static final String INNER_JOIN_TABLES = "INNER JOIN Client ON Reservation.client_id = Client.id INNER JOIN Vehicle ON Reservation.vehicle_id = Vehicle.id";
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET client_id=?, vehicle_id=?, debut=?, fin=? WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY =
			"SELECT Reservation.id, Reservation.vehicle_id, debut, fin, " + CLIENT_FIELDS + ", " +
			VEHICLE_FIELDS + " FROM Reservation " + INNER_JOIN_TABLES +
			" WHERE Reservation.client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY =
			"SELECT Reservation.id, Reservation.client_id, debut, fin, " + CLIENT_FIELDS + ", " +
			VEHICLE_FIELDS + " FROM Reservation " + INNER_JOIN_TABLES +
			" WHERE Reservation.vehicle_id=?;";
	private static final String FIND_RESERVATION_QUERY =
			"SELECT Reservation.id, Reservation.client_id, vehicle_id, debut, fin, " +
			CLIENT_FIELDS + ", " + VEHICLE_FIELDS + " FROM Reservation " + INNER_JOIN_TABLES +
			" WHERE Reservation.id=?;";
	private static final String FIND_RESERVATIONS_QUERY =
			"SELECT Reservation.id, Reservation.client_id, vehicle_id, debut, fin, " +
			CLIENT_FIELDS + ", " + VEHICLE_FIELDS + " FROM Reservation " + INNER_JOIN_TABLES + ";";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(id) AS count FROM Reservation;";

	public long create(Reservation reservation) throws DaoException {
		long reservationId = 0;
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(
						CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS)
		) {
			preparedStatement.setLong(1, reservation.renterClient().identifier());
			preparedStatement.setLong(2, reservation.rentedVehicle().identifier());
			preparedStatement.setDate(3, Date.valueOf(reservation.startDate()));
			preparedStatement.setDate(4, Date.valueOf(reservation.endDate()));
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();

			if (rs.next()) {
				reservationId = rs.getLong("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return reservationId;
	}

	public long delete(Reservation reservation) throws DaoException {
		long reservationId = reservation.identifier();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(
						DELETE_RESERVATION_QUERY)
		) {
			preparedStatement.setLong(1, reservationId);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return reservationId;
	}

	public void update(long id, Reservation newData) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(
						UPDATE_RESERVATION_QUERY)
		) {
			preparedStatement.setLong(1, newData.renterClient().identifier());
			preparedStatement.setLong(2, newData.rentedVehicle().identifier());
			preparedStatement.setDate(3, Date.valueOf(newData.startDate()));
			preparedStatement.setDate(4, Date.valueOf(newData.endDate()));
			preparedStatement.setLong(5, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> clientReservations = new ArrayList<>();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(
						FIND_RESERVATIONS_BY_CLIENT_QUERY)
		) {
			preparedStatement.setLong(1, clientId);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Client client = new Client(rs.getLong("Client.id"), rs.getString("nom"),
										   rs.getString("prenom"), rs.getString("email"),
										   rs.getDate("naissance").toLocalDate());
				Vehicle vehicle = new Vehicle(rs.getLong("Vehicle.id"),
											  rs.getString("constructeur"), rs.getString("modele"),
											  rs.getShort("nb_places"));
				clientReservations.add(new Reservation(rs.getLong("id"), client, vehicle,
													   rs.getDate("debut").toLocalDate(),
													   rs.getDate("fin").toLocalDate()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return clientReservations;
	}

	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> vehicleReservations = new ArrayList<>();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(
						FIND_RESERVATIONS_BY_VEHICLE_QUERY)
		) {
			preparedStatement.setLong(1, vehicleId);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Client client = new Client(rs.getLong("Client.id"), rs.getString("nom"),
										   rs.getString("prenom"), rs.getString("email"),
										   rs.getDate("naissance").toLocalDate());
				Vehicle vehicle = new Vehicle(rs.getLong("Vehicle.id"),
											  rs.getString("constructeur"), rs.getString("modele"),
											  rs.getShort("nb_places"));
				vehicleReservations.add(new Reservation(rs.getLong("id"), client, vehicle,
														rs.getDate("debut").toLocalDate(),
														rs.getDate("fin").toLocalDate()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return vehicleReservations;
	}

	public Reservation findById(long id) throws DaoException {
		Reservation reservation = null;
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(
						FIND_RESERVATION_QUERY)
		) {
			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Client client = new Client(rs.getLong("Client.id"), rs.getString("nom"),
										   rs.getString("prenom"), rs.getString("email"),
										   rs.getDate("naissance").toLocalDate());
				Vehicle vehicle = new Vehicle(rs.getLong("Vehicle.id"),
											  rs.getString("constructeur"), rs.getString("modele"),
											  rs.getShort("nb_places"));
				reservation = new Reservation(id, client, vehicle,
											  rs.getDate("debut").toLocalDate(),
											  rs.getDate("fin").toLocalDate());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return reservation;
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (
				Connection connection = ConnectionManager.getConnection();
				Statement statement = connection.createStatement()
		) {
			ResultSet rs = statement.executeQuery(FIND_RESERVATIONS_QUERY);

			while (rs.next()) {
				Client client = new Client(rs.getLong("Client.id"), rs.getString("nom"),
										   rs.getString("prenom"), rs.getString("email"),
										   rs.getDate("naissance").toLocalDate());
				Vehicle vehicle = new Vehicle(rs.getLong("Vehicle.id"),
											  rs.getString("constructeur"), rs.getString("modele"),
											  rs.getShort("nb_places"));
				reservations.add(new Reservation(rs.getLong("id"), client, vehicle,
												 rs.getDate("debut").toLocalDate(),
												 rs.getDate("fin").toLocalDate()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return reservations;
	}

	public int count() throws DaoException {
		int reservationCount = 0;
		try (
				Connection connection = ConnectionManager.getConnection();
				Statement statement = connection.createStatement()
		) {
			ResultSet rs = statement.executeQuery(COUNT_RESERVATIONS_QUERY);

			if (rs.next()) {
				reservationCount = rs.getInt("count");
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return reservationCount;
	}
}
