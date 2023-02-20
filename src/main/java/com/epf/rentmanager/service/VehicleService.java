package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
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
			return VehicleDao.getInstance().create(vehicle);
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

}
