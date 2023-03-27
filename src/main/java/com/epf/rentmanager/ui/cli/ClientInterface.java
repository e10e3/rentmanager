package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.exception.ValidationException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;
import java.util.List;

public class ClientInterface {
	private final ClientService clientService;

	public ClientInterface(ClientService clientService) {
		this.clientService = clientService;
	}

	public void listClients() {
		try {
			for (Client client : clientService.findAll()) {
				IOUtils.print(client.toString());
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public void createClient() {
		IOUtils.print("Création d'un client");
		String lastName = IOUtils.readString("Entrez le nom : ", true).toUpperCase();
		String firstName = IOUtils.readString("Entrez le prénom", true);
		LocalDate birthDate = IOUtils.readDate("Entre la date de naissance (jj/mm/aaaa) : ", true);
		String email;
		do {
			email = IOUtils.readString("Entrez l'adresse courriel : ", true);
		} while (!email.matches("^(.+)@(\\S+)$"));
		Client cli = new Client(0, lastName, firstName, email, birthDate);
		try {
			long resId = clientService.create(cli);
			IOUtils.print("Le client a été créé avec l'identifiant " + resId);
		} catch (ServiceException e) {
			e.printStackTrace();
			IOUtils.print("Le client n'a pas pu être créé.");
		} catch (ValidationException e) {
			e.printStackTrace();
			IOUtils.print(
					"Le client %s n'a pas pu être validé : certaines propriétés sont invalides (%s).".formatted(
							cli, e.getMessage()));
		}
	}

	public Client selectClient() throws ServiceException {
		IOUtils.print("Sélectionner un client");
		List<Client> clientList = clientService.findAll();
		int index;
		do {
			for (int i = 0; i < clientList.size(); i++) {
				IOUtils.print(" [" + (i + 1) + "] " + clientList.get(i));
			}
			index = IOUtils.readInt("Entrez un indice : ");
		} while (index < 1 || index > clientList.size());

		return clientList.get(index - 1);
	}

	public void deleteClient() {
		IOUtils.print("Supprimer un client");
		try {
			long index = selectClient().getIdentifier();
			long deleted = clientService.delete(clientService.findById(index));
			assert deleted == index;

			IOUtils.print("Supprimé.");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
