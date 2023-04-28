package com.epf.rentmanager.ui.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.exception.ValidationException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/users/edit")
public class UserEditServlet extends HttpServlet {
	@Autowired
	ClientService clientService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(HttpServletRequest request,
						 HttpServletResponse response) throws ServletException, IOException {
		int clientId = Integer.parseInt(request.getParameter("id"));
		try {
			request.setAttribute("client", this.clientService.findById(clientId));
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		this.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/users/edit.jsp")
				.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response) throws IOException, ServletException {
		int clientId = Integer.parseInt(request.getParameter("id"));
		request.setCharacterEncoding("UTF-8");

		Client client = new Client(clientId, request.getParameter("last_name"),
								   request.getParameter("first_name"),
								   request.getParameter("email"),
								   LocalDate.parse(request.getParameter("naissance")));
		try {
			clientService.edit(clientId, client);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			request.setAttribute("errorMessage", e.getMessage());
			request.setAttribute("client", client);
			this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/users/edit.jsp")
					.forward(request, response);
			return;
		}
		response.sendRedirect("/rentmanager/users");
	}
}
