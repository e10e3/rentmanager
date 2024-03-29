package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.exception.ValidationException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Local utility class representing a period between two dates.
 *
 * @param start The start of the range
 * @param end   The end of the range
 */
record DateRange(LocalDate start, LocalDate end) {
	DateRange {
		Objects.requireNonNull(start);
		Objects.requireNonNull(end);
		if (start.isAfter(end)) {
			throw new IllegalArgumentException("End must be after start.");
		}
	}

	/**
	 * Tests if a date comes immediately after the range.
	 *
	 * @param date The date to test
	 * @return If the date immediately follows the range.
	 */
	public boolean isContiguouslyFollowedBy(LocalDate date) {
		return date.isAfter(this.end) && Period.between(this.end, date).getDays() <= 1;
	}

	/**
	 * Determines if a date is included in the range, limits included.
	 *
	 * @param date The date to test
	 * @return If the date is in the range.
	 */
	public boolean contains(LocalDate date) {
		return this.start.compareTo(date) < 1 && this.end.compareTo(date) > -1;
	}

	public long durationInDays() {
		return ChronoUnit.DAYS.between(this.start, this.end);
	}
}

@Repository
public class ReservationService {
	private final ReservationDao reservationDao;
	private static final int MAX_DAYS_CONTIGUOUS_RENTAL = 30;
	private static final int MAX_DAYS_CLIENT_RENTAL = 7;

	private ReservationService(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}

	public long create(Reservation reservation) throws ServiceException, ValidationException {
		isValid(reservation);
		try {
			return reservationDao.create(reservation);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	private void isValid(Reservation checkedReservation) throws ValidationException {
		if (checkedReservation == null) {
			throw new ValidationException("Reservation cannot be null");
		}

		/* Low-hanging fruits */
		if (checkedReservation.startDate().isAfter(checkedReservation.endDate())) {
			throw new ValidationException("End must be after start.");
		}
		if (ChronoUnit.DAYS.between(checkedReservation.startDate(), checkedReservation.endDate()) >
			MAX_DAYS_CLIENT_RENTAL) {
			throw new ValidationException(
					"A client cannot rent a vehicle for more than " + MAX_DAYS_CLIENT_RENTAL +
					" days.");
		}
		try {
			ArrayList<Reservation> sameVehicleReservations = new ArrayList<>(
					this.findResaByVehicleId(checkedReservation.rentedVehicle().identifier())
							.stream()
							.filter(r -> (r.identifier() != checkedReservation.identifier()))
							.toList());
			if (sameVehicleReservations.size() == 0) {
				return;
			}

			/* ---------------------- */
			if (sameVehicleReservations.stream()
					.filter(r -> r.startDate().compareTo(checkedReservation.startDate()) < 1)
					.anyMatch(r -> r.endDate().compareTo(checkedReservation.startDate()) > -1)) {
				throw new ValidationException(
						"This reservation overlaps with an existing reservation for this vehicle.");
			}
			if (sameVehicleReservations.stream()
					.filter(r -> r.startDate().compareTo(checkedReservation.endDate()) < 1)
					.anyMatch(r -> r.endDate().compareTo(checkedReservation.endDate()) > -1)) {
				throw new ValidationException(
						"This reservation overlaps with an existing reservation for this vehicle.");
			}

			/* ---------------------- */
			sameVehicleReservations.add(checkedReservation);
			var sortedVehicleReservations = sameVehicleReservations.stream()
					.sorted(Comparator.comparing(Reservation::startDate))
					.toList();
			LinkedList<DateRange> periodList = new LinkedList<>();
			for (Reservation res : sortedVehicleReservations) {
				if (periodList.isEmpty()) {
					periodList.add(new DateRange(res.startDate(), res.endDate()));
					continue;
				}
				var lastElem = periodList.getLast();
				if (lastElem.isContiguouslyFollowedBy(res.startDate())) {
					periodList.removeLast();
					periodList.addLast(new DateRange(lastElem.start(), res.endDate()));
				} else {
					periodList.addLast(new DateRange(res.startDate(), res.endDate()));
				}
			}
			for (DateRange dRange : periodList) {
				if (dRange.contains(checkedReservation.startDate()) &&
					dRange.contains(checkedReservation.endDate())) {
					if (dRange.durationInDays() >= MAX_DAYS_CONTIGUOUS_RENTAL) {
						throw new ValidationException(
								"A vehicle cannot be rented for " + MAX_DAYS_CONTIGUOUS_RENTAL +
								" days or more in a row.");
					}
					break;
				}
			}

			/* ---------------------- */
			var sortedVehicleReservationsWithClient = sameVehicleReservations.stream()
					.filter(r -> r.renterClient() == checkedReservation.renterClient())
					.sorted(Comparator.comparing(Reservation::startDate))
					.toList();

			LinkedList<DateRange> periodListClient = new LinkedList<>();
			for (Reservation res : sortedVehicleReservationsWithClient) {
				if (periodListClient.isEmpty()) {
					periodListClient.add(new DateRange(res.startDate(), res.endDate()));
					continue;
				}
				var lastElem = periodListClient.getLast();
				if (lastElem.isContiguouslyFollowedBy(res.startDate())) {
					periodListClient.removeLast();
					periodListClient.addLast(new DateRange(lastElem.start(), res.endDate()));
				} else {
					periodListClient.addLast(new DateRange(res.startDate(), res.endDate()));
				}
			}
			for (DateRange dRange : periodListClient) {
				if (dRange.contains(checkedReservation.startDate()) &&
					dRange.contains(checkedReservation.endDate())) {
					if (dRange.durationInDays() > MAX_DAYS_CLIENT_RENTAL) {
						throw new ValidationException(
								"A client cannot rent a vehicle for more than " +
								MAX_DAYS_CLIENT_RENTAL + " days.");
					}
					break;
				}
			}
		} catch (ServiceException e) {
			throw new ValidationException(e);
		}
	}

	public long delete(Reservation reservation) throws ServiceException {
		try {
			return reservationDao.delete(reservation);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public void edit(long id, Reservation newData) throws ServiceException, ValidationException {
		isValid(newData);
		try {
			reservationDao.update(id, newData);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public List<Reservation> findResaByClientId(long clientId) throws ServiceException {
		try {
			return reservationDao.findResaByClientId(clientId);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public List<Reservation> findResaByVehicleId(long vehicleId) throws ServiceException {
		try {
			return reservationDao.findResaByVehicleId(vehicleId);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public List<Vehicle> findRentedVehiclesByClientId(long clientId) throws ServiceException {
		try {
			return reservationDao.findRentedVehiclesByClientId(clientId);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public List<Client> findRenterClientByVehicleId(long vehicleId) throws ServiceException {
		try {
			return reservationDao.findRenterClientByVehicleId(vehicleId);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public Reservation findById(long id) throws ServiceException {
		try {
			return reservationDao.findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public List<Reservation> findAll() throws ServiceException {
		// fix this
		try {
			return reservationDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public int count() throws ServiceException {
		try {
			return reservationDao.count();
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
}
