package com.epf.rentmanager.ui.servlet;

import com.epf.rentmanager.exception.ServiceException;
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

@WebServlet("/cars/details")
public class VehicleDetailsServlet extends HttpServlet {
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
		int vehicleId = Integer.parseInt(request.getParameter("id"));
		try {
			request.setAttribute("vehicle", this.vehicleService.findById(vehicleId));
			request.setAttribute("reservations",
								 this.reservationService.findResaByVehicleId(vehicleId));
			request.setAttribute("renterClients", this.reservationService.findRenterClientByVehicleId(vehicleId));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		this.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp")
				.forward(request, response);
	}
}
