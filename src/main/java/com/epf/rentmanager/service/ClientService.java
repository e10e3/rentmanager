package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;

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
		try {
			if (client.getFirstName().isBlank()) {
				throw new ServiceException("First name cannot be empty.");
			}
			if (client.getLastName().isBlank()) {
				throw new ServiceException("Last name cannot be empty.");
			}
			client.setLastName(client.getLastName().toUpperCase());
			return ClientDao.getInstance().create(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public long delete(Client client) throws ServiceException {
		try {
			for (Reservation res : ReservationService.getInstance()
					.findResaByClientId(client.getIdentifier())) {
				/* Reservations from a client can be deleted if the client is removed. */
				ReservationService.getInstance().delete(res);
			}
			return ClientDao.getInstance().delete(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public void modify(long id, Client newData) throws ServiceException {
		try {
			ClientDao.getInstance().update(id, newData);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
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
