package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Commande;


public interface ICommandeDao {

	public Commande addCommande(Commande c);
	
	public Commande updateCommande(Commande c);
	
	public void deleteCommande(long idCommande);
	
	public Commande getCommandeByIdClNULL(long idCl);
	
	public List<Commande> gettAllCommande(long idCl);
	
	public Commande getCommande(long idCommande);
	
	
}
