package com.epf.rentmanager.ui.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/users/create")
public class UserCreateServlet extends HttpServlet {
	ClientService clientService;

	public UserCreateServlet(ClientService clientService) {
		this.clientService = clientService;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		Client client = new Client();
		client.setLastName(request.getParameter("last_name"));
		client.setFirstName(request.getParameter("first_name"));
		client.setEmailAddress(request.getParameter("email"));
		client.setBirthDate(LocalDate.parse(request.getParameter("naissance")));
		try {
			clientService.create(client);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		response.sendRedirect("/rentmanager/users");
	}
}
