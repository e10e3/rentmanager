package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;

import java.util.List;

public class VehicleService {

	public static VehicleService instance;
	private final VehicleDao vehicleDao;

	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}

	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}

		return instance;
	}

	public long create(Vehicle vehicle) throws ServiceException {
		try {
			if (vehicle.getConstructor().isBlank()) {
				throw new ServiceException("Constructor cannot be empty.");
			}
			if (vehicle.getSeatCount() <= 0) {
				throw new ServiceException("Number of seats must be at least one");
			}
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public long delete(Vehicle vehicle) throws ServiceException {
		ReservationService reservationService = ReservationService.getInstance();
		try {
			for (Reservation res : reservationService.findResaByVehicleId(vehicle.getIdentifier())) {
				/* Remove vehicle from reservations */
				res.setRentedVehicle(null);
				reservationService.edit(res.getIdentifier(), res);
			}
			return vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public void edit(long id, Vehicle newData) throws ServiceException {
		try {
			vehicleDao.update(id, newData);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {
			return vehicleDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public int count() throws ServiceException {
		try {
			return vehicleDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

}
