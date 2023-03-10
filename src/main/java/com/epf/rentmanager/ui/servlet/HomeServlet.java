package com.epf.rentmanager.ui.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	@Serial
	private static final long serialVersionUID = 1L;

	ClientService clientService;
	VehicleService vehicleService;
	ReservationService reservationService;

	public HomeServlet(ClientService clientService, VehicleService vehicleService, ReservationService reservationService) {
		this.clientService = clientService;
		this.vehicleService = vehicleService;
		this.reservationService = reservationService;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("clientCount", clientService.count());
			request.setAttribute("vehicleCount", vehicleService.count());
			request.setAttribute("reservationCount", reservationService.count());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

}
