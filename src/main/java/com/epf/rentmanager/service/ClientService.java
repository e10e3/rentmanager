package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;

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
		try {
			return ClientDao.getInstance().findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public List<Client> findAll() throws ServiceException {
		try {
			return ClientDao.getInstance().findAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

}
