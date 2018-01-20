package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Produit;

@Repository
public class ProduitDaoImpl implements IProduitDao {

	@Autowired // Injection du collaborateur sf
	private SessionFactory sf;

	// Setter pour l'inkection de dependance
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public Produit addproduit(Produit p) {
		Session s = sf.getCurrentSession();
		s.save(p);
		return p;
	}

	@Override
	public Produit getProduit(int id_p) {
		Session s = sf.getCurrentSession();
		String req = "select p from Produit p where p.idProduit=:pId ";
		// Creer query
		Query query = s.createQuery(req);

		// passage des param
		query.setParameter("pId", id_p);
		try {
			return (Produit) query.uniqueResult();
		} catch (Exception e) {
			return null;

		}
	}

	@Override
	public void deleteProduit(int id_p) {
		Session s = sf.getCurrentSession();
		// creation de la requete jpql
		String req = "delete from Produit as p where p.idProduit=:pId";

		// Creer query
		Query query = s.createQuery(req);

		// passage des param
		query.setParameter("pId", id_p);

		query.executeUpdate();

	}

	@Override
	public Produit updateProduit(Produit p) {
		Session s = sf.getCurrentSession();
		s.saveOrUpdate(p);
		return p;
	}

	@Override
	public List<Produit> getAllPorduit() {
		Session s = sf.getCurrentSession();
		// Creation de la requete JPQL
		String req = "select p from Produit as p ";
		// Creer un query
		Query query = s.createQuery(req);

		// Envoyer la requete
		return query.list();
	}

	@Override
	public List<Produit> getAllPorduitByCategorie(int id_c) {
		Session s = sf.getCurrentSession();
		// Creation de la requete JPQL
		String req = "select p from Produit as p where categorie_idCategorie=:cId";
		System.out.println("ID categorie =" + id_c);
		// Creer un query
		Query query = s.createQuery(req);

		// passage des param
		query.setParameter("cId", id_c);

		// Envoyer la requete
		return query.list();
	}

	@Override
	public List<Produit> getSearchProduct(String req) {
		Session s = sf.getCurrentSession();
		// Creation de la requete JPQL
		String requete = "from Produit as p WHERE p.designation LIKE :searchKeyword";
		// Creer un query
		Query query = s.createQuery(requete);

		query.setParameter("searchKeyword", req + "%");
		// Envoyer la requete
		return query.list();
	}

}
