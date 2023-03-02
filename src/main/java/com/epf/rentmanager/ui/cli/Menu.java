package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.utils.IOUtils;

public class Menu {
	private static boolean quit = false;

	public static void main(String[] args) {
		System.out.println("Bienvenue dans l'application.");
		while (!quit) {
			displayMenu();
		}
	}

	public static void displayMenu() {
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
		}
	}

	public static void displayListOptions() {
		IOUtils.print("""
                    [1] Lister les clients
                    [2] Lister les véhicules
                    [3] Lister les réservations
                    [4] Quitter le programme""");

		int choice = IOUtils.readInt("Entrez votre choix : ");
		switch (choice) {
			case 1 -> ClientInterface.listClients();
			case 2 -> VehicleInterface.listVehicles();
			case 3 -> ReservationInterface.listReservations();
			case 4 -> {
				IOUtils.print("Au revoir !");
				quit = true;
			}
		}
	}

	public static void displayCreateOptions() {
		IOUtils.print("""
                    [1] Créer un client
                    [2] Créer un véhicule
                    [3] Créer une réservation
                    [4] Quitter le programme""");

		int choice = IOUtils.readInt("Entrez votre choix : ");
		switch (choice) {
			case 1 -> ClientInterface.createClient();
			case 2 -> VehicleInterface.createVehicle();
			case 3 -> ReservationInterface.createReservation();
			case 4 -> {
				IOUtils.print("Au revoir !");
				quit = true;
			}
		}
	}

	public static void displayDeleteOptions() {
		IOUtils.print("""
                    [1] Supprimer un client
                    [2] Supprimer un véhicule
                    [3] Supprimer une réservation
                    [4] Quitter le programme""");

		int choice = IOUtils.readInt("Entrez votre choix : ");
		switch (choice) {
			case 1 -> ClientInterface.deleteClient();
			case 2 -> VehicleInterface.deleteVehicle();
			case 3 -> ReservationInterface.deleteReservation();
			case 4 -> {
				IOUtils.print("Au revoir !");
				quit = true;
			}
		}
	}
}
