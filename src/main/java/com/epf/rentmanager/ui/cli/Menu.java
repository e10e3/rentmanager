package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.config.AppConfiguration;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Menu {
	private static boolean quit = false;

	private final ClientInterface clientInterface;
	private final VehicleInterface vehicleInterface;
	private final ReservationInterface reservationInterface;

	public Menu() {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		ClientService clientService = context.getBean(ClientService.class);
		VehicleService vehicleService = context.getBean(VehicleService.class);
		ReservationService reservationService = context.getBean(ReservationService.class);

		clientInterface = new ClientInterface(clientService);
		vehicleInterface = new VehicleInterface(vehicleService);
		reservationInterface = new ReservationInterface(reservationService, clientInterface,
														vehicleInterface);
	}

	public void entryPoint() {
		IOUtils.print("Bienvenue dans l'application.");
		while (!quit) {
			displayMenu();
		}
	}

	public void displayMenu() {
		IOUtils.print("Que voulez-vous faire ?");
		// @formatter:off
		IOUtils.print("""
						  [1] Lister des enregistrements
						  [2] Créer des enregistrements
						  [3] Supprimer des enregistrements
						  [4] Quitter le programme\
						  """);
		// @formatter:on
		int choice = IOUtils.readInt("Entrez votre choix : ");
		switch (choice) {
			case 1 -> displayListOptions();
			case 2 -> displayCreateOptions();
			case 3 -> displayDeleteOptions();
			case 4 -> {
				IOUtils.print("Au revoir !");
				quit = true;
			}
			default -> IOUtils.print("Option invalide.");
		}
	}

	public void displayListOptions() {
		// @formatter:off
		IOUtils.print("""
						  [1] Lister les clients
						  [2] Lister les véhicules
						  [3] Lister les réservations
						  [4] Quitter le programme\
						  """);
		// @formatter:on

		int choice = IOUtils.readInt("Entrez votre choix : ");
		switch (choice) {
			case 1 -> clientInterface.listClients();
			case 2 -> vehicleInterface.listVehicles();
			case 3 -> reservationInterface.listReservations();
			case 4 -> {
				IOUtils.print("Au revoir !");
				quit = true;
			}
			default -> IOUtils.print("Option invalide.");
		}
	}

	public void displayCreateOptions() {
		// @formatter:off
		IOUtils.print("""
						  [1] Créer un client
						  [2] Créer un véhicule
						  [3] Créer une réservation
						  [4] Quitter le programme\
						  """);
		// @formatter:on

		int choice = IOUtils.readInt("Entrez votre choix : ");
		switch (choice) {
			case 1 -> clientInterface.createClient();
			case 2 -> vehicleInterface.createVehicle();
			case 3 -> reservationInterface.createReservation();
			case 4 -> {
				IOUtils.print("Au revoir !");
				quit = true;
			}
			default -> IOUtils.print("Option invalide.");
		}
	}

	public void displayDeleteOptions() {
		// @formatter:off
		IOUtils.print("""
						  [1] Supprimer un client
						  [2] Supprimer un véhicule
						  [3] Supprimer une réservation
						  [4] Quitter le programme\
						  """);
		// @formatter:on

		int choice = IOUtils.readInt("Entrez votre choix : ");
		switch (choice) {
			case 1 -> clientInterface.deleteClient();
			case 2 -> vehicleInterface.deleteVehicle();
			case 3 -> reservationInterface.deleteReservation();
			case 4 -> {
				IOUtils.print("Au revoir !");
				quit = true;
			}
			default -> IOUtils.print("Option invalide.");
		}
	}
}
