package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.exception.ValidationException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VehicleService {
	private final VehicleDao vehicleDao;
	private final ReservationService reservationService;

	private VehicleService(VehicleDao vehicleDao, ReservationService reservationService) {
		this.vehicleDao = vehicleDao;
		this.reservationService = reservationService;
	}

	public long create(Vehicle vehicle) throws ServiceException, ValidationException {
		isValid(vehicle);
		try {
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	/**
	 * Checks if the vehicle satisfies the constraints.
	 *
	 * @param vehicle The vehicle to check.
	 * @throws ValidationException If the vehicle is not valid, with the failed criterion.
	 */
	private void isValid(Vehicle vehicle) throws ValidationException {
		if (vehicle == null) {
			throw new ValidationException("The vehicle to check cannot be null");
		}
		if (vehicle.constructor().isBlank()) {
			throw new ValidationException("The vehicle's constructor must not be empty.");
		}
		if (vehicle.model().isBlank()) {
			throw new ValidationException("The vehicle's model must not be empty.");
		}
		if (vehicle.seatCount() < 2 || vehicle.seatCount() > 9) {
			throw new ValidationException("Number of seats must be at least 2 and at most 9.");
		}
	}

	public long delete(Vehicle vehicle) throws ServiceException {
		try {
			for (Reservation res : reservationService.findResaByVehicleId(vehicle.identifier())) {
				reservationService.delete(res);
			}
			return vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public void edit(long id, Vehicle newData) throws ServiceException, ValidationException {
		isValid(newData);
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
