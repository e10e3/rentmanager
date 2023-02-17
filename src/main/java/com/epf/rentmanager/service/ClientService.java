package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientService {

	public static ClientService instance;
	private final ClientDao clientDao;

	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}

	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}

		return instance;
	}


	public long create(Client client) throws ServiceException {
		// TODO: créer un client
		return 0;
	}

	public Client findById(long id) throws ServiceException {
		// TODO: récupérer un client par son id
		return new Client();
	}

	public List<Client> findAll() throws ServiceException {
		// TODO: récupérer tous les clients
		return new ArrayList<Client>();
	}

}
