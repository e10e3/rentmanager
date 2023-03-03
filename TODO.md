Items to do for completion
============================

Exercise 1
-----------

- [X] Create the project.
- [X] Set up a git repository.

Exercise 2
------------

- [X] Create the `Client`, `Vehicle` and `Reservation` model classes.
    - [X] Use `LocalDate` for dates.
    - [X] Create them a `toString` method.

Exercise 3
-----------

- [X] Create the `DaoException` exception class.
- [X] Implement the `ClientDao` and `VehicleDao` classes.

Exercise 4
-----------

- [X] Create the `ServiceException` exception class.
- [X] Implement the `ClientService` and `VehicleService` classes.
    - [X] A client cannot be added with an empty first name or last name.
    - [X] A vehicle cannot be added with an empty constructor.
    - [X] A vehicle must have a least one seat.
    - [X] A client’s last name should be set in UPPERCASE.

Exercise 5
-----------

- [X] Create the package `ui.cli`.
- The interface shall be able to:
    - [X] create a client,
    - [X] list all clients,
    - [X] create a vehicle,
    - [X] list all vehicles,
    - [X] delete a client,
    - [X] delete a vehicle.
- Tips:
    - [X] a client should have a last name, a first name, an email address, and a birth date,
    - [X] date and email address formats should be checked,
    - [X] the `IOUtils` class in the *utils* package contains some tools.

Exercise 6
-----------

- [X] Implement the `Reservation` model class.
- [X] Implement the `ReservationDao` class.
- [X] Create the `ReservationService` class.
- [X] Complete the interface with:
    - [X] creating a reservation,
    - [X] list all reservations,
    - [X] delete a reservation.

Web interface
--------------

- [X] Display the real counts on the dashboard.
- [X] Create the *list* pages.
- [ ] Create the *create* pages.
- [ ] Create the *details* pages.
- [ ] Create the *edit* pages.
- [ ] Create *delete* pages.