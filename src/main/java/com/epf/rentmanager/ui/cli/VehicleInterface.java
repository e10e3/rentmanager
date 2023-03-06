package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import java.util.List;

public class VehicleInterface {
	public static void listVehicles() {
		try {
			for (Vehicle vehicle : VehicleService.getInstance().findAll()) {
				IOUtils.print(vehicle.toString());
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public static void createVehicle() {
		IOUtils.print("Création d'un véhicule");
		Vehicle veh = new Vehicle();
		veh.setConstructor(IOUtils.readString("Entrez le nom du contructeur : ", true));
		veh.setModel(IOUtils.readString("Entrez le modèle : ", false));
		int seatCount;
		do {
			seatCount = IOUtils.readInt("Entrez le nombre de sièges (1–200) : ");
		} while (seatCount < 1 || seatCount > 200);
		veh.setSeatCount((short) seatCount);
		try {
			long resId = VehicleService.getInstance().create(veh);
			IOUtils.print("Véhicule créé avec l'identifiant " + resId);
		} catch (ServiceException e) {
			e.printStackTrace();
			IOUtils.print("Le véhicule n'a pas pu être créé.");
		}
	}

	public static Vehicle selectVehicle() throws ServiceException {
		IOUtils.print("Sélectionner un véhicule");
		List<Vehicle> vehicleList = VehicleService.getInstance().findAll();
		int index;
		do {
			for (int i = 0; i < vehicleList.size(); i++) {

				IOUtils.print(" [" + (i + 1) + "] " + vehicleList.get(i));
			}
			index = IOUtils.readInt("Entrez un indice : ");
		} while (index < 1 || index > vehicleList.size());

		return vehicleList.get(index - 1);
	}

	public static void deleteVehicle() {
		IOUtils.print("Supprimer un véhicule");
		try {
			long index = selectVehicle().getIdentifier();
			long deleted = VehicleService.getInstance().delete(VehicleService.getInstance().findById(index));
			assert deleted == index;

			IOUtils.print("Supprimé.");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
