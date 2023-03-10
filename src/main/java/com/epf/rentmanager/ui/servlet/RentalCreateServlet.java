package com.epf.rentmanager.ui.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/rents/create")
public class RentalCreateServlet extends HttpServlet {
	ReservationService reservationService;
	ClientService clientService;
	VehicleService vehicleService;

	public RentalCreateServlet(ReservationService reservationService, ClientService clientService, VehicleService vehicleService) {
		this.reservationService = reservationService;
		this.clientService = clientService;
		this.vehicleService = vehicleService;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("clients", this.clientService.findAll());
			request.setAttribute("vehicles", this.vehicleService.findAll());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		Reservation reservation = new Reservation();
		try {
			reservation.setRentedVehicle(vehicleService.findById(Integer.parseInt(request.getParameter("car"))));
			reservation.setRenterClient(clientService.findById(Integer.parseInt(request.getParameter("client"))));
			reservation.setStartDate(LocalDate.parse(request.getParameter("begin")));
			reservation.setEndDate(LocalDate.parse(request.getParameter("end")));
			reservationService.create(reservation);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		response.sendRedirect("/rentmanager/rents");
	}
}
