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
    - [X] A vehicle must have at least one seat.
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
    - [X] a client should have a last name, a first name, an email address, and
a date of birth,
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
- [X] Create the *create* pages.
- [X] Create the *details* pages.
- [X] Create the *edit* pages.
- [X] Create the *delete* pages.

Constrains
------------

- [X] A client must be at least 18 years old.
- [X] Two different clients cannot have the same email address.
- [X] The last name and first name of a client must each be at least three
characters long.
- [X] A vehicle cannot have two reservations on the same day.
- [X] A vehicle cannot be booked more than seven days in a row by the same
customer.
- [X] A vehicle cannot be booked 30 days in a row without a pause.
- [X] If a customer or a vehicle is deleted, all associated reservations must be
deleted as well.
- [X] A vehicle must have a manufacturer, a model, and a seat count between 2 
and 9.
- [ ] Add a validation error message in the web interface.

Style
------

- [ ] Use optionals where relevant
- [X] Use records for model classes