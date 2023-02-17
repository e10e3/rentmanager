package com.epf.rentmanager.dao;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;

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
		return 0;
	}

	public long delete(Client client) throws DaoException {
		return 0;
	}

	public Client findById(long id) throws DaoException {
		return new Client();
	}

	public List<Client> findAll() throws DaoException {
		return new ArrayList<Client>();
	}

}
