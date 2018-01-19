package fr.adaming.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;

@Repository
public class LigneCommandeDaoImpl implements ILigneCommandeDao {

	@Autowired // Injection du collaborateur sf
	private SessionFactory sf;

	// Setter pour l'inkection de dependance
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public LigneCommande addLigneCommande(LigneCommande lc) {
		Session s= sf.getCurrentSession();
		s.save(lc);
		return lc;
	}

	@Override
	public LigneCommande updateLigneCommande(LigneCommande lc) {
		Session s= sf.getCurrentSession();
		s.saveOrUpdate(lc);
		return lc;
	}

	@Override
	public void deleteLigneCommande(int idLigneCommande) {
		Session s= sf.getCurrentSession();
		LigneCommande lcOut = (LigneCommande) s.get(LigneCommande.class, idLigneCommande);
		s.delete(lcOut);

	}

	@Override
	public LigneCommande getLigneCommande(int idLigneCommande) {
		Session s= sf.getCurrentSession();
		LigneCommande lcOut = (LigneCommande) s.get(LigneCommande.class, idLigneCommande);
		return lcOut;

	}

	@Override
	public List<LigneCommande> getAllLigneCommandeByIdCommande(long idCommande) {
		Session s= sf.getCurrentSession();
		// construre la requête
		String req = "SELECT lc FROM LigneCommande AS lc WHERE lc.commande.idCommande=:idC";

		// création du query
		Query query = s.createQuery(req);

		// Spécification des paramètres
		query.setParameter("idC", idCommande);

		// création de la nouvelle liste des lignes commandes
		List<LigneCommande> listeLigneCommande = (List<LigneCommande>) query.list();

		return listeLigneCommande;
	}

	@Override
	public double calculPrixLigneCommande(LigneCommande lc, Produit p) {
		System.out.println("lc :" + lc + "\n" + "p : " + p);
		System.out.println("p.getprix : " + p.getPrix());
		double prixTotal = p.getPrix() * lc.getQuantite();
		System.out.println("prix :" + prixTotal);
		return prixTotal;
	}

	@Override
	public List<LigneCommande> getAllLignesCommandes() {
		Session s= sf.getCurrentSession();
		// construre la requête
		String req = "SELECT lc FROM LigneCommande AS lc WHERE lc.commande IS NULL";
		Query query = s.createQuery(req);
		System.out.println("query : " + query);

		// mettre les parametres
		// query.setParameter("idCommande", null);
		// System.out.println("query : " + query);
		System.out.println("liste avant evoyer liste : " + query.list());
		// création de la nouvelle liste des lignes commandes
		List<LigneCommande> listeLigneCommande = query.list();
		System.out.println("liste : " + listeLigneCommande);

		return listeLigneCommande;
	}

	@Override
	public double calculPrixToutesLignesCommandes(List<LigneCommande> listeLC) {
		double sommePrix=0;
		for (LigneCommande element : listeLC) {
			sommePrix += element.getPrix();
		}
		System.out.println("sommePrix -------------------------------" +sommePrix);
		
		return sommePrix;
	}

}
