package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;

import java.util.List;

public class ReservationService {
	public static ReservationService instance;
	private final ReservationDao reservationDao;

	private ReservationService() {
		this.reservationDao = ReservationDao.getInstance();
	}

	public static ReservationService getInstance() {
		if (instance == null) {
			instance = new ReservationService();
		}

		return instance;
	}

	public long create(Reservation reservation) throws ServiceException {
		try {
			return reservationDao.create(reservation);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public long delete(Reservation reservation) throws ServiceException {
		try {
			return reservationDao.delete(reservation);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public void modify(long id, Reservation newData) throws ServiceException {
		try {
			reservationDao.update(id, newData);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public List<Reservation> findResaByClientId(long clientId) throws ServiceException {
		try {
			return reservationDao.findResaByClientId(clientId);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public List<Reservation> findResaByVehicleId(long vehicleId) throws ServiceException {
		try {
			return reservationDao.findResaByVehicleId(vehicleId);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public Reservation findById(long id) throws ServiceException {
		try {
			return reservationDao.findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public List<Reservation> findAll() throws ServiceException {
		try {
			return reservationDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public int count() throws ServiceException {
		try {
			return reservationDao.count();
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
}
