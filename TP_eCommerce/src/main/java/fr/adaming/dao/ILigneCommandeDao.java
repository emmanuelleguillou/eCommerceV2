package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;

public interface ILigneCommandeDao {

	public LigneCommande addLigneCommande(LigneCommande lc);
	
	public LigneCommande updateLigneCommande(LigneCommande lc);
	
	public void deleteLigneCommande(int idLigneCommande);
	
	public LigneCommande getLigneCommande(int idLigneCommande);
	
	public List<LigneCommande> getAllLigneCommandeByIdCommande(long idCommande);
	
	public double calculPrixLigneCommande(LigneCommande lc, Produit p);
	
	public List<LigneCommande> getAllLignesCommandes();
	
	public double calculPrixToutesLignesCommandes(List<LigneCommande> listeLC);
	
}
