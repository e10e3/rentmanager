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
			return VehicleDao.getInstance().create(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public long delete(Vehicle vehicle) throws ServiceException {
		try {
			for (Reservation res : ReservationService.getInstance()
					.findResaByVehicleId(vehicle.getIdentifier())) {
				/* Remove vehicle from reservations */
				res.setRentedVehicle(null);
				ReservationService.getInstance().modify(res.getIdentifier(), res);
			} return VehicleDao.getInstance().delete(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public void modify(long id, Vehicle newData) throws ServiceException {
		try {
			VehicleDao.getInstance().update(id, newData);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		try {
			return VehicleDao.getInstance().findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {
			return VehicleDao.getInstance().findAll();
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
