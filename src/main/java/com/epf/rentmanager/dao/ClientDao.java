package com.epf.rentmanager.dao;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientDao {

	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(id) AS count FROM Client;";

	public long create(Client client) throws DaoException {
		long clientId = 0;
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CLIENT_QUERY,
				                                                                  Statement.RETURN_GENERATED_KEYS)
		) {
			preparedStatement.setString(1, client.getLastName());
			preparedStatement.setString(2, client.getFirstName());
			preparedStatement.setString(3, client.getEmailAddress());
			preparedStatement.setDate(4, Date.valueOf(client.getBirthDate()));
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();

			if (rs.next()) {
				clientId = rs.getLong("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return clientId;
	}

	public long delete(Client client) throws DaoException {
		long clientId = client.getIdentifier();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT_QUERY,
				                                                                  Statement.RETURN_GENERATED_KEYS)
		) {
			preparedStatement.setLong(1, clientId);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();

			rs.next();
			assert rs.getLong("id") == clientId;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return clientId;
	}

	public void update(long id, Client newData) throws DaoException {
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT_QUERY)
		) {
			preparedStatement.setString(1, newData.getLastName());
			preparedStatement.setString(2, newData.getFirstName());
			preparedStatement.setString(3, newData.getEmailAddress());
			preparedStatement.setDate(4, Date.valueOf(newData.getBirthDate()));
			preparedStatement.setLong(5, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	public Client findById(long id) throws DaoException {
		Client client = null;
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_QUERY)
		) {
			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				assert rs.getLong("id") == id;
				client = new Client(id, rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),
				                    rs.getDate("naissance").toLocalDate());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return client;
	}

	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<>();
		try (
				Connection connection = ConnectionManager.getConnection();
				Statement statement = connection.createStatement()
		) {
			ResultSet rs = statement.executeQuery(FIND_CLIENTS_QUERY);

			while (rs.next()) {
				clients.add(
						new Client(rs.getLong("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),
						           rs.getDate("naissance").toLocalDate()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return clients;
	}

	public int count() throws DaoException {
		int userCount = 0;
		try (
				Connection connection = ConnectionManager.getConnection();
				Statement statement = connection.createStatement()
		) {
			ResultSet rs = statement.executeQuery(COUNT_CLIENTS_QUERY);

			if (rs.next()) {
				userCount = rs.getInt("count");
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return userCount;
	}

}
