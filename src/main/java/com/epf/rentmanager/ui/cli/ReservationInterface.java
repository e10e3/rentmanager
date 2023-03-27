package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.exception.ValidationException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;
import java.util.List;

public class ReservationInterface {
	private final ReservationService reservationService;
	private final ClientInterface clientInterface;
	private final VehicleInterface vehicleInterface;

	public ReservationInterface(ReservationService reservationService,
	                            ClientInterface clientInterface,
	                            VehicleInterface vehicleInterface) {
		this.reservationService = reservationService;
		this.clientInterface = clientInterface;
		this.vehicleInterface = vehicleInterface;
	}

	public void listReservations() {
		try {
			for (Reservation reservation : reservationService.findAll()) {
				IOUtils.print(reservation.toString());
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public void createReservation() {
		IOUtils.print("Création d'une réservation");
		try {
			Client client = clientInterface.selectClient();
			Vehicle vehicle = vehicleInterface.selectVehicle();
			LocalDate startDate = IOUtils.readDate("Entrez une date de début de réservation : ",
												   true);
			LocalDate endDate;
			do {
				endDate = IOUtils.readDate("Entrez une date de fin de réservation : ", true);
			} while (endDate.isBefore(startDate));
			Reservation res = new Reservation(0, client, vehicle, startDate, endDate);
			long resId = reservationService.create(res);
			IOUtils.print("La réservation a été créée avec l'identifiant " + resId);
		} catch (ServiceException e) {
			e.printStackTrace();
			IOUtils.print("La réservation n'a pas pu être créée.");
		} catch (ValidationException e) {
			e.printStackTrace();
			IOUtils.print(
					"La réservation est invalide : certaines propriétés sont invalides (%s)".formatted(
							e.getMessage()));
		}
	}

	public Reservation selectReservation() throws ServiceException {
		List<Reservation> reservationList = reservationService.findAll();
		int index;
		do {
			for (int i = 0; i < reservationList.size(); i++) {

				IOUtils.print(" [" + (i + 1) + "] " + reservationList.get(i));
			}
			index = IOUtils.readInt("Entrez un indice : ");
		} while (index < 1 || index > reservationList.size());

		return reservationList.get(index - 1);
	}

	public void deleteReservation() {
		IOUtils.print("Supprimer une réservation");
		try {
			long index = selectReservation().identifier();
			long deleted = reservationService.delete(reservationService.findById(index));
			assert deleted == index;

			IOUtils.print("Supprimé.");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
