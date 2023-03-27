package com.epf.rentmanager;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.exception.ValidationException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTests {
	@InjectMocks
	private ClientService clientService;
	@Mock
	private ReservationService reservationService;
	@Mock
	private ClientDao clientDao;

	@Test
	public void create_should_fail_when_dao_create_throws_exception() throws DaoException {
		Client client = new Client(0, "lastName", "firstName", "email", LocalDate.MIN);
		when(this.clientDao.create(isA(Client.class))).thenThrow(DaoException.class);
		assertThrows(ServiceException.class, () -> clientService.create(client));
	}

	@Test
	public void delete_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {
		Client client = new Client();
		when(this.clientDao.delete(isA(Client.class))).thenThrow(DaoException.class);
		when(this.reservationService.delete(isA(Reservation.class))).thenReturn(0L);
		assertThrows(ServiceException.class, () -> clientService.delete(client));
	}

	@Test
	public void edit_should_fail_when_dao_throws_exception() throws DaoException {
		Client client = new Client(0, "lastName", "firstName", "email", LocalDate.now());
		doThrow(DaoException.class).when(clientDao).update(isA(Long.class), isA(Client.class));
		assertThrows(ServiceException.class, () -> clientService.edit(0, client));
	}

	@Test
	public void findById_should_fail_when_dao_throws_exception() throws DaoException {
		when(this.clientDao.findById(isA(Long.class))).thenThrow(DaoException.class);
		assertThrows(ServiceException.class, () -> clientService.findById(0));
	}

	@Test
	public void findAll_should_fail_when_dao_throws_exception() throws DaoException {
		when(this.clientDao.findAll()).thenThrow(DaoException.class);
		assertThrows(ServiceException.class, () -> clientService.findAll());
	}

	@Test
	public void count_should_fail_when_dao_throws_exception() throws DaoException {
		when(this.clientDao.count()).thenThrow(DaoException.class);
		assertThrows(ServiceException.class, () -> clientService.count());
	}

	@Test
	public void create_should_fail_with_younger_client() {
		Client client = new Client(0, "Test", "Test", "test", LocalDate.now());
		assertThrows(ValidationException.class, () -> clientService.create(client));
	}

	@Test
	public void create_should_fail_with_short_lastname() {
		Client client = new Client(0, "no", "Test", "test", LocalDate.of(2000, 1, 1));
		assertThrows(ValidationException.class, () -> clientService.create(client));
	}

	@Test
	public void create_should_fail_with_short_firstname() {
		Client client = new Client(0, "Test", "no", "test", LocalDate.of(2000, 1, 1));
		assertThrows(ValidationException.class, () -> clientService.create(client));
	}

	@Test
	public void create_should_fail_with_existing_email() throws DaoException {
		Client client = new Client(0, "Test", "Test", "test", LocalDate.of(2000, 1, 1));
		when(clientDao.isEmailUsed(isA(String.class))).thenReturn(true);
		assertThrows(ValidationException.class, () -> clientService.create(client));
	}

	@Test
	public void create_should_fail_with_null_client() throws DaoException {
		when(clientDao.isEmailUsed(isA(String.class))).thenReturn(true);
		assertThrows(ServiceException.class, () -> clientService.create(null));
	}

	@Test
	public void create_should_succeed_with_valid_client() throws DaoException, ServiceException, ValidationException {
		Client client = new Client(0, "Test", "Test", "test@test.test", LocalDate.of(2000, 1, 1));
		when(clientDao.isEmailUsed(isA(String.class))).thenReturn(false);
		when(clientDao.create(isA(Client.class))).thenReturn(0L);
		assertEquals(clientService.create(client), 0L);
	}

	@Test
	public void delete_should_fail_on_null_client() throws DaoException, ServiceException {
		when(reservationService.delete(isA(Reservation.class))).thenReturn(0L);
		when(clientDao.delete(isA(Client.class))).thenReturn(0L);
		assertThrows(ServiceException.class, () -> clientService.delete(null));
	}

	@Test
	public void delete_should_succeed() throws DaoException, ServiceException {
		Client client = new Client(0, "Test", "Test", "test@test.test", LocalDate.of(2000, 1, 1));
		when(reservationService.delete(isA(Reservation.class))).thenReturn(0L);
		when(clientDao.delete(isA(Client.class))).thenReturn(0L);
		assertEquals(clientService.create(client), 0L);
	}
}
