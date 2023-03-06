package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;
import java.util.List;

public class ReservationInterface {
	public static void listReservations() {
		try {
			for (Reservation reservation : ReservationService.getInstance().findAll()) {
				IOUtils.print(reservation.toString());
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public static void createReservation() {
		IOUtils.print("Création d'une réservation");
		Reservation res = new Reservation();
		try {
			res.setRenterClient(ClientInterface.selectClient());
			res.setRentedVehicle(VehicleInterface.selectVehicle());
			res.setStartDate(IOUtils.readDate("Entrez une date de début de réservation : ", true));
			LocalDate end;
			do {
				end = IOUtils.readDate("Entrez une date de fin de réservation : ", true);
			} while (end.isBefore(res.getStartDate()));
			res.setEndDate(end);
			long resId = ReservationService.getInstance().create(res);
			IOUtils.print("La réservation a été créée avec l'identifiant " + resId);
		} catch (ServiceException e) {
			e.printStackTrace();
			IOUtils.print("La réservation n'a pas pu être créée.");
		}
	}

	public static Reservation selectReservation() throws ServiceException {
		List<Reservation> reservationList = ReservationService.getInstance().findAll();
		int index;
		do {
			for (int i = 0; i < reservationList.size(); i++) {

				IOUtils.print(" [" + (i + 1) + "] " + reservationList.get(i));
			}
			index = IOUtils.readInt("Entrez un indice : ");
		} while (index < 1 || index > reservationList.size());

		return reservationList.get(index - 1);
	}

	public static void deleteReservation() {
		IOUtils.print("Supprimer une réservation");
		try {
			long index = selectReservation().getIdentifier();
			long deleted = ReservationService.getInstance().delete(ReservationService.getInstance().findById(index));
			assert deleted == index;

			IOUtils.print("Supprimé.");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
