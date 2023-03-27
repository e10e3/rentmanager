package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.exception.ValidationException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import java.util.List;

public class VehicleInterface {
	private final VehicleService vehicleService;

	public VehicleInterface(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	public void listVehicles() {
		try {
			for (Vehicle vehicle : vehicleService.findAll()) {
				IOUtils.print(vehicle.toString());
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public  void createVehicle() {
		IOUtils.print("Création d'un véhicule");
		String constructor = IOUtils.readString("Entrez le nom du contructeur : ", true);
		String model = IOUtils.readString("Entrez le modèle : ", false);
		int seatCount;
		do {
			seatCount = IOUtils.readInt("Entrez le nombre de sièges (1–200) : ");
		} while (seatCount < 1 || seatCount > 200);
		Vehicle veh = new Vehicle(0, constructor, model, (short) seatCount);
		try {
			long resId = vehicleService.create(veh);
			IOUtils.print("Véhicule créé avec l'identifiant " + resId);
		} catch (ServiceException e) {
			e.printStackTrace();
			IOUtils.print("Le véhicule n'a pas pu être créé.");
		} catch (ValidationException e) {
			e.printStackTrace();
			IOUtils.print(
					"Le véhicule %s est invalide : certaines propriétés sont invalides (%s)".formatted(
							veh, e.getMessage()));
		}
	}

	public  Vehicle selectVehicle() throws ServiceException {
		IOUtils.print("Sélectionner un véhicule");
		List<Vehicle> vehicleList = vehicleService.findAll();
		int index;
		do {
			for (int i = 0; i < vehicleList.size(); i++) {

				IOUtils.print(" [" + (i + 1) + "] " + vehicleList.get(i));
			}
			index = IOUtils.readInt("Entrez un indice : ");
		} while (index < 1 || index > vehicleList.size());

		return vehicleList.get(index - 1);
	}

	public  void deleteVehicle() {
		IOUtils.print("Supprimer un véhicule");
		try {
			long index = selectVehicle().getIdentifier();
			long deleted = vehicleService.delete(vehicleService.findById(index));
			assert deleted == index;

			IOUtils.print("Supprimé.");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
