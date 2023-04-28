package com.epf.rentmanager.ui.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.exception.ValidationException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/rents/edit")
public class RentalEditServlet extends HttpServlet {
	@Autowired
	ClientService clientService;
	@Autowired
	VehicleService vehicleService;
	@Autowired
	ReservationService reservationService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(HttpServletRequest request,
						 HttpServletResponse response) throws ServletException, IOException {
		long rentalId = Long.parseLong(request.getParameter("id"));
		try {
			request.setAttribute("clients", this.clientService.findAll());
			request.setAttribute("vehicles", this.vehicleService.findAll());
			request.setAttribute("rental", this.reservationService.findById(rentalId));
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		this.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/rents/edit.jsp")
				.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response) throws IOException, ServletException {
		long rentalId = Long.parseLong(request.getParameter("id"));
		request.setCharacterEncoding("UTF-8");

		Reservation reservation = null;
		try {
			reservation = new Reservation(rentalId, clientService.findById(
					Integer.parseInt(request.getParameter("client"))), vehicleService.findById(
					Integer.parseInt(request.getParameter("car"))), LocalDate.parse(
					request.getParameter("begin")), LocalDate.parse(request.getParameter("end")));
			reservationService.edit(rentalId, reservation);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			request.setAttribute("errorMessage", e.getMessage());
			request.setAttribute("rental", reservation);
			try {
				request.setAttribute("clients", this.clientService.findAll());
				request.setAttribute("vehicles", this.vehicleService.findAll());
			} catch (ServiceException ex) {
				e.printStackTrace();
			}
			this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/rents/create.jsp")
					.forward(request, response);
			return;
		}
		response.sendRedirect("/rentmanager/rents");
	}
}
