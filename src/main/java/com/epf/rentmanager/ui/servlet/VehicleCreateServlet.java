package com.epf.rentmanager.ui.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.exception.ValidationException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {
	@Autowired
	VehicleService vehicleService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");

		Vehicle vehicle = new Vehicle(0, request.getParameter("manufacturer"),
									  request.getParameter("modele"),
									  Short.parseShort(request.getParameter("seats")));
		try {
			vehicleService.create(vehicle);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			request.setAttribute("errorMessage", e.getMessage());
			request.setAttribute("vehicle", vehicle);
			this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp")
					.forward(request, response);
			return;
		}
		response.sendRedirect("/rentmanager/cars");
	}
}
