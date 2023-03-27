package com.epf.rentmanager.main;

import com.epf.rentmanager.config.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.exception.ValidationException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Test {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		ClientService clientService = context.getBean(ClientService.class);
		VehicleService vehicleService = context.getBean(VehicleService.class);
		ReservationService reservationService = context.getBean(ReservationService.class);
		try {
			System.out.println(clientService.findAll());
			System.out.println(vehicleService.findAll());
			System.out.println(clientService.findById(1L));
			System.out.println(vehicleService.findById(2L));
			Client cli = new Client(5, "Testy", "McTestface", "test@example.org",
									LocalDate.of(2000, 1, 1));
			Vehicle veh = new Vehicle(7, "Constr", "Mod", (short) 8);
			cli = clientService.findById(clientService.create(cli));
			veh = vehicleService.findById(vehicleService.create(veh));
			System.out.println(cli.identifier());
			System.out.println(veh.identifier());
			clientService.edit(1L,
							   new Client(0, "Test", "Mod", "test@m" + ".org", LocalDate.now()));
			System.out.println(clientService.findById(1L));
			System.out.println(clientService.findAll());
			System.out.println(vehicleService.findAll());
			System.out.println(reservationService.findAll());
			System.out.println(reservationService.findById(1L));
			System.out.println(reservationService.findAll());
			reservationService.create(
					new Reservation(0, clientService.findById(1L), vehicleService.findById(1L),
									LocalDate.of(2006, 4, 5), LocalDate.of(2006, 4, 20)));
			System.out.println(reservationService.findResaByClientId(1L));
			System.out.println(reservationService.findResaByVehicleId(1L));
		} catch (ServiceException | ValidationException e) {
			e.printStackTrace();
		}
	}
}
