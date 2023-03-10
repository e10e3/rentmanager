package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientService {

	private final ClientDao clientDao;
	private final ReservationService reservationService;

	private ClientService(ClientDao clientDao, ReservationService reservationService) {
		this.clientDao = clientDao;
		this.reservationService = reservationService;
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
			return clientDao.create(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public long delete(Client client) throws ServiceException {
		try {
			for (Reservation res : reservationService.findResaByClientId(client.getIdentifier())) {
				/* Reservations from a client can be deleted if the client is removed. */
				reservationService.delete(res);
			}
			return clientDao.delete(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public void edit(long id, Client newData) throws ServiceException {
		try {
			clientDao.update(id, newData);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public Client findById(long id) throws ServiceException {
		try {
			return clientDao.findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public List<Client> findAll() throws ServiceException {
		try {
			return clientDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public int count() throws ServiceException {
		try {
			return clientDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

}
