package com.epf.rentmanager.dao;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDao {

	private static final String CREATE_RESERVATION_QUERY =
			"INSERT INTO Reservation(client_id, vehicle_id, debut, " + "fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String UPDATE_RESERVATION_QUERY =
			"UPDATE Reservation SET client_id=?, vehicle_id=?, " + "debut=?, fin=? " + "WHERE id=?;";

	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY =
			"SELECT id, vehicle_id, debut, fin FROM " + "Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY =
			"SELECT id, client_id, debut, fin FROM " + "Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATION_QUERY =
			"SELECT id, client_id, debut, fin FROM Reservation WHERE " + "id=?;";
	private static final String FIND_RESERVATIONS_QUERY =
			"SELECT id, client_id, vehicle_id, debut, fin FROM " + "Reservation;";
	private static ReservationDao instance = null;

	private ReservationDao() {
	}

	public static ReservationDao getInstance() {
		if (instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}

	public long create(Reservation reservation) throws DaoException {
		long reservationId = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_RESERVATION_QUERY,
			                                                                  Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, reservation.getRenterClient().getIdentifier());
			preparedStatement.setLong(2, reservation.getRentedVehicle().getIdentifier());
			preparedStatement.setDate(3, Date.valueOf(reservation.getStartDate()));
			preparedStatement.setDate(4, Date.valueOf(reservation.getEndDate()));
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();

			while (rs.next()) {
				reservationId = rs.getLong("id");
			}

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return reservationId;
	}

	public long delete(Reservation reservation) throws DaoException {
		long reservationId = reservation.getIdentifier();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_QUERY,
			                                                                  Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, reservationId);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();

			while (rs.next()) {
				assert rs.getLong("id") == reservationId;
			}

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return 0;
	}

	public void update(long id, Reservation newData) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RESERVATION_QUERY);
			preparedStatement.setLong(1, newData.getRenterClient().getIdentifier());
			preparedStatement.setLong(2, newData.getRentedVehicle().getIdentifier());
			preparedStatement.setDate(3, Date.valueOf(newData.getStartDate()));
			preparedStatement.setDate(4, Date.valueOf(newData.getEndDate()));
			preparedStatement.setLong(5, id);
			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> clientReservations = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			preparedStatement.setLong(1, clientId);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				clientReservations.add(new Reservation(rs.getLong("id"), ClientService.getInstance().findById(clientId),
				                                       VehicleService.getInstance().findById(rs.getLong("vehicle_id")),
				                                       rs.getDate("debut").toLocalDate(),
				                                       rs.getDate("fin").toLocalDate()));
			}

			preparedStatement.close();
			connection.close();
		} catch (SQLException | ServiceException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return clientReservations;
	}

	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> vehicleReservations = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			preparedStatement.setLong(1, vehicleId);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				vehicleReservations.add(
						new Reservation(rs.getLong("id"), ClientService.getInstance().findById(rs.getLong("client_id")),
						                VehicleService.getInstance().findById(vehicleId),
						                rs.getDate("debut").toLocalDate(), rs.getDate("fin").toLocalDate()));
			}

			preparedStatement.close();
			connection.close();
		} catch (SQLException | ServiceException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return vehicleReservations;
	}

	public Reservation findById(long id) throws DaoException {
		Reservation reservation = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATION_QUERY);
			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				reservation = new Reservation(id, ClientService.getInstance().findById(rs.getLong("client_id")),
				                              VehicleService.getInstance().findById(rs.getLong("vehicle_id")),
				                              rs.getDate("debut").toLocalDate(), rs.getDate("fin").toLocalDate());
			}

			preparedStatement.close();
			connection.close();
		} catch (SQLException | ServiceException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return reservation;
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_RESERVATIONS_QUERY);

			while (rs.next()) {
				reservations.add(
						new Reservation(rs.getLong("id"), ClientService.getInstance().findById(rs.getLong("client_id")),
						                VehicleService.getInstance().findById(rs.getLong("vehicle_id")),
						                rs.getDate("debut").toLocalDate(), rs.getDate("fin").toLocalDate()));
			}

			statement.close();
			connection.close();
		} catch (SQLException | ServiceException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return reservations;
	}
}
