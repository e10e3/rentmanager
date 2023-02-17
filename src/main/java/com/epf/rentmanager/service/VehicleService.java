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
		// TODO: créer un véhicule
		return 0;
	}

	public Vehicle findById(long id) throws ServiceException {
		// TODO: récupérer un véhicule par son id
		return null;
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {
			return VehicleDao.getInstance().findAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

}
