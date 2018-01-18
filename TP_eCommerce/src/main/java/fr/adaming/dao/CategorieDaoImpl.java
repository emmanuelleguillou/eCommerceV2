package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Categorie;


@Repository
public class CategorieDaoImpl implements ICategorieDao {
	@Autowired // Injection du collaborateur sf
	private SessionFactory sf;

	// Setter pour l'inkection de dependance
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	public Categorie addCategorie(Categorie c) {
		Session s= sf.getCurrentSession();
		s.save(c);
		return c;
	}

	@Override
	public Categorie getCategorie(int id_c) {
		Session s= sf.getCurrentSession();
		Categorie cOut = (Categorie) s.get(Categorie.class, id_c);
		return cOut;
	}

	@Override
	public void deleteCategorie(int id_c) {
		Session s= sf.getCurrentSession();
		// creation de la requete jpql
		String req = "delete from Categorie as c where c.idCategorie=:cId";

		// Creer query
		Query query = s.createQuery(req);

		// passage des param
		query.setParameter("cId", id_c);

		query.executeUpdate();
	}

	@Override
	public Categorie updateCategorie(Categorie c) {
		Session s= sf.getCurrentSession();
		s.saveOrUpdate(c);
		return c;
	}

	@Override
	public List<Categorie> getAllCategorie() {
		Session s= sf.getCurrentSession();
		// Creation de la requete JPQL
		String req = "select c from Categorie as c ";
		// Creer un query
		Query query = s.createQuery(req);

		// Envoyer la requete
		return query.list();
	}
}
