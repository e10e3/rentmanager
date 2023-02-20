package com.epf.rentmanager.dao;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {

	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static ClientDao instance = null;

	private ClientDao() {
	}

	public static ClientDao getInstance() {
		if (instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}

	public long create(Client client) throws DaoException {
		long client_id = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CLIENT_QUERY,
			                                                                  Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, client.getLastName());
			preparedStatement.setString(2, client.getFirstName());
			preparedStatement.setString(3, client.getEmailAddress());
			preparedStatement.setDate(4, Date.valueOf(client.getBirthDate()));
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();

			while (rs.next()) {
				client_id = rs.getLong("id");
				assert rs.getString("nom").equals(client.getLastName());
				assert rs.getString("prenom").equals(client.getFirstName());
				assert rs.getString("email").equals(client.getEmailAddress());
			}

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return client_id;
	}

	public long delete(Client client) throws DaoException {
		return 0;
	}

	public Client findById(long id) throws DaoException {
		Client client = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_QUERY);
			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				client = new Client(id, rs.getString("nom"), rs.getString("prenom"),
				                    rs.getString("email"), rs.getDate("naissance").toLocalDate());
			}

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return client;
	}

	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_CLIENTS_QUERY);

			while (rs.next()) {
				clients.add(
						new Client(rs.getLong("id"), rs.getString("nom"), rs.getString("prenom"),
						           rs.getString("email"), rs.getDate("naissance").toLocalDate()));
			}

			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		return clients;
	}

}
