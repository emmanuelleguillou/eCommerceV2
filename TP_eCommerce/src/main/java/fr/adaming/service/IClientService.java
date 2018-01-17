package fr.adaming.service;



import fr.adaming.model.Client;

public interface IClientService {

	public Client addClient(Client cl);

	public void deleteClient(long idCl);

	public Client getClientByNomEmail(String nom, String email);

	public Client updateClient(Client cl);

}
