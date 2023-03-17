package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Repository
public class ClientService {
	private static final int MINIMUM_AGE = 18;

	private final ClientDao clientDao;
	private final ReservationService reservationService;

	private ClientService(ClientDao clientDao, ReservationService reservationService) {
		this.clientDao = clientDao;
		this.reservationService = reservationService;
	}

	public long create(Client client) throws ServiceException {
		if (client == null) {
			throw new ServiceException("Client cannot be null.");
		}
		try {
			isValid(client);
			client.setLastName(client.getLastName().toUpperCase());
			return clientDao.create(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	/**
	 * Checks if the client satisfies the constraints.
	 *
	 * @param client The client to check.
	 * @throws ServiceException If the client is not valid, with the failed criterion.
	 */
	private void isValid(Client client) throws ServiceException {
		if (client == null) {
			throw new ServiceException("Client to check must not be null.");
		}
		if (client.getFirstName().length() < 3) {
			throw new ServiceException("First name must be at least 3 characters long.");
		}
		if (client.getLastName().length() < 3) {
			throw new ServiceException("Last name must be at least 3 characters long.");
		}
		if (Period.between(client.getBirthDate(), LocalDate.now()).getYears() < MINIMUM_AGE) {
			throw new ServiceException("The client must be 18 or older.");
		}
		try {
			if (clientDao.isEmailUsed(client.getEmailAddress())) {
				throw new ServiceException("This email address is already registered.");
			}
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	public long delete(Client client) throws ServiceException {
		if (client == null) {
			throw new ServiceException("Client cannot be null.");
		}
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
		if (newData == null) {
			throw new ServiceException("New client cannot be null.");
		}
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
