package fr.adaming.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Client;

@Repository
public class ClientDaoImpl implements IClientDao {

	@Autowired // Injection du collaborateur sf
	private SessionFactory sf;

	// Setter pour l'inkection de dependance
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	// Les méthodes
	@Override
	public Client addClient(Client cl) {
		Session s= sf.getCurrentSession();
		s.save(cl);
		return cl;
	}

	@Override
	public void deleteClient(long idCl) {
		Session s= sf.getCurrentSession();
		Client clOut = (Client) s.get(Client.class, idCl);
		s.delete(clOut);
	}

	@Override
	public Client getClientByNomEmail(String nom, String email) {
		Session s= sf.getCurrentSession();
		// création de la requete
		String req = "SELECT cl FROM Client AS cl WHERE cl.nomClient=:nomCl AND cl.email=:emailCl";

		// création du query
		Query query = s.createQuery(req);

		// Spécification des paramètres
		query.setParameter("nomCl", nom);
		query.setParameter("emailCl", email);

		// obtention du client en question
		Client clOut = (Client) query.uniqueResult();
		return clOut;
	}

	@Override
	public Client updateClient(Client cl) {
		Session s= sf.getCurrentSession();
		s.update(cl);
		return cl;
	}

}
