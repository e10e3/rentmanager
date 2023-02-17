package com.epf.rentmanager.main;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

public class Main {
	public static void main(String[] args) {
		try {
			System.out.println(ClientService.getInstance().findAll());
			System.out.println(VehicleService.getInstance().findAll());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
