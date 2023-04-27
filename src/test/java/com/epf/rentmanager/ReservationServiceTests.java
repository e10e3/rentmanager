package com.epf.rentmanager;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.exception.ValidationException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ReservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTests {

	@InjectMocks
	private ReservationService reservationService;
	@Mock
	private ReservationDao reservationDao;

	@Test
	public void shouldFail_whenTwoReservationsOnSameDayForVehicle() throws DaoException {
		Client sampleClient = new Client(0, "Test", "test", "test@test.test", LocalDate.now());
		Vehicle sampleVehicle = new Vehicle(0L, "Test", "test", (short) 2);
		Reservation existingReservation = new Reservation(1000, sampleClient, sampleVehicle,
														  LocalDate.of(2023, 3, 10),
														  LocalDate.of(2023, 3, 20));
		Reservation newReservationBefore = new Reservation(1001, sampleClient, sampleVehicle,
														   LocalDate.of(2023, 3, 9),
														   LocalDate.of(2023, 3, 12));
		Reservation newReservationAfter = new Reservation(1002, sampleClient, sampleVehicle,
														  LocalDate.of(2023, 3, 19),
														  LocalDate.of(2023, 3, 22));
		when(this.reservationDao.findResaByVehicleId(isA(Long.class))).thenReturn(
				List.of(existingReservation));
		assertThrows(ValidationException.class,
					 () -> this.reservationService.create(newReservationBefore));
		assertThrows(ValidationException.class,
					 () -> this.reservationService.create(newReservationAfter));
	}

	@Test
	public void shouldFail_when30DaysOfConsecutiveReservationBefore() throws DaoException {
		Client sampleClient = new Client(0, "Test", "test", "test@test.test", LocalDate.now());
		Vehicle sampleVehicle = new Vehicle(0L, "Test", "test", (short) 2);
		List<Reservation> existingReservations = List.of(
				new Reservation(1000, sampleClient, sampleVehicle, LocalDate.of(2023, 3, 1),
								LocalDate.of(2023, 3, 20)),
				new Reservation(1001, sampleClient, sampleVehicle, LocalDate.of(2023, 3, 21),
								LocalDate.of(2023, 3, 31)));
		Reservation newReservation = new Reservation(1002, sampleClient, sampleVehicle,
													 LocalDate.of(2023, 4, 1),
													 LocalDate.of(2023, 4, 3));
		when(this.reservationDao.findResaByVehicleId(isA(Long.class))).thenReturn(
				existingReservations);
		assertThrows(ValidationException.class,
					 () -> this.reservationService.create(newReservation));
	}

	@Test
	public void shouldFail_when30DaysOfConsecutiveReservationAfter() throws DaoException {
		Client sampleClient = new Client(0, "Test", "test", "test@test.test", LocalDate.now());
		Vehicle sampleVehicle = new Vehicle(0L, "Test", "test", (short) 2);
		List<Reservation> existingReservations = List.of(
				new Reservation(1000, sampleClient, sampleVehicle, LocalDate.of(2023, 3, 10),
								LocalDate.of(2023, 3, 20)),
				new Reservation(1001, sampleClient, sampleVehicle, LocalDate.of(2023, 3, 21),
								LocalDate.of(2023, 4, 8)));
		Reservation newReservation = new Reservation(1002, sampleClient, sampleVehicle,
													 LocalDate.of(2023, 3, 6),
													 LocalDate.of(2023, 3, 9));
		when(this.reservationDao.findResaByVehicleId(isA(Long.class))).thenReturn(
				existingReservations);
		assertThrows(ValidationException.class,
					 () -> this.reservationService.create(newReservation));
	}

	@Test
	public void shouldFail_when30DaysOfConsecutiveReservationMiddle() throws DaoException {
		Client sampleClient = new Client(0, "Test", "test", "test@test.test", LocalDate.now());
		Vehicle sampleVehicle = new Vehicle(0L, "Test", "test", (short) 2);
		List<Reservation> existingReservations = List.of(
				new Reservation(1000, sampleClient, sampleVehicle, LocalDate.of(2023, 3, 10),
								LocalDate.of(2023, 3, 22)),
				new Reservation(1001, sampleClient, sampleVehicle, LocalDate.of(2023, 3, 28),
								LocalDate.of(2023, 4, 10)));
		Reservation newReservation = new Reservation(1002, sampleClient, sampleVehicle,
													 LocalDate.of(2023, 3, 23),
													 LocalDate.of(2023, 3, 27));
		when(this.reservationDao.findResaByVehicleId(isA(Long.class))).thenReturn(
				existingReservations);
		assertThrows(ValidationException.class,
					 () -> this.reservationService.create(newReservation));
	}

	@Test
	public void shouldFail_whenClientRentsMoreThan7Days() {
		Client sampleClient = new Client(0, "Test", "test", "test@test.test", LocalDate.now());
		Vehicle sampleVehicle = new Vehicle(0L, "Test", "test", (short) 2);
		Reservation newReservation = new Reservation(1000, sampleClient, sampleVehicle,
													 LocalDate.of(2023, 2, 1),
													 LocalDate.of(2023, 2, 20));
		assertThrows(ValidationException.class,
					 () -> this.reservationService.create(newReservation));
	}

	@Test
	public void shouldFail_whenClientRentsMoreThan7DaysAfter() throws DaoException {
		Client sampleClient = new Client(0, "Test", "test", "test@test.test", LocalDate.now());
		Vehicle sampleVehicle = new Vehicle(0L, "Test", "test", (short) 2);
		List<Reservation> existingReservations = List.of(
				new Reservation(1000, sampleClient, sampleVehicle, LocalDate.of(2023, 3, 10),
								LocalDate.of(2023, 3, 12)));
		Reservation newReservation = new Reservation(1001, sampleClient, sampleVehicle,
													 LocalDate.of(2023, 3, 13),
													 LocalDate.of(2023, 3, 18));
		when(this.reservationDao.findResaByVehicleId(isA(Long.class))).thenReturn(
				existingReservations);
		assertThrows(ValidationException.class,
					 () -> this.reservationService.create(newReservation));
	}

	@Test
	public void shouldFail_whenClientRentsMoreThan7DaysBefore() throws DaoException {
		Client sampleClient = new Client(0, "Test", "test", "test@test.test", LocalDate.now());
		Vehicle sampleVehicle = new Vehicle(0L, "Test", "test", (short) 2);
		List<Reservation> existingReservations = List.of(
				new Reservation(1000, sampleClient, sampleVehicle, LocalDate.of(2023, 3, 10),
								LocalDate.of(2023, 3, 12)));
		Reservation newReservation = new Reservation(1001, sampleClient, sampleVehicle,
													 LocalDate.of(2023, 3, 4),
													 LocalDate.of(2023, 3, 10));
		when(this.reservationDao.findResaByVehicleId(isA(Long.class))).thenReturn(
				existingReservations);
		assertThrows(ValidationException.class,
					 () -> this.reservationService.create(newReservation));
	}

	@Test
	public void shouldSucceed_whenValidReservation() throws DaoException, ValidationException, ServiceException {
		Client sampleClient = new Client(0, "Test", "test", "test@test.test", LocalDate.now());
		Vehicle sampleVehicle = new Vehicle(0L, "Test", "test", (short) 2);
		List<Reservation> existingReservations = List.of(
				new Reservation(1000, sampleClient, sampleVehicle, LocalDate.of(2023, 3, 10),
								LocalDate.of(2023, 3, 12)));
		Reservation newReservation = new Reservation(1001, sampleClient, sampleVehicle,
													 LocalDate.of(2023, 3, 1),
													 LocalDate.of(2023, 3, 4));
		when(this.reservationDao.findResaByVehicleId(isA(Long.class))).thenReturn(
				existingReservations);
		when(this.reservationDao.create(isA(Reservation.class))).thenReturn(123L);
		assertEquals(this.reservationService.create(newReservation), 123L);
	}

	@Test
	public void shouldSucceed_whenEditingExistingReservationWithoutChanges() throws DaoException, ValidationException, ServiceException {
		Client sampleClient = new Client(0, "Test", "test", "test@test.test", LocalDate.now());
		Vehicle sampleVehicle = new Vehicle(0L, "Test", "test", (short) 2);
		List<Reservation> existingReservations = List.of(
				new Reservation(1000, sampleClient, sampleVehicle, LocalDate.of(2023, 3, 10),
								LocalDate.of(2023, 3, 12)));
		Reservation editedReservation = new Reservation(1000, sampleClient, sampleVehicle,
														LocalDate.of(2023, 3, 10),
														LocalDate.of(2023, 3, 12));
		when(this.reservationDao.findResaByVehicleId(isA(Long.class))).thenReturn(
				existingReservations);
		this.reservationService.edit(existingReservations.get(0).identifier(), editedReservation);
	}
}
