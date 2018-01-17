package fr.adaming.dao;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Administrateur;


@Repository
public class AdministrateurDaoImpl implements IAdministrateurDao {
	
	@Autowired // Injection du collaborateur sf
	private SessionFactory sf;

	// Setter pour l'inkection de dependance
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}


	@Override
	public Administrateur isExist(Administrateur a) {
		Session s= sf.getCurrentSession();
		// Construire la requete JPQL
		String req = "select a from Administrateur a where a.mail=:pMail and a.mdp=:pMdp ";
		// Creer un query
		Query query = s.createQuery(req);
		// Parametrer le query
		query.setParameter("pMail", a.getMail());
		query.setParameter("pMdp", a.getMdp());

		// Envoyer la requete
		Administrateur aOut = (Administrateur) query.uniqueResult();

		return aOut;
	}

	

}
