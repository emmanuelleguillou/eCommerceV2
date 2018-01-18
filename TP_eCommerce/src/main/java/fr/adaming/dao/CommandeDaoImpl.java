package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;


@Repository
public class CommandeDaoImpl implements ICommandeDao {

	@Autowired // Injection du collaborateur sf
	private SessionFactory sf;

	// Setter pour l'inkection de dependance
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public Commande addCommande(Commande c) {
		Session s= sf.getCurrentSession();
		s.save(c);
		return c;
	}

	@Override
	public Commande updateCommande(Commande c) {
		Session s= sf.getCurrentSession();
		s.saveOrUpdate(c);
		return c;
	}

	@Override
	public void deleteCommande(long idCommande) {
		Session s= sf.getCurrentSession();
		Commande cOut = (Commande) s.get(Commande.class, idCommande);
		s.delete(cOut);

	}

	@Override
	public Commande getCommandeByIdClNULL(long idCl) {
		Session s= sf.getCurrentSession();
		// construre la requête
		String req = "SELECT c FROM Commande AS c WHERE c.client.idClient IS NULL";

		// création du query
		Query query = s.createQuery(req);

		// création de la nouvelle liste des lignes commandes
		Commande cOut = (Commande) query.uniqueResult();

		return cOut;
	}

	@Override
	public List<Commande> gettAllCommande(long idCl) {
		Session s= sf.getCurrentSession();
		// construre la requête
		String req = "SELECT c FROM Commande AS c WHERE c.client.idClient=:idCl";

		// création du query
		Query query = s.createQuery(req);

		// Spécification des paramètres
		query.setParameter("idCl", idCl);

		// création de la nouvelle liste des lignes commandes
		List<Commande> listeCommande = (List<Commande>) query.uniqueResult();

		return listeCommande;
	}

	@Override
	public Commande getCommande(long idCommande) {
		Session s= sf.getCurrentSession();
		Commande cOut = (Commande) s.get(Commande.class, idCommande);
		
		return cOut;
	}

}
