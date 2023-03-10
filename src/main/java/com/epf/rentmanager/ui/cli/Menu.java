package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.utils.IOUtils;

public class Menu {
	private static boolean quit = false;
	private final ClientInterface clientInterface;
	private final VehicleInterface vehicleInterface;
	private final ReservationInterface reservationInterface;

	public Menu() {
		this.clientInterface = new ClientInterface();
		this.vehicleInterface = new VehicleInterface();
		this.reservationInterface = new ReservationInterface(clientInterface, vehicleInterface);
	}

	public void entryPoint() {
		IOUtils.print("Bienvenue dans l'application.");
		while (!quit) {
			displayMenu();
		}
	}

	public void displayMenu() {
		IOUtils.print("Que voulez-vous faire ?");
		IOUtils.print("""
                    [1] Lister des enregistrements
                    [2] Créer des enregistrements
                    [3] Supprimer des enregistrements
                    [4] Quitter le programme""");

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
		IOUtils.print("""
                    [1] Lister les clients
                    [2] Lister les véhicules
                    [3] Lister les réservations
                    [4] Quitter le programme""");

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
		IOUtils.print("""
                    [1] Créer un client
                    [2] Créer un véhicule
                    [3] Créer une réservation
                    [4] Quitter le programme""");

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
		IOUtils.print("""
                    [1] Supprimer un client
                    [2] Supprimer un véhicule
                    [3] Supprimer une réservation
                    [4] Quitter le programme""");

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
