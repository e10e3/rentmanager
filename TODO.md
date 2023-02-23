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
- [ ] Implement the `ClientDao` and `VehicleDao` classes.

Exercise 4
-----------

- [X] Create the `ServiceException` exception class.
- [X] Implement the `ClientService` and `VehicleService` classes.
    - [X] A client cannot be added with an empty first name or last name.
    - [X] A vehicle cannot be added with an empty constructor.
    - [X] A vehicle must have a least one seat.
    - [ ] A client’s last name should be set in UPPERCASE.

Exercise 5
-----------

- [X] Create the package `ui.cli`.
- The interface shall be able to:
    - [ ] create a client,
    - [ ] list all clients,
    - [ ] create a vehicle,
    - [ ] list all vehicle,
    - [ ] delete a client,
    - [ ] delete a vehicle.
- Tips:
    - [ ] a client should have a last name, a first name, an email address, and a brith date,
    - [ ] date and email address formats should be checked,
    - [ ] the `IOUtils` class in the *utils* package contains some tools.

Exercise 6
-----------

- [X] Implement the `Reservation` model class.
- [ ] Implement the `ReservationDao` class.
- [ ] Create the `ReservationService` class.
- [ ] Complete the interface with:
    - [ ] creating a reservation,
    - [ ] list all reservations.
